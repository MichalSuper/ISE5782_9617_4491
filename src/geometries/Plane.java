package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.*;

/**
 * Plane class represents plane in 3D Cartesian coordinate and normal
 * @author Michal Superfine & Evgi
 */
public class Plane extends Geometry {
    final private Point q0;
    final private Vector normal;

    /**
     * constructor
     *
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Plane(Point p1, Point p2, Point p3) {
        q0 = p1;
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        normal = v1.crossProduct(v2).normalize();
    }

    /**
     * constructor
     *
     * @param p a point
     * @param v a vector
     */
    public Plane(Point p, Vector v) {
        q0 = p;
        normal = v.normalize();
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    public Point getQ0() {
        return this.q0;
    }

    /**
     * getter for normal field
     * @return
     */
    public Vector getNormal() {
        return this.normal;
    }

    /**
     * return the normal of the plane
     * @param p a point
     * @return normal vector (normalized)
     */
    public Vector getNormal(Point p) {
        return getNormal();
    }

    /**
     * find all intersection points {@link Point}
     * that intersect with a specific ray{@link Ray}
     * @param ray ray pointing towards the plane
     * @return immutable list of intersection geo points
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Vector n = normal;

        double nv = n.dotProduct(v);
        //ray parallel to plane or ray begins in the same point which appears as the plane's reference point
        if (isZero(nv) || q0.equals(p0))
            return null;
        double nQMinusP0 = n.dotProduct(q0.subtract(p0));
        double t = alignZero(nQMinusP0 / nv);
        if (t > 0){
            Point p = ray.getPoint(t);
            return List.of(new GeoPoint(this,p));
        }
        //t<=0
        return null;
    }
}
