package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);

    @Test
    void testZeroPoint(){
        try { // test zero vector
            new Vector(0, 0, 0);
            fail("ERROR: zero vector does not throw an exception");
        } catch (IllegalArgumentException e) {
            out.println("good: Vector 0 not created");
        }

    }
    @Test
    void add() {
        //test if the add function is good
        try {
            Vector v3=new Vector(-1,-2,-3);
            if (!v3.equals(v2.add(v1)))
                fail("Error: the add method not right");
            else
                out.println("good: add method");
        }
        catch (IllegalArgumentException e) {
            out.println("Error: can not add to zero!");
        }

    }

    @Test
    void subtract() {
        //test if the subtract function is good
        try {
            Vector v3=new Vector(3,6,9);
            if (!v3.equals(v1.subtract(v2)))
                fail("Error: the sub method not right");
            else
                out.println("good: sub method");
        }
        catch (IllegalArgumentException e) {
            out.println("Error: can not sub to zero!");
        }
    }

    @Test
    void scale() {
        //test if the scale is good
        Vector vscale = v1.scale(-0.9999999999999999999999999);
        assertEquals(new Vector(-1,-2,-3),vscale);
    }

    @Test
    void dotProduct() {
        //test if the dot product is good
        Vector v3 = new Vector(0, 3, -2);
        if (!isZero(v1.dotProduct(v3)))
            fail("ERROR: dotProduct() for orthogonal vectors is not zero");
        if (!isZero(v1.dotProduct(v2) + 28))
            fail("ERROR: dotProduct() wrong value");
    }

    @Test
    void dotProduct2() {
        Vector v3 = new Vector(0.000000000000877887, 3, -2);
        double v1DotV3 = v1.dotProduct(v3);
        assertEquals(0,alignZero(v1DotV3),"ERROR: dotProduct() for orthogonal vectors is not zero");
        assertEquals(0,v1DotV3,"ERROR: dotProduct() for orthogonal vectors is not zero");

        if (!isZero(v1.dotProduct(v2) + 28))
            fail("ERROR: dotProduct() wrong value");
    }

    @Test
    void crossProduct() {

        Vector v1 = new Vector(3.5, -5.0, 10.0);
        Vector v2 = new Vector(2.5,7,0.5);
        Vector v3 = v1.crossProduct(v2);

        assertEquals( 0, v3.dotProduct(v2), 1e-10);
        assertEquals( 0, v3.dotProduct(v1), 1e-10);

        Vector v4 = v2.crossProduct(v1);

        try {
            v3.add(v4);
            fail("Vector (0,0,0) not valid");
        }
        catch  (IllegalArgumentException e) { }
//        assertTrue(v3.length() >84);
        assertEquals(84,v3.length(),0.659);

    }

    @Test
    void lengthSquared() {
        //test if the length squared is good
        if (!isZero(v1.lengthSquared() - 14))
            fail("ERROR: lengthSquared() wrong value");
        else
            out.println("good");
    }

    @Test
    void length() {
        //test if the length is good
        if (!isZero(new Vector(0, 3, 4).length() - 5))
            fail("ERROR: length() wrong value");
        else
            out.println("good");
    }

    @Test
    void normalized() {
        // test vector normalization vs vector length and cross-product
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.get_head());
        Vector vCopyNormalize = vCopy.normalize();
        assertEquals (vCopy ,vCopyNormalize,"ERROR: normalize() function creates a new vector");

        if (!isZero(vCopyNormalize.length() - 1))
            out.println("ERROR: normalize() result is not a unit vector");
        Vector u = v.normalized();
        v.normalize();
        if (u == v)
            out.println("ERROR: normalizated() function does not create a new vector");
        if (u.equals(v))
            out.println("GOOD: content are equals");

    }

    @Test
    void normalize() {
        // test vector normalization vs vector length and cross-product
        Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v.get_head());
        Vector vCopyNormalize = vCopy.normalize();
        if (vCopy != vCopyNormalize)
            out.println("ERROR: normalize() function creates a new vector");
        if (!isZero(vCopyNormalize.length() - 1))
            out.println("ERROR: normalize() result is not a unit vector");

    }
}