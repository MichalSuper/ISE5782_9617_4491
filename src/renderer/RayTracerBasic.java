package renderer;

import lighting.LightSource;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * RayTracerBasic class is responsible for scanning the rays in a Scene and find their color
 * @author Michal Superfine & Evgi
 */
public class RayTracerBasic extends RayTracer {

    //parameter for size of first moving rays for shading rays
    private static final double DELTA = 0.1;

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
        return calcColor(closestPoint,ray);
    }

    /**
     * calculate the color at a specific point
     * @param geoPoint a geo point
     * @return the color at this point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        Color ambientLight = scene.getAmbientLight().getIntensity();
        Color emissionLight = geoPoint.geometry.getEmission();
        Color localEffects = calcLocalEffects(geoPoint,ray);
        return ambientLight.add(emissionLight).add(localEffects);
    }

    /**
     * calculated light contribution from all light sources
     * @param geoPoint the geo point we calculate the color of
     * @param ray ray from the camera to the point
     * @return the color from the lights at the point
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Vector v=ray.getDir();
        Vector n=geoPoint.geometry.getNormal(geoPoint.point);
        double nv=alignZero(n.dotProduct(v));
        if(nv==0) return Color.BLACK;
        int nShininess=geoPoint.geometry.getMaterial().shininess;
        double kd=geoPoint.geometry.getMaterial().kd;
        double ks=geoPoint.geometry.getMaterial().ks;

        Color color=Color.BLACK;
        for(LightSource lightSource: scene.lights){
            Vector l=lightSource.getL(geoPoint.point);
            double nl=alignZero(n.dotProduct(l));
            if(nl*nv>0){
                if (unshaded(lightSource, geoPoint, l, n, nl)){
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point);
                    color = color.add(calcDiffusive(kd, nl, lightIntensity), calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    /**
     * Calculate the Specular component of the light at this point
     * @param ks specular component
     * @param l direction from light to point
     * @param n normal from the object at the point
     * @param nl dot-product n*l
     * @param v direction from the camera to the point
     * @param nShininess shininess level
     * @param lightIntensity light intensity
     * @return the Specular component at the point
     */
    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color lightIntensity) {
        Vector r=l.add(n.scale(-2*nl));
        double vr=alignZero(v.dotProduct(r));
        return lightIntensity.scale(ks*Math.pow(Math.max(0,-1*vr),nShininess));
    }

    /**
     * Calculate the diffusive component of the light at this point
     * @param kd diffusive component
     * @param nl dot-product n*l
     * @param lightIntensity light intensity
     * @return the diffusive component at the point
     */
    private Color calcDiffusive(double kd, double nl, Color lightIntensity) {
        return lightIntensity.scale(Math.abs(nl)*kd);
    }

    /**
     * Checking for shading between a point and the light source
     * @param light the light source
     * @param gp the peo point which is shaded or not
     * @param l direction from light to point
     * @param n normal from the object at the point
     * @param nl dot-product n*l
     * @return
     */
    private boolean unshaded(LightSource light, GeoPoint gp, Vector l, Vector n, double nl){
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nl < 0 ? DELTA : -1*DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay, light.getDistance(gp.point));
        return intersections==null;
    }
}