package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class for spot light
 */
public class SpotLight extends PointLight{
    /**
     * Direction of light
     */
    private final Vector _direction;

    /**
     * constructor
     * @param intensity
     * @param position
     * @param direction
     */
    protected SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        _direction = direction.normalized();
    }

    /**
     * Calculates the light intensity reflected at the point
     * @param p
     * @return Il of spot light intensity
     */
    @Override
    public Color getIntensity(Point3D p) {
        double cosTetha = _direction.dotProduct(getL(p));
        //Color intensity calc from point light(super)
        Color intensity=super.getIntensity(p);
        return intensity.scale(Math.max(0,cosTetha));   // If the angle is over 90 degree returns 0
    }
}
