package presentationWindow.engine;

import org.joml.Matrix4f;
import presentationWindow.renderItems.MainItem;

/**
 * Scene containing the main item
 */
public class Scene {

    /**
     * content of the scene
     */
    @SuppressWarnings("unused")
    private MainItem mainItem;

    /**
     * constructor creates hashMap and lightHandler
     *
     * @param window the window the scene is inside
     */
    public Scene(Window window) {
        mainItem = new MainItem(window);
    }

    /**
     * @return returns the main item of the scene
     */
    public MainItem getMainItem() {
        return mainItem;
    }

    /**
     * starts the rendering of the scene by calling the render method of the main item
     *
     * @param shaderProgram  shader program used to render the scene
     * @param orthographic   orthographic projection matrix
     * @param transformation transformation class
     */
    void render(ShaderProgram shaderProgram, Matrix4f orthographic, Transformation transformation) {
        if (mainItem != null) {
            mainItem.render(shaderProgram, orthographic, transformation);
        }
    }

    /**
     * sets the main item of the scene
     *
     * @param mainItem new main item
     */
    public void setMainItem(MainItem mainItem) {
        this.mainItem = mainItem;
    }
}
