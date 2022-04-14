package renderer;

import org.junit.jupiter.api.Test;

import lighting.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;
import static java.awt.Color.*;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */

public class LightsTests {
    private final Scene scene1 = new Scene.SceneBuilder("Test scene").build();
    private final Scene scene2 = new Scene.SceneBuilder("Test scene") //
            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15))).build();
    private final Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVPSize(150, 150) //
            .setVPDistance(1000);
    private final Camera camera2 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVPSize(200, 200) //
            .setVPDistance(1000);

    private final Point[] p = { // The Triangles' vertices:
            new Point(-110, -110, -150), // the shared left-bottom
            new Point(80, 100, -150), // the shared right-top
            new Point(110, -110, -150), // the right-bottom
            new Point(-75, 85, 0) }; // the left-top
    private final Point trPL = new Point(50, 30, -100); // Triangles test Position of Light
    private final Point spPL = new Point(-50, -50, 25); // Sphere test Position of Light
    private final Color trCL = new Color(800, 500, 250); // Triangles test Color of Light
    private final Color spCL = new Color(800, 500, 0); // Sphere test Color of Light
    private final Vector trDL = new Vector(-2, -2, -2); // Triangles test Direction of Light
    private final Material material = new Material().setKd(0.5).setKs(0.5).setShininess(300);
    private final Geometry triangle1 = new Triangle(p[0], p[1], p[2]).setMaterial(material);
    private final Geometry triangle2 = new Triangle(p[0], p[1], p[3]).setMaterial(material);
    private final Geometry sphere = new Sphere(new Point(0, 0, -50), 50d) //
            .setEmission(new Color(BLUE).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    /**
     * Produce a picture of a sphere lighted by a directional light
     */

    @Test
    public void sphereDirectional() {
        scene1.getGeometries().add(sphere);
        scene1.lights.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));

        ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        scene1.getGeometries().add(sphere);
        scene1.lights.add(new PointLight(spCL, spPL).setKl(0.001).setKq(0.0002));

        ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
        camera1.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() {
        scene1.getGeometries().add(sphere);
        scene1.lights.add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setKl(0.001).setKq(0.0001));

        ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
        camera1.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene1)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a picture of a two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        scene2.getGeometries().add(triangle1, triangle2);
        scene2.lights.add(new DirectionalLight(trCL, trDL));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a picture of a two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        scene2.getGeometries().add(triangle1, triangle2);
        scene2.lights.add(new PointLight(trCL, trPL).setKl(0.001).setKq(0.0002));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage(); //
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() {
        scene2.getGeometries().add(triangle1, triangle2);
        scene2.lights.add(new SpotLight(trCL, trPL, trDL).setKl(0.001).setKq(0.0001));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
        camera2.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene2)) //
                .renderImage() //
                .writeToImage(); //
    }

}
