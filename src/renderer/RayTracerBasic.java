package renderer;

import geometries.Geometry;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * RayTracerBasic class is responsible for scanning the rays in a Scene and find their color
 * @author Michal Superfine & Evgi
 */
public class RayTracerBasic extends RayTracer {



    //Two constants for stopping conditions in the recursion of transparencies / reflections
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = new Double3( 1.0);

    /**
     * constructor for Ray tracer basic
     * @param scene the scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Scans the ray and looks for the first point that cuts the ray
     * @param ray the ray
     * @return the closest point that cuts the ray and null if there is no points
     */
    private GeoPoint findClosestIntersection(Ray ray){
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(ray);
        return intersections == null? null :  ray.findClosestGeoPoint(intersections);
    }

    /**
     * find the closest point that cuts the ray
     * returns its color if found any point
     * and returns the color background if it doesn't
     * @param ray a ray
     * @return the color of the closet point the ray cut
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        if (closestPoint == null)
            //ray did not intersect any geometrical object
            return scene.getBackground();
        return calcColor(closestPoint,ray);
    }

    /**
     * calculate the color at a specific point
     * @param closestPoint the point that we calculate its color
     * @param ray the ray towards the pixel
     * @return the color at this point with the ambient light, local and global effects
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray) {
        return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.getAmbientLight().getIntensity());
    }

    /**
     * calculate the color at a specific point
     * @param geoPoint the point that we calculate its color
     * @param ray the ray towards the pixel
     * @param level the level of the recursion
     * @return the color at this point with the local and global effects
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k){
        Color color = geoPoint.geometry.getEmission()
                .add(calcLocalEffects(geoPoint, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    /**
     *calculate the global effects on the color in a point
     * @param intersection the closet point intersect with the ray
     * @param ray the raya from the camera
     * @param level the level of the recursion
     * @return the global effects color
     */
    private Color calcGlobalEffects(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Point p= intersection.point;
        Geometry g=intersection.geometry;
        Vector n = g.getNormal(p);
        Color color = Color.BLACK;
        Double3 kr = g.getMaterial().kR, kkr = k.product(kr);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            Ray reflectedRay = constructReflectedRay(p, ray, n);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay, level-1, kkr).scale(kr));
        }
        Double3 kt = g.getMaterial().kT , kkt = k.product(kt);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            Ray refractedRay = constructRefractedRay(p, ray, n);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay, level- 1, kkt).scale(kt));
        }
        return color;
    }

    /**
     * calculated light contribution from all light sources
     * @param geoPoint the geo point we calculate the color of
     * @param ray ray from the camera to the point
     * @return the color from the lights at the point
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
        Vector v=ray.getDir();
        Vector n=geoPoint.geometry.getNormal(geoPoint.point);
        double nv=alignZero(n.dotProduct(v));
        if(nv==0) return Color.BLACK;
        int nShininess=geoPoint.geometry.getMaterial().shininess;
        Double3 kd=geoPoint.geometry.getMaterial().kD;
        Double3 ks=geoPoint.geometry.getMaterial().kS;

        Color color=Color.BLACK;
        for(LightSource lightSource: scene.lights){
            Vector l=lightSource.getL(geoPoint.point);
            double nl=alignZero(n.dotProduct(l));
            if(nl*nv>0){
                Double3 ktr= transparency(geoPoint,lightSource,l,n);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)){
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, nl, lightIntensity),
                            calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
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
    private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color lightIntensity) {
        Vector r=l.add(n.scale(-2*nl));
        double vr=alignZero(v.dotProduct(r));
        return lightIntensity.scale(ks.scale(Math.pow(Math.max(0,-1*vr),nShininess)));
    }

    /**
     * Calculate the diffusive component of the light at this point
     * @param kd diffusive component
     * @param nl dot-product n*l
     * @param lightIntensity light intensity
     * @return the diffusive component at the point
     */
    private Color calcDiffusive(Double3 kd, double nl, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(Math.abs(nl)));
    }

    /**
     * Checking for shading between a point and the light source
     * @param light the light source
     * @param gp the geo point which is shaded or not
     * @param l direction from light to point
     * @param n normal from the object at the point
     * @param nl dot-product n*l
     * @return if unshaded or not
     */
    private boolean unshaded(LightSource light, GeoPoint gp, Vector l, Vector n, double nl){
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.getGeometries()
                .findGeoIntersections(lightRay, light.getDistance(gp.point));
        if(intersections==null)
            return true;
        for (GeoPoint geoP : intersections)
            if(geoP.geometry.getMaterial().kT.equals(new Double3(0.0)))
               return false;
        return true;
    }

    /**
     * calculate how shaded the point is
     * @param gp the geo point we check how shaded it is
     * @param light the light source
     * @param l direction from light to point
     * @param n normal from the object at the point
     * @return the shadow level on the spot
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n){
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.getGeometries()
                .findGeoIntersections(lightRay, light.getDistance(gp.point));
        Double3 ktr= new Double3(1.0);
        if(intersections==null)
            return ktr;

        for (GeoPoint geoP : intersections) {
            ktr = ktr.product(geoP.geometry.getMaterial().kT);
            if(ktr.lowerThan(MIN_CALC_COLOR_K))
                return new Double3(0.0);
        }
        return ktr;
    }

    /**
     * calculate the reflected ray with shift in delta
     * @param p the initial point
     * @param ray the ray towards the object
     * @param n the normal
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Point p, Ray ray, Vector n) {
        //r = v - 2.(v.n).n
        Vector v = ray.getDir();
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(p, r, n);
    }

    /**
     * calculate the refracted ray with shift in delta
     * @param p the initial point
     * @param ray the ray towards the object
     * @param n the normal
     * @return the refracted ray
     */
    private Ray constructRefractedRay(Point p, Ray ray, Vector n) {

        return new Ray(p, ray.getDir(), n);
    }
}