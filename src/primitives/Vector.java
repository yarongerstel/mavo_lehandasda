package primitives;

import static primitives.Point3D.ZERO;

public class Vector {
    final Point3D _head;

    public Vector(Point3D head) {
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
        _head = head;
    }

    public double dotProduct(Vector v) {
        double u1 = _head._x.coord;
        double u2 = _head._y.coord;
        double u3 = _head._z.coord;
        double v1 = v._head._x.coord;
        double v2 = v._head._y.coord;
        double v3 = v._head._z.coord;

        return (u1 * v1 + u2 * v2 + u3 * v3);

    }

    public Vector crossProduct(Vector v) {
        double u1 = _head._x.coord;
        double u2 = _head._y.coord;
        double u3 = _head._z.coord;
        double v1 = v._head._x.coord;
        double v2 = v._head._y.coord;
        double v3 = v._head._z.coord;

        return new Vector(new Point3D(
                u2*v3-u3*v2,
                u3*v1-u1*v3,
                u1*v2-u2*v1
        ));
    }
}