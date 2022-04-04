package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

public class Scene {
    private final String name;
    private final AmbientLight ambientLight;
    private final Color background;
    private final Geometries geometries;

    private Scene(SceneBuilder builder){
        name= builder.name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;
    }

    public String getName() {
        return name;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public Color getBackground() {
        return background;
    }

    public Geometries getGeometries() {
        return geometries;
    }

    public static class SceneBuilder {
        private final String name;
        private Color background= Color.BLACK;
        private AmbientLight ambientLight= new AmbientLight();
        private Geometries geometries= new Geometries();

        public SceneBuilder(String name){
            this.name=name;
        }

        //chaining method

        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        public  Scene build(){
            Scene scene= new Scene(this);
            //.
            return scene;
        }
    }
}
