package primitives;

import java.util.Objects;

public class Ray {
    private Point p0;
    private Vector dir;

    /**
     * constructor
     * @param p0 the point
     * @param dir the vector
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    /**
     * to string
     * @return the description of ray
     */
    @Override
    public String toString() {
        return p0.toString() + " , "+dir.toString();
    }
}
