package presentationWindow.renderItems;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.assets.Quad;
import presentationWindow.engine.ShaderProgram;
import presentationWindow.engine.Transformation;

/**
 * super class for all items that display something on the screen
 */
@SuppressWarnings("unused")
public class ChildItem extends PresentationViewRenderItem {




    /**
     * the way the edge color is added to the hud, it either is layered over the component
     * it belongs to or replaces it
     */
    public enum BlendMode {
        REPLACE,MULTIPLY
    }

    /**
     * determines what effect transparent pixels have on the mask
     */
    public enum MaskMode {
        DISPOSE_TRANSPARENT, USE_TRANSPARENT, ONLY_EDGES
    }

    /**
     * size of the corners
     */
    private float cornerSize = 0f;

    /**
     * size of the edges
     */
    private float edgeSize = 0f;

    /**
     * opacity of the item
     */
    private float opacity = 1f;

    /**
     * color scheme of the item
     */
    private ColorScheme colorScheme = new ColorScheme(Color.RED);

    /**
     * flag to decide if the item should use the color shading
     */
    private boolean useColorShade = false;

    /**
     * inner color of the edge
     */
    private Color edgeStartColor = Color.BLACK;

    /**
     * outer color of the edge
     */
    private Color edgeEndColor = Color.BLACK;

    /**
     * blend mode of the edge
     */
    private BlendMode edgeBlendMode = BlendMode.REPLACE;

    /**
     * mask mode of the item
     */
    private MaskMode maskMode = MaskMode.DISPOSE_TRANSPARENT;

    /**
     * creates a new child item with a quad as rendered item
     */
    ChildItem() {
        gameItem = new Quad();
        gameItem.setPosition(0.5f,0.5f,1);
        gameItem.setScale3(0.5f,0.5f,1);
    }

    /**
     * sets up the shader values for the item
     *
     * @param shaderProgram shader program of the window
     * @param orthographic orthographic projection matrix
     * @param transformation transformation class
     * @param level z level of the item
     */
    @Override
    protected void setupShader(ShaderProgram shaderProgram, Matrix4f orthographic, Transformation transformation, int level) {
        super.setupShader(shaderProgram, orthographic, transformation, level);

        @SuppressWarnings("SpellCheckingInspection")
        Matrix4f projModelMatrix = transformation.buildOrtoProjModelMatrix(gameItem, orthographic);

        /*
            SET UNIFORM VALUES
         */
        //noinspection SpellCheckingInspection
        shaderProgram.setUniform("projModelMatrix", projModelMatrix);
        shaderProgram.setUniform("transparencyMode", maskMode.ordinal());

        shaderProgram.setUniform("opacity", Math.max(opacity,0));

        shaderProgram.setUniform("colors", colorScheme.getVectorArray());
        shaderProgram.setUniform("useColorShade",useColorShade?1:0);   //boolean is made to an int uniform

        shaderProgram.setUniform("edgeStartColor",edgeStartColor.getVector4f());
        shaderProgram.setUniform("edgeEndColor",edgeEndColor.getVector4f());

        shaderProgram.setUniform("edgeBlendMode",edgeBlendMode.ordinal());

        float tempCornerSize = cornerSize/getOnScreenWidth();

        shaderProgram.setUniform("cornerSize",tempCornerSize);

        shaderProgram.setUniform("edgeSize", tempCornerSize!=0?tempCornerSize - (edgeSize * tempCornerSize):1 - edgeSize);


        /*
           CALCULATING AND SETTING VALUES FOR CORNER CREATION
         */
        if(cornerSize > 0) {

            shaderProgram.setUniform("keepCornerProportion", -(getOnScreenWidth() / getOnScreenHeight()) * ((float) getWindow().getWidth() / getWindow().getHeight()));
            shaderProgram.setUniform("cornerScale", new Vector2f(0.5f, ((((getOnScreenHeight() * 0.5f) / getOnScreenWidth()) / (window.getWidth() / (float) window.getHeight())))));


        } else if(edgeSize > 0){

            shaderProgram.setUniform("keepCornerProportion", -(getOnScreenWidth() / getOnScreenHeight()) * ((float) getWindow().getWidth() / getWindow().getHeight()));
            shaderProgram.setUniform("cornerScale", new Vector2f(0, 0));

        } else {
            shaderProgram.setUniform("keepCornerProportion", 0);
            shaderProgram.setUniform("cornerScale", new Vector2f(0,0));

        }


    }

    /*
            SETTER
     */

    public void setPosition(float x, float y) {
        gameItem.setPosition(x,y,gameItem.getPosition().z);
    }

    public void setYPosition(float y) {
        setPosition(gameItem.getPosition().x,y);
    }

    public void setXPosition(float x) {
        setPosition(x,gameItem.getPosition().y);
    }

    public void setSize(float x, float y) {
        gameItem.setScale3(x,y,1);
    }

    public void setSize(Vector2f size) {
         setSize(size.x,size.y);
    }

    public Vector2f getSize() {
        return new Vector2f(gameItem.getScale3().x, gameItem.getScale3().y);
    }

    public void setPosition(Vector2f position) {
        setPosition(position.x,position.y);
    }

    public Vector2f getPosition() {
        return new Vector2f(gameItem.getPosition().x, gameItem.getPosition().y);
    }

    public void setHeight(float height) {
        setSize(gameItem.getScale3().x,height);
    }

    public float getHeight() {
        return gameItem.getScale3().y;
    }

    public float getWidth() {
        return gameItem.getScale3().x;
    }

    public void setWidth(float width) {
        setSize(width,gameItem.getScale3().y);
    }

    public void setCornerSize(float cornerSize) {
        this.cornerSize = cornerSize;
    }

    public void setEdgeSize(float edgeSize) {
        this.edgeSize = edgeSize;
    }

    public void setColorScheme(ColorScheme colorScheme) {
        this.colorScheme = colorScheme;
    }

    public void setUseColorShade(boolean useColorShade) {
        this.useColorShade = useColorShade;
    }

    public void setEdgeStartColor(Color edgeStartColor) {
        this.edgeStartColor = edgeStartColor;
    }

    public void setEdgeEndColor(Color edgeEndColor) {
        this.edgeEndColor = edgeEndColor;
    }

    public void setEdgeBlendMode(BlendMode edgeBlendMode) {
        this.edgeBlendMode = edgeBlendMode;
    }

    public void setMaskMode(MaskMode maskMode) {
        this.maskMode = maskMode;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public float getXPosition() {
        return gameItem.getPosition().x;
    }

    public float getYPosition() {
        return gameItem.getPosition().y;
    }

    public float getOpacity() {
        return opacity;
    }

    public Color getColor() {
        return colorScheme.getLeft();
    }

    public ColorScheme getColorScheme() {
        return colorScheme;
    }
}
