package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {

    final Point3D _q0;
    final Vector _normal;

    public Plane(Point3D q0, Vector normal) {
        _q0 = q0;
        _normal = normal;
    }

    public Plane(Point3D vertex, Point3D vertex1, Point3D vertex2) {
        _q0 = vertex;
        Vector v1=vertex1.subtract(vertex);
        Vector v2=vertex2.subtract(vertex);
        Vector cross=v1.crossProduct(v2);
        _normal = cross.normalize();
    }


    @Override
    public Vector getNormal(Point3D point) {

        return _normal.normalized();
    }


}