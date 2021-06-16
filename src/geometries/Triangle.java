package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * class of triangular representation by three point3D
 */
public class Triangle extends Polygon {
    /**
     * constructor
     *
     * @param vertices
     */
    public Triangle(Point3D... vertices) {
        super(vertices);
        // if(vertices.length != 3)
        //   throw new IllegalArgumentException("A triangle must 3 vertices!");
    }

    /**
     * constructor with 3 point
     *
     * @param p1 vertic 1
     * @param p2 vertic 2
     * @param p3 vertic 3
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    /**
     * find GeoIntsersections on this Triangle
     *
     * @param ray
     * @return list of Intsersections
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray,boolean BVH) {
        List<GeoPoint> intersections = super.findGeoIntersections(ray,BVH);
        if (intersections == null)  // if not intersection on plane not intersection with triangle
            return null;
        Vector v = ray.getDirection();
        Point3D p0 = ray.getPoint();

        //Creates a pyramid from the beginning of the ray to the triangle
        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector v3 = vertices.get(2).subtract(p0);

        //Checks if the ray is contained in one of the pyramid wigs
        double s1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(s1)) return null;
        double s2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(s2)) return null;
        double s3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(s3)) return null;

        // If s1, s2, s3 is positive or negative then it returns the GeoPoint intersections
        return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0))
                ? List.of(new GeoPoint(this, intersections.get(0).point))
                : null;
    }


    /**
     * get the normal from this Triangle
     *
     * @param point
     * @return the normal
     */
    @Override
    public Vector getNormal(Point3D point) {
        return super.getNormal(point);
    }
}
