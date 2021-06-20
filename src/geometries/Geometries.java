package geometries;
import primitives.Point3D;
import primitives.Ray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{

    List<Intersectable> intersectables = new ArrayList<>();
    Box box;

    /**
     * copy constractor
     * @param intersectables
     */
    public Geometries(List<Intersectable> intersectables) {
        this.intersectables = intersectables;
        createBox();
    }

    public Geometries(Intersectable... _geometries){
        add(_geometries);
        createBox();
    }

    /***
     *
     * @param _geometries add to the list all shapes that are given as a list
     *                    and updating the box
     */
    public void add(Intersectable... _geometries){
        if (box == null)
            createBox();
        double Xmax = box._max.getX();
        double Xmin = box._min.getX();
        double Ymax = box._max.getY();
        double Ymin = box._min.getY();
        double Zmax = box._max.getZ();
        double Zmin = box._min.getZ();
        for (var inter : _geometries){ // Finds the box size that will contain all the boxes
            Xmax = Math.max(Xmax,inter.getBox()._max.getX());
            Xmin = Math.min(Xmin,inter.getBox()._min.getX());
            Ymax = Math.max(Ymax,inter.getBox()._max.getY());
            Ymin = Math.min(Ymin,inter.getBox()._min.getY());
            Zmax = Math.max(Zmax,inter.getBox()._max.getZ());
            Zmin = Math.min(Zmin,inter.getBox()._min.getZ());
        } // update the big box
        box._max = new Point3D(Xmax,Ymax,Zmax);
        box._min = new Point3D(Xmin,Ymin,Zmin);
        this.intersectables.addAll(Arrays.asList(_geometries));
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray,boolean BVH) {
        if(BVH) {
            if (!box.intersectBox(ray)) {
                return null;
            }
        }
        List<GeoPoint> answer = null;
        for (var shape: intersectables) {
            if (shape.findGeoIntersections(ray,BVH) != null) {
                if(answer==null){
                    answer=new LinkedList<>();
                }
                answer.addAll(shape.findGeoIntersections(ray,BVH));
            }
        }
        return answer;
    }
    @Override
    public Box getBox() {
        return new Box(box._min,box._max);
    }

    /**
     * create a box around all the intersectabels
     */
    public void createBox(){
        double xmin =Double.POSITIVE_INFINITY, ymin = Double.POSITIVE_INFINITY, zmin = Double.POSITIVE_INFINITY,
                xmax = Double.NEGATIVE_INFINITY, ymax = Double.NEGATIVE_INFINITY, zmax = Double.NEGATIVE_INFINITY;
        for(var geometry:this.intersectables){
            xmin = Math.min(xmin,geometry.getBox()._min.getX());
            ymin = Math.min(ymin,geometry.getBox()._min.getY());
            zmin = Math.min(zmin,geometry.getBox()._min.getZ());
            xmax = Math.max(xmax, geometry.getBox()._max.getX());
            ymax = Math.max(ymax, geometry.getBox()._max.getY());
            zmax = Math.max(zmax, geometry.getBox()._max.getZ());
        }
        box = new Box(new Point3D(xmin, ymin, zmin), new Point3D(xmax, ymax, zmax));
    }

    /**
     * BVH recursive algoritem. create binary tree.
     * @param deep
     */
    @Override
    public void BVH(int deep) {
        if(intersectables.size() < 2 || deep--  <= 0)
            return;
        int range = 2;

        Geometries[] geometriesArr = new Geometries[range];
        for (int i = 0; i < range; i++) {
            geometriesArr[i] = new Geometries();
        }
        for (var geo : this.intersectables){
            // check where the geo is closer.
            double toMax = geo.getBox().mid.distance(box._max);
            double toMin = geo.getBox().mid.distance(box._min);
            if (Math.min(toMax,toMin) == toMax)
                geometriesArr[0].add(geo);
            else
                geometriesArr[1].add(geo);
        }
        intersectables.clear();

        for (int i = 0; i < range; i++) {
            this.add(geometriesArr[i]);
            if (deep > 0)
                geometriesArr[i].BVH(deep);
        }
    }

}
