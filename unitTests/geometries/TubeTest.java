package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 * @author Michal Superfine & Michal Evgi
 */
class TubeTest {

    /**
     * Test method for {@link Tube#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Tube tube = new Tube(new Ray(new Point(0,0,1),new Vector(0,-1,0)),1.0);
        Vector normal = tube.getNormal(new Point(0,8,2));
        double check = normal.dotProduct(tube.getAxisRay().getDir());

        //TC01: Test that normal is orthogonal to the tube
        assertEquals(0d,check,"normal is not orthogonal to the tube");

        //TC02: Test if the normal is proper
        assertEquals(new Vector(0,0,1),normal,"wrong normal to tube");

        // =============== Boundary Values Tests ==================
        normal = tube.getNormal(new Point(0,0,2));
        check = normal.dotProduct(tube.getAxisRay().getDir());

        //TC11: Test that normal is orthogonal to the tube
        assertEquals(0d,check,"normal is not orthogonal to the tube");

        //TC02: Test if the normal is proper
        assertEquals(new Vector(0,0,1),normal,"wrong normal to tube");
    }
}