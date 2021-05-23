package primitives;

import java.util.Objects;

public class Vector {
    Point3D _head;

    /**
     * constructor by coordinate
     * @param x
     * @param y
     * @param z
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        if (x.coord == 0 && y.coord == 0 && z.coord == 0)
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        this._head = new Point3D(x, y, z);
    }

    /**
     * constructor by double
     * @param x
     * @param y
     * @param z
     */
    public Vector(double x, double y, double z) {
        if (x == 0 && y == 0 && z == 0)
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        this._head = new Point3D(x, y, z);
    }

    /**
     * Adds the "head" point to our vector
     * @param head
     */
    public Vector(Point3D head) {
        _head = head;
    }

    public Point3D get_head() {
        return _head;
    }

    /**
     *
     * @param v
     * @return connection of vectors by a new vector
     */
    public Vector add(Vector v){
        return new Vector(this._head._x.coord+v._head._x.coord,
                this._head._y.coord+v._head._y.coord,
                this._head._z.coord+v._head._z.coord);
    }

    /**
     *
     * @param v
     * @return subtraction of vectors by a new vector
     */
    public Vector subtract(Vector v){
        return new Vector(this._head._x.coord-v._head._x.coord,
                this._head._y.coord-v._head._y.coord,
                this._head._z.coord-v._head._z.coord);
    }

    /**
     *
     * @param s
     * @return vector multiplied by scalar
     */
    public Vector scale(double s){
        return new Vector(this._head._x.coord*s,
                this._head._y.coord*s,
                this._head._z.coord*s);
    }

    /**
     *
     * @param v
     * @return return double of dotProduct
     */
    public double dotProduct(Vector v) {
        double u1 = _head._x.coord;
        double u2 = _head._y.coord;
        double u3 = _head._z.coord;
        double v1 = v._head._x.coord;
        double v2 = v._head._y.coord;
        double v3 = v._head._z.coord;

        return (u1 * v1 + u2 * v2 + u3 * v3);

    }

    /**
     *
     * @param v
     * @return vector of crossProduct
     */
    public Vector crossProduct(Vector v) {
        double u1 = _head._x.coord;
        double u2 = _head._y.coord;
        double u3 = _head._z.coord;
        double v1 = v._head._x.coord;
        double v2 = v._head._y.coord;
        double v3 = v._head._z.coord;
        if(u1/v1 == u2/v2 && u2/v2 == u3/v3 && u1/v1 == u3/v3)
            throw new IllegalArgumentException("Error: parallel vectors");

        return new Vector(new Point3D(
                u2 * v3 - u3 * v2,
                u3 * v1 - u1 * v3,
                u1 * v2 - u2 * v1
        ));
    }

    /**
     *
     * @return double of lengthSquared  of this Vector
     */
    public double lengthSquared(){
         return this._head._x.coord*this._head._x.coord+
                  this._head._y.coord*this._head._y.coord+
                   this._head._z.coord*this._head._z.coord;
    }

    /**
     *
     * @return double length of this Vector
     */
    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    /**
     *
     * @return Normalizes the vector
     */
    public Vector normalize(){
        double length=this.length();
        this._head=new Point3D(this._head._x.coord/length,
                this._head._y.coord/length,
                this._head._z.coord/length);
        return this;
    }

    /**
     *
     * @return Returns a new normalized vector
     */
    public Vector normalized(){

        return new Vector(this._head).normalize();
    }

    @Override
    public String toString() {
        return "" +_head;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_head);
    }
}