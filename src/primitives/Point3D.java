package primitives;

import java.util.Objects;

public class Point_3D {
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    public Point_3D(Coordinate x, Coordinate y, Coordinate z) {
        this._x =new Coordinate(x.coord);
        this._y =new Coordinate(y.coord);
        this._z =new Coordinate(z.coord);
    }

    public Coordinate get_x() {
        return _x;
    }

    public Coordinate get_y() {
        return _y;
    }

    public Coordinate get_z() {
        return _z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point_3D point_3D = (Point_3D) o;
        return _x.equals(point_3D._x) && _y.equals(point_3D._y) && _z.equals(point_3D._z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_x, _y, _z);
    }
}
