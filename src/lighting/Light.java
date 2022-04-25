package lighting;

import primitives.Color;

abstract class Light {

    private Color intensity; //light intensity as Color

    /**
     * constructor for light
     * @param intensity the intensity color
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * getter for intensity
     * @return the intensity color
     */
    public Color getIntensity() {
        return intensity;
    }
}
