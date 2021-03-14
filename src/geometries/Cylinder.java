package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Cylinder extends Tube {
    double height;

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height: " + height +"\t"+ super.toString() +'}';
    }

}
