package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
        assertEquals(new Vector(sqrt3,sqrt3,sqrt3),pl.getNormal(new Point(0,0,1)),"Wrong normal to plane");
    }
}