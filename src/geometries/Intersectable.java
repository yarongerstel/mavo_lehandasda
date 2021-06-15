package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interface for a Intersectable shape
 */
public interface Intersectable {
    void BVH(int deep);
    Box getBox();

    /**
     * an inside class that define a box
     * box is around avery intersectable
     */
    public class Box
    {
        Point3D _min;
        Point3D _max;
        Point3D mid;

        /**
         * constractor that generate a box with two points
         * @param min point
         * @param max point
         */
        public Box(Point3D min, Point3D max) {
            _max = max;
            _min = min;
            mid = new Point3D(max.getX()+min.getX()/2,max.getY()+min.getY()/2,max.getZ()+min.getZ()/2);
        }

        public Point3D get_min() {
            return _min;
        }

        public Point3D get_max() {
            return _max;
        }

        /**
         *
         * @param ray
         * @return true if the ray intersect withe the box and false if not
         */
        public boolean inBox(Ray ray) {
            double tmin = (_min.getX() - ray.getPoint().getX()) / ray.getDirection().get_head().getX();
            double tmax = (_max.getX() - ray.getPoint().getX()) / ray.getDirection().get_head().getX();
            if (tmin > tmax) {
                tmax = swap(tmin, tmin = tmax);
            }
            double tymin = (_min.getY() - ray.getPoint().getY()) / ray.getDirection().get_head().getY();
            double tymax = (_max.getY() - ray.getPoint().getY()) / ray.getDirection().get_head().getY();
            if(tymin > tymax)
                tymax = swap(tymin, tymin = tymax);
            if(tmin > tymax || tymin > tmax)
                return false;
            if (tymin > tmin)
                tmin = tymin;

            if (tymax < tmax)
                tmax = tymax;
            double tzmin = (_min.getZ() - ray.getPoint().getZ()) / ray.getDirection().get_head().getZ();
            double tzmax = (_max.getZ() - ray.getPoint().getZ()) / ray.getDirection().get_head().getZ();
            if (tzmin > tzmax)
                tzmax = swap(tzmin, tzmin = tzmax);
            if ((tmin > tzmax) || (tzmin > tmax))
                return false;
            return true;
        }

        public double swap(double a, double b) {
            return a;
        }

    }

    /**
     * inside class that define geo point
     * geo point is for intersection point
     * its hold the point of intersection and the object that the ray intesect with
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;


        /**
         * constructor Point on the geometric shape
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }
    }
    /**
     * the list of all the Intersectable of the shape
     **/
    List<Intersectable> intersectables = new LinkedList<>();


    /***
     *
     * @param ray
     * @return List of point the ray Intersections
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param ray
     * @returnList all the GeoPoint on the geometric shape that the beam cuts
     */
    List<GeoPoint> findGeoIntersections(Ray ray);


}
