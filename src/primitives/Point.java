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

    @Override
    public String toString() {
        return xyz.toString();
    }

    public Point add(Vector vector) {
        Double3 newXyz = xyz.add(vector.xyz);
        return new Point(newXyz);
    }
    public Vector subtract(Point point)  {
        Double3 newXyz = xyz.subtract(point.xyz);
        return new Vector(newXyz);
    }

    public double distanceSquared(Point point){
        double result=  (xyz.d1- point.xyz.d1)*(xyz.d1- point.xyz.d1)+
        (xyz.d2- point.xyz.d2)*(xyz.d2- point.xyz.d2)+
        (xyz.d3- point.xyz.d3)*(xyz.d3- point.xyz.d3);
        return result;
    }

    public double distance(Point point) {
        return Math.sqrt(this.distanceSquared(point));
    }


}
