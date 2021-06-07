package elements;

import primitives.Color;

/**
 * Abstract class for general light features
 */
abstract class Light {
    /**
     * Light intensity
     */
    protected final Color _intensity;

    /**
     * constructor
     * @param intensity
     */
    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * get intensity: get the power of the light to a point
     * @return The intensity of the light at the point p
     */
    public Color getIntensity() {
        return _intensity;
    }
}
