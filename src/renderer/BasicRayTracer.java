package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

/**
 * A class whose job is to calculate the color of the dot and uses global and local effects calculations including shadow consideration
 */
public class BasicRayTracer extends RayTracerBase {
    /**
     * constructor
     * @param scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Begins the ray close to the body and not the body itself (so as not to create intersection error)
     */
    private static final double DELTA = 0.1;
    /**
     * initial of attenuation
     */
    private static final double INITIAL_K = 1.0;
    /**
     * The maximum number of times the recursion calculation will be performed
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * The minimum value for which we will do the color calculation
     */
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * the amount of rays in the beam for soft shadow
     */
    private int _amountOfRaysForSoftShadow = 1;
    /**
     * the radius of the point/spot light source, we using it when we calculate the shadow rays
     */
    private double _radiusOfLightSource = 1;
    /**
     * A function that calculates the pixel color that the beam crosses
     * @param ray the ray from the camera through the pixel to the body
     * @return the color of  the pixel
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? _scene.background : calcColor(closestPoint, ray);
    }

    /**
     * @param closestPoint geopoint on shape
     * @param ray          the ray from the geoPoint to the light
     * @return the color that the point will be painted
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray) {
        return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientlight.getIntensity());
    }

    /**
     * help function to calcColor up
     *
     * @param intersection geo point
     * @param ray          the ray from the intersection point to the light
     * @param level        for brake recurs condition
     * @param k            attenuation
     * @return the color that the point will be painted
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDirection(), level, k));
    }

    /**
     * Calculate the color at the point after calculating transparency / reflection
     * @param gp the point
     * @param v vector from the camera
     * @param level for brake recurs condition
     * @param k attenuation
     * @return Returns the color at the point after calculating transparency / reflection
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        //kkr = kr * k
        double kkr = k * material.Kr;   // Reduces the reflection by doubling the attenuation factor
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.Kr, kkr);
        double kkt = k * material.Kt; // Reduces transparency by doubling the attenuation factor
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.Kt, kkt));
        return color;
    }

    /**
     * Calculate the color at the point after calculating transparency / reflection
     * @param ray Reflected/Refracted
     * @param level for brake recurs condition
     * @param kx kt/kr
     * @param kkx kkt/kkr Attenuation factor that Small in every iteration
     * @return Returns the color at the point after calculating transparency / reflection
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? _scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * Calculates the effects at a point
     * @param geoPoint the point
     * @param ray ray from the camera
     * @param k attenuation
     * @return the effects at a point
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, double k) {
        Vector v = ray.getDirection();  // direction of the camera view
        Vector n = geoPoint.geometry.getNormal(geoPoint.point); // the normal in the point
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {  // the camera don't see the point
            return Color.BLACK;
        }
        Material material = geoPoint.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.Kd;
        double ks = material.Ks;
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if (unshaded(lightSource, l, n, geoPoint)) {    // if this light source don't make shadow
                    double ktr = transparency(lightSource, l, n, geoPoint); // shkifut
                    if (ktr * k > MIN_CALC_COLOR_K) {
                        Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                        // calc The Phong Reflectance Model in the point
                        color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                                calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                    }
                }
            }
        }
        return color;
    }

    /**
     * help function for calculating pong model
     * Returns the value of the specular
     * @param ks value of the material Specular
     * @param l vector from the light source
     * @param n normal to the point
     * @param v vector of Camera direction
     * @param nShininess
     * @param lightIntensity
     * @return the specular value
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));    // reflectance vector
        double vrMinus = v.scale(-1).dotProduct(r);
        double vrn = Math.pow(vrMinus, nShininess);
        return lightIntensity.scale(ks * vrn);
    }

    /**
     * help function for calculating pong model
     * @param kd             of the material
     * @param l vector from the light source
     * @param n normal to the point
     * @param lightIntensity the color of the light
     * @return the diffusive affect of the light
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln = Math.abs(l.dotProduct(n));
        return lightIntensity.scale(kd * ln);
    }

    /**
     * Checks if the point is shaded or not
     * @param ls type of light source
     * @param l vector from the light source
     * @param n normal to the point
     * @param geoPoint the point
     * @return True if the point is not shaded
     */
    private boolean unshaded(LightSource ls, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1);    // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);  //check if the dot prodact -/+
        Point3D point = geoPoint.point.add(delta);  // move the point by delta
        Ray lightRay = new Ray(point, lightDirection, n);   // new ray from the geoPoint to the light source
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return true; //if is intersection "intersections" not null
        double lightDistance = ls.getDistance(geoPoint.point);
        for (GeoPoint gp : intersections) {
            // Checks for each point of intersection, if it is between the body and the light, and if is Non-transparent
            // - returns a false (shaded)
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0 &&
                    gp.geometry.getMaterial().Kt == 0)
                return false;
        }
        return true;    //return true if have geoPoint between the body and the light, and is transparent
    }

    /**
     * Calculates the transparency
     * @param ls the type of light source
     * @param l vector from the light source
     * @param n Normal vector to point
     * @param geoPoint point on the geometry
     * @return ktr value
     */
    private double transparency1(LightSource ls, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);  // new ray from the geoPoint to the light source
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay);// if is intersection "intersections" not null
        double lightDistance = ls.getDistance(geoPoint.point);
        if (intersections == null)  // no intersection between the body and the light
            return 1d;
        double ktr = 1.0;
        for (GeoPoint gp : intersections) { // For each point of intersection we become more opaque (opaque == atimut)
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0)
                ktr *= gp.geometry.getMaterial().Kt;    // if something in middle ktr*this.kt
            if (ktr < MIN_CALC_COLOR_K) // If it is below the minimum then returns 0
                return 0.0;
        }
        return ktr;
    }

    /**
     * Calculate the reflection
     * @param point the point
     * @param v vector from the camera
     * @param n vector normal to the point
     * @return r vector (Reflected)
     */
    private Ray constructReflectedRay(Point3D point, Vector v, Vector n) {
        return new Ray(point, v.subtract(n.scale(2 * v.dotProduct(n))), n);
    }

    /**
     * Calculate the Refracted
     * @param point the point
     * @param v vector from the camera
     * @param n vector normal to the point
     * @return A new ray that continues the existing ray through the geometry
     */
    private Ray constructRefractedRay(Point3D point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /**
     * @param ray from the camera
     * @return the closest point of intersection of the ray
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        if (ray == null)
            return null;

        GeoPoint closesPoint = null;
        double colsesDistance = Double.MAX_VALUE;
        Point3D ray_p0 = ray.getPoint();
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return null;

        for (var geoPoint : intersections) {
            double distance = ray_p0.distance(geoPoint.point);
            if (distance < colsesDistance) {
                colsesDistance = distance;
                closesPoint = geoPoint;
            }
        }
        return closesPoint;
    }

    /**
     * Calculate the transparency of the shadow in a point
     *
     * @param ls light source
     * @param l vector from the light to the point
     * @param n normal
     * @param geopoint the point we want to calculate the shadow
     * @return the transparency of the shadow in a point
     */
    private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        double ktrAll = 0.0, ktrMain;
        Ray lightRay = new Ray(geopoint.point, lightDirection, n); // new ray from the geoPoint to the light source
        ktrMain = getKtr(ls, geopoint, lightRay);
        if (_amountOfRaysForSoftShadow > 1) {
            Beam beam = new Beam(lightRay,//the main ray
                    geopoint.point.add(lightDirection.scale(ls.getDistance(geopoint.point))),//the location of the light
                    _radiusOfLightSource,//the radius of the light source
                    _amountOfRaysForSoftShadow);//amount of shadow rays to create
            for (int i = 1; i < beam._rayList.size(); i++) {
                ktrAll += getKtr(ls, geopoint, beam._rayList.get(i));
            }
            ktrMain = (ktrAll) / beam._rayList.size();
        }
        return ktrMain;
    }

    /**
     * return the value of the attenuation of the shadow
     * @param ls the light source
     * @param geopoint the point we want to calculate the shadow in
     * @param lightRay the light ray
     * @return attenuation of the shadow in the given point
     */
    private double getKtr(LightSource ls, GeoPoint geopoint, Ray lightRay) {
        double ktr = 1;
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay);
        if (intersections != null) {
            double lightDistance = ls.getDistance(geopoint.point);
            for (GeoPoint gp : intersections) { // For each point of intersection we become more opaque (opaque == atimut)
                if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
                    ktr *= gp.geometry.getMaterial().Kt; // if something in middle ktr*this.kt
                    if (ktr < MIN_CALC_COLOR_K) {// If it is below the minimum then returns 0
                        return 0.0;
                    }
                }
            }
        }
        return ktr;
    }


    /**
     * set the amount of ray to create in the beam for the soft shadow,1 or less to disable
     * @param amountOfRaysForSoftShadow the amount of rays
     */
    public BasicRayTracer setAmountOfRaysForSoftShadow(int amountOfRaysForSoftShadow) {
        if (amountOfRaysForSoftShadow < 1)
            amountOfRaysForSoftShadow = 1;
        _amountOfRaysForSoftShadow = amountOfRaysForSoftShadow;
        return this;
    }

    /**
     * set the radius of the lights sources in the scene
     * @param radiusOfLights the radius
     */
    public BasicRayTracer setRadiusOfLightSource(double radiusOfLights) {
        _radiusOfLightSource = radiusOfLights;
        return this;
    }


}
