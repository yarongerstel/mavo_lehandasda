package primitives;

import java.util.List;
import java.util.Objects;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.isZero;

public class Ray {
    Point3D _p0;
    Vector _dir;

    public Ray(Vector dir, Point3D p0) {
        _p0 = p0;
        _dir = dir.normalize();
    }

    /**
     * copy constructor
     *
     * @param other
     */
    public Ray(Ray other) {
        this._dir = other._dir;
        this._p0 = other._p0;
    }

    /**
     * @param lis list of points
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


    public Point3D getTargetPoint(double length) {
        return isZero(length) ? _p0 : _p0.add(_dir.scale(length));
    }

    public Vector getDirection() {
        return _dir;
    }

    public Point3D getPoint() {
        return _p0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_p0, _dir);
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

