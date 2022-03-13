package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
        Sphere sph= new Sphere(new Point(0,0,1),1.0);
        assertEquals(new Vector(0,0,1),sph.getNormal(new Point(0,0,2)),"Wrong normal to sphere");
    }
}