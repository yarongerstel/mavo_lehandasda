package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class of spot light source
 */
public class PointLight extends Light implements LightSource {
    /**
     * A specific location in the scene
     */
    private final Point3D _position;
    /**
     * const
     */
    private double _Kc = 1;
    /**
     * linear
     */
    private double _Kl = 0;
    /**
     * Squares
     */
    private double _Kq = 0;

    /**
     * constructor
     *
     * @param intensity (0-255 the color of light)
     * @param position  the location in the scene
     */
    protected PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }

    /**
     * Calculates the attenuation of light at a point
     * @param p the point
     * @return The intensity of the light at the point after calculating the attenuation
     */
    @Override
    public Color getIntensity(Point3D p) {
        double d = _position.distance(p);    // The distance between the light source and the point
        double attenuation = 1d / (_Kc + _Kl * d + _Kq * d * d);
        return _intensity.scale(attenuation);
    }

    /**
     * Returns a vector of direction from light to point
     * @param p the point
     * @return a vector of direction from light to point
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalized();
    }

    /**
     * @param point the point
     * @return Distance between point and light
     */
    @Override
    public double getDistance(Point3D point) {
        return _position.distance(point);
    }

    public PointLight setKc(double kc) {
        _Kc = kc;
        return this;
    }

    public PointLight setKl(double kl) {
        _Kl = kl;
        return this;
    }

    public PointLight setKq(double kq) {
        _Kq = kq;
        return this;
    }
}
