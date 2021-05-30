package elements;

import primitives.Color;

abstract class Light {
    protected final Color _intensity;

    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * get intensity: get the power of the light to a point
     *
     * @return The intensity of the light at the point p
     */
    public Color getIntensity() {
        return _intensity;
    }
}
