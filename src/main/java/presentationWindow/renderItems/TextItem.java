package presentationWindow.renderItems;

import org.joml.Matrix4f;
import presentationWindow.assets.ColorScheme;
import presentationWindow.engine.ShaderProgram;
import presentationWindow.engine.Transformation;
import presentationWindow.items.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * class used to display text on the screen. It uses a buffered image to draw the text and than converts it into an open Gl texture
 * Bad performance with fast changing texts
 */
@SuppressWarnings("unused")
public class TextItem extends ChildItem {

    /**
     * text of the component
     */
    private String text;

    /**
     * Font of the component
     */
    private Font font;

    /**
     * aspect ratio of the text, can be used to make the text item match this ratio
     */
    private float aspectRatio;

    /**
     * creates a new Text item from a string
     *
     * @param text text of the item
     */
    public TextItem(String text) {
        this(text, new Font("Arial", Font.PLAIN, 200));
    }

    public TextItem(String text, Font font) {
        super();

        this.text = text;

        this.setColorScheme(new ColorScheme(presentationWindow.assets.Color.BLACK));

        this.font = font;

        if(!createTexture()) {
            changeFont(new Font("Arial", Font.PLAIN, 200));
        }
    }

    /**
     * creates an open gl texture with the text on it by using a buffered image
     */
    private boolean createTexture() {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();


        FontMetrics metrics = g.getFontMetrics(font);
        int width = metrics.stringWidth(text);
        if (width < 1) {
            return false;
        }
        int height = metrics.getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawRect(-1, -1, width + 1, height + 1);
        g.setColor(Color.WHITE);
        g.drawString(text, 0, metrics.getAscent());

        aspectRatio = (float) width / (float) height;

        gameItem.getMesh().getMaterial().setTexture(new Texture(image));

        return true;
    }

    /**
     * changes the text and recreates the texture
     *
     * @param text new text
     */
    public void changeText(String text) {
        if(!text.equals("")) {
            this.text = text;
            createTexture();
        }
    }

    /**
     * changes the font and recreates the texture
     *
     * @param font new font
     */
    public void changeFont(Font font) {
        this.font = font;
        if(!createTexture()) {
            changeFont(new Font("Arial", Font.PLAIN, 200));
        }
    }

    /**
     * @return returns the aspect ratio
     */
    public float getAspectRatio() {
        return aspectRatio;
    }

    /**
     * sets up the shader for the text rendering
     *
     * @param shaderProgram  shader program of the window
     * @param orthographic   orthographic projection matrix
     * @param transformation transformation class
     * @param level          z level of the item
     */
    @Override
    protected void setupShader(ShaderProgram shaderProgram, Matrix4f orthographic, Transformation transformation, int level) {
        super.setupShader(shaderProgram, orthographic, transformation, level);

        shaderProgram.setUniform("useTexture", 3);
        shaderProgram.setUniform("texture2d", 0);
    }

    /**
     * @return returns the text of this item
     */
    public String getText() {
        return text;
    }

    /**
     * @return returns the font of this item
     */
    public Font getFont() {
        return font;
    }
}
