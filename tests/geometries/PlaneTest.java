package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlaneTest {

    @Test
    void getNormal() {
        //test if get normal function return right normal
        Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
                //new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)), "Bad normal to trinagle");
    }
    @Test
    public void testFindIntersections() {

        // ============ Equivalence Partitions Tests ==============
        Plane plane=new Plane(new Point3D(1,0,0),new Point3D(0,1,0),new Point3D(1,1,0));
        List<Point3D> resolt=plane.findIntersections(new Ray(new Point3D(0,-1,-2), new Vector(1,1,1)));
        assertEquals(1,resolt.size(),"ray crosses the plane");
        assertEquals(new Point3D(2,1,0),resolt.get(0),"ray crosses the plane");
        //TC02
        //need to check if not perallel
        resolt=plane.findIntersections(new Ray(new Point3D(1,-1,1), new Vector(1,2,0)));
        assertEquals(null,resolt,"ray doesn't crosses the plane");

        // =============== Boundary Values Tests ==================
        // TC03 ray parallel to palne and not in the plane
        resolt=plane.findIntersections(new Ray(new Point3D(1,-1,1), new Vector(1,2,0)));
        assertEquals(null,resolt,"ray doesn't crosses the plane");
        // TC04 ray parallel to palne and in the plane
        resolt=plane.findIntersections(new Ray(new Point3D(1,-1,0), new Vector(1,2,0)));
        assertEquals(null,resolt,"ray doesn't crosses the plane");
        //TC05 ray is orthogonal to the plane p0 is before the plane
        resolt=plane.findIntersections(new Ray(new Point3D(1,2,-2), new Vector(0,0,1)));
        assertEquals(1,resolt.size(),"ray crosses the plane");
        assertEquals(new Point3D(1,2,0),resolt.get(0),"ray crosses the plane");
        //TC06 ray is orthogonal to the plane p0 is in the plane
        resolt=plane.findIntersections(new Ray(new Point3D(0,0,1), new Vector(1,2,0)));
        assertEquals(null,resolt,"ray doesn't crosses the plane");
        //TC07 ray is orthogonal to the plane p0 is after the plane
        resolt=plane.findIntersections(new Ray(new Point3D(0,0,1), new Vector(1,2,2)));
        assertEquals(null,resolt,"ray doesn't crosses the plane");
        //TC08 ray is neither orthogonal nor parallel to the plane p0 is in the plane
        resolt=plane.findIntersections(new Ray(new Point3D(1,2,0), new Vector(1,1,1)));
        assertEquals(null,resolt,"ray doesn't crosses the plane");
        //TC08 ray is neither orthogonal nor parallel to the plane p0 is the plane point
        resolt=plane.findIntersections(new Ray(plane.getPointInPlane(), new Vector(1,1,1)));
        assertEquals(null,resolt,"ray doesn't crosses the plane");
    }
}