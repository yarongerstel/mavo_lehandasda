package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

public class Camera {
    private
    final Point3D _location;
    final Vector _Vto;
    final Vector _Vup;
    final Vector _Vright;
    double _ViewPlaneSize;
    double _Distance;

    /**
     * constructor
     * @param location of the camera
     * @param to the vector from the camera To the center of the image
     * @param up the vector from the camera to Up (90 degrees)
     */
    public Camera(Point3D location, Vector to, Vector up) {
        //Makes sure the vectors are indeed perpendicular otherwise throws an error
        if (Util.alignZero(up.dotProduct(to))!=0)
            throw new IllegalArgumentException("vectors are not vertical");
        _Vup =up.normalized();
        _Vto =to.normalized();
        _Vright =(to.crossProduct(up)).normalized();//Creates a balance vector by cross-calculating the vector direction vector up
        this._location = location;
    }
    public Camera setVpSize(double width, double height){
        this._ViewPlaneSize=width*height;
        return this;
    }
    public Camera setVpDistance(double distance){
        this._Distance=distance;
        return this;
    }
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){

    }



    public Point3D get_location() {
        return _location;
    }

    public Vector get_Vto() {
        return _Vto;
    }

    public Vector get_Vup() {
        return _Vup;
    }

    public Vector get_Vright() {
        return _Vright;
    }


}
