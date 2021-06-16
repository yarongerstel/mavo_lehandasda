package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class of Tube Representation by ray and radius
 */
public class Tube extends Geometry {

    Ray _axisRay;
    double _radius;

    public Ray getAxisRay() {
        return _axisRay;
    }

    public double getRadius() {
        return _radius;
    }

    /**
     * constructor of tube
     *
     * @param axisRay The ray that represents the axis
     * @param radius  the radius of the tube
     */
    public Tube(Ray axisRay, double radius) {
        if (radius <= 0)
            throw new IllegalArgumentException("can't tube withe negative radius");
        _axisRay = axisRay;
        _radius = radius;
    }

    @Override
    public String toString() {
        return _axisRay.toString() + '\t' + "Radius: " + _radius;
    }

    /**
     * calc the normal to point on the tube
     *
     * @param point the point on the tube
     * @return normal vector from the given point
     */
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
     * @return List of geoPoint the ray Intersections
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray,boolean BVH) {
        if(BVH) {
            if (!_box.intersectBox(ray)) {
                return null;
            }
        }
        return null;
    }

    @Override
    public Box getBox() {
        return null;
    }
}
