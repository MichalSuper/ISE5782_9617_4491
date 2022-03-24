package primitives;

import java.util.Objects;

public class Ray {
    private Point _p0;
    private Vector dir;

    /**
     * constructor
     *
     * @param p0  the point
     * @param dir the vector
     */
    public Ray(Point p0, Vector dir) {
        this._p0 = p0;
        this.dir = dir.normalize();
    }

    public Point getP0() {
        return _p0;
    }

    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(_p0, ray._p0) && Objects.equals(dir, ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_p0, dir);
    }

    /**
     * to string
     *
     * @return the description of ray
     */
    @Override
    public String toString() {
        return _p0.toString() + " , " + dir.toString();
    }

    /**
     * calculate point on the ray
     * @param t scalar
     * @return p0+ v*t
     */
    public Point getPoint(double t){
        return _p0.add(dir.scale(t));
    }
}
