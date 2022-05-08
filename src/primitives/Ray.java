package primitives;

import java.util.List;
import java.util.Objects;
import geometries.Intersectable.GeoPoint;

/**
 * Ray class represents Ray in 3D Point and a Vector
 * @author Michal Superfine & Evgi
 */
public class Ray {
    private Point _p0;
    private Vector _dir;

    //parameter for size of first moving rays for shading rays
    private static final double DELTA = 0.1;

    /**
     * constructor
     *
     * @param p0  the point
     * @param dir the vector
     */
    public Ray(Point p0, Vector dir) {
        this._p0 = p0;
        this._dir = dir.normalize();
    }

    public Ray(Point p, Vector dir, Vector n) {
        //point + normal.scale(±DELTA)
        _dir = dir.normalize();

        double nv = n.dotProduct(_dir);

        Vector normalEpsilon = n.scale((nv > 0 ? DELTA : -DELTA));
        _p0 = p.add(normalEpsilon);
    }


    public Point getP0() {
        return _p0;
    }

    public Vector getDir() {
        return _dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(_p0, ray._p0) && Objects.equals(_dir, ray._dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_p0, _dir);
    }

    @Override
    public String toString() {
        return _p0.toString() + " , " + _dir.toString();
    }

    /**
     * calculate point on the ray
     * @param t scalar
     * @return p0+ v*t
     */
    public Point getPoint(double t){
        return _p0.add(_dir.scale(t));
    }

    /**
     * ind the closest point to the beginning of the ray
     * @param points the list of points
     * @return the closest point
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * find the closest GeoPoint to the beginning of the ray
     * @param geoPoints the geo points
     * @return the closest GeoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints){
        if(geoPoints==null || geoPoints.isEmpty())
            return null;
        GeoPoint result= null;
        Double closest= Double.MAX_VALUE;
        for (GeoPoint p: geoPoints) {
            double temp = p.point.distance(_p0);
            if (temp < closest) {
                closest = temp;
                result = p;
            }
        }
        return result;
    }
}
