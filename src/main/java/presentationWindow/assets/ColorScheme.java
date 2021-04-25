package presentationWindow.assets;

import org.joml.Vector4f;

/**
 * class for handling the color schemes of the components
 * a color scheme is composed of four <code>Color</code> objects
 * for each side of the component
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ColorScheme {

    /**
     * enums to identify standard color schemes for the default hud layout
     */
    public enum StandardColorSchemes {
        BUTTON_STANDARD,BUTTON_ENTERED,BUTTON_PRESSED,BUTTON_TEXT_STANDARD,BUTTON_TEXT_PRESSED,BUTTON_DISABLED,BUTTON_TEXT_DISABLED
    }

    /**
     * method to access the default color schemes for the standard layout
     *
     * @param colorScheme identifier of the color scheme
     * @return default color scheme
     */
    public static ColorScheme getStandardColorScheme(StandardColorSchemes colorScheme) {
        switch (colorScheme) {
            case BUTTON_STANDARD: return new ColorScheme(Color.DARK_GRAY,Color.DARK_GRAY,Color.LIGHT_GRAY,Color.VERY_DARK_GREY);
            case BUTTON_ENTERED: return new ColorScheme(Color.GREY,Color.GREY,Color.GREY,Color.VERY_DARK_GREY);
            case BUTTON_PRESSED: return new ColorScheme(Color.DARK_GRAY,Color.DARK_GRAY,Color.GREY,Color.GREY);
            case BUTTON_TEXT_STANDARD:
            case BUTTON_TEXT_PRESSED:
                return new ColorScheme(Color.BLACK);
            case BUTTON_DISABLED: return new ColorScheme(Color.WHITE,Color.WHITE,Color.WHITE,Color.LIGHT_GRAY);
            case BUTTON_TEXT_DISABLED: return new ColorScheme(Color.GREY);
            default:return new ColorScheme();
        }
    }

    /**
     * contains possible directions of a color gradient for the <code>createGradient</code>
     * method
     */
    public enum GradientDirection {
        TOP_TO_BOTTOM,
        RIGHT_TOP_TO_LEFT_BOTTOM,
        RIGHT_TO_LEFT,
        RIGHT_BOTTOM_TO_LEFT_TOP,
        BOTTOM_TO_TOP,
        LEFT_BOTTOM_TO_RIGHT_TOP,
        LEFT_TO_RIGHT,
        LEFT_TOP_TO_RIGHT_BOTTOM
    }

    /**
     * enum used to identify color sides in the getter method
     */
    public enum ColorSide {
        LEFT,RIGHT,TOP,BOTTOM
    }

    /**
     * colors of the sides of the component
     */
    private Color right,left,top,bottom;

    /**
     * constructor sets all colors of the scheme
     *
     * @param right right color
     * @param left left color
     * @param top top color
     * @param bottom bottom color
     */
    public ColorScheme(Color right, Color left, Color top, Color bottom) {
        this.right = right;
        this.left = left;
        this.top = top;
        this.bottom = bottom;
    }

    /**
     * constructor sets one color for all of the sides of the scheme
     *
     * @param color color set to all sides
     */
    public ColorScheme(Color color) {
        this(color,color,color,color);
    }

    /**
     * default constructor creates a color scheme with four white sides
     */
    public ColorScheme() {
        this(new Color());
    }

    /**
     * copy constructor
     *
     * @param colorScheme to be copied
     */
    public ColorScheme(ColorScheme colorScheme) {
        this.right = new Color(colorScheme.getRight());
        this.left = new Color(colorScheme.getLeft());
        this.top = new Color(colorScheme.getTop());
        this.bottom = new Color(colorScheme.getBottom());
    }

    /**
     * sets all four sides of the scheme to one color
     *
     * @param color set to all four sides
     */
    public void setAllColors(Color color) {
        this.right = new Color(color);
        this.left = new Color(color);
        this.bottom = new Color(color);
        this.top = new Color(color);
    }

    /**
     * sets all four colors
     *
     * @param left left color
     * @param top top color
     * @param right right color
     * @param bottom bottom color
     */
    public void setColors(Color left,Color top,Color right,Color bottom) {
        this.right = right;
        this.left = left;
        this.top = top;
        this.bottom = bottom;
    }

    /**
     * sets all the colors to the value of the passed Vector4f
     *
     * @param value new value for all four colors
     */
    public void setValues(Vector4f value) {
        right.setColor(value);
        left.setColor(value);
        top.setColor(value);
        bottom.setColor(value);
    }

    /**
     * creates a gradient inside this color scheme
     *
     * @param startColor start color of the gradient
     * @param endColor end color of the gradient
     * @param direction direction of the gradient
     */
    public void createGradient(Color startColor,Color endColor,GradientDirection direction) {
        switch (direction) {
            case BOTTOM_TO_TOP: setColors(startColor,endColor,startColor,startColor);break;
            case TOP_TO_BOTTOM: setColors(startColor,startColor,startColor,endColor);break;
            case LEFT_TO_RIGHT: setColors(startColor,startColor,endColor,startColor);break;
            case RIGHT_TO_LEFT: setColors(endColor,startColor,startColor,startColor);break;
            case LEFT_BOTTOM_TO_RIGHT_TOP: setColors(startColor,endColor,endColor,startColor);break;
            case LEFT_TOP_TO_RIGHT_BOTTOM: setColors(startColor,startColor,endColor,endColor);break;
            case RIGHT_BOTTOM_TO_LEFT_TOP: setColors(endColor,endColor,startColor,startColor);break;
            case RIGHT_TOP_TO_LEFT_BOTTOM: setColors(endColor,startColor,startColor,endColor);break;
        }
    }

    /**
     * sets the color of a specific side
     *
     * @param color color to be set
     * @param side side where the color is set to
     */
    public void setColor(Color color,ColorSide side) {
        switch (side) {
            case TOP: this.top = color;break;
            case BOTTOM: this.bottom = color;break;
            case LEFT: this.left = color;break;
            case RIGHT: this.right = color;break;
        }
    }

    /**
     * returns the color of a specific side
     *
     * @param side side the color is red form
     * @return the color of the specified side
     */
    public Color getColor(ColorSide side) {
        switch (side) {
            case TOP:return top;
            case BOTTOM:return bottom;
            case LEFT:return left;
            default:return right;
        }
    }

    /**
     * creates and returns an array of Vector4f objects with the length four. This is the
     * form the color data is passed on to the shaders
     *
     * order: left,right,top,bottom
     *
     *
     * @return the color scheme as an array of vectors
     */
    public Vector4f[] getVectorArray() {
        return new Vector4f[] {left.getVector4f(),right.getVector4f(),top.getVector4f(),bottom.getVector4f()};
    }

    public Color getRight() {
        return right;
    }

    public void setRight(Color right) {
        this.right = right;
    }

    public Color getLeft() {
        return left;
    }

    public void setLeft(Color left) {
        this.left = left;
    }

    public Color getTop() {
        return top;
    }

    public void setTop(Color top) {
        this.top = top;
    }

    public Color getBottom() {
        return bottom;
    }

    public void setBottom(Color bottom) {
        this.bottom = bottom;
    }
}
