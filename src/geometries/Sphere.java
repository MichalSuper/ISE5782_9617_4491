package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

public class Sphere implements Geometry {
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
     * @return immutable list of intersection points {@link Point}
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector u;
        try {
            u = _center.subtract(p0);
        } catch (IllegalArgumentException ignore) {
            return List.of(ray.getPoint(_radius));
        }

        double tm = alignZero(v.dotProduct(u));
        double dSqr = alignZero(u.lengthSquared() - tm * tm);
        double thSqr = _radius*_radius - dSqr;
        // no intersections : the ray direction is above the sphere
        if (alignZero(thSqr) <= 0) return null;

        double th = alignZero(Math.sqrt(thSqr));

        double t2 = alignZero(tm + th);
        if (t2 <= 0) return null;

        double t1 = alignZero(tm - th);
        return t1 <= 0 ? List.of(ray.getPoint(t2)) : List.of(ray.getPoint(t1), ray.getPoint(t2));
    }
}
