package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;

/**
 * RayTracerBasic class is responsible for scanning the rays in a Scene and find their color
 * @author Michal Superfine & Evgi
 */
public class RayTracerBasic extends RayTracer {

    /**
     * constructor for Ray tracer basic
     * @param scene the scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Scans the ray and looks for the first point that cuts the ray
     * returns its color if found any point
     * and returns the color background if it doesn't
     * @param ray a ray
     * @return the color of the closet point the ray cut
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(ray);
        if (intersections == null)
            //ray did not intersect any geometrical object
            return scene.getBackground();
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * calculate the color at a specific point
     * @param geoPoint a geo point
     * @return the color at this point
     */
    private Color calcColor(GeoPoint geoPoint) {
        return scene.getAmbientLight().getIntensity().add(geoPoint.geometry.getEmission());
    }
}
