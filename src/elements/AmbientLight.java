package elements;

import primitives.Color;

/**
 * Department of Environmental Lighting
 */
public class AmbientLight extends Light {
    /**
     * Default constructor
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * constructor for the environmental lighting
     *
     * @param ia Original light intensity parameter
     * @param ka The light intensity damping parameter
     */
    public AmbientLight(Color ia, double ka) {
        super(ia.scale(ka));
    }
}
