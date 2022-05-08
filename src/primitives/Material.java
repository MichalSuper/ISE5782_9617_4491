package primitives;

/**
 * class foe the material of object represented by its shininess, diffuse and specular
 */
public class Material {
    public Double3 kD = new Double3(0.0);
    public Double3 kS = new Double3(0.0);
    public int shininess =0;
    public Double3 kT= new Double3(0.0);
    public Double3 kR= new Double3(0.0);

    /**
     * setter for kt
     * @param kT the promotes transparency
     * @return the material
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * setter for kr
     * @param kR Coefficient of reflection
     * @return the material
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * setter for kt
     * @param kT the promotes transparency
     * @return the material
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * setter for kr
     * @param kR Coefficient of reflection
     * @return the material
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * setter for kd
     * @param kd the diffuse
     * @return the material
     */
    public Material setKd(double kd) {
        this.kD = new Double3(kd);
        return  this;
    }

    /**
     * setter for ks
     * @param ks the specular
     * @return the material
     */
    public Material setKs(double ks) {
        this.kS = new Double3(ks);
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
        this.kD = kd;
        return  this;
    }

    /**
     * setter for ks
     * @param ks the specular
     * @return the material
     */
    public Material setKs(Double3 ks) {
        this.kS = ks;
        return this;
    }


}
