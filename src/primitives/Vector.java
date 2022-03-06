package primitives;

import java.lang.*;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (Double3.ZERO.equals(xyz)) {
            throw new IllegalArgumentException("ZERO vector not allowed");
        }
    }

    public Vector(Double3 xyz) {
        super(xyz);
        if (Double3.ZERO.equals(xyz))
            throw new IllegalArgumentException("ZERO vector not allowed");
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Vector subtract(Point point) {
        return super.subtract(point);
    }

    /**
     * add method
     *
     * @param other the vector to add
     * @return algebraic added vector
     */
    public Vector add(Vector other) {
        Point result=super.add(other);
        return new Vector(result.xyz);
    }

    public Vector scale(double scalar){
        Double3 result = xyz.scale(scalar);
        return new Vector(result);
    }
    public double dotProduct(Vector v){
        Double3 result = xyz.product(v.xyz);
        return result.d1+result.d2+result.d3;
    }
    public Vector crossProduct(Vector v){
        Vector newV= new Vector(this.xyz.d2*v.xyz.d3- this.xyz.d3*v.xyz.d2,
                this.xyz.d3*v.xyz.d1- this.xyz.d1*v.xyz.d3,
                this.xyz.d1*v.xyz.d2- this.xyz.d2*v.xyz.d1);
        return newV;
    }
    public double lengthSquared(){
        Point p= new Point(0,0,0);
        return super.distanceSquared(p);
    }
    public double length(){
        return Math.sqrt((this.lengthSquared()));
    }

    public Vector normalize(){ return new Vector(xyz.reduce(this.length())); }
}
