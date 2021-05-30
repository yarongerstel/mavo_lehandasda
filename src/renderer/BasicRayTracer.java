package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

/**
 * todo
 */
public class BasicRayTracer extends RayTracerBase {
    /**
     * todo
     * @param scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Begins the beam close to the body and not the body itself (so as not to create intersection error)
     */
    private static final double DELTA = 0.1;
    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

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
     * @param intersection geopoint
     * @param ray          the ray from the intersection point to the light
     * @param level        of recorsia
     * @param k            for bracek recurs condition
     * @return the color that the point will be painted
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission();
        color = color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDirection(), level, k));
    }

    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        //kkr = k*kr
        double kkr = k * material.Kr;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.Kr, kkr);
        double kkt = k * material.Kt;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.Kt, kkt));
        return color;
    }

    /**
     *
     * @param ray
     * @param level
     * @param kx
     * @param kkx 
     * @return
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? _scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }


    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, double k) {
        Vector v = ray.getDirection();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
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
                if (unshaded(lightSource, l, n, geoPoint)) {
                    double ktr = transparency(lightSource, l, n, geoPoint);
                    if (ktr * k > MIN_CALC_COLOR_K) {
                        Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                        color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                                calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                    }
                }
            }
        }
        return color;
    }

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        double vrMinus = v.scale(-1).dotProduct(r);
        double vrn = Math.pow(vrMinus, nShininess);
        return lightIntensity.scale(ks * vrn);
    }

    /**
     * @param kd             of the material
     * @param l
     * @param n
     * @param lightIntensity the color of the light
     * @return the diffusive affect of the light
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln = Math.abs(l.dotProduct(n));
        return lightIntensity.scale(kd * ln);
    }


    private boolean unshaded(LightSource ls, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);//check if the dot prodact -/+
        Point3D point = geoPoint.point.add(delta);
        Ray lightRay = new Ray(point, lightDirection, n);
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay);//if is intersection "intersections" not null
        if (intersections == null) return true;
        double lightDistance = ls.getDistance(geoPoint.point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0 &&
                    gp.geometry.getMaterial().Kt == 0)
                return false;
        }
        return true;
    }

    private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
//        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);//check if the dot prodact -/+
//        Point3D point = gp.point.add(delta);
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay);//if is intersection "intersections" not null
//        if (intersections == null) return true;
        double lightDistance = ls.getDistance(geoPoint.point);
        if (intersections == null)
            return 1d;
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0)
                ktr *= gp.geometry.getMaterial().Kt;
            if (ktr < MIN_CALC_COLOR_K)
                return 0.0;
        }
        return ktr;
    }


    private Ray constructReflectedRay(Point3D point, Vector v, Vector n) {
        return new Ray(point, v.subtract(n.scale(2 * v.dotProduct(n))), n);
    }


    private Ray constructRefractedRay(Point3D point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /**
     * @param ray
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

}
