package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube {
    double height;

    public Cylinder(Ray _axisRay, double radius) {
        super(_axisRay, radius);
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return super.toString() + '\t' + "Height: " + height;
    }

}
