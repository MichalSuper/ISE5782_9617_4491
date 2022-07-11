package pictures;

import geometries.Sphere;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

public class AntiAnalysing {

    @Test
    public void anti() {
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

        camera.setImageWriter(new ImageWriter("antiAnalysing", 500, 500)) //
                .setAntiAliasing(true)
                .renderImage() //
                .writeToImage();
    }
}
