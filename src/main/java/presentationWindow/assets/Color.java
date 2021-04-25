package presentationWindow.assets;

import org.joml.Vector4f;

/**
 * Color class contains methods to create and get rgb colors in different formats
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Color {

    /**
     * default colors
     */
    public static final Color BLACK = new Color(0,0,0,1f);
    public static final Color WHITE = new Color(1f,1f,1f,1f);
    public static final Color TRANSPARENT = new Color(0,0,0,0);
    public static final Color RED = new Color(1f,0,0,1f);
    public static final Color GREEN = new Color(0,1f,0,1f);
    public static final Color BLUE = new Color(0,0,1f,1f);
    public static final Color PINK = new Color(1f,0,1f,1f);
    public static final Color YELLOW = new Color(1f,1f,0,1f);
    public static final Color TEAL = new Color(0,1f,1f,1f);
    public static final Color ORANGE = new Color(1f,0.5f,0,1f);
    public static final Color PURPLE = new Color(0.5f,0,1f,1f);
    public static final Color MID_GREEN = new Color(0,1f,0.5f,1f);
    public static final Color DARK_GREEN = new Color(0,0.4f,0,1f);
    public static final Color GREY = new Color(0.5f,0.5f,0.5f,1f);
    public static final Color VERY_LIGHT_GRAY = new Color(0.9f,0.9f,0.9f,1f);
    public static final Color LIGHT_GRAY = new Color(0.7f,0.7f,0.7f,1f);
    public static final Color DARK_GRAY = new Color(0.3f,0.3f,0.3f,1f);
    public static final Color VERY_DARK_GREY = new Color(0.15f,0.15f,0.15f,1f);

    /**
     * creates a color vector from an awt color
     *
     * @param color aet color
     */
    public Color(java.awt.Color color) {
        this(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
    }

    /**
     * creates a color from an int array with length 4
     *
     * @param color rgba values
     */
    public Color(int[] color) {
        this(color[0], color[1], color[2], color[3]);
    }

    /**
     * creates the transparent equivalent of a color by keeping its r,g and b value
     * and setting the alpha value to 0
     *
     * @param color to be made transparent
     * @return new transparent version of the color
     */
    public static Color getTransparent(Color color) {
        return new Color(color.getRed(),color.getGreen(),color.getBlue(),0);
    }

    /**
     * vector containing the rgba data of the color
     */
    private Vector4f color;

    /**
     * copy constructor
     *
     * @param color to be copied
     */
    public Color(Color color) {
        this.color = new Vector4f(color.getVector4f());
    }

    /**
     * default constructor (creates white)
     */
    public Color() {
        this(new Vector4f(1,1,1,1));
    }

    /**
     * constructor setting r,g,b and a value as a float between 0 and 1f
     *
     * @param red red value
     * @param green green value
     * @param blue blue value
     * @param alpha alpha value
     */
    public Color(float red,float green,float blue,float alpha) {
        this.color = new Vector4f(red,green,blue,alpha);
    }

    /**
     * constructor setting r,g and b value as a float between 0 and 1f;
     * the alpha value is 1f
     *
     * @param red red value
     * @param green green value
     * @param blue blue value
     */
    public Color(float red,float green,float blue) {
        this(red,green,blue,1f);
    }

    /**
     * creates a color object from a vector4f
     *
     * @param color color data
     */
    public Color(Vector4f color) {
        this.color = color;
    }

    /**
     * constructor setting r,g and b value as an int between 0 and 255;
     * the alpha value is 255
     *
     * @param red red value
     * @param green green value
     * @param blue blue value
     */
    public Color(int red,int green,int blue) {
        this(red,green,blue,255);
    }

    /**
     * constructor setting r,g,b and a value as an int between 0 and 255
     *
     * @param red red value
     * @param green green value
     * @param blue blue value
     * @param alpha alpha value
     */
    public Color(int red,int green,int blue,int alpha) {
        color = new Vector4f(red/255f,green/255f,blue/255f,alpha/255f);
    }

    /**
     * multiplies the r,g and b value of the color with the passed shift value
     *
     * @param shift value r,g and b get multiplied with
     */
    public void shiftColor(float shift) {
        color = new Vector4f(color.x * shift,color.y * shift,color.z * shift,color.w);
    }

    /**
     * adds the r,g,b and a value of the passed color to this color and returns this
     *
     * @param color to be added to this color
     * @return this color after addition
     */
    public Color add(Color color) {
        this.color.x += color.getRed();
        this.color.y += color.getGreen();
        this.color.z += color.getBlue();
        this.color.w += color.getAlpha();
        return this;
    }

    public Vector4f getVector4f() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = new Vector4f(color);
    }

    public void setRed(float red) {
        this.color.x = red;
    }

    public void setRed(int red) {
        this.color.x = red/255f;
    }

    public float getRed() {
        return this.color.x;
    }

    public void setGreen(float green) {
        this.color.y = green;
    }

    public void setGreen(int green) {
        this.color.y = green/255f;
    }

    public float getGreen() {
        return this.color.y;
    }

    public void setBlue(float blue) {
        this.color.z = blue;
    }

    public void setBlue(int blue) {
        this.color.z = blue/255f;
    }

    public float getBlue() {
        return this.color.z;
    }

    public void setAlpha(float alpha) {
        this.color.w = alpha;
    }

    public void setAlpha(int alpha) {
        this.color.w = alpha/255f;
    }

    public float getAlpha() {
        return this.color.w;
    }

    public Color half() {
        return new Color(getRed() / 2, getGreen() / 2, getBlue() / 2, getAlpha() / 2);
    }
}
