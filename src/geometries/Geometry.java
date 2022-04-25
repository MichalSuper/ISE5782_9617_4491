package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * interface for all graphic 3D shapes that are
 * positioned in our 3D
 */
public abstract class Geometry extends Intersectable{

    protected Color emission= Color.BLACK;

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
}
