package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry{
    protected Ray axisRay;
    protected double radius;

    /**
     * constructor
     * @param axisRay a ray
     * @param radius a double radius
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    /**
     * to string
     * @return description of the tube
     */
    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    /**
     * return the normal of the tube
     * @param p a point
     * @return the normal
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
