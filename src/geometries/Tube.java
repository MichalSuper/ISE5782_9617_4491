package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Plane Tube represents Tube in ray and radius
 * @author Michal Superfine & Evgi
 */
public class Tube implements Geometry{
    final protected Ray axisRay;
    final protected double radius;

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
        double t= axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()));
        if (isZero(t))
            return p.subtract(axisRay.getP0()).normalize();

        //calculate the projection of the vector from p to p0 on ray
        //getDir return normalized vector, so we don't need to divide by its length
        Vector projection= axisRay.getDir().scale(t);
        return p.subtract(axisRay.getP0().add(projection)).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
