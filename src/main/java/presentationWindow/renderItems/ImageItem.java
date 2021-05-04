package presentationWindow.renderItems;

import org.joml.Matrix4f;
import presentationWindow.engine.ShaderProgram;
import presentationWindow.engine.Transformation;
import presentationWindow.items.Texture;

/**
 * item used to display an image in the window
 */
public class ImageItem extends ChildItem {

    /**
     * aspect ratio of the image
     */
    private float aspectRatio;

    /**
     * creates an image item from a file
     *
     * @param filename name of the image file
     */
    @SuppressWarnings("unused")
    public ImageItem(String filename) {
        super();

        try {
            gameItem.getMesh().getMaterial().setTexture(new Texture(filename, Texture.FilterMode.NEAREST));
            aspectRatio = gameItem.getMesh().getMaterial().getTexture().getWidth() / (float) gameItem.getMesh().getMaterial().getTexture().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * creates an image item from an already build texture
     *
     * @param textTexture texture used for the component
     */
    @SuppressWarnings("unused")
    public ImageItem(Texture textTexture) {
        setTexture(textTexture);
    }

    /**
     * sets up the shader for image rendering
     *
     * @param shaderProgram  shader program of the window
     * @param orthographic   orthographic projection matrix
     * @param transformation transformation class
     * @param level          z level of the item
     */
    @Override
    protected void setupShader(ShaderProgram shaderProgram, Matrix4f orthographic, Transformation transformation, int level) {
        super.setupShader(shaderProgram, orthographic, transformation, level);

        shaderProgram.setUniform("useTexture", 1);
        shaderProgram.setUniform("texture2d", 0);
    }

    /**
     * @return returns the aspect ratio of the image in its initial form
     * (not the aspect ratio it currently has)
     */
    public float getAspectRatio() {
        return aspectRatio;
    }

    /**
     * sets a new <code>Texture</code> to this <code>ImageItem</code>
     *
     * @param backgroundTexture new <code>Texture</code>
     */
    public void setTexture(Texture backgroundTexture) {
        gameItem.getMesh().getMaterial().setTexture(backgroundTexture);
        aspectRatio = backgroundTexture.getWidth() / (float) backgroundTexture.getHeight();
    }
}
