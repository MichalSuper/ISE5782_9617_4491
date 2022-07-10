package renderer;

import geometries.Plane;
import multiThreading.ThreadPool;
import primitives.*;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Random;
import static java.lang.Math.sqrt;
import static primitives.Util.*;

/**
 * Camera class represents Camera in 3D Point, 3 Vectors,
 * height, width and distance from view plane,
 * the ImageWriter of the image and a RayTracer
 *
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

    private int _N = 8;
    private int _M = 8;

    /*
    isAntiAliasing- for anti aliasing
     */
    private boolean isAntiAliasing = false;

    public Camera setAntiAliasing(boolean antiAliasing) {
        isAntiAliasing = antiAliasing;
        return this;
    }

    /*
     isSoftShadows- for soft shadows
     */
    boolean isSoftShadows = false;

    /**
     * setter for softShadows
     *
     * @param softShadows is soft shadows
     * @return the camera
     */
    public Camera setSoftShadows(boolean softShadows) {
        isSoftShadows = softShadows;
        return this;
    }


    /**
     * A boolean variable that determines whether to use depth of filed.
     */
    private boolean isDepthOfFiled = false;

    /** Aperture properties. **/

    /**
     * number with integer square for the matrix of points.
     */
    private int APERTURE_NUMBER_OF_POINTS = 100;

    /**
     * Declaring a variable called apertureSize of type double.
     */
    private double apertureSize;

    /**
     * Creating an array of Point objects.
     */
    private Point[] aperturePoints;

    /** Focal plane parameters. **/

    /**
     * as instructed it is a constant value of the class.
     */
    private double FP_distance;

    /**
     * Declaring a variable called FOCAL_PLANE of type Plane.
     */
    private Plane FOCAL_PLANE;

    /** Depth Of Filed improvements **/

    /**
     * This function sets the depth of field to the value of the parameter.
     *
     * @param isDepthOfFiled If true, the camera will have a depth of field effect.
     */
    public Camera setDepthOfFiled(boolean isDepthOfFiled) {
        this.isDepthOfFiled = isDepthOfFiled;
        return this;
    }

    /**
     * The function sets the distance of the focal plane from the camera's position
     *
     * @param distance The distance from the camera to the focal plane.
     * @return The camera itself.
     */
    public Camera setFPDistance(double distance) {
        this.FP_distance = distance;
        this.FOCAL_PLANE = new Plane(this._p0.add(this._vTo.scale(FP_distance)), this._vTo);
        return this;
    }

    /**
     * This function sets the aperture size of the camera and initializes the points of the aperture.
     *
     * @param size the size of the aperture.
     * @return The camera object itself.
     */
    public Camera setApertureSize(double size) {
        this.apertureSize = size;

        //initializing the points of the aperture.
        if (size != 0) initializeAperturePoint();

        return this;
    }

    /**
     * The function initializes the aperture points array by calculating the distance between the points and the initial
     * point, and then initializing the array with the points
     */
    private void initializeAperturePoint() {
        //the number of points in a row
        int pointsInRow = (int) sqrt(this.APERTURE_NUMBER_OF_POINTS);

        //the array of point saved as an array
        this.aperturePoints = new Point[pointsInRow * pointsInRow];

        //calculating the initial values.
        double pointsDistance = (this.apertureSize * 2) / pointsInRow;
        //calculate the initial point to be the point with coordinates outside the aperture in the down left point, so we won`t have to deal with illegal vectors.
        Point initialPoint = this._p0
                .add(this._vUp.scale(-this.apertureSize - pointsDistance / 2)
                        .add(this._vRight.scale(-this.apertureSize - pointsDistance / 2)));

        //initializing the points array
        for (int i = 1; i <= pointsInRow; i++) {
            for (int j = 1; j <= pointsInRow; j++) {
                this.aperturePoints[(i - 1) + (j - 1) * pointsInRow] = initialPoint
                        .add(this._vUp.scale(i * pointsDistance).add(this._vRight.scale(j * pointsDistance)));
            }
        }
    }

    private ThreadPool<Pixel> threadPool = null;
    /**
     * Next pixel of the scene
     */
    private Pixel nextPixel = null;

    /*
     * random variable used for stochastic ray creation
     */
    private final Random r = new Random();

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
     *
     * @param imageWriter
     * @return the camera
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * setter for ray tracer
     *
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
     *
     * @return the camera
     */
    public Camera writeToImage() {
        if (this.imageWriter == null)
            throw new MissingResourceException("No value was added to the image writer", ImageWriter.class.getName(), "");

        imageWriter.writeToImage();
        return this;
    }

    /**
     * Sends rays to all pixels in the view plane
     * checks what color each pixel is and colors it
     *
     * @return the camera
     */
    public Camera renderImage() {
        try {
            if (this.imageWriter == null)
                throw new MissingResourceException("No value was added to the image writer", ImageWriter.class.getName(), "");
            if (this.rayTracer == null)
                throw new MissingResourceException("No value was added to the ray tracer", RayTracer.class.getName(), "");

            final int nX = imageWriter.getNx();
            final int nY = imageWriter.getNy();

            //rendering the image with multithreaded
            if (threadPool != null) {
                nextPixel = new Pixel(0, 0);
                threadPool.execute();
                threadPool.join();
                return this;
            }

            for (int i = 0; i < nY; i = ++i) {
                for (int j = 0; j < nX; ++j) {
                    this.imageWriter.writePixel(j, i, castRay(j, i));
                }
            }
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
        return this;
    }

    /**
     * send ray to the pixel and check which color it is
     *
     * @param j first coordinate of the pixel
     * @param i second coordinate of the pixel
     * @return the color of the pixel
     */
    private Color castRay(int j, int i) {
        Ray ray = constructRay(imageWriter.getNx(), imageWriter.getNy(), j, i);
        if (isAntiAliasing) {
            if (_N == 0 || _M == 0)
                throw new MissingResourceException("You need to set the n*m value for the rays launching", RayTracer.class.getName(), "");

            List<Ray> rays = constructRaysGridFromRay(imageWriter.getNx(), imageWriter.getNy(), _N, _M, ray);
            Color sum = Color.BLACK;
            for (Ray rayy : rays) {
                sum = sum.add(rayTracer.traceRay(rayy, isSoftShadows));
            }
            return sum.reduce(rays.size());
        }
        if (isDepthOfFiled) {
            return averagedBeamColor(ray);
        }

        return rayTracer.traceRay(ray, isSoftShadows);
    }

    /**
     * Create a network of lines
     *
     * @param gap   network interval (in Pixels)
     * @param color color of the network
     * @return the camera
     */
    public Camera printGrid(int gap, Color color) {
        if (this.imageWriter == null)
            throw new MissingResourceException("No value was added to the image writer", ImageWriter.class.getName(), "");

        imageWriter.printGrid(gap, color);
        return this;
    }

    /**
     * This function get a ray launched in the center of a pixel and launch a beam n * m others rays
     * on the same pixel
     *
     * @param nX  number of pixels in a row of view plane
     * @param nY  number of pixels in a column of view plane
     * @param n   number of the rays to launch in pixel
     * @param m   number of the ray to launch in the pixel
     * @param ray the ray that it is already launched in the center of the pixel
     * @return list of rays when every ray is launched inside a pixel with random emplacement
     */
    public List<Ray> constructRaysGridFromRay(int nX, int nY, int n, int m, Ray ray) {

        Point p0 = ray.getPoint(_distance); //center of the pixel
        List<Ray> myRays = new LinkedList<>(); //to save all the rays

        double pixelHeight = alignZero(_height / nY);
        double pixelHWidth = alignZero(_width / nX);

        //We call the function constructRayThroughPixel like we used to but this time we launch m * n ray in the same pixel

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                myRays.add(constructRay(m, n, j, i, pixelHeight, pixelHWidth, p0));
            }
        }

        return myRays;
    }

    /**
     * The function constructs a ray from Camera location through a point (i,j) on the grid of a
     * pixel in the view plane
     *
     * @param m      grid's height
     * @param n      grid's width
     * @param j      number of the pixel in the row
     * @param i      number of the pixel in the column
     * @param pixelH height of the pixel
     * @param pixelW width of the pixel
     * @param pc     pixel center
     * @return the ray through pixel's center
     */
    private Ray constructRay(int m, int n, double j, double i, double pixelH, double pixelW, Point pc) {

        Point pIJ = pc;

        //Ry = height / nY : height of a pixel
        double rY = pixelH / n;
        //Ry = weight / nX : width of a pixel
        double rX = pixelW / m;
        //xJ is the value of width we need to move from center to get to the point
        //we get to the bottom/top of the pixel and then we move randomly in the pixel to get the point
        double xJ = ((j + r.nextDouble() / (r.nextBoolean() ? 2 : -2)) - ((m - 1) / 2d)) * rX;
        //yI is the value of height we need to move from center to get to the point
        //we get to the side of the pixel and then we move randomly in the pixel to get the point
        double yI = -((i + r.nextDouble() / (r.nextBoolean() ? 2 : -2)) - ((n - 1) / 2d)) * rY;

        if (xJ != 0) {
            pIJ = pIJ.add(_vRight.scale(xJ));
        }
        if (yI != 0) {
            pIJ = pIJ.add(_vUp.scale(yI));
        }

        //get vector from camera p0 to the point
        Vector vIJ = pIJ.subtract(_p0);

        //return ray to the center of the pixel
        return new Ray(_p0, vIJ);

    }

    /**
     * It takes a ray, finds the point where it intersects the focal plane, and then shoots rays from the aperture points
     * to that point. It then averages the colors of all the rays
     *
     * @param ray The ray that is being traced.
     * @return The average color of the image.
     */
    private Color averagedBeamColor(Ray ray) {
        Color averageColor = Color.BLACK, apertureColor;
        int numOfPoints = this.aperturePoints.length;
        Ray apertureRay;
        Point focalPoint = this.FOCAL_PLANE.findGeoIntersections(ray).get(0).point;
        for (Point aperturePoint : this.aperturePoints) {
            apertureRay = new Ray(aperturePoint, focalPoint.subtract(aperturePoint));
            apertureColor = rayTracer.traceRay(apertureRay, isSoftShadows);
            averageColor = averageColor.add(apertureColor.reduce(numOfPoints));
        }
        return averageColor;
    }

    /**
     * Chaining method for setting number of threads.
     * If set to 1, the render won't use the thread pool.
     * If set to greater than 1, the render will use the thread pool with the given threads.
     * If set to 0, the thread pool will pick the number of threads.
     *
     * @param threads number of threads to use
     * @return the current render
     * @throws IllegalArgumentException when threads is less than 0
     */
    public Camera setMultithreading(int threads) {
        if (threads < 0) {
            throw new IllegalArgumentException("threads can be equals or greater to 0");
        }

        // run as single threaded without the thread pool
        if (threads == 1) {
            threadPool = null;
            return this;
        }

        threadPool = new ThreadPool<Pixel>() // the thread pool choose the number of threads (in0 case threads is 0)
                .setParamGetter(this::getNextPixel)
                .setTarget(this::renderImageMultithreaded);
        if (threads > 0) {
            threadPool.setNumThreads(threads);
        }

        return this;
    }

    /**
     * Returns the next pixel to draw on multithreaded rendering.
     * If finished to draw all pixels, returns {@code null}.
     */
    private synchronized Pixel getNextPixel() {

        // notifies the main thread in order to print the percent
        notifyAll();


        Pixel result = new Pixel();
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        // updates the row of the next pixel to draw
        // if got to the end, returns null
        if (nextPixel.col >= nX) {
            if (++nextPixel.row >= nY) {
                return null;
            }
            nextPixel.col = 0;
        }

        result.col = nextPixel.col++;
        result.row = nextPixel.row;
        return result;
    }

    /**
     * Renders a given pixel on multithreaded rendering.
     * If the given pixel is null, returns false which means kill the thread.
     *
     * @param p the pixel to render
     */
    private boolean renderImageMultithreaded(Pixel p) {
        if (p == null) {
            return false; // kill the thread
        }

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        this.imageWriter.writePixel(p.col, p.row, castRay(p.col, p.row));
        return true; // continue the rendering
    }

    /**
     * Helper class to represent a pixel to draw in a multithreading rendering.
     */
    private static class Pixel {
        public int col, row;

        public Pixel(int col, int row) {
            this.col = col;
            this.row = row;
        }

        public Pixel() {
        }
    }
}