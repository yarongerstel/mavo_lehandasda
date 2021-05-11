package elements;

import primitives.Color;

/**
 * Department of Environmental Lighting
 */
public class AmbientLight {

    Color intensity;

    /**
     * Default constructor
     */
    public AmbientLight() {
        intensity = Color.BLACK;
    }

    /**
     * constructor for the environmental lighting
     *
     * @param ia Original light intensity parameter
     * @param ka The light intensity damping parameter
     */
    public AmbientLight(Color ia, double ka) {
        this.intensity = ia.scale(ka);
    }

    public Color getIntensity() {
        return this.intensity;
    }


}
