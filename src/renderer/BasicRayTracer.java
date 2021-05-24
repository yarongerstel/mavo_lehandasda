package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

public class BasicRayTracer extends RayTracerBase {

    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Begins the beam close to the body and not the body itself (so as not to create intersection error)
     */
    private static final double DELTA = 0.1;

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return calcColor(closestPoint, ray);
        }
        //ray did not intersect any geometrical object
        return _scene.background;
    }

    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        Color emissionColor = geoPoint.geometry.getEmission();
        Color basicColor = _scene.ambientlight.getIntensity().add(emissionColor);
        // add calculated light contribution from all light sources)
        return basicColor.add(calcLocalEffects(geoPoint, ray));

    }

    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
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
                if (unshaded(lightSource,l,n, geoPoint)) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
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

    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double ln = Math.abs(l.dotProduct(n));
        return lightIntensity.scale(kd * ln);
    }

    /**
     * Checks if gp is unshaded or not
     *
     * @param l  vector from the light
     * @param n  normal to gp
     * @param gp the geoPoint on the shape
     * @return boolean if unshaded or not
     */
    private boolean unshaded(LightSource ls,Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);//check if the dot prodact -/+
        Point3D point = gp.point.add(delta);
        Ray lightRay = new Ray(lightDirection, point);
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(lightRay);//if is intersection "intersections" not null
        if (intersections == null) return true;
        double lightDistance = ls.getDistance(gp.point);
        for (GeoPoint geopoint : intersections) {
            if (alignZero(geopoint.point.distance(gp.point)-lightDistance) <= 0)
                return false;
        }
        return true;
    }
}
