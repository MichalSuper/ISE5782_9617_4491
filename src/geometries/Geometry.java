package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * interface for all graphic 3D shapes that are
 * positioned in our 3D
 * @author Michal Superfine & Evgi
 */
public abstract class Geometry extends Intersectable{

    protected Color emission= Color.BLACK;
    private Material material = new Material();

    /**
     * getter for emission
     * @return the emission color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * setter for emission
     * @param emission the emission color
     * @return the geometry object
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * return the normal of the shape from a point
     * @param p a point outside the shape
     * @return the normal vector
     */
    public abstract Vector getNormal(Point p);

    /**
     * setter for material
     * @param material the material of the geometry object
     * @return the geometry object
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * getter for material
     * @return the material of the geometry object
     */
    public Material getMaterial() {
        return material;
    }
}
