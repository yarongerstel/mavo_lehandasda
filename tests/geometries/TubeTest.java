package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    @Test
    public void testConstractor() {
        try {
            new Tube(new Ray(new Vector(3, 7,5),new Point3D(2, 4, 5)), 4);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct tube");
        }
        try {
            new Tube(new Ray( new Vector(3, 7, 5),new Point3D(2, 4, 5)), -4);
            fail("Constructed a tube withe negative radius");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    void getNormal() {
        //test if get normal function return right normal
        Tube tube = new Tube(new Ray( new Vector(0, 1, 0),new Point3D(0, 0, 1)), 1.0);
        assertEquals(new Vector(0, 0, 1), tube.getNormal(new Point3D(0, 0.5, 2)), "Bad normal to tube");
    }

    @Test
    void getRadius() {
    }

    @Test
    void findIntersections() {
    }
}