package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Triangle class represents Triangle in 3 3D points
 * @author Michal Superfine & Evgi
 */
public class Triangle extends Polygon {
    /**
     * find all intersection points {@link Point}
     * that intersect with a specific ray{@link Ray}
     * @param ray ray pointing towards the triangle
     * @return immutable list of intersection geo points
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> result = plane.findGeoIntersectionsHelper(ray);
        for (GeoPoint g : result)
            g.geometry=this;

        //Check if the ray intersect the plane.
        if (result == null) {
            return null;
        }

        Vector v1 = vertices.get(0).subtract(ray.getP0());
        Vector v2 = vertices.get(1).subtract(ray.getP0());
        Vector v3 = vertices.get(2).subtract(ray.getP0());

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        Vector v = ray.getDir();

        double vn1 = alignZero(v.dotProduct(n1));
        double vn2 = alignZero(v.dotProduct(n2));
        double vn3 = alignZero(v.dotProduct(n3));

        //The point is inside if all 𝒗 ∙ 𝒏𝒊 have the same sign (+/-)
        if ((vn1 > 0 && vn2 > 0 && vn3 > 0) || (vn1 < 0 && vn2 < 0 && vn3 < 0)) {
            return result;
        }
        return null;
    }

    /**
     * constructor for triangle
     *
     * @param p1 first point{@link Point}
     * @param p2 second point
     * @param p3 third point
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }


}
