package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

/**
 * Sphere class represents Sphere in 3D point center and radius
 *
 * @author Michal Superfine & Evgi
 */
public class Sphere extends Geometry {
    final private Point _center;
    final private double _radius;

    /**
     * constructor
     *
     * @param center a point
     * @param radius a double radius
     */
    public Sphere(Point center, double radius) {
        this._center = center;
        this._radius = radius;
    }

    public Point getCenter() {
        return _center;
    }

    public double getRadius() {
        return _radius;
    }

    /**
     * to string
     *
     * @return description of the sphere
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + _radius +
                '}';
    }

    /**
     * return the normal of the sphere
     *
     * @param p a point
     * @return the normal
     */
    @Override
    public Vector getNormal(Point p) {
        return p.subtract(_center).normalize();
    }

    /**
     * find all intersection points {@link Point}
     * that intersect with a specific ray{@link Ray}
     *
     * @param ray ray pointing towards the sphere
     * @return immutable list of intersection geo points
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> result = null;
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector u;
        try {
            u = _center.subtract(p0);
        } catch (IllegalArgumentException ignore) {
            if (alignZero(_radius - maxDistance) <= 0)
                result = List.of(new GeoPoint(this, ray.getPoint(_radius)));
            return result;
        }

        double tm = alignZero(v.dotProduct(u));
        double dSqr = alignZero(u.lengthSquared() - tm * tm);
        double thSqr = _radius * _radius - dSqr;
        // no intersections : the ray direction is above the sphere
        if (alignZero(thSqr) <= 0) return null;

        double th = alignZero(Math.sqrt(thSqr));

        double t2 = alignZero(tm + th);
        if (t2 <= 0) return null;

        double t1 = alignZero(tm - th);
        if (t1 <= 0) {
            if (alignZero(t2 - maxDistance) <= 0)
                result = List.of(new GeoPoint(this, ray.getPoint(t2)));
            return result;
        } else {
            result = new LinkedList<>();
            if (alignZero(t1 - maxDistance) <= 0)
                result.add(new GeoPoint(this, ray.getPoint(t1)));
            if (alignZero(t2 - maxDistance) <= 0)
                result.add(new GeoPoint(this, ray.getPoint(t2)));
            return result.isEmpty() ? null : result;
        }
    }
}
