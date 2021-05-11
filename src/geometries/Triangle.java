package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

public class Triangle extends Polygon {

    public Triangle(Point3D... vertices) {
        super(vertices);
    }

    /**
     * constructor
     * @param p1 vertic 1
     * @param p2 vertic 2
     * @param p3 vertic 3
     */
    public Triangle(Point3D p1,Point3D p2,Point3D p3) {
        super(p1,p2,p3);
    }
    /**
     * find Intsersections
     * @param ray
     * @return list of Intsersections
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> intersections = plane.findGeoIntersections(ray);
        if (intersections == null)
            return null;
        Vector v = ray.getDirection();
        Point3D p0 = ray.getPoint();

        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector v3 = vertices.get(2).subtract(p0);

        double s1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(s1)) return null;
        double s2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(s2)) return null;
        double s3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(s3)) return null;
        return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0))
                ? List.of(new GeoPoint(this,intersections.get(0).point))
                : null;
    }



    /**
     * getter
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point3D point) {
        return super.getNormal(point);
    }
}
