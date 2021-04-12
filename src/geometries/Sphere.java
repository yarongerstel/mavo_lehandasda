package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

public class Sphere implements Geometry{

    Point3D _center;
    double _radius;

    public Sphere(Point3D center, double radius) {
        this._center = center;
        this._radius = radius;
    }

    public Sphere(double radius, Point3D center) {
        this._center = center;
        this._radius = radius;
    }

    public Point3D getCenter() {
        return _center;
    }

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
     * @return List of point the ray Intersections
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        if (ray.getPoint().equals(this._center))
            return List.of(ray.getTargetPoint(_radius));
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
            return List.of(ray.getTargetPoint(t1));
        if (t2 > 0&&t1<=0)
            return List.of(ray.getTargetPoint(t2));
        return List.of((ray.getTargetPoint(t1)),ray.getTargetPoint(t2));
    }
}

