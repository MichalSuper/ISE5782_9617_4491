package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{
    private double height;

    /**
     * constructor
     * @param axisRay the ray
     * @param radius the radius
     * @param height the height
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * return the height of the cylinder
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * to string
     * @return the description of the cylinder
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    /**
     * return the normal of the cylinder
     * @param p a point
     * @return the normal
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
