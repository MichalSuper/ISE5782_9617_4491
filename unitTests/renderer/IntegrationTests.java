package renderer;

import static org.junit.jupiter.api.Assertions.*;

import geometries.Intersectable;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import primitives.Point;

import java.util.LinkedList;
import java.util.List;

/**
 * Unit tests for integration in Camera class
 * @author Michal Superfine & Michal Evgi
 */
public class IntegrationTests {

    /**
     * Check if the intersections is as expected
     * @param cam      camera
     * @param geo      The body that the camera intersect with
     * @param expected amount of intersections
     */
    private void assertCountIntersections(Camera cam, Intersectable geo, int expected) {
        int count = 0;

        List<Point> points = null;

        cam.setVPSize(3, 3);
        cam.setVPDistance(1);
        int nX =3;
        int nY =3;
        //view plane 3X3 (WxH 3X3 & nx,ny =3 => Rx,Ry =1)
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                var intersections = geo.findIntersections(cam.constructRay(nX, nY, j, i));
                if (intersections != null) {
                    if (points == null) {
                        points = new LinkedList<>();
                    }
                    points.addAll(intersections);
                }
                count += intersections == null ? 0 : intersections.size();
            }
        }

        System.out.format("there is %d points:%n", count);
        if (points != null) {
            for (var item : points) {
                System.out.println(item);
            }
        }
        System.out.println();

        assertEquals(expected, count, "Wrong amount of intersections");
    }

    Camera cam1 = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVPDistance(1).setVPSize(3, 3);
    Camera cam2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVPDistance(1).setVPSize(3, 3);
    /**
     * Integration tests of Camera Ray construction with Ray-Sphere intersections
     */
    @Test
    public void testSphereIntegration() {

        // TC01: Sphere 2 points
        assertCountIntersections(cam1, new Sphere(new Point(0, 0, -3),1.0), 2);

        // TC02:  Sphere 18 points
        assertCountIntersections(cam2, new Sphere( new Point(0, 0, -2.5),2.5), 18);

        // TC03:  Sphere 10 points
        assertCountIntersections(cam2, new Sphere(new Point(0, 0, -2), 2.0), 10);

        // TC04: Sphere 9 points
        assertCountIntersections(cam2, new Sphere(new Point(0, 0, -1), 4.0), 9);

        // TC05: Sphere 0 points
        assertCountIntersections(cam2, new Sphere(new Point(0, 0, 1), 0.5), 0);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Plane intersections
     */
    @Test
    public void testPlaneIntegration() {

        // TC01: Plane against camera 9 points
        assertCountIntersections(cam2, new Plane(new Point(0, 0, -2), new Vector(0, 0, 1)), 9);

        // TC02: Plane with small angle 9 points
        assertCountIntersections(cam2, new Plane(new Point(0, 0, -1.5), new Vector(0, -0.5, 1)), 9);

        // TC03: Plane parallel to lower rays 6 points
        assertCountIntersections(cam2, new Plane(new Point(0, 0, -3), new Vector(0, -1, 1)), 6);

        // TC04: Beyond Plane 0 points
        assertCountIntersections(cam2, new Plane(new Point(0, -5, 0), new Vector(-1, -9, 13)), 0);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Triangle intersections
     */
    @Test
    public void testTriangleIntegration() {

        // TC01: Small triangle 1 point
        assertCountIntersections(cam1, new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 1);

        // TC02: Medium triangle 2 points
        assertCountIntersections(cam1, new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2)), 2);
    }
}