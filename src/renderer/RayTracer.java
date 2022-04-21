package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * RayTracer class is responsible for scanning the rays in a Scene and find their color
 * @author Michal Superfine & Evgi
 */
public abstract class RayTracer {

    protected final Scene scene;

    /**
     * constructor for Ray tracer
     * @param scene the scene
     */
    public RayTracer(Scene scene){
        this.scene = scene;
    }

    /**
     * Scans the ray and looks for the first point that cuts the ray
     * returns its color if found any point
     * and returns the color background if it doesn't
     * @param ray a ray
     * @return the color of the closet point the ray cut
     */
    public abstract Color traceRay(Ray ray);
}
