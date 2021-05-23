package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

public class Sphere extends Geometry{

    Point3D _center;
    double _radius;

    /**
     * constructor sphere
     * @param center
     * @param radius
     */
    public Sphere(double radius, Point3D center) {
        this._center = center;
        this._radius = radius;
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
        if (ray.getPoint().equals(this._center))
            return List.of(new GeoPoint(this,ray.getTargetPoint(_radius)));
        Vector u = _center.subtract(ray.getPoint());
        double tm = ray.getDirection().dotProduct(u);
        double d = Math.sqrt(u.lengthSquared() - tm * tm);
        if (d > _radius ||Util.isZero(d-_radius) )
            return null;
        double th = Math.sqrt(_radius * _radius - d * d);
        double t1 = Util.alignZero(tm + th), t2 = Util.alignZero(tm - th);
        if (t2 <= 0 && t1 <= 0)
            return null;
        if (t1 > 0&&t2<=0)
            return List.of(new GeoPoint(this,ray.getTargetPoint(t1)));
        if (t2 > 0&&t1<=0)
            return List.of(new GeoPoint(this,ray.getTargetPoint(t2)));
        return List.of(new GeoPoint(this,ray.getTargetPoint(t1)),new GeoPoint(this,ray.getTargetPoint(t2)));
    }
}

