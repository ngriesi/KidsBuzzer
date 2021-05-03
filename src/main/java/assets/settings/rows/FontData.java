package assets.settings.rows;

import java.awt.*;

/**
 * class containing all the data that is displayed and updated by a font settings row
 */
public class FontData {

    /**
     * font containing the information about the font and its style
     */
    private Font font;

    /**
     * color of the font
     */
    private Color color;

    /**
     * creates a new FontData object from a font and a color
     *
     * @param font  font of the font data
     * @param color color of the font data
     */
    public FontData(Font font, Color color) {
        this.font = font;
        this.color = color;
    }

    /**
     * getter to access the font
     *
     * @return returns the font
     */
    public Font getFont() {
        return font;
    }

    /**
     * setter to set the font
     *
     * @param font new font
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * getter to access the color
     *
     * @return returns the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * setter to set the color
     *
     * @param color new color
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
