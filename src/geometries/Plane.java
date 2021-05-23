package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Plane extends Geometry {

    final Point3D _p0;
    final Vector _normal;

    public Plane(Point3D q0, Vector normal) {
        _p0 = q0;
        _normal = normal;
    }

    /**
     * constructor get 3 point and calc the _p0 and _normal to this plane
     * @param vertex
     * @param vertex1
     * @param vertex2
     */
    public Plane(Point3D vertex, Point3D vertex1, Point3D vertex2) {
        _p0 = vertex;
        Vector v1=vertex1.subtract(vertex);
        Vector v2=vertex2.subtract(vertex);
        Vector cross=v1.crossProduct(v2);
        //if the v1 and v2 are parallel trow exception
        _normal = cross.normalize();
    }

    /**
     *
     * @return point on this plane
     */
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
     * @return List of GeoPoint the ray Intersections
     */

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        if (ray.getPoint()==_p0)
            return null;
        double isParallel=ray.getDirection().dotProduct(_normal);
        if(isParallel==0)
            return null;
        double t=(_normal.dotProduct(_p0.subtract(ray.getPoint())))/(_normal.dotProduct(ray.getDirection()));

        if(t>0) {
            return List.of(new GeoPoint(this,ray.getPoint().add(ray.getDirection().scale(t))));
        }
        return null;
    }

}