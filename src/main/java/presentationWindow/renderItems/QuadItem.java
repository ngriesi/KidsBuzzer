package presentationWindow.renderItems;

import org.joml.Matrix4f;
import presentationWindow.engine.ShaderProgram;
import presentationWindow.engine.Transformation;

/**
 * class used to display a Quad on the screen
 */
public class QuadItem extends ChildItem {

    /**
     * creates a new quad item
     */
    public QuadItem() {
        super();
    }

    /**
     * sets up the shader for the quad
     *
     * @param shaderProgram shader program of the window
     * @param orthographic orthographic projection matrix
     * @param transformation transformation class
     * @param level z level of the item
     */
    @Override
    protected void setupShader(ShaderProgram shaderProgram, Matrix4f orthographic, Transformation transformation, int level) {
        super.setupShader(shaderProgram, orthographic, transformation, level);

        shaderProgram.setUniform("useTexture",0);
    }
}
