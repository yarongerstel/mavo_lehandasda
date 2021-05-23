package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    private final String _name;

    public Color background = Color.BLACK;
    public AmbientLight ambientlight = new AmbientLight(new Color(0, 0, 0), 1.d);
    public Geometries geometries = null;
    public List<LightSource> lights=new LinkedList<>();

    /**
     * constructor get only the name of the scene
     *
     * @param name the name of the scene
     */
    public Scene(String name) {
        _name = name;
        geometries = new Geometries();
    }

    /**
     *
     * @param background
     * @return this scene with background we get
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     *
     * @param ambientlight
     * @return this scene with ambientlight we get
     */
    public Scene setAmbientLight(AmbientLight ambientlight) {
        this.ambientlight = ambientlight;
        return this;
    }

    /**
     *
     * @param geometries
     * @return this scene with all the Geometries we get
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

}
