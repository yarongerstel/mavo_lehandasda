package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

public class Scene {
        String _name;
    public Color background = Color.BLACK;
    public AmbientLight ambientlight= new AmbientLight(new Color(192, 192, 192),1.d); ;
    public Geometries geometries = null;

    public Scene(String name) {
        _name = name;
        geometries= new Geometries();
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return  this;
    }

    public Scene setAmbientLight(AmbientLight ambientlight) {
        this.ambientlight = ambientlight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return  this;
    }

}
