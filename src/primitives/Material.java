package primitives;

/**
 * Material properties
 */
public class Material {
    /**
     * Light diffuse in the material
     * ambient + diffuse + specular = pong model
     */
    public double Kd = 0;

    /**
     * specular - The light reflected from the material
     */
    public double Ks = 0;

    public int nShininess = 0;
    /**
     * transparency - atimot(0) shakof(1)
     */
    public double Kt = 0d;
    //kt+kr<=1
    /**
     * reflection - mara(1) lo mara(0)
     */
    public double Kr = 0d;

    /**
     * @param kt
     * @return this material with transparency we get
     */
    public Material setKt(double kt) {
        Kt = kt;
        return this;
    }

    /**
     * @param kr
     * @return this material with reflection we get
     */
    public Material setKr(double kr) {
        Kr = kr;
        return this;
    }

    /**
     * @param kd
     * @return this material with diffuse we get
     */
    public Material setKd(double kd) {
        this.Kd = kd;
        return this;
    }

    /**
     * @param ks
     * @return this material with specular we get
     */
    public Material setKs(double ks) {
        this.Ks = ks;
        return this;
    }

    /**
     * @param nShininess
     * @return this material with Shininess we get
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

}
