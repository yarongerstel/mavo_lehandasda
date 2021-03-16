package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void getNormal() {
        //test if get normal function return right normal
        Tube tube = new Tube(new Ray(new Point3D(0, 0, 1), new Vector(0, 1, 0)), 1.0);
        assertEquals(new Vector(0, 0, 1), tube.getNormal(new Point3D(0, 0.5, 2)), "Bad normal to tube");
    }
}