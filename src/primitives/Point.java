package primitives;

import java.util.Objects;
import java.lang.*;

public class Point {
    Double3 xyz;

    /**
     * constructor
     * @param x first number value
     * @param y second number value
     * @param z third number value
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x,y,z);
    }

    /**
     * constructor
     * @param xyz the xyz value
     */
    public Point(Double3 xyz) { this.xyz = xyz; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return xyz.equals(point.xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    /**
     * to string
     * @return the description of point
     */
    @Override
    public String toString() {
        return "Point: "+ xyz;
    }

    /**
     * Getter for x coordinate
     * @return The x coordinate
     */
    public double getX() {
        return xyz.d1;
    }

    /**
     * Getter for y coordinate
     * @return The y coordinate
     */
    public double getY() {
        return xyz.d2;
    }

    /**
     * Getter for z coordinate
     * @return The z coordinate
     */
    public double getZ() {
        return xyz.d3;
    }

    /**
     * add vector to point
     * @param vector the vector to add
     * @return the point after the adding
     */
    public Point add(Vector vector) {
        Double3 newXyz = xyz.add(vector.xyz);
        return new Point(newXyz);
    }

    /**
     * subtract the point by the other point
     * @param point the point to subtract
     * @return result of subtracting vector
     */
    public Vector subtract(Point point)  {
        Double3 newXyz = xyz.subtract(point.xyz);
        return new Vector(newXyz);
    }

    /**
     * calculate squared distance from point to point
     * @param point the other point
     * @return the squared distance
     */
    public double distanceSquared(Point point){
        double result=  (xyz.d1- point.xyz.d1)*(xyz.d1- point.xyz.d1)+
        (xyz.d2- point.xyz.d2)*(xyz.d2- point.xyz.d2)+
        (xyz.d3- point.xyz.d3)*(xyz.d3- point.xyz.d3);
        return result;
    }

    /**
     * calculate distance from point to point
     * @param point the other point
     * @return the distance
     */
    public double distance(Point point) {
        return Math.sqrt(this.distanceSquared(point));
    }


}
