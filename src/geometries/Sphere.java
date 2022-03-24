package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

public class Sphere implements Geometry{
    private Point _center;
    private double _radius;

    /**
     * constructor
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
     * @param ray ray pointing towards the sphere
     * @return immutable list of intersection points {@link Point}
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        Point P0 = ray.getP0();
        Vector v = ray.getDir();

        if (P0.equals(_center)) {
            return List.of(_center.add(v.scale(_radius)));
        }

        Vector U = _center.subtract(P0);
        double tm = alignZero(v.dotProduct(U));
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm));

        // no intersections : the ray direction is above the sphere
        if (d >= _radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(_radius * _radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 > 0 && t2 > 0) {
            Point P1 = ray.getPoint(t1);
            Point P2 = ray.getPoint(t2);
            return List.of(P1, P2);
        }
        if (t1 > 0) {
            Point P1 = ray.getPoint(t1);
            return List.of(P1);
        }
        if (t2 > 0) {
            Point P2 = ray.getPoint(t2);
            return List.of(P2);
        }
        return null;
    }
}
