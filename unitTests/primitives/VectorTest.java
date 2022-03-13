package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 * @author Michal Superfine & Michal Evgi
 */
class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(0, 3, -2);
    Vector v3 = new Vector(-2, -4, -6);

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void scale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the scaled vector has a scaled length
        assertEquals(3 * v1.length(), v1.scale(3).length(), "scale() gave wrong result");

        // =============== Boundary Values Tests ==================
        // TC11: Test that scaling a vector by 0 throws an exception
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0),
                "scale() by 0 gave wrong result");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}.
     */
    @Test
    void dotProduct() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that dot product return value is proper
        assertEquals(-28, v1.dotProduct(v3), "ERROR: dotProduct() wrong value");

        // TC02: test that orthogonal vectors return 0 on dot product
        assertTrue(isZero(v1.dotProduct(v2)), "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    void normalize() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that normalize create a unit vector
        assertEquals(u.length(), 1.0, "ERROR: the normalized vector is not a unit vector");

        // TC02: Test that the vectors are co-lined
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(u), "ERROR: the normalized vector is not parallel to the original one");
    }

    /**
     * Test method for {@link Vector#lengthSquared()}.
     */
    @Test
    void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that lengthSquared value is proper
        assertEquals(14, v1.lengthSquared(), "ERROR: lengthSquared() gave wrong value");
    }

    /**
     * Test method for {@link Vector#length()}.
     */
    @Test
    void length() {
        Vector v = new Vector(0, 3, 4);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that length value is proper
        assertEquals(5, v.length(), "ERROR: length() gave wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)}.
     */
    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that add is proper
        assertEquals((v1.add(v2)), new Vector(1, 5, 1), "add() gave wrong result");

        // =============== Boundary Values Tests ==================
        //TC11: Test v1 plus -v1 throw exception
        assertThrows(IllegalArgumentException.class, () -> v1.add(new Vector(-1, -2, -3)), "add() gave wrong result");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(Vector)}.
     */
    @Test
    void crossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3)
                , "crossProduct() for parallel vectors does not throw an exception");

    }
}