package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Cylinder extends Tube {

    double height;

    public Cylinder(Ray _axisRay, double radius) {
        super(_axisRay, radius);
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double getRadius() {
        return super.getRadius();
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    /***
     *
     * @param ray
     * @return List of point the ray Intersections
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }

    @Override
    public String toString() {
        return super.toString() + '\t' + "Height: " + height;
    }

}
