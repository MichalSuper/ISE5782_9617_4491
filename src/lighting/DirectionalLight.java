package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * class for a direction light without position
 * @author michal superfine & evgi
 */
public class DirectionalLight extends Light implements LightSource{

    private Vector direction;

    /**
     *constructor of direction light
     * @param intensity=the color of the light
     * @param direction=the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * getter for intensity
     * @param p= the point that we return its color
     * @return the intensity color
     */
    @Override
    public Color getIntensity(Point p) {
       return super.getIntensity();
    }

    /**
     * getter for the light direction
     * @param p=the point the light comes to
     * @return the light direction
     */
    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public Vector[][] getListL(Point p) {
        Vector[][] v = {{getL(p)}};
        return v;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
