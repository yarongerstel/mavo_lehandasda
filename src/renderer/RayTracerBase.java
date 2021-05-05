package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene _scene;

    public RayTracerBase(Scene _scene) {
        this._scene = _scene;
    }
    public abstract Color traceRay (Ray ray) ;

}
