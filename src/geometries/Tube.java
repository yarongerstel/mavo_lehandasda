package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry{

    Ray _axisRay;
    double radius;

    public Ray getAxisRay() {
        return _axisRay;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "Ray: " + _axisRay +
                ", Radius: " + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        Vector v = _axisRay.getDir();
        Point3D P0 = _axisRay.getP0();
        Point3D P = point;
        Vector PMinusP0 = P.subtract(P0);
        double t = v.dotProduct(PMinusP0);
        Point3D O = P0.add(v.scale(t));
        Vector sub = P.subtract(O);
        sub.normalize();
        return sub;
    }

}
