package renderer;

import primitives.*;

import static primitives.Util.*;

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
     * constructor of camera
     * set the location and vTo, vUp and vRight vectors
     * @param p0 the camera location
     * @param vTo vector vto
     * @param vUp vector vup
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if(!isZero(vTo.dotProduct(vUp)))
            throw new IllegalArgumentException("vector to and vector up aren't orthogonal");
        this._p0 = p0;
        this._vTo = vTo.normalize();
        this._vUp = vUp.normalize();
        this._vRight=_vTo.crossProduct(_vUp);
    }

    /**
     * set the width and height of view plane
     * @param width the width of view plane
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
     * @param distance the distance between camera and view plane
     * @return the camera
     */
    public Camera setVPDistance(double distance) {
        _distance = distance;
        return this;
    }

    /**
     * constructing a ray passing through pixel(j,i) of the view plane
     * @param nX number of columns
     * @param nY number of rows
     * @param j first coordinate of pixel
     * @param i second coordinate of pixel
     * @return the ray passing through pixel(j,i) of the view plane
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracer rayTracer){
        this.rayTracer = rayTracer;
        return this;
    }


    public void writeToImage() {
        imageWriter.writeToImage();
    }

    public void renderImage() {
        //.
    }

    public void printGrid(int gap, Color color) {
        imageWriter.printGrid(gap, color);
    }
}
