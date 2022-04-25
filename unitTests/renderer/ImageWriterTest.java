package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for render.ImageWriter class
 * @author Michal Superfine & Michal Evgi
 */
class ImageWriterTest {

    @Test
    void testWriteToImage() {
        int Nx = 800;
        int Ny = 500;

        Color yellow = new Color(255d, 255d, 0d);
        Color red = new Color(255d, 0d, 0d);

        int interval = 50; // (800 / 16) == (500 / 10) == 50
        ImageWriter imageWriter = new ImageWriter("basetest", Nx, Ny);
        for (int i = 0; i < Nx; i++) {
            for (int j = 0; j < Ny; j++) {
                if(i%interval==0 || j%interval==0){
                    imageWriter.writePixel(i,j,red);
                }
                else
                    imageWriter.writePixel(i, j, yellow);
            }
        }
        imageWriter.writeToImage();
    }
}