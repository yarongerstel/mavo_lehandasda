package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public interface Intersectable {
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
