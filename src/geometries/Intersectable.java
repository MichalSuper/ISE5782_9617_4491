package geometries;

import primitives.*;
import java.util.List;

/**
 * Common interface for all graphic objects
 * that intersect with a ray{@link Ray}
 */
public interface Intersectable{
    /**
     * find all intersection points {@link Point}
     * that intersect with a specific ray{@link Ray}
     * @param ray ray pointing towards the graphic object
     * @return immutable list of intersection points {@link Point}
     */
    List<Point> findIntersections(Ray ray);
}
