package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry{
    private Point center;
    private double radius;

    /**
     * constructor
     * @param center a point
     * @param radius a double radius
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    /**
     * to string
     * @return description of the sphere
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    /**
     * return the normal of the sphere
     * @param p a point
     * @return the normal
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
