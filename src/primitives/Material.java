package primitives;

/**
 * class foe the material of object represented by its shininess, diffuse and specular
 */
public class Material {
    public double kd = 0;
    public double ks = 0;
    public int shininess =0;

    /**
     * setter for kd
     * @param kd the diffuse
     * @return the material
     */
    public Material setKd(double kd) {
        this.kd = kd;
        return  this;
    }

    /**
     * setter for ks
     * @param ks the specular
     * @return the material
     */
    public Material setKs(double ks) {
        this.ks = ks;
        return this;
    }

    /**
     * setter for shininess
     * @param shininess the shininess
     * @return the material
     */
    public Material setShininess(int shininess) {
        this.shininess = shininess;
        return this;
    }

    /**
     * setter for kd
     * @param kd the diffuse
     * @return the material
     */
    public Material setKd(Double3 kd) {
        this.kd = kd.d1;
        return  this;
    }

    /**
     * setter for ks
     * @param ks the specular
     * @return the material
     */
    public Material setKs(Double3 ks) {
        this.ks = ks.d1;
        return this;
    }


}
