package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Class of light from infinite distance (like the sun)
 */
public class DirectionalLight extends Light implements LightSource {

    /**
     * the direction of the light
     */
    private final Vector _direction;

    /**
     * Light from a distant source that does not weaken with distance
     * @param intensity (0-255 the color of light)
     * @param direction vector of the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    /**
     * get intensity: get the power of the light to a point
     * @param p
     * @return The intensity of the light at the point p
     */
    @Override
    public Color getIntensity(Point3D p) {
        return _intensity;
    }

    /**
     * get L: get the direction of the light
     * @param p
     * @return the direction of the light
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    /**
     * @param point
     * @return Infinite distance
     */
    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
