package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TriangleTest {

    @Test
    void findIntersections() {
        Triangle triangleTest = new Triangle(new Point3D(2,0,0),new Point3D(0,2,0),new Point3D(-2,0,0));
        Point3D p=new Point3D(0.5,0.5,0);
        Vector vector=new Vector(1,1,1);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray is inside triangle
        List<Point3D> result=triangleTest.findIntersections(new Ray(new Point3D(-0.5,-0.5,-1), vector));
        assertEquals(p,result.get(0),"Ray crosses triangle");
        //TC02 נקודה בחוץ בזווית בין הצלעות
        result=triangleTest.findIntersections(new Ray(new Point3D(-5,-2,-1), vector));
        assertEquals(null,result,"ray doesn't crosses the triangle");
        //TC03 נקודה בחוץ ליד הצלע\
        result=triangleTest.findIntersections(new Ray(new Point3D(-3,0,-1), vector));
        assertEquals(null,result,"ray doesn't crosses the triangle");
        // =============== Boundary Values Tests ==================
        //TC04 Ray begins inside the triangle
        result=triangleTest.findIntersections(new Ray(new Point3D(0.5,0.5,0), vector));
        assertEquals(null,result,"ray doesn't crosses the triangle");
        //TC05 קרן מתחילה בחוץ בין הצלעות
        result=triangleTest.findIntersections(new Ray(new Point3D(0.5,0.5,0), vector));
        assertEquals(null,result,"ray doesn't crosses the triangle");
        //TC06
        result=triangleTest.findIntersections(new Ray(new Point3D(-2,1,0), vector));
        assertEquals(null,result,"ray doesn't crosses the triangle");
        //TC07 Ray line is on the side of the triangle
        result=triangleTest.findIntersections(new Ray(new Point3D(-2,0,-1), vector));
        assertEquals(null,result,"ray doesn't crosses the triangle");
        //TC08 Ray line is on a vertex of the triangle
        result=triangleTest.findIntersections(new Ray(new Point3D(-3,-1,-1), vector));
        assertEquals(null,result,"ray doesn't crosses the triangle");
        //TC09 Ray line is on the continuance of the triangle's side
        result=triangleTest.findIntersections(new Ray(new Point3D(-4,-2,-1), vector));
        assertEquals(null,result,"ray doesn't crosses the triangle");
        //TC10 Ray starts on the side of the triangle
        result=triangleTest.findIntersections(new Ray(new Point3D(-1,1,0), vector));
        assertEquals(null,result,"ray doesn't crosses the triangle");
        //TC11 Ray starts on a vertex of the triangle
        result=triangleTest.findIntersections(new Ray(new Point3D(-2,0,0), vector));
        assertEquals(null,result,"ray doesn't crosses the triangle");
        //TC12 Ray starts on the continuance of the triangle's side
        result=triangleTest.findIntersections(new Ray(new Point3D(-3,-1,0), vector));
        assertEquals(null,result,"ray doesn't crosses the triangle");

        }


    @Test
    void getNormal() {
        Triangle tr=new Triangle(new Point3D(0,0,0),new Point3D(0,1,2),new Point3D(6,5,7));
        double sqrt=Math.sqrt(21);
        double cons=sqrt/21;
        Vector n=new Vector(-cons,4*cons,-2*cons);
        assertEquals(tr.getNormal(new Point3D(3,4,4)),n,"bad normal to triangle");
    }
}