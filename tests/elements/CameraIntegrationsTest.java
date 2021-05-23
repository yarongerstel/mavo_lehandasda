package elements;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraIntegrationsTest {
    /**
     * Finds multiple cuts with sphere
     */
    @Test
    void testSphereWithCam(){
        //TC01: 1 camera ray crosses the sphere(2 intersection)
        Sphere sphere = new Sphere(1, new Point3D(0, 0, -3));

        Camera cam = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        assertEquals(numOfIntersections(sphere,cam), 2, "wrong number of intersections");

        //TC02: the camera is outside the sphere and all of the ray crossing the sphere
        sphere = new Sphere(4.9,new Point3D(0, 0, -5));
        assertEquals(numOfIntersections(sphere,cam), 18, "wrong number of intersections");

        //TC03: all the ray beside the corners crossing the sphere
        sphere=new Sphere(1.5,new Point3D(0,0,-2));
        assertEquals(numOfIntersections(sphere,cam), 10, "wrong number of intersections");

        //TC04: the camera is inside the sphere(all rays intersect once with the sphere
        sphere=new Sphere(4,new Point3D(0,0,-1));
        assertEquals(numOfIntersections(sphere,cam), 9, "wrong number of intersections");

        //TC05: no intersection of the camera rays with the sphere
        sphere=new Sphere(0.5,new Point3D(0,0,1));
        assertEquals(numOfIntersections(sphere,cam), 0, "wrong number of intersections");

    }
    /**
     * Finds multiple cuts with plan
     */
    @Test
    public void cameraRaysPlaneIntersections(){
        Camera cam = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        //TC01: the plane is ortogonal to the towards vertor of the camera(9 intersection)
        Plane plane=new Plane(new Point3D(1,3,-3),new Point3D(5,2,-3),new Point3D(7,1,-3));
        assertEquals(9,numOfIntersections(plane,cam),"9 intersections");

        //TC02: the plane have a little shift (still 9 intersection)
        plane=new Plane(new Point3D(1,3,-3),new Point3D(5,2,-3.4),new Point3D(7,1,-3.4));
        assertEquals(9,numOfIntersections(plane,cam),"9 intersections");

//        //TC03: the place have a bif shipt the 3 lower rays dousnt intersect with the plane(6 intersection)
        plane=new Plane(new Point3D(1,3,-3),new Point3D(5,-2,-10),new Point3D(-7,-1,-10));
        assertEquals(6,numOfIntersections(plane,cam),"6 intersections");
    }
    /**
     * Finds multiple cuts with Triangle
     */
    @Test
    public void cameraRaysTriangleIntersections(){
        Camera cam = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        //TC01: only the ray that go through the center intersect with the triangle
        Triangle triangle=new Triangle(new Point3D(0,1,-2),new Point3D(1,-1,-2),new Point3D(-1,-1,-2));
        assertEquals(1,numOfIntersections(triangle,cam),"1 intersections");

        //TC02: the ray that go through the center and the one above her intersect with the triangle
        triangle=new Triangle(new Point3D(0,20,-2),new Point3D(1,-1,-2),new Point3D(-1,-1,-2));
        assertEquals(2,numOfIntersections(triangle,cam),"2 intersections");

    }

    /***
     * help function that returns the numbers of intersection of camera rays and the shape
     *the help function is for 3x3 screen
     * @param shape the shape that we check if there are intersection
     * @param cam the cmera
     * @return the number of intersections between all the camera ray and the shape
     */
    int numOfIntersections(Intersectable shape,Camera cam){
        int numOfIntersections = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = cam.constructRayThroughPixel(3, 3, i, j);
                List intersection=shape.findIntersections(ray);
                numOfIntersections+=intersection!= null? intersection.size() :0;
            }
        }
        return numOfIntersections;
    }

}
