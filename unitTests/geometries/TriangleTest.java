package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class
 * @author Michal Superfine & Michal Evgi
 */
class TriangleTest {
    /**
     * Test method for {@link Triangle#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr = new Triangle(new Point(0,0,1),new Point(1,0,0),new Point(0,0,0));
        assertEquals(new Vector(0, 1, 0), tr.getNormal(new Point(0.3, 0, 0.3)), "Bad normal to triangle");
    }

    /**
     * Test method for {@link Triangle#findIntersections(Ray)} (Ray)}.
     */
    @Test
    void findIntersections() {
        Triangle triangle = new Triangle(new Point(1,0,0), new Point(0,1,0),new Point(0,0,0));

        // ============ Equivalence Partitions Tests ==============
        //TC01: the ray begins inside the triangle
        assertNull(triangle.findIntersections(new Ray(new Point(0.5,0.5,0), new Vector(1,0,0))),"Ray from triangle outside. EP doesn't work.");

        //TC02: the ray begins outside against edge
        assertNull(triangle.findIntersections(new Ray(new Point(0.5,-1,0), new Vector(0,1,0))),"Ray from outside of triangle against edge. EP doesn't work.");

        //TC03: the ray begins outside against vertex
        assertNull(triangle.findIntersections(new Ray(new Point(2,0,0), new Vector(-1,0,0))),"Ray from outside of triangle against vertex. EP doesn't work.");

        // =============== Boundary Values Tests ==================
        //TC11: the ray begins on edge
        assertNull(triangle.findIntersections(new Ray(new Point(0.5,0,0), new Vector(0,0,1))),"Ray begins on edge against outside. BVA doesn't work.");

        //TC12: the ray begins in vertex
        assertNull(triangle.findIntersections(new Ray(new Point(0,1,0), new Vector(0,0,1))),"Ray begins in vertex against outside. BVA doesn't work.");

        //TC13: the ray begins on edge's continuation
        assertNull(triangle.findIntersections(new Ray(new Point(0,2,0), new Vector(0,-1,0))),"Ray begins on edge's continuation against outside. BVA doesn't work.");

    }
}