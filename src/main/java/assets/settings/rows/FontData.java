package assets.settings.rows;

import java.awt.*;

public class FontData {

    private Font font;

    private Color color;

    public FontData(Font font, Color color) {
        this.font = font;
        this.color = color;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
