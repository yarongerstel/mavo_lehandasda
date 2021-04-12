package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Tube implements Geometry{

    Ray _axisRay;
    double _radius;

    public Ray getAxisRay() {
        return _axisRay;
    }

    public double getRadius() {
        return _radius;
    }

    public Tube(Ray axisRay, double radius) {
        if(radius<=0) throw new IllegalArgumentException("can't tube withe negative radius");
        _axisRay = axisRay;
        _radius = radius;
    }

    @Override
    public String toString() {
        return _axisRay.toString() + '\t' + "Radius: " + _radius;
    }

    @Override
    public Vector getNormal(Point3D point) {
        Vector v = _axisRay.getDirection();
        Point3D P0 = _axisRay.getPoint();
        double t = v.dotProduct(point.subtract(P0));
        Point3D O = P0.add(v.scale(t));
        Vector sub = point.subtract(O);
        sub.normalize();
        return sub;
    }

    /***
     *
     * @param ray
     * @return List of point the ray Intersections
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
