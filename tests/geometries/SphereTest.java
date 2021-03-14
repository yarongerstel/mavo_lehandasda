package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void getNormal() {
        Point3D p=new Point3D(1,1,1);

        Sphere S=new Sphere(p,3);
        double sqrt3 = Math.sqrt(1d / 3);
        //Vector v=new Vector(sqrt3,sqrt3,sqrt3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), S.getNormal(new Point3D(2, 2, 2)), "Bad normal to trin");
    }
}