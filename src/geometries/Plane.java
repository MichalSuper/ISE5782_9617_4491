package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{
    private Point q0;
    private Vector normal;

    public Plane(Point p1, Point p2, Point p3){
        q0= p1;
        normal= null;
    }

    public Plane(Point p, Vector v){
        q0= p;
        normal= v.normalize();
    }

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

    public Vector getNormal(Point p){
        return this.normal;
    }
}
