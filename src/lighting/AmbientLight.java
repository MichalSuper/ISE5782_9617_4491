package lighting;

import primitives.*;

/**
 * Ambient light foe all graphic object
 */
public class AmbientLight {

    private final Color intensity; //light intensity as Color

    /**
     * primary constructor
     * @param Ia basic intensity light
     * @param Ka attenuation factor
     */
    public AmbientLight(Color Ia, Double3 Ka){
        intensity = Ia.scale(Ka);
    }

    /**
     * getter for intensity
     * @return actual intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
