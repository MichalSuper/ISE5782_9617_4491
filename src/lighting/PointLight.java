package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;


/**
 * class for a point light with position and without direction
 * @author michal superfine & evgi
 */
public class PointLight extends Light implements LightSource{

    private Point position;
    private double kC, kL, kQ;
    private double radius=10;

    private final int numOfRays=10;
    /**
     * constructor of point light
     * @param intensity=the color of the light
     * @param position=the position of the light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
        this.kC = 1;
        this.kL = 0;
        this.kQ = 0;
    }

    /**
     * setter for kc
     * @param kC the constant attenuation
     * @return the point light
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * setter for kl
     * @param kL the linear attenuation
     * @return the point light
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * setter for kq
     * @param kQ the quadratic attenuation
     * @return the point light
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     *getter for intensity
     * @param p=the point that we return its color
     * @return the intensity color
     */
    @Override
    public Color getIntensity(Point p) {
        // IL / (kc + kl *distance + kq * distanceSquared)
        double distance = p.distance(position);
        double distanceSquared = p.distanceSquared(position);

        double factor = kC + kL * distance + kQ * distanceSquared;

        return getIntensity().reduce(factor);
    }

    /**
     * getter for the light direction
     * @param p=the point the light comes to
     * @return the light direction
     */
    @Override
    public Vector getL(Point p) {
        if(!p.equals(position))
            return p.subtract(position).normalize();
        return null;
    }

    @Override
    public Vector[][] getListL(Point p) {
        Vector[][] vectors = new Vector[2 * numOfRays][2 * numOfRays];
        int row = 0;
        int column;
        double y = getL(p).getY();
        for (double i = -radius; i < radius; i += radius / numOfRays, ++row) {
            column = 0;
            for (double j = -radius; j < radius; j += radius / numOfRays, ++column) {
                if (i != 0 && j != 0) {
                    Point point = position.add(new Vector(i, y, j));
                    vectors[row][column] = (p.subtract(point).normalize());
                } else
                    vectors[row][column] = getL(p);
            }
        }
        return vectors;
    }

    /**
     * return the distance between the light and the point
     * @param point the point
     * @return return the distance from light to point
     */
    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}
