package primitives;

/**
 * todo
 */
public class Material {
    /**
     * diffuse
     */
    public double Kd =0;

    /**
     * specular
     */
    public double Ks =0;

    public int nShininess=0;
    /**
     * transparency
     */
    public double Kt=0d;
    //kt+kr<=1
    /**
     * reflection
     */
    public double Kr=0d;

    /**
     *
     * @param kt
     * @return this material with transparency we get
     */
    public Material setKt(double kt) {
        Kt = kt;
        return this;
    }

    /**
     *
     * @param kr
     * @return this material with reflection we get
     */
    public Material setKr(double kr) {
        Kr = kr;
        return this;
    }

    public Material setKd(double kd) {
        this.Kd = kd;
        return this;
    }

    public Material setKs(double ks) {
        this.Ks = ks;
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

}
