package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 * @author Michal Superfine & Michal Evgi
 */
class SphereTest {

    /**
     * Test method for {@link Sphere#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test that the normal is proper
        Sphere sph = new Sphere(new Point(0, 0, 1), 1.0);
        assertEquals(new Vector(0, 0, 1), sph.getNormal(new Point(0, 0, 2)), "Wrong normal to sphere");
    }

    /**
     * Test method for {@link Sphere#findIntersections(Ray)} (Ray)}.
     */
    @Test
    void findIntersections() {
        Sphere sphere = new Sphere(new Point(-3,0,0), 1);
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray does not intersect the sphere.
        Ray ray = new Ray(new Point(3,0,0),new Vector(-1,0,1));
        assertNull(sphere.findIntersections(ray), "Ray that doesn't intersect sphere EP doesn't work.");

        // TC02: Ray intersects the sphere twice.
        ray = new Ray(new Point(3,0,0),new Vector(-1,0,0));
        List<Point> expRes = List.of(new Point(-4,0,0), new Point(-2,0,0));
        List<Point> res = sphere.findIntersections(ray);
        if (res.get(0).getX() > res.get(1).getX())
            res = List.of(res.get(1), res.get(0));
        assertEquals(res.size(), 2, "Ray intersects sphere twice EP doesn't work.");
        assertEquals(res, expRes, "Ray intersects sphere twice EP doesn't work.");

        // TC03: Ray intersects the sphere from inside the sphere.
        ray = new Ray(new Point(-3.5,0,0),new Vector(-1,0,0));
        expRes = List.of(new Point(-4,0,0));
        res = sphere.findIntersections(ray);
        assertEquals(res.size(), 1, "Ray from inside sphere EP doesn't work.");
        assertEquals(res, expRes, "Ray from inside sphere EP doesn't work.");


        // TC04: Ray goes to the opposite direction of the sphere (then 0 intersection points).
        ray = new Ray(new Point(3,0,0),new Vector(1,0,0));
        assertNull(sphere.findIntersections(ray), "Ray in opposite dir of sphere EP doesn't work.");


        // =============== Boundary Values Tests ==================
        // ** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        ray = new Ray(new Point(-4,0,0),new Vector(1,0,1));
        expRes = List.of(new Point(-3,0,1));
        res = sphere.findIntersections(ray);
        assertEquals(res.size(), 1, "Ray from the sphere inwards BVA doesn't work.");
        assertEquals(expRes, res, "Ray from the sphere inwards BVA doesn't work.");

        // TC12: Ray starts at sphere and goes outside (0 points)
        ray = new Ray(new Point(-4,0,0),new Vector(-1,0,-1));
        assertNull(sphere.findIntersections(ray), "Ray from the sphere outwards BVA doesn't work.");

        // ** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        ray = new Ray(new Point(-5,0,0),new Vector(1,0,0));
        expRes = List.of(new Point(-2,0,0), new Point(-4,0,0));
        res = sphere.findIntersections(ray);
        if (res.get(0).getX() < res.get(1).getX())
            res = List.of(res.get(1), res.get(0));
        assertEquals(res.size(), 2, "Ray through center 2 points BVA doesn't work.");
        assertEquals(expRes, res, "Ray through center 2 points BVA doesn't work.");

        // TC14: Ray starts at sphere and goes inside (1 points)
        ray = new Ray(new Point(-4,0,0),new Vector(1,0,0));
        expRes = List.of(new Point(-2,0,0));
        res = sphere.findIntersections(ray);
        assertEquals(res.size(), 1, "Ray on sphere through center inwards BVA doesn't work.");
        assertEquals(expRes, res, "Ray on sphere through center inwards BVA doesn't work.");

        // TC15: Ray starts inside after the center (1 points)
        ray = new Ray(new Point(-2.5,0,0),new Vector(1,0,0));
        expRes = List.of(new Point(-2,0,0));
        res = sphere.findIntersections(ray);
        assertEquals(res.size(), 1, "Ray in sphere through center BVA doesn't work.");
        assertEquals(expRes, res, "Ray in sphere through center BVA doesn't work.");

        // TC16: Ray starts at the center (1 points)
        ray = new Ray(new Point(-3,0,0),new Vector(1,0,0));
        expRes = List.of(new Point(-2,0,0));
        res = sphere.findIntersections(ray);
        assertEquals(res.size(), 1, "Ray from center BVA doesn't work.");
        assertEquals(expRes, res, "Ray from center BVA doesn't work.");

        // TC17: Ray starts at sphere and goes outside (0 points)
        ray = new Ray(new Point(-4,0,0),new Vector(-1,0,0));
        res = sphere.findIntersections(ray);
        assertNull(res, "Ray on sphere through center outwards BVA doesn't work.");

        // TC18: Ray starts after sphere (0 points)
        ray = new Ray(new Point(-1,0,0),new Vector(1,0,0));
        res = sphere.findIntersections(ray);
        assertNull(res, "Ray out of sphere through center BVA doesn't work.");


        // ** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        ray = new Ray(new Point(0,0,1),new Vector(-1,0,0));
        assertNull(sphere.findIntersections(ray), "Ray tangent to the sphere BVA doesn't work.");

        // TC20: Ray starts at the tangent point
        ray = new Ray(new Point(-3,0,1),new Vector(-1,0,0));
        assertNull(sphere.findIntersections(ray), "Ray tangent to the sphere BVA doesn't work.");

        // TC21: Ray starts after the tangent point
        ray = new Ray(new Point(-4,0,1),new Vector(-1,0,0));
        assertNull(sphere.findIntersections(ray), "Ray tangent to the sphere BVA doesn't work.");


        // ** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        ray = new Ray(new Point(-1,0,0),new Vector(0,0,1));
        assertNull(sphere.findIntersections(ray), "Ray outside orthogonal to sphere center line BVA doesn't work.");

    }
}