package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere implements Geometry{

    Point3D center;

    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    double radius;

    public Point3D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center: " + center +
                ", radius: " + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        return point.subtract(center).normalize();
    }
}
