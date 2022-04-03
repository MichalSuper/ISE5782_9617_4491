package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 * @author Michal Superfine & Michal Evgi
 */
class PlaneTest {

    /**
     * Test method for {@link Plane#Plane(Point, Point, Point)}.
     */
    @Test
    void testConstructor() {
        // =============== Boundary Values Tests ==================
        Point p0 = new Point(1, 2, 3);
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(4, 5, 6);

        //TC11: Test that constructor doesn't accept two points equals
        assertThrows(IllegalArgumentException.class, () -> new Plane(p0, p1, p2),
                "the constructor must throw exception when two point equals");

        //TC12: Test that constructor doesn't accept three points that are co-lined
        Point p3 = new Point(2, 4, 6);
        Point p4 = new Point(4, 8, 12);
        assertThrows(IllegalArgumentException.class, () -> new Plane(p0, p3, p4),
                "the constructor must throw exception when three points are co-lined");
    }

    /**
     * Test method for {@link Plane#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test that the normal is proper
        Plane pl= new Plane(new Point(0,0,1),new Point(1,0,0),new Point(0,1,0));
        double sqrt3= Math.sqrt(1d/3);
        Vector norm=pl.getNormal(new Point(0,0,1));
        boolean normal1= new Vector(sqrt3,sqrt3,sqrt3).equals(norm);
        boolean normal2= new Vector(-sqrt3,-sqrt3,-sqrt3).equals(norm);
        assertTrue(normal1||normal2,"Wrong normal to plane");
    }

    /**
     * Test method for {@link Plane#findIntersections(Ray)} (Ray)}.
     */
    @Test
    void findIntersections() {
       Plane plane = new Plane(new Point(-0.5,-0.5,0), new Point(1,0,0), new Point(0,1,0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane.
        Ray ray = new Ray(new Point(1,1,1),new Vector(-1,0,-1));
        List<Point> expRes = List.of(new Point(0,1,0));
        List<Point> res = plane.findIntersections(ray);
        assertEquals(res.size(), 1, "Ray intersects the plane EP doesn't work.");
        assertEquals(res, expRes, "Ray intersects the plane EP doesn't work.");

        // TC02: Ray does not intersect the plane.
        ray = new Ray(new Point(1,1,1),new Vector(1,1,2));
        assertNull(plane.findIntersections(ray), "Ray does not intersects the plane EP doesn't work.");


        // =============== Boundary Values Tests ==================
        // TC11: Ray is parallel and included in the plane.
        ray = new Ray(new Point(0,1,0),new Vector(1,0,0));
        assertNull(plane.findIntersections(ray), "Ray is parallel and included in the plane BVA doesn't work.");

        // TC12: Ray is parallel and not included in the plane.
        ray = new Ray(new Point(0,1,1),new Vector(1,0,0));
        assertNull(plane.findIntersections(ray), "Ray is parallel and not included in the plane BVA doesn't work.");

        // TC13: Ray is orthogonal to the plane and before the plane.
        ray = new Ray(new Point(0,1,1),new Vector(0,0,-1));
        expRes = List.of(new Point(0,1,0));
        res = plane.findIntersections(ray);
        assertEquals(res.size(), 1, "Ray is orthogonal to the plane and before the plane BVA doesn't work.");
        assertEquals(res, expRes, "Ray is orthogonal to the plane and before the plane BVA doesn't work.");

        // TC14: Ray is orthogonal to the plane and on the plane.
        ray = new Ray(new Point(0,2,0),new Vector(0,0,-1));
        assertNull(plane.findIntersections(ray), "Ray is orthogonal to the plane and in the plane BVA doesn't work.");

        // TC15: Ray is orthogonal to the plane and after the plane.
        ray = new Ray(new Point(0,2,-1),new Vector(0,0,-1));
        assertNull(plane.findIntersections(ray), "Ray is orthogonal to the plane and after the plane BVA doesn't work.");

        // TC16: Ray begins in the same point which appears as the plane's reference point.
        ray = new Ray(plane.getQ0(), new Vector(1,1,1));
        assertNull(plane.findIntersections(ray), "Ray begins in the same point which appears as the plane's reference point BVA doesn't work.");

        // TC17: Ray begins in the same plane but the ray it's not parallel or orthogonal to the plane
        ray = new Ray(new Point(0,1,0), new Vector(1,1,-1));
        assertNull(plane.findIntersections(ray), "Ray begins at the plane but the ray it's not parallel or orthogonal to the plane BVA doesn't work.");
    }
}