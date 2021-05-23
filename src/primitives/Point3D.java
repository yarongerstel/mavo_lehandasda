package primitives;

public class Point3D {
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    final public static Point3D ZERO = new Point3D(0d, 0d, 0d);

    /**
     * constructor for Point3D by coordinate
     *
     * @param x coordinate for X axis
     * @param y coordinate for y axis
     * @param z coordinate for z axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this(x.coord, y.coord, z.coord);
    }

    /**
     * constructor for Point3D by double
     * @param x coordinate for X axis
     * @param y coordinate for y axis
     * @param z coordinate for z axis
     */
    public Point3D(double x, double y, double z) {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }
    /**
     * copy constructor
     * @param other
     */
    public Point3D(Point3D other) {
        this._x = other._x;
        this._y = other._y;
        this._z = other._z;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "(" + _x + ", " + _y + ", " + _z + ")";
    }

    /**
     * @param other
     * @return (x2 - x1)^2 + (y2-y1)^2 + (z2-z1)^2
     */
    public double distanceSquared(Point3D other) {
        final double x1 = _x.coord;
        final double y1 = _y.coord;
        final double z1 = _z.coord;
        final double x2 = other._x.coord;
        final double y2 = other._y.coord;
        final double z2 = other._z.coord;

        return ((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)) + ((z2 - z1) * (z2 - z1));
    }

    /**
     * @param point3D
     * @return euclidean distance between 2  3D points
     */
    public double distance(Point3D point3D) {
        return Math.sqrt(distanceSquared(point3D));
    }

    /**
     *
     * @param pt2
     * @return Vector The result of subtracting a vector by a point.
     */
    public Vector subtract(Point3D pt2) {
        if (pt2.equals(this)) {
            throw new IllegalArgumentException("cannot create Vector to Point(0,0,0)");
        }
        return new Vector(new Point3D(
                _x.coord - pt2._x.coord,
                _y.coord - pt2._y.coord,
                _z.coord - pt2._z.coord
        ));
    }

    /**
     * Adds vector to point
     * @param vector
     * @return new point3D
     */
    public Point3D add(Vector vector) {
        return new Point3D(
                _x.coord + vector._head._x.coord,
                _y.coord + vector._head._y.coord,
                _z.coord + vector._head._z.coord
        );
    }
   //**********************geters*****************

    public double getX() {
        return _x.coord;
    }

    public double getY() {
        return _y.coord;
    }

    public double getZ() {
        return _z.coord;
    }


}