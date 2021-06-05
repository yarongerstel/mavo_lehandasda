package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 *
 */
public abstract class Geometry implements Intersectable {
    /**
     * The color that returns from the object after the light hits it
     */
    protected Color _emission = Color.BLACK;
    private Material _material = new Material();

    public Material getMaterial() {
        return _material;
    }

    /**
     * Adding material to the current geometry
     *
     * @param material
     * @return this geometry with material
     */
    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }

    /**
     * @return _emission color
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * Add color emission the current shape
     *
     * @param emission
     * @return this shpe with emision color
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     * interfasce of get normal
     *
     * @param point
     * @return A vector perpendicular to the shape
     */
    public abstract Vector getNormal(Point3D point);

}