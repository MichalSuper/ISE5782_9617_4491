package geometries;


import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    public List<Intersectable> _intersectables;

    public Geometries() {
        _intersectables = new LinkedList<>();
    }

    public Geometries(Intersectable... geometries) {
        _intersectables = new LinkedList<>();
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        Collections.addAll(_intersectables, geometries);
    }

    /**
     * find all intersection points {@link Point}
     * that intersect with a specific ray{@link Ray}
     * @param ray ray pointing towards the shapes
     * @return immutable list of intersection points {@link Point}
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result= new LinkedList<Point>();
        for(Intersectable item: _intersectables)
        {
            List<Point> itemResult= item.findIntersections(ray);
            if (itemResult!= null)
                result.addAll(itemResult);
        }
        if(result.isEmpty())
            return null;
        return result;
    }
}
