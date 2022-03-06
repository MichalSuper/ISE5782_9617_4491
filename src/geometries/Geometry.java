package geometries;

import primitives.Point;
import primitives.Vector;

public interface Geometry {

    /**
     * return the normal of the shape
     * @param p a point
     * @return the normal
     */
    public Vector getNormal(Point p);
}
