package presentationWindow.renderItems;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import presentationWindow.assets.Quad;
import presentationWindow.engine.ShaderProgram;
import presentationWindow.engine.Transformation;
import presentationWindow.engine.Window;

/**
 * main item of the scene
 */
public class MainItem extends PresentationViewRenderItem {

    /**
     * creates a new main item
     *
     * @param window the window the main item is inside
     */
    public MainItem(Window window) {
        setWindow(window);
        super.gameItem = new Quad();
        gameItem.getMesh().getMaterial().setAmbientColor(new Vector4f(1,1,1,1));
        gameItem.setPosition(0.5f,0.5f,50);
        gameItem.setScale3(1f,1f,1);
    }

    /**
     * sets up the shader for the main item
     *
     * @param shaderProgram shader program used
     * @param orthographic orthographic projection matrix
     * @param transformation transformation class
     * @param level z level
     */
    @Override
    protected void setupShader(ShaderProgram shaderProgram, Matrix4f orthographic, Transformation transformation, int level) {
        super.setupShader(shaderProgram, orthographic, transformation, level);
        //calculates the projection model matrix for this component
        //noinspection SpellCheckingInspection
        Matrix4f projModelMatrix = transformation.buildOrtoProjModelMatrix(gameItem, orthographic);


        /*
            SET UNIFORM VALUES

         */

        //noinspection SpellCheckingInspection
        shaderProgram.setUniform("projModelMatrix", projModelMatrix);

        shaderProgram.setUniform("useTexture",0);
        shaderProgram.setUniform("colors", new Vector4f[]{new Vector4f(1,1,1,0)});
        shaderProgram.setUniform("useColorShade",0);   //boolean is made to an int uniform

        shaderProgram.setUniform("keepCornerProportion", 0);
        shaderProgram.setUniform("cornerScale", new Vector2f(0,0));


        shaderProgram.setUniform("cornerSize",0);

        shaderProgram.setUniform("edgeSize", 0);


    }
}
