package primitives;

import java.util.Objects;

public class Ray {
    Point3D _p0;
    Vector _dir;

    public Ray(Point3D p0, Vector dir) {
        _p0 = p0;
        _dir = dir.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _dir.equals(ray._dir);
    }

    public Point3D getP0() {
        return _p0;
    }

    public Vector getDir() {
        return _dir;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_p0, _dir);
    }

    @Override
    public String toString() {
        return "Point: " + _p0 + '\t' + " Direction: " + _dir;
    }
}

