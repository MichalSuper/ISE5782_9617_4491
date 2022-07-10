package pictures;

import lighting.*;
import org.junit.jupiter.api.Test;
import renderer.*;
import geometries.Sphere;
import primitives.*;
import scene.Scene;

/**
 * tests for Depth of filed
 */
public class DepthOfFiledTests {
    /**
     * Produce a picture of a row of spheres lighted by a spot light.
     * with Depth of filed with focal distance of 10, aperture 0f 1 and than 0.5, and 15 rays in the beam
     */
    @Test
    public void depth() {
        Scene scene = new Scene.SceneBuilder("Test scene").
                setBackground(new Color(java.awt.Color.BLACK)).
                setAmbientLight(new AmbientLight(Color.BLACK, new Double3(0))).build();
        Camera camera=new Camera(new Point(0, -500, 0), new Vector(0, 1, 0), new Vector(0, 0, 1)).
                setVPSize(200, 200).setVPDistance(500) //
                .setRayTracer(new RayTracerBasic(scene));
            scene.getGeometries().add(
                    new Sphere( new Point(30,100,0), 20)
                            .setEmission(new Color(java.awt.Color.MAGENTA))
                            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3).setKr(0)),
                    new Sphere( new Point(0,0,0), 20)
                            .setEmission(new Color(java.awt.Color.BLUE))
                            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3).setKr(0)),
                    new Sphere(new Point(-30,-100,0), 20)
                            .setEmission(new Color(java.awt.Color.GREEN))
                            .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3).setKr(0)));

        scene.lights.add(new SpotLight(new Color(1000, 600, 0), new Point(-100, 100, -500), new Vector(-1, 1, 2))
                        .setKc( 1).setKl(0.0004).setKq(0.0000006));
        scene.lights.add(new PointLight(new Color(500,500,500),new Point(0,0,100)));

        camera.setImageWriter(new ImageWriter("depthOfField", 500, 500)) //
                .setDepthOfFiled(true)
                .setFPDistance(400)
                .setApertureSize(1)
                .renderImage() //
                .writeToImage();
    }
}