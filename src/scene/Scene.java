package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Scene class represents Scene in its name, AmbientLight,
 * Color of background and geometry objects in the scene
 * This class is responsible for creating and describing the scene
 * @author Michal Superfine & Evgi
 */
public class Scene {
    private final String name;
    private final AmbientLight ambientLight;
    private final Color background;
    private final Geometries geometries;
    public List<LightSource> lights;

    private Scene(SceneBuilder builder){
        name= builder.name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;
        lights= builder.lights;
    }

    /**
     * getter of name
     * @return the name of scene
     */
    public String getName() {
        return name;
    }

    /**
     * getter of ambient light
     * @return the ambient light of the scene
     */
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    /**
     * getter of background
     * @return the background of the scene
     */
    public Color getBackground() {
        return background;
    }

    /**
     * getter of geometries
     * @return the geometry objects in the scene
     */
    public Geometries getGeometries() {
        return geometries;
    }

    /**
     * Builder Class for Scene
     */
    public static class SceneBuilder {
        private final String name;
        private Color background= Color.BLACK;
        private AmbientLight ambientLight= new AmbientLight();
        private Geometries geometries= new Geometries();
        public List<LightSource> lights=new LinkedList<>();

        public SceneBuilder(String name){
            this.name=name;
        }

        //chaining method

        /**
         * setter for background
         * @param background
         * @return this scene
         */
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        /**
         * setter for ambient light
         * @param ambientLight
         * @return this scene
         */
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        /**
         * setter for geometries
         * @param geometries geometry objects in the scene
         * @return this scene
         */
        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        /**
         * setter for light
         * @param lights the light sources
         * @return this scene
         */
        public SceneBuilder setLights(List<LightSource> lights) {
            this.lights = lights;
            return this;
        }

        public  Scene build(){
            Scene scene= new Scene(this);
            return scene;
        }
    }
}
