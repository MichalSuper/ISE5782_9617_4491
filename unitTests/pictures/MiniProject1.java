package pictures;

import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

public class MiniProject1 {
    @Test
    public void pic() {
        Scene scene = new Scene.SceneBuilder("Test scene")
                .setBackground(new Color(34,14,90))
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.yellow)
                        , new Double3(0))).build();
        Camera camera = new Camera(new Point(-80, 100, 20), new Vector(0.8, -1, 0),
                new Vector(0, 0, 1))
                .setVPSize(200, 200).setVPDistance(400) //
                .setRayTracer(new RayTracerBasic(scene));
        Point D = new Point(11.52, 15.04, 5);
        Point E = new Point(14.20, 12.66, 5);
        Point F = new Point(-3.19, -6.87, 9.5);
        Point G = new Point(-5.87, -4.49, 9.5);
        Point H = new Point(11.52, 15.04, 6);
        Point I = new Point(14.2, 12.66, 6);
        Point J = new Point(-3.19, -6.87, 10.5);
        Point K = new Point(-5.87, -4.49, 10.5);
        Point L = new Point(12.80513, 12.34578, 10.5);
        Point M = new Point(9.54098, 13.32958, 10.35);
        Point R = new Point(10.32, 10.01, 10.09);
        Point S = new Point(10.85, 11.76, 13.23);
        Point T = new Point(-0.86495, -3.89701, 17.5);
        Point U = new Point(-4.09792, -2, 17.3);
        Point V = new Point(-4.13, -5.75, 17.55);
        Point W = new Point(-3.1, -3.67, 20.51);
        Point N = new Point(12, 13, 16.5);
        Point O = new Point(9.2, 12.4, 16.5);
        Point P = new Point(9.77, 9.61, 16.5);
        Point Q = new Point(12.57, 10.21, 16.5);
        Point NN = new Point(12, 13, 20);
        Point OO = new Point(9.2, 12.4, 20);
        Point PP = new Point(9.77, 9.61, 20);
        Point QQ = new Point(12.57, 10.21, 20);

        scene.getGeometries().add(
                new Plane(new Point(0, 0, 0), new Vector(0, 0, 1))
                        .setEmission(new Color(252, 139, 255))
                        .setMaterial(new Material().setShininess(20)),
                new Polygon(new Point(30, 25, 0), new Point(30, -25, 0),
                        new Point(30, -25, 40), new Point(30, 25, 40))
                        .setEmission(new Color(66, 69, 70))
                        .setMaterial(new Material().setKr(0.8)),
                new Polygon(new Point(25, -30, 40), new Point(-20, -30, 40),
                        new Point(-20, -30, 0), new Point(25, -30, 0))
                        .setEmission(new Color(66, 69, 70))
                        .setMaterial(new Material().setKr(0.8)),
                new Sphere(new Point(2.3, 2, 2), 2)
                        .setEmission(new Color(2, 194, 139))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(5, 5, 2), 2)
                        .setEmission(new Color(59, 19, 195))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(3.65, 3.5, 5.36712), 2)
                        .setEmission(new Color(161, 1, 175))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Polygon(D, E, F, G).setEmission(new Color(173, 220, 18))
                        .setMaterial(new Material().setKd(1)),
                new Polygon(D, G, K, H).setEmission(new Color(173, 220, 18))
                        .setMaterial(new Material().setKd(1)),
                new Polygon(E, I, J, F).setEmission(new Color(173, 220, 18))
                        .setMaterial(new Material().setKd(1)),
                new Polygon(K, J, F, G).setEmission(new Color(173, 220, 18))
                        .setMaterial(new Material().setKd(1)),
                new Polygon(I, H, D, E).setEmission(new Color(173, 220, 18))
                        .setMaterial(new Material().setKd(1)),
                new Polygon(I, J, K, H).setEmission(new Color(173, 220, 18))
                        .setMaterial(new Material().setKd(1)),
                new Sphere(new Point(11, 12, 8.5), 2)
                        .setEmission(new Color(255,0,127))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(10.8452, 11.74803, 15), 1.75)
                        .setEmission(new Color(0,190,241))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-3, -4, 12), 2)
                        .setEmission(new Color(60,125,8))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-3, -4, 15.7), 1.7)
                        .setEmission(new Color(0,195,153))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Polygon(N, O, P, Q).setEmission(new Color(90, 0, 176))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Polygon(NN, OO, PP, QQ).setEmission(new Color(90, 0, 176))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Polygon(N, O, OO, NN).setEmission(new Color(90, 0, 176))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Polygon(O, P, PP, OO).setEmission(new Color(90, 0, 176))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Polygon(P, Q, QQ, PP).setEmission(new Color(90, 0, 176))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Polygon(Q, N, NN, QQ).setEmission(new Color(90, 0, 176))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-10, -7, 4.5), 0.5)
                        .setEmission(new Color(255, 234, 0))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-9.42276, -6.29469, 4.3), 0.5)
                        .setEmission(new Color(217, 12, 33))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-10.93227, -7.11962, 4.3), 0.5)
                        .setEmission(new Color(217, 12, 33))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-9.7301, -7.77361, 4.6), 0.5)
                        .setEmission(new Color(217, 12, 33))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-10.38949, -7.80124, 4.6), 0.5)
                        .setEmission(new Color(217, 12, 33))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-9.13664, -7.12766, 4.5), 0.5)
                        .setEmission(new Color(217, 12, 33))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-10.40145, -6.31565, 4.2), 0.5)
                        .setEmission(new Color(217, 12, 33))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-11.65, -7.8, 6), 0.5)
                        .setEmission(new Color(255, 234, 0))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-12.46279, -7.38834, 5.8), 0.5)
                        .setEmission(new Color(217, 12, 33))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-10.90171, -7.4652, 5.8), 0.5)
                        .setEmission(new Color(217, 12, 33))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-11.83585, -6.95029, 5.7), 0.5)
                        .setEmission(new Color(217, 12, 33))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-11.52645, -8.70528, 6.1), 0.5)
                        .setEmission(new Color(217, 12, 33))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-12.36017, -8.29549, 5.9), 0.5)
                        .setEmission(new Color(217, 12, 33))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10)),
                new Sphere(new Point(-10.83832, -8.2892, 6), 0.5)
                        .setEmission(new Color(217, 12, 33))
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(10))
        );
        pyramid(scene, L, M, R, S, new Color(192,0,181), 0.3, 0.2, 10);
        pyramid(scene, T, U, V, W, new Color(106,13,173), 0.3, 0.2, 10);
        pyramid(scene, new Point(-9.15, -7.24, 0), new Point(-9.41, -7.54, 0),
                new Point(-9.55, -7.224, 0), new Point(-9, -7, 2), new Color(73, 113, 13),
                0.3, 0.2, 10);
        pyramid(scene, new Point(-11.94, -6.85, 0), new Point(-11.92, -7.26, 0),
                new Point(-12.32, -7.01, 0), new Point(-12.64, -7.18, 2), new Color(73, 113, 13),
                0.3, 0.2, 10);
        pyramid(scene, new Point(-10.32, -7.44, 0), new Point(-10.55, -7.11, 0),
                new Point(-10.68, -7.49, 0), new Point(-10, -7, 4), new Color(73, 113, 13),
                0.3, 0.2, 10);
        pyramid(scene, new Point(-11.49, -7.54, 0), new Point(-11.18, -7.75, 0),
                new Point(-11.24, -7.53, 0), new Point(-11.65, -7.8, 5.5), new Color(73, 113, 13),
                0.3, 0.2, 10);
        pyramid3(scene,12, 18, 0,new Color(73, 113, 13), 0.3, 0.2, 10);
        pyramid2(scene,-3.78, 0.32, 0, new Color(73, 113, 13),0.3, 0.2, 10);
        pyramid2(scene,11.15,24.75,0, new Color(73, 113, 13),0.3, 0.2, 10);
        pyramid3(scene, 4.88,16.59,0, new Color(73, 113, 13),0.3, 0.2, 10);
        pyramid2(scene,-19.53,-8.11,0, new Color(73, 113, 13),0.3, 0.2, 10);
        pyramid3(scene,-2.79,7.49,0, new Color(73, 113, 13),0.3, 0.2, 10);
        pyramid2(scene,-6.39,31.12,0, new Color(73, 113, 13),0.3, 0.2, 10);
        pyramid3(scene,-10.56,22.89,0, new Color(73, 113, 13),0.3, 0.2, 10);
        pyramid2(scene,-17.71,12.7,0, new Color(73, 113, 13),0.3, 0.2, 10);
        pyramid3(scene, -25.21,7.71,0, new Color(73, 113, 13),0.3, 0.2, 10);
        pyramid2(scene, -20.74,25.11,0, new Color(73, 113, 13),0.3, 0.2, 10);

                scene.lights.add(new PointLight(new Color(131, 131, 131), new Point(-100, 100, 600))
                .setKl(0.0004).setKq(0.0000006));
        scene.lights.add(new SpotLight(new Color(100, 100, 100), new Point(30, 25, 40),
                new Vector(-2, -1, -2))
                .setKc(1).setKl(0.0004).setKq(0.0000006));
        scene.lights.add(new DirectionalLight(new Color(100, 100, 100),
                new Vector(-18, -11, -20)));

        camera.setImageWriter(new ImageWriter("miniProject", 1000, 1000)) //
                .setAntiAliasing(true)
                .renderImage() //
                .writeToImage();
    }

    void pyramid3(Scene scene, double x, double y, double z,Color col, double kd, double ks, int shine){
        pyramid(scene, new Point(x, y, z), new Point(x-0.62, y-0.21, z),
                new Point(x-0.3, y-0.77, z), new Point(x+0.92, y-0.52, z+4), col,
                kd, ks, shine);
        pyramid(scene, new Point(x-1.16, y-0.61, z), new Point(x-1.13, y-1.24, z),
                new Point(x-1.62, y-0.93, z), new Point(x-1.75, y-1.38, z+2), col,
                kd, ks, shine);
        pyramid(scene, new Point(x+0.51, y+1.26, z), new Point(x-0.02, y+1.23, z),
                new Point(x-0.51, y+1.7, z), new Point(x+0.56, y+1.7, z+2), col,
                kd, ks, shine);
    }

    void pyramid2(Scene scene, double x, double y, double z,Color col, double kd, double ks, int shine){
        pyramid(scene, new Point(x, y, z), new Point(x-0.1, y-0.77, z),
                new Point(x-0.62, y-0.1, z), new Point(x-0.54, y+0.89, z+3.8), col,
                kd, ks, shine);
        pyramid(scene, new Point(x-0.44, y-0.92, z), new Point(x-0.46, y-1.27, z),
                new Point(x-1.09, y-0.91, z), new Point(x-1.93, y-0.81, z+1.8), col,
                kd, ks, shine);
    }

    void pyramid(Scene scene, Point a, Point b, Point c, Point d, Color col, double kd, double ks, int shine) {
        scene.getGeometries().add(
                new Triangle(a, b, c).setEmission(col)
                        .setMaterial(new Material().setKd(kd).setKs(ks).setShininess(shine)),
                new Triangle(a, b, d).setEmission(col)
                        .setMaterial(new Material().setKd(kd).setKs(ks).setShininess(shine)),
                new Triangle(c, b, d).setEmission(col)
                        .setMaterial(new Material().setKd(kd).setKs(ks).setShininess(shine)),
                new Triangle(a, c, d).setEmission(col)
                        .setMaterial(new Material().setKd(kd).setKs(ks).setShininess(shine))
        );

    }
}
