package primitives;

public class Material {
    public double kd = 0;
    public double ks = 0;
    public int shininess =0;


    public Material setKd(double kd) {
        this.kd = kd;
        return  this;
    }

    public Material setKs(double ks) {
        this.ks = ks;
        return this;
    }

    public Material setShininess(int shininess) {
        this.shininess = shininess;
        return this;
    }

    public Material setKd(Double3 kd) {
        this.kd = kd.d1;
        return  this;
    }

    public Material setKs(Double3 ks) {
        this.ks = ks.d1;
        return this;
    }


}
