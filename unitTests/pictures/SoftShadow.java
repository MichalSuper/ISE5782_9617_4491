package pictures;

import geometries.*;
import org.junit.jupiter.api.Test;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

public class SoftShadow {
    @Test
    void soft() {
        Scene scen = new Scene.SceneBuilder("Test scene").
                setBackground(new Color(java.awt.Color.BLACK)).
                setAmbientLight(new AmbientLight(Color.BLACK, new Double3(0))).build();
        Camera camera = new Camera(new Point(-100, -100, 20), new Vector(1, 1, 0), new Vector(0, 0, 1)).
                setVPSize(200, 200).setVPDistance(100) //
                .setRayTracer(new RayTracerBasic(scen));
        scen.getGeometries().add(
                new Sphere(new Point(0, 0, 30), 30)
                        .setEmission(new Color(java.awt.Color.MAGENTA))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3).setKr(0)),
                new Plane(new Point(0, 0, 0), new Vector(0,0,1))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3).setKr(0)));

        scen.lights.add(new SpotLight(new Color(1000, 600, 0), new Point(-60, 60, 200), new Vector(1, -1, -2))
                .setKc(1).setKl(0.0004).setKq(0.0000006));

        camera.setImageWriter(new ImageWriter("softShadow", 500, 500)) //
                .setSoftShadows(true)
                .renderImage() //
                .writeToImage();
    }
}