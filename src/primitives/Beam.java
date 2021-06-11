package primitives;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static primitives.Util.isZero;

/**
 * Class Beam presents a A group (list) of rays around a main ray,
 * expanding the lighting capabilities of the scene
 */
public class Beam {
    public final List<Ray> _rayList;
    /**
     * Constructor for beam through a circle(radios of light source)
     * Is used in soft shadow
     * @param mainRay the main ray
     * @param pC the center of the circle
     * @param radius the radius of the circle
     * @param amountRays the amount of rays to throw
     */
    public Beam(Ray mainRay, Point3D pC, double radius, int amountRays) {
        _rayList = new LinkedList<>();
        //adding the main ray to the list
        _rayList.add(mainRay);
        if (!Util.isZero(radius)) {
            double x, y;
            Ray r;
            Vector vx = mainRay.getDirection().getVertical(),
                    vy = vx.crossProduct(mainRay.getDirection());//two vertical vectors
            for (int i = 1; i < amountRays; i++) {
                do {
                    //create random ray in the boundary of the circle that doesn't exist in the list already
                    double cosTeta = ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D);
                    double sinTeta = Math.sqrt(1 - cosTeta * cosTeta); // trigonometric identity
                    double d = ThreadLocalRandom.current().nextDouble(-radius, radius);
                    // Convert polar coordinates to Cartesian ones
                    x = d * cosTeta;
                    y = d * sinTeta;
                    r = constructRay(mainRay, pC, x, y, vx, vy);
                } while (_rayList.contains(r));// check if the new ray is already exists in the list
                _rayList.add(r);
            }
        }
    }


    /**
     * Construct a new ray for beam list
     * by moving the destination of the main ray
     * @param ray the main ray
     * @param pC the center of the circle
     * @param x the factor of x axis
     * @param y the factor of y axis
     * @param vx the x axis
     * @param vy the y axis
     * @return new ray start at p0 and directed by moving the main ray slightly
     */
    private Ray constructRay(Ray ray, Point3D pC, double x, double y, Vector vx, Vector vy) {
        Point3D point = pC;
        // create a new destination point (for the new vector)
        if (!isZero(x)) point = point.add(vx.scale(x)); // new x coord inside the radios of the light source
        if (!isZero(y)) point = point.add(vy.scale(y));// new y coord inside the radios of the light source
        return new Ray(ray.getPoint(),point.subtract(ray.getPoint()));
    }

}
