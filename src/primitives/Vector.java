package primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (Double3.ZERO.equals(xyz)) {
            throw new IllegalArgumentException("ZERO vector not allowed");
        }
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

        Double3 result = new Double3(
                xyz.d1 + other.xyz.d1,
                xyz.d2 + other.xyz.d2,
                xyz.d3 + other.xyz.d3);

        if (Double3.ZERO.equals(result)) {
            throw new IllegalArgumentException("add vector resulting in ZERO vector not allowed");
        }
        return new Vector(result.d1,result.d2, result.d3);
    }

    public Vector scale(double scalar){
        Double3 result = new Double3(xyz.d1 *scalar, xyz.d2 *scalar, xyz.d3*scalar);
        if (Double3.ZERO.equals(result)) {
            throw new IllegalArgumentException("add vector resulting in ZERO vector not allowed");
        }
        return new Vector(result.d1,result.d2, result.d3);
    }
}
