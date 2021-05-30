package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RayTest {

    @Test
    void findClosestPoint() {
        Ray ray = new Ray(Point3D.ZERO, new Vector(0, 0, 1));
        // \closest point is in the middle of the list
        List<Point3D> list = new LinkedList<>() {{
            add(new Point3D(-100, 90, 100));
            add(new Point3D(48, 48, 48));
            add(new Point3D(0, 5, 1));
            add(new Point3D(-60, 60, 60));
            add(new Point3D(0, 0, -90));
        }};
        assertEquals(list.get(2), ray.findClosestPoint(list), "wrong point!");
        //no points
        assertNull(ray.findClosestPoint(null), "supposed to be null!");
        // closest point is at the end of the list
        list.add(new Point3D(2, 2, 2));
        assertEquals(list.get(list.size() - 1), ray.findClosestPoint(list), "wrong point!");
        // the closest point is at the beginning of the list
        list.add(0, new Point3D(1,1,1));
        assertEquals(list.get(0), ray.findClosestPoint(list), "wrong point!");
    }
}