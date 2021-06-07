package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene _scene;

    /**
     * constructor
     * @param _scene
     */
    public RayTracerBase(Scene _scene) {
        this._scene = _scene;
    }

    /**
     * A function that calculates the pixel color that the beam crosses
     * @param ray the ray from the camera through the pixel to the body
     * @return the color of  the pixel
     */
    public abstract Color traceRay (Ray ray) ;

}
