package elements;

import primitives.Color;

public class AmbientLight {

    Color intensity ;

    public AmbientLight(Color ia, double ka) {
        this.intensity=ia.scale(ka);
    }

    public Color getIntensity(){
        return this.intensity;
    }


}
