package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{
    private Point q0;
    private Vector normal;

    /**
     * constructor
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Plane(Point p1, Point p2, Point p3){
        q0= p1;
        normal= null;
    }

    /**
     * constructor
     * @param p a point
     * @param v a vector
     */
    public Plane(Point p, Vector v){
        q0= p;
        normal= v.normalize();
    }

    /**
     * to string
     * @return description of the plane
     */
    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    public Point getQ0(){
        return this.q0;
    }

    public Vector getNormal(){
        return this.normal;
    }

    /**
     * return the normal of the plane
     * @param p a point
     * @return normal
     */
    public Vector getNormal(Point p){
        return this.normal;
    }
}
