package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 * @author Michal Superfine & Michal Evgi
 */
class RayTest {

    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(30, -11, -140));

        // ============ Equivalence Partitions Tests ==============
        // TC01: A point in the middle of the list is closest to the beginning of the ray
        List<Point> list = new LinkedList<Point>();
        list.add(new Point(1, 1, -100));
        list.add(new Point(-1, 1, -99));
        list.add(new Point(0, 2, -8));
        list.add(new Point(0.5, 100, -1));
        list.add(new Point(-5, 0, 50));

        assertEquals(list.get(2), ray.findClosestPoint(list),"try again");

        // =============== Boundary Values Tests ==================
        // TC11: An empty list
        list.clear();
        assertNull(ray.findClosestPoint(list), "try again");

        // TC12: The first point is closest to the beginning of the ray
        list.add(new Point(1, 1, -1));
        list.add(new Point(-1, 1, -99));
        list.add(new Point(0, 2, -8));
        list.add(new Point(0.5, 100, -1));
        list.add(new Point(-5, 0, 50));

        assertEquals(list.get(0), ray.findClosestPoint(list),"try again");

        // TC13: The last point is closest to the beginning of the ray
        list.add(new Point(0 ,1, -1));

        assertEquals(list.get(list.size() - 1), ray.findClosestPoint(list),"try again");
    }
}