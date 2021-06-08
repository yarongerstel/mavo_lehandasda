package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class for plane representation by point and vertical vector
 */
public class Plane extends Geometry {

    final Point3D _p0;
    final Vector _normal;

    /**
     * constructor of plane
     *
     * @param q0     the point
     * @param normal normal to the plane
     */
    public Plane(Point3D q0, Vector normal) {
        _p0 = q0;
        _normal = normal;
    }

    /**
     * constructor get 3 point and calc the _p0 and _normal to this plane
     *
     * @param vertex
     * @param vertex1
     * @param vertex2
     */
    public Plane(Point3D vertex, Point3D vertex1, Point3D vertex2) {
        _p0 = vertex;
        Vector v1 = vertex1.subtract(vertex);
        Vector v2 = vertex2.subtract(vertex);
        Vector cross = v1.crossProduct(v2);
        //if the v1 and v2 are parallel trow exception
        _normal = cross.normalize();
    }

    /**
     * @return point on this plane
     */
    public Point3D getPointInPlane() {
        return _p0;
    }

    /**
     * get normal to the plane
     * @param point
     * @return normal to the plane
     */

    @Override
    public Vector getNormal(Point3D point) {
        return _normal.normalized();
    }

    /**
     * get normal to the plane
     * @return normal to the plane
     */
    public Vector getNormal() {
        return _normal.normalized();
    }


    /***
     * List of GeoPoint the ray Intersections
     * @param ray
     * @return List of GeoPoint the ray Intersections
     */

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        if (ray.getPoint() == _p0)  // the ray start on the plane and not intersection
            return null;
        double isParallel = ray.getDirection().dotProduct(_normal); //parallel to the plane give 0
        if (isParallel == 0)
            return null;
        // -+ or +- not intersection else one intersection
        double t = (_normal.dotProduct(_p0.subtract(ray.getPoint()))) / (_normal.dotProduct(ray.getDirection()));

        if (t > 0) {
            return List.of(new GeoPoint(this, ray.getPoint().add(ray.getDirection().scale(t))));
        }
        return null;
    }
    /**
     * the function checks if a point is in the plane
     *
     * @param p a point
     * @return true if the point is in the plane else false
     */
    public boolean isPointInPlane(Point3D p) {
        // Plane equation: aX + bY + cZ + d = 0
        double a = _normal.get_head().getX(),
                b = _normal.get_head().getY(),
                c = _normal.get_head().getZ(),
                d = -a * _p0.getX() - b * _p0.getY() - c * _p0.getZ();

        // Placing the point in the plane equation
        return (a * p.getX() + b * p.getY() + c * p.getZ() + d) == 0;
    }

}