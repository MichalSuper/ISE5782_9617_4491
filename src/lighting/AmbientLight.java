package lighting;

import primitives.*;

/**
 * Ambient light foe all graphic object
 * @author Michal Superfine & Evgi
 */
public class AmbientLight extends Light{

    /**
     * primary constructor
     * @param Ia basic intensity light
     * @param Ka attenuation factor
     */
    public AmbientLight(Color Ia, Double3 Ka){
        super(Ia.scale(Ka));
    }

    /**
     * default constructor
     */
    public AmbientLight(){
        super(Color.BLACK);
    }

}
