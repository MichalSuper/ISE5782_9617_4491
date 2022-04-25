package geometries;


import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class represents a list of geometry objects
 * @author Michal Superfine & Evgi
 */
public class Geometries extends Intersectable {
    public List<Intersectable> _intersectables;

    /**
     * default constructor for Geometries
     */
    public Geometries() {
        _intersectables = new LinkedList<>();
    }

    /**
     * constructor for Geometries
     * @param geometries a list of geometry objects
     */
    public Geometries(Intersectable... geometries) {
        _intersectables = new LinkedList<>();
        add(geometries);
    }

    /**
     * add more geometry objects
     * @param geometries a list of geometry objects
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(_intersectables, geometries);
    }

    /**
     * find all intersection points {@link Point}
     * that intersect with a specific ray {@link Ray}
     * @param ray ray pointing towards the shapes
     * @return immutable list of intersection geo points
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> result = null;
        for (Intersectable item : _intersectables) {
            List<GeoPoint> itemResult = item.findGeoIntersectionsHelper(ray);
            if (itemResult != null) {
                if (result == null) result = new LinkedList<>();
                result.addAll(itemResult);
            }
        }
        return result;
    }
}
