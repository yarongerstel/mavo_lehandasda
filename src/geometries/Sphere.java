package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

/**
 * Class for representing a sphere by point3D and radius
 */
public class Sphere extends Geometry{

    Point3D _center;
    double _radius;

    /**
     * constructor sphere
     * @param center
     * @param radius
     */
    public Sphere(double radius, Point3D center) {
        _center = center;
        _radius = radius;
    }

    /**
     *
     * @return the center of sphere
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     *
     * @return the radius of sphere
     */
    public double getRadius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "Center: " + _center + ", Radius: " + _radius;
    }

    /**
     * calc a normal to the sphere from the point
     * @param point the point on the sphere
     * @return normal vector to the sphere from the point
     */
    @Override
    public Vector getNormal(Point3D point) {
        return point.subtract(_center).normalize();
    }

    /***
     *
     * @param ray
     * @return List of GeoPoint the ray Intersections on this sphere
     */

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        if (ray.getPoint().equals(_center))    // if the point of ray is equal to center
            return List.of(new GeoPoint(this,ray.getTargetPoint(_radius))); // return the point on the sphere
        Vector u = _center.subtract(ray.getPoint());    // u is vector from the ray to the center
        double tm = ray.getDirection().dotProduct(u);   // tm is the distance from point of the ray to the point on the ray how vertical to the center (call middle)
        double d = Math.sqrt(u.lengthSquared() - tm * tm);  // distance from the center to "middle" point (calc by Pythagoras)
        if (d > _radius || Util.isZero(d-_radius) )  // not intersection or tangent
            return null;
        double th = Math.sqrt(_radius * _radius - d * d);
        double t1 = Util.alignZero(tm + th), t2 = Util.alignZero(tm - th);
        if (t2 <= 0 && t1 <= 0) // ray start behind the sphere and don't have intersection
            return null;
        if (t1 > 0 && t2<=0)    // ray start inside the sphere
            return List.of(new GeoPoint(this,ray.getTargetPoint(t1)));
        if (t2 > 0 && t1<=0)    // ray start inside the sphere (-dir)
            return List.of(new GeoPoint(this,ray.getTargetPoint(t2)));
        // tow intersections
        return List.of(new GeoPoint(this,ray.getTargetPoint(t1)),new GeoPoint(this,ray.getTargetPoint(t2)));
    }
}

