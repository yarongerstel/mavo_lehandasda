package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void getNormal() {
        //test if get normal function return right normal
        Point3D p=new Point3D(1,1,1);
        Sphere S=new Sphere(p,3);
        double sqrt3 = Math.sqrt(1d / 3);
        //Vector v=new Vector(sqrt3,sqrt3,sqrt3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), S.getNormal(new Point3D(2, 2, 2)), "Bad normal to trin");
    }

    @Test
    void findIntersections() {
        Sphere sphere = new Sphere( new Point3D(1, 0, 0),1d);
        Vector vector= new Vector(3, 1, 0);
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals(null,sphere.findIntersections(new Ray( new Vector(1, 1, 0),new Point3D(-1, 0, 0))),"Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntersections(new Ray(vector,
                new Point3D(-1, 0, 0)));
        assertEquals( 2, result.size(),"Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals( List.of(p1, p2), result,"Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)

        result=sphere.findIntersections(new Ray(vector,new Point3D(0.6651530771650466, 0.555051025721682, 0)));
        assertEquals(1, result.size(),"Wrong number of points" );
        assertEquals( p2, result.get(0),"Ray crosses sphere");
        // TC04: Ray starts after the sphere (0 points)
        result=sphere.findIntersections(new Ray(vector,new Point3D(1.83484692283495, 0.944948974278318, 0)));
        assertEquals( null, result,"Wrong number of points");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result=sphere.findIntersections(new Ray(vector,p1));
        assertEquals( 1, result.size(),"Wrong number of points");
        assertEquals( p2, result.get(0),"Ray crosses sphere");
        // TC12: Ray starts at sphere and goes outside (0 points)
        result=sphere.findIntersections(new Ray(vector,p2));
        assertEquals(null, result,"Wrong number of points");
        // **** Group: Ray's line goes through the center
        vector=new Vector(0,1,0);
        p1 = new Point3D(1, -1, 0);
        p2 = new Point3D(1, 1, 0);

        // TC13: Ray starts before the sphere (2 points)

        result=sphere.findIntersections(new Ray(vector,new Point3D(1,-2,0)));
        assertEquals( 2, result.size(),"Wrong number of points");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals( List.of(p1, p2), result,"Ray crosses sphere");
        // TC14: Ray starts at sphere and goes inside (1 points)
        result=sphere.findIntersections(new Ray(vector,p1));
        assertEquals( 1, result.size(),"Wrong number of points");
        assertEquals( p2, result.get(0),"Ray crosses sphere");
        // TC15: Ray starts inside (1 points)
        result=sphere.findIntersections(new Ray(vector,new Point3D(1,0.5,0)));
        assertEquals( 1, result.size(),"Wrong number of points");
        assertEquals( p2, result.get(0),"Ray crosses sphere");
        // TC16: Ray starts at the center (1 points)
        result=sphere.findIntersections(new Ray(new Vector(1,1,0),sphere.getCenter()));
        assertEquals( 1, result.size(),"Wrong number of points");
        assertEquals( new Point3D(1.7071067811865475,0.7071067811865475,0), result.get(0),"Ray crosses sphere");
        // TC17: Ray starts at sphere and goes outside (0 points)
        result=sphere.findIntersections(new Ray(vector,p2));
        assertEquals( null, result,"Wrong number of points");
        // TC18: Ray starts after sphere (0 points)
        result=sphere.findIntersections(new Ray(vector,new Point3D(1,2,0)));
        assertEquals( null, result,"Wrong number of points");
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        p1=new Point3D(0.7071067812,0.7071067812,0);
        vector=new Vector(1,-1,0);
        // TC19: Ray starts before the tangent point
        result=sphere.findIntersections(new Ray(vector,new Point3D(0.7071067811865475,1.7071067811865475,0)));
        assertEquals( null, result,"Wrong number of points");
        // TC20: Ray starts at the tangent point
        result=sphere.findIntersections(new Ray(vector,new Point3D(1.7071067811865475,0.7071067811865475,0)));
        assertEquals( null, result,"Wrong number of points");
        // TC21: Ray starts after the tangent point
        result=sphere.findIntersections(new Ray(vector,new Point3D(2.7071067811865475,0.29289321881345254,0)));
        assertEquals( null, result,"Wrong number of points");
        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        result=sphere.findIntersections(new Ray(new Vector(0,1,0),new Point3D(-1,0,0)));
        assertEquals(null, result,"Wrong number of points");
    }
}