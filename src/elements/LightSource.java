package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public interface LightSource {
    /**
     * an intrface for all light soucrce withe
     * get L: get the direction of the light
     * get intensity: get the power of the light to a point
     */

    /**
     *
     * @param p
     * @return get intensity: get the power of the light to a point
     */
    public Color getIntensity(Point3D p);

    /**
     *
     * @param p
     * @return get L: get the direction of the light
     */
    public Vector getL(Point3D p);


}
