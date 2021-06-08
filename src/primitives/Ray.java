package primitives;

import java.util.List;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.isZero;

/**
 * A class representing a ray by a point3D and direction vector
 */
public class Ray {
    private final Point3D _p0;
    private final Vector _dir;
    private static final double DELTA = 0.1;    // Its purpose is to move the beginning of the ray

    /**
     * constructor by point and direction
     * @param p0 point3D
     * @param dir direction vector
     */
    public Ray(Point3D p0, Vector dir) {
        _p0 = p0;
        _dir = dir.normalized();
    }

    /**
     * copy constructor
     * @param other
     */
    public Ray(Ray other) {
        _p0 = other._p0;
        _dir = other._dir;

    }

    /**
     * construct new ray offset by DELTA*normal(n) to get ray not on the square --> neer
     * @param point the point3D
     * @param l direction vector
     * @param n normal vector
     */
    public Ray(Point3D point, Vector l, Vector n){
        _p0 = point.add(n.scale(n.dotProduct(l) > 0 ? DELTA : -DELTA));
        _dir = l;
    }

    /**
     * find the closest point to the beginning of our ray
     * @param lis the list of points (list of point the ray intersection)
     * @return the closest point to the beginning of the ray
     */
    public Point3D findClosestPoint(List<Point3D> lis) {
        if (lis == null) {
            return null;
        }
        double min = lis.get(0).distance(_p0);
        Point3D pmin = lis.get(0);
        for (int i = lis.size() - 1; i > 0; i--)
            if (lis.get(i).distance(_p0) < min) {
                min = lis.get(i).distance(_p0);
                pmin = lis.get(i);
            }
        return pmin;
    }

    /**
     * Returns from all the cuts of the foundation with the shape the closest cut - the only one actually seen
     * @param lis
     * @return The closest GeoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> lis) {
        if (lis == null) {
            return null;
        }
        double min = Double.MAX_VALUE;
        GeoPoint pmin = null;
        for (GeoPoint gp : lis) {
            double d = gp.point.distance(_p0);
            if (d <= min) {
                pmin = gp;
            }
        }
        return pmin;
    }

    /**
     * Returns the point where the beam is supposed to hit according to the given length
     * @param length
     * @return The point where the foundation is supposed to hit
     */
    public Point3D getTargetPoint(double length) {
        return isZero(length) ? _p0 : _p0.add(_dir.scale(length));
    }

//*************geters**************

    public Vector getDirection() {
        return _dir;
    }

    public Point3D getPoint() {
        return _p0;
    }

    @Override
    public String toString() {
        return "Point: " + _p0 + '\t' + " Direction: " + _dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray.getPoint()) && _dir.equals(ray._dir);
    }
}

