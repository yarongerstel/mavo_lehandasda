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
     * The color of this geometry
     */
    protected Color _emission = Color.BLACK;
    /**
     * the material properties
     */
    private Material _material = new Material();
    protected Box _box;

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
     * @param point
     * @return A vector perpendicular to the shape
     */
    public abstract Vector getNormal(Point3D point);


    @Override
    public Box getBox() {
        return new Box(_box._min, _box._max);
    }

    @Override
    public void BVH(int deep) {
        return;
    }
}