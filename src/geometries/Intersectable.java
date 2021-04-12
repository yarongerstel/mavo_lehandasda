package geometries;

import primitives.*;

import java.util.List;
import java.util.LinkedList;
public interface Intersectable {

    /**the list of all the shapes**/
    List<Intersectable> intersectables = new LinkedList<>();


    /***
     *
     * @param ray
     * @return List of point the ray Intersections
     */
    List<Point3D> findIntersections(Ray ray);



}
