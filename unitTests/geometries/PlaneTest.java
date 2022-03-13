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
     * Test method for {@link Plane#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Plane pl= new Plane(new Point(0,0,1),new Point(1,0,0),new Point(0,1,0));
        double sqrt3= Math.sqrt(1d/3);
        assertEquals(new Vector(sqrt3,sqrt3,sqrt3),pl.getNormal(new Point(0,0,1)),"Wrong normal to sphere");
    }

}