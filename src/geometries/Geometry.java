package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * interface for all grphic 3D shapes that are
 * positioned in our 3D
 */
public interface Geometry extends Intersectable{

    /**
     * return the normal of the shape from a point
     * @param p a point outside the shape
     * @return the normal vector
     */
    Vector getNormal(Point p);
}
