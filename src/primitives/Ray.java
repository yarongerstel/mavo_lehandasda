package primitives;

import java.util.Objects;

import static primitives.Util.isZero;

public class Ray {
    Point3D _p0;
    Vector _dir;

    public Ray(Vector dir,Point3D p0) {
        _p0 = p0;
        _dir = dir.normalize();
    }

    /**
     * copy constructor
     * @param other
     */
    public Ray(Ray other){
        this._dir = other._dir;
        this._p0 = other._p0;
    }



    public Point3D getTargetPoint(double length) {
        return isZero(length ) ? _p0 : _p0.add(_dir.scale(length));
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

