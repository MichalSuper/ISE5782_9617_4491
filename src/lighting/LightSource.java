package lighting;

import primitives.*;

/**
 * interface for all the lights
 */
public interface LightSource {

    /**
     * get the light intensity at a point
     * @param p the point
     * @return the light intensity at the point as color
     */
    public Color getIntensity(Point p);

    /**
     * get the direction of the light towards the point
     * @param p the point
     * @return the direction from the light to the point
     */
    public Vector getL(Point p);

    /**
     * calculate the distance between the point to the light source
     * @param point the point
     * @return the distance from point to the light
     */
    double getDistance(Point point);
}
