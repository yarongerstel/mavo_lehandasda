package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere implements Geometry{

    Point3D _center;
    double _radius;

    public Sphere(Point3D center, double radius) {
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
}
