package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {

    private final Vector _direction;

    /**
     * Light from a distant source that does not weaken with distance
     * @param intensity (0-255 the color of light)
     * @param direction
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
     * @return  get the direction of the light
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }
}
