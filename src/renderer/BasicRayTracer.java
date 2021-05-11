package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;

import static geometries.Intersectable.GeoPoint;

public class BasicRayTracer extends RayTracerBase{

    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
        if (intersections != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return calcColor(closestPoint);
        }
        //ray did not intersect any geometrical object
        return _scene.background;
    }

    private Color calcColor(GeoPoint geoPoint) {
        Color emissionColor=geoPoint.geometry.getEmission();
        return _scene.ambientlight.getIntensity().add(emissionColor);
    }
}
