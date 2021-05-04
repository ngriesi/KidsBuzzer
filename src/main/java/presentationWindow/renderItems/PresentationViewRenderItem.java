package presentationWindow.renderItems;

import org.joml.Matrix4f;
import presentationWindow.engine.ShaderProgram;
import presentationWindow.engine.Transformation;
import presentationWindow.engine.Window;
import presentationWindow.items.GameItem;

import java.util.ArrayList;
import java.util.List;

/**
 * super class for all items rendered to the screen
 */
public class PresentationViewRenderItem {

    /**
     * maximal possible z positions
     */
    public static int MAX_DEPTH = 16777215;

    /**
     * rendered item
     */
    GameItem gameItem;

    /**
     * if true the item is visible
     */
    private boolean visible = true;

    /**
     * window the item is inside
     */
    protected Window window;

    /**
     * list of child components
     */
    private List<PresentationViewRenderItem> content = new ArrayList<>();

    /**
     * manually set depth (z cord) of the item
     */
    private int manualDepth = -1;


    /**
     * sets the z position
     *
     * @param shaderProgram  shader program used
     * @param orthographic   orthographic projection matrix
     * @param transformation transformation class
     * @param level          z level
     */
    protected void setupShader(ShaderProgram shaderProgram, Matrix4f orthographic, Transformation transformation, int level) {
        gameItem.setPosition(gameItem.getPosition().x, gameItem.getPosition().y, manualDepth == -1 ? level : manualDepth);
    }

    /**
     * @return returns the width of the item on the screen
     */
    float getOnScreenWidth() {
        return gameItem.getScale3().x;
    }

    /**
     * @return returns the height of the item on the screen
     */
    float getOnScreenHeight() {
        return gameItem.getScale3().y;
    }

    /**
     * @return returns the window the item is inside
     */
    public Window getWindow() {
        return window;
    }

    /**
     * render method for the item used to set the initial z level
     *
     * @param shaderProgram  shader program used
     * @param orthographic   orthographic projection matrix
     * @param transformation transformation class
     */
    public void render(ShaderProgram shaderProgram, Matrix4f orthographic, Transformation transformation) {
        render(shaderProgram, orthographic, transformation, MAX_DEPTH - 2);
    }

    /**
     * sets up the shader, renders this item and its children
     *
     * @param shaderProgram  shader program used
     * @param orthographic   orthographic projection matrix
     * @param transformation transformation class
     * @param level          z level
     */
    private void render(ShaderProgram shaderProgram, Matrix4f orthographic, Transformation transformation, int level) {
        if (visible) {
            setupShader(shaderProgram, orthographic, transformation, level);
            gameItem.getMesh().render();
        }
        for (PresentationViewRenderItem item : content) {
            item.render(shaderProgram, orthographic, transformation, level - 1);
        }
    }

    /**
     * adds an item to this item
     *
     * @param item to be added
     */
    public void addItem(PresentationViewRenderItem item) {
        content.add(item);
        item.setWindow(window);
    }

    /**
     * sets a manual depth for the component, must be in the range of:
     * 0 to {@value MAX_DEPTH} - 2
     *
     * @param manualDepth new depth of the item
     */
    public void setManualDepth(int manualDepth) {
        this.manualDepth = MAX_DEPTH - 2 - manualDepth;
    }

    /**
     * sets the window of this item
     *
     * @param window window
     */
    public void setWindow(Window window) {
        this.window = window;
    }

    /**
     * cleans up the mesh
     */
    public void cleanup() {
        gameItem.getMesh().cleanup();
        for (PresentationViewRenderItem item : content) {
            item.cleanup();
        }
    }

    /**
     * sets the visibility flag of the item which decides whether the item and
     * all of its children should be rendered or not
     *
     * @param visible new visibility of the item
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * sets the manual depth to -1 deactivating its usage
     */
    public void deactivateManualDepth() {
        manualDepth = -1;
    }
}
