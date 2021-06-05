package elements;

import primitives.*;

import static primitives.Util.isZero;

/**
 * class for camera elements and its location in space
 */
public class Camera {
    /**
     * Location of the camera in space
     */
    final private Point3D _P0;
    /**
     * Camera angle in space
     */
    final private Vector _vUP;
    /**
     * Direction of camera view
     */
    final private Vector _vTO;
    /**
     * vTO X vUP
     */
    final private Vector _vRIGHT;
    /**
     * param of plan
     */
    private double _width;
    private double _height;
    /**
     * The distance of the plan from the camera
     */
    private double _distance;

    /**
     * constructor
     * @param p0 location of the camera
     * @param vTO the vector from the camera To the objects
     * @param vUP the vector from the camera to Up (90 degrees)
     */
    public Camera(Point3D p0, Vector vTO, Vector vUP) {
        if(!isZero(vUP.dotProduct(vTO))){   // Make sure the vectors are vertical
            throw new IllegalArgumentException("up vector and  to vector arent orthogonal");
        }
        _P0 = p0;
        _vUP = vUP.normalized();
        _vTO = vTO.normalized();
        _vRIGHT = _vTO.crossProduct(_vUP);
    }

    /**
     * @return the point P0
     */
    public Point3D getP0() {
        return _P0;
    }

    /**
     * @return the vector vUP
     */
    public Vector getvUP() {
        return _vUP;
    }

    /**
     * @return the vector vTO
     */
    public Vector getvTO() {
        return _vTO;
    }

    /**
     * @return the vector vRIGHT
     */
    public Vector getvRIGHT() {
        return _vRIGHT;
    }

    public double getWidth() {
        return _width;
    }

    public double getHeight() {
        return _height;
    }

    /**
     * Creating the plan
     * @param width the plan
     * @param height the plan
     * @return The camera after updating the height and width of the plan
     */
    public Camera setViewPlaneSize(double width, double height) {
        _height = height;
        _width = width;
        return this;
    }

    /**
     * @param distance of the plan from the camera
     * @return The camera after updating its distance from the plan
     */
    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }

    public double getDistance() {
        return _distance;
    }

    /**
     * Calculate the vector from the camera to the center of the given pixel in the view plan
     * @param nX number of width pixels
     * @param nY number of height pixels
     * @param j the index that the ray go through
     * @param i the index that the ray go through
     * @return  the ray prom the camera through the wanted pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){

        Point3D Pc = _P0.add(_vTO.scale(_distance));

        double Rx = _width / nX;
        double Ry = _height / nY;

        Point3D Pij = Pc;
        // Calculates the position of the pixel center
        double Yi = -Ry * (i - (nY - 1) / 2d);
        double Xj = Rx * (j - (nX - 1) / 2d);

        if(!isZero(Xj)){
            Pij = Pij.add(_vRIGHT.scale(Xj));
        }
        if(!isZero(Yi)){
            Pij = Pij.add(_vUP.scale(Yi));
        }
        // Calculate the vector from the camera to the center of the given pixel in the view plan
        Vector Vij = Pij.subtract(_P0);

        return  new Ray(_P0, Vij);
    }
}
