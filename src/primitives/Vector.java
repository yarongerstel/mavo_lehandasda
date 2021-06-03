package primitives;

import java.util.Objects;

/**
 * class for Vector Representation by point3D (Head)
 */
public class Vector {
    Point3D _head;

    /**
     * constructor by coordinate
     * @param x
     * @param y
     * @param z
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        if (x._coord == 0 && y._coord == 0 && z._coord == 0)
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        _head = new Point3D(x, y, z);
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
        _head = new Point3D(x, y, z);
    }

    /**
     * copy constructor
     * @param head
     */
    public Vector(Point3D head) {
        _head = head;
    }

    public Point3D get_head() {
        return _head;
    }

    /**
     * Connects two vectors
     * @param v the second vector
     * @return connection of vectors by a new vector
     */
    public Vector add(Vector v){
        return new Vector(this._head._x._coord +v._head._x._coord,
                this._head._y._coord +v._head._y._coord,
                this._head._z._coord +v._head._z._coord);
    }

    /**
     * subtraction of vectors by a new vector: this - new
     * @param v the second vector
     * @return subtraction of vectors by a new vector
     */
    public Vector subtract(Vector v){
        return new Vector(_head._x._coord -v._head._x._coord,
                _head._y._coord -v._head._y._coord,
                _head._z._coord -v._head._z._coord);
    }

    /**
     * vector multiplied by scalar
     * @param s double scale
     * @return vector multiplied by scalar
     */
    public Vector scale(double s){
        return new Vector(this._head._x._coord *s,
                this._head._y._coord *s,
                this._head._z._coord *s);
    }

    /**
     * double of dotProduct
     * @param v
     * @return double of dotProduct u1 * v1 + u2 * v2 + u3 * v3
     */
    public double dotProduct(Vector v) {
        double u1 = _head._x._coord;
        double u2 = _head._y._coord;
        double u3 = _head._z._coord;
        double v1 = v._head._x._coord;
        double v2 = v._head._y._coord;
        double v3 = v._head._z._coord;

        return (u1 * v1 + u2 * v2 + u3 * v3);

    }

    /**
     * Returns a vertical vector to our vector and also to the additional vector
     * @param v the other vector
     * @return vector of crossProduct
     */
    public Vector crossProduct(Vector v) {
        double u1 = _head._x._coord;
        double u2 = _head._y._coord;
        double u3 = _head._z._coord;
        double v1 = v._head._x._coord;
        double v2 = v._head._y._coord;
        double v3 = v._head._z._coord;
        if(u1/v1 == u2/v2 && u2/v2 == u3/v3 && u1/v1 == u3/v3)
            throw new IllegalArgumentException("Error: parallel vectors");

        return new Vector(new Point3D(
                u2 * v3 - u3 * v2,
                u3 * v1 - u1 * v3,
                u1 * v2 - u2 * v1
        ));
    }

    /**
     * Vector length squared
     * @return double of lengthSquared  of this Vector
     */
    public double lengthSquared(){
         return this._head._x._coord *this._head._x._coord +
                  this._head._y._coord *this._head._y._coord +
                   this._head._z._coord *this._head._z._coord;
    }

    /**
     * Vector length
     * @return double length of this Vector
     */
    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * @return Normalizes this vector
     */
    public Vector normalize(){
        double length=this.length();
        this._head=new Point3D(this._head._x._coord /length,
                this._head._y._coord /length,
                this._head._z._coord /length);
        return this;
    }

    /**
     * @return a new normalized vector
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