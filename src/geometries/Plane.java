package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Plane implements Geometry {

    final Point3D _p0;
    final Vector _normal;

    public Plane(Point3D q0, Vector normal) {
        _p0 = q0;
        _normal = normal;
    }

    public Plane(Point3D vertex, Point3D vertex1, Point3D vertex2) {
        _p0 = vertex;
        Vector v1=vertex1.subtract(vertex);
        Vector v2=vertex2.subtract(vertex);
        Vector cross=v1.crossProduct(v2);
        _normal = cross.normalize();
    }
    public Point3D getPointInPlane() {
        return _p0;
    }

    @Override
    public Vector getNormal(Point3D point) {

        return _normal.normalized();
    }


    /***
     *
     * @param ray
     * @return List of point the ray Intersections
     */
    @Override

    public List<Point3D> findIntersections(Ray ray) {
        if (ray.getPoint()==this._p0)
            return null;
        double isParallel=ray.getDirection().dotProduct(_normal);
        if(isParallel==0)
            return null;
        double t=(this._normal.dotProduct(this._p0.subtract(ray.getPoint())))/(this._normal.dotProduct(ray.getDirection()));

        if(t>0) {
            return List.of(ray.getPoint().add(ray.getDirection().scale(t)));
        }
        return null;
    }

}