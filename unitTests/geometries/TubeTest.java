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
        Vector normal = tube.getNormal(new Point(0,0.5,2)).normalize();
        double dotProduct = normal.dotProduct(tube.getAxisRay().getDir());
        assertEquals(0d,dotProduct,"normal is not orthogonal to the tube");
        boolean firstNormal = new Vector(0,0,1).equals(normal);
        boolean secondNormal = new Vector(0,0,-1).equals(normal);
        assertTrue(firstNormal||secondNormal,"wrong normal to tube");
        assertEquals(new Vector(0,0,1),normal,"wrong normal to tube");
    }
}