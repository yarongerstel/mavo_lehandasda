package geometries;

import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{

    List<Intersectable> intersectables = new ArrayList<>();

    public Geometries(List<Intersectable> intersectables) {
        this.intersectables = intersectables;
    }
    public Geometries(Intersectable... _geometries){
        add(_geometries);
    }

    /**
     * add to  List<intersectable> more geometries
     * @param _geometries
     */
    public void add(Intersectable... _geometries){
        this.intersectables.addAll(Arrays.asList(_geometries));
    }
    /***
     *
     * @param ray
     * @return List of GeoPoint the ray Intersections
     */

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> answer = null;
        for (var shape: intersectables) {
            if (shape.findGeoIntersections(ray) != null) {
                if(answer==null){
                    answer=new LinkedList<>();
                }
                answer.addAll(shape.findGeoIntersections(ray));
            }
        }
        return answer;
    }
}
