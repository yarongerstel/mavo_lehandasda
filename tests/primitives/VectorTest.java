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
        Vector vscale = v1.scale(-0.9999999999999999999999999);
        assertEquals(new Vector(-1,-2,-3),vscale);
    }

    @Test
    void dotProduct() {
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

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals( v1.length() * v3.length(), vr.length(), 0.00001,"crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}

    }

    @Test
    void lengthSquared() {
        if (!isZero(v1.lengthSquared() - 14))
            fail("ERROR: lengthSquared() wrong value");
        else
            out.println("good");
    }

    @Test
    void length() {
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