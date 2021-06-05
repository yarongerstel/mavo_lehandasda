package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class for Final Cylinder Representation
 */
public class Cylinder extends Tube {
    /**
     * Cylinder height
     */
    double _height;

    /**
     * constructor from cylinder
     * @param axisRay The ray that represents the axis
     * @param radius the radius of the cylinder
     * @param height the height of the cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        _height = height;
    }

    /**
     * @return height of cylinder
     */
    public double getHeight() {
        return _height;
    }

    /**
     * @return radius of cylinder
     */
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
        return super.toString() + '\t' + "Height: " + _height;
    }

}
