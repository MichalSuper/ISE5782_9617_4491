package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Random;

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


    /*
     *  focalDistance - the distance of the  focus.
     *  aperture      - the radius of the aperture.
     */
    private double focalDistance;
    private double aperture;
    private boolean isDepthOfField = false;

    /**
     * setter of Depth of filed. if Depth of filed function is called the camera will be focused for a specific distance.
     * if Depth of filed will not be called the camera will be focused on the whole scene equally.
     *
     * @param focalDistance - the distance of the  focus.
     * @param aperture      - the radius of the aperture.
     */
    public Camera setDepthOfFiled(double focalDistance, double aperture) {
        this.focalDistance = focalDistance;
        this.aperture = aperture;
        isDepthOfField = true;
        return this;
    }

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

            for (int i = 0; i < imageWriter.getNx(); i++) {
                for (int j = 0; j < imageWriter.getNy(); j++) {
                    imageWriter.writePixel(i, j, castRay(j, i));
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
        Ray ray = constructRay(imageWriter.getNx(), imageWriter.getNy(), i, j);
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
        if (isDepthOfField) {
            List<Ray> rays = constructRaysGridFromCamera(_N, _M, ray);
            Color sum = Color.BLACK;
            for (Ray rayy : rays) {
                sum = sum.add(rayTracer.traceRay(rayy, isSoftShadows));
            }
            return sum.reduce(rays.size());
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
     * This function get a ray launched from the camera of a pixel and launch others rays
     * from all the aperture of the camera in direction of the point on the depth of field plane
     *
     * @param n   height of the grid
     * @param m   width of the grid
     * @param ray the ray that it is already launched from the camera
     * @return list of rays when every ray is launched from the grid inside a pixel with random emplacement
     */
    public List<Ray> constructRaysGridFromCamera(int n, int m, Ray ray) {

        List<Ray> myRays = new LinkedList<>(); //to save all the rays

        double t = _distance / (_vTo.dotProduct(ray.getDir())); //cosinus of the angle
        Point point = ray.getPoint(t);

        double pixelSize = alignZero((aperture * 2) / n);

        //We call the function constructRayFromPixel like we used to but this time we launch m * n ray from the aperture grid

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Ray tmpRay = constructRayFromPixel(n, m, j, i, pixelSize, point);
                //check that the point of base of the ray is inside the aperture circle
                if (tmpRay.getP0().equals(_p0)) { //to avoid vector ZERO
                    myRays.add(tmpRay);
                } else if (tmpRay.getP0().distance(_p0) <= aperture) {
                    myRays.add(tmpRay);
                }
            }
        }
        return myRays;
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
     * This function returns a ray from a point in the aperture circle
     *
     * @param nX        grid's width
     * @param nY        grid's height
     * @param j         y emplacement of the point
     * @param i         x emplacement of the point
     * @param pixelSize size of side of the pixel on the grid
     * @param point     point on the depth of field plane
     * @return a ray to the point on the depth of field plane
     */
    private Ray constructRayFromPixel(int nX, int nY, double j, double i, double pixelSize, Point point) {

        Point pIJ = _p0;

        //get the emplacement of the base point of the ray
        double xJ = ((j + r.nextDouble() / (r.nextBoolean() ? 2 : -2)) - ((nX - 1) / 2d)) * pixelSize;
        double yI = -((i + r.nextDouble() / (r.nextBoolean() ? 2 : -2)) - ((nY - 1) / 2d)) * pixelSize;

        if (xJ != 0) {
            pIJ = pIJ.add(_vRight.scale(xJ));
        }
        if (yI != 0) {
            pIJ = pIJ.add(_vUp.scale(yI));
        }

        Vector vIJ = point.subtract(pIJ);

        return new Ray(pIJ, vIJ);
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

}
