package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.*;

/**
 * Camera class represents Camera in 3D Point, 3 Vectors,
 * height, width and distance from view plane,
 * the ImageWriter of the image and a RayTracer
 * @author Michal Superfine & Evgi
 */
public class Camera {
    private Point _p0;
    private Vector _vTo;
    private Vector _vUp;
    private Vector _vRight;
    private double _height;
    private double _width;
    private double _distance;
    private ImageWriter imageWriter;
    private RayTracer rayTracer;

    /**
     * get height method
     *
     * @return the height of view plane
     */
    public double get_height() {
        return _height;
    }

    /**
     * get width method
     *
     * @return the width of view plane
     */
    public double get_width() {
        return _width;
    }

    /**
     * get distance method
     *
     * @return the distance between camera and view plane
     */
    public double get_distance() {
        return _distance;
    }

    /**
     * constructor of camera
     * set the location and vTo, vUp and vRight vectors
     *
     * @param p0  the camera location
     * @param vTo vector vto
     * @param vUp vector vup
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp)))
            throw new IllegalArgumentException("vector to and vector up aren't orthogonal");
        this._p0 = p0;
        this._vTo = vTo.normalize();
        this._vUp = vUp.normalize();
        this._vRight = _vTo.crossProduct(_vUp);
    }

    /**
     * set the width and height of view plane
     *
     * @param width  the width of view plane
     * @param height the height of view plane
     * @return the camera
     */
    public Camera setVPSize(double width, double height) {
        _width = width;
        _height = height;
        return this;
    }

    /**
     * set the distance between camera and view plane
     *
     * @param distance the distance between camera and view plane
     * @return the camera
     */
    public Camera setVPDistance(double distance) {
        _distance = distance;
        return this;
    }

    /**
     * constructing a ray passing through pixel(j,i) of the view plane
     *
     * @param nX number of columns
     * @param nY number of rows
     * @param j  first coordinate of pixel
     * @param i  second coordinate of pixel
     * @return the ray passing through pixel(j,i) of the view plane
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point Pc = _p0.add(_vTo.scale(_distance)); //Image center

        //Ratio (pixel width & height)
        double Rx = _width / nX;
        double Ry = _height / nY;

        Point Pij; //Pixel[i,j] center
        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        // Pixel[i,j] is the center
        if (isZero(Xj) && isZero(Yi)) {
            Pij = Pc;
            return new Ray(_p0, Pij.subtract(_p0));
        }
        // Pixel[i,j] is in the middle column
        if (isZero(Xj)) {
            Pij = Pc.add(_vUp.scale(Yi));
            return new Ray(_p0, Pij.subtract(_p0));
        }
        //Pixel[i,j] is in the middle row
        if (isZero(Yi)) {
            Pij = Pc.add(_vRight.scale(Xj));
            return new Ray(_p0, Pij.subtract(_p0));
        }

        Pij = Pc.add(_vRight.scale(Xj).add(_vUp.scale(Yi)));
        return new Ray(_p0, Pij.subtract(_p0));

    }

    /**
     * setter for image writer
     * @param imageWriter
     * @return the camera
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * setter for ray tracer
     * @param rayTracer
     * @return the camera
     */
    public Camera setRayTracer(RayTracer rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Function writeToImage produces unoptimized png file of the image according to
     * pixel color matrix in the directory of the project
     * @return the camera
     */
    public Camera writeToImage() {
        if (this.imageWriter == null)
            throw new MissingResourceException("No value was added to the image writer", ImageWriter.class.getName(), "");

        imageWriter.writeToImage();
        return this;
    }

    /**
     *Sends rays to all pixels in the view plane
     * checks what color each pixel is and colors it
     * @return the camera
     */
    public Camera renderImage() {
        try {
            if (this.imageWriter == null)
                throw new MissingResourceException("No value was added to the image writer", ImageWriter.class.getName(), "");
            if (this.rayTracer == null)
                throw new MissingResourceException("No value was added to the ray tracer", RayTracer.class.getName(), "");

            for (int i = 0; i < imageWriter.getNx(); i++) {
                for (int j = 0; j < imageWriter.getNy(); j++) {
                    imageWriter.writePixel(i, j, castRay(j, i));
                }
            }
        }
        catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
        return this;
    }

    /**
     * send ray to the pixel and check which color it is
     * @param j first coordinate of the pixel
     * @param i second coordinate of the pixel
     * @return the color of the pixel
     */
    private Color castRay(int j, int i) {
        Ray ray = constructRay(imageWriter.getNx(), imageWriter.getNy(), i, j);
        Color result = rayTracer.traceRay(ray);
        return result;
    }

    /**
     * Create a network of lines
     * @param gap network interval (in Pixels)
     * @param color color of the network
     * @return the camera
     */
    public Camera printGrid(int gap, Color color) {
        if (this.imageWriter == null)
            throw new MissingResourceException("No value was added to the image writer", ImageWriter.class.getName(), "");

        imageWriter.printGrid(gap, color);
        return this;
    }
}
