package elements;

import geometries.Intersectable;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraIntegrationsTest {
    @Test
    void testSphereWithCam(){
        //TC01: 1 camera ray crosses the sphere(2 intersection)
        Sphere sphere = new Sphere(1, new Point3D(0, 0, -3));

        Camera cam = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setDistance(1)
                .setViewPlaneSize(3, 3);

        assertEquals(numOfIntersections(sphere,cam), 2, "wrong number of intersections");

        //TC02: the camera is outside the sphere and all of the ray crossing the sphere
        sphere = new Sphere(new Point3D(0, 0, -5), 4.9);
        assertEquals(numOfIntersections(sphere,cam), 18, "wrong number of intersections");

        //TC03: all the ray beside the corners crossing the sphere
        sphere=new Sphere(new Point3D(0,0,-2),1.5);
        assertEquals(numOfIntersections(sphere,cam), 10, "wrong number of intersections");

        //TC04: the camera is inside the sphere(all rays intersect once with the sphere
        sphere=new Sphere(new Point3D(0,0,0),4);
        assertEquals(numOfIntersections(sphere,cam), 9, "wrong number of intersections");

        //TC05: no intersection of the camera rays with the sphere
        sphere=new Sphere(new Point3D(0,0,1),0.5);
        assertEquals(numOfIntersections(sphere,cam), 0, "wrong number of intersections");

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
