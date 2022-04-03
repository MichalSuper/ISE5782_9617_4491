package primitives;

import java.lang.*;

public class Vector extends Point {
    /**
     * constructor
     * @param x first number value
     * @param y second number value
     * @param z third number value
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (Double3.ZERO.equals(xyz)) {
            throw new IllegalArgumentException("ZERO vector not allowed");
        }
    }

    /**
     * constructor
     * @param xyz the xyz value
     */
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
        return "Vector: "+xyz;
    }

    /**
     * subtract the point by the other point
     * @param point the point to subtract
     * @return result of subtracting vector
     */
    @Override
    public Vector subtract(Point point) {
        return super.subtract(point);
    }

    /**
     * add method
     * @param other the vector to add
     * @return algebraic added vector
     */
    public Vector add(Vector other) {
        Point result=super.add(other);
        return new Vector(result.xyz);
    }

    /**
     * multiply the vector by scalar
     * @param scalar the scalar to multiply
     * @return result of scale
     */
    public Vector scale(double scalar){
        Double3 result = xyz.scale(scalar);
        return new Vector(result);
    }

    /**
     * calculate the scalar multiple between 2 vector
     * @param v the other vector
     * @return the result of scalar multiple
     */
    public double dotProduct(Vector v){
        Double3 result = xyz.product(v.xyz);
        return result.d1+result.d2+result.d3;
    }

    /**
     * calculate the vector multiple between 2 vector
     * @param v the other vector
     * @return the vector multiple
     */
    public Vector crossProduct(Vector v){
        Vector newV= new Vector(this.xyz.d2*v.xyz.d3- this.xyz.d3*v.xyz.d2,
                this.xyz.d3*v.xyz.d1- this.xyz.d1*v.xyz.d3,
                this.xyz.d1*v.xyz.d2- this.xyz.d2*v.xyz.d1);
        return newV;
    }

    /**
     * calculate the squared length of the vector
     * @return the squared length of the vector
     */
    public double lengthSquared(){
        Point p= new Point(0,0,0);
        return super.distanceSquared(p);
    }

    /**
     * calculate the length of the vector
     * @return the length of the vector
     */
    public double length(){
        return Math.sqrt((this.lengthSquared()));
    }

    /**
     * normalize the vector
     * @return the result of the normalized vector
     */
    public Vector normalize(){ return new Vector(xyz.reduce(this.length())); }
}
