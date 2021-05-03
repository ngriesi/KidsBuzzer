package assets.standardAssets;

import java.awt.*;

/**
 * clean button with a text
 */
@SuppressWarnings("unused")
public class MyButton extends CleanButton {

    /**
     * color of the text
     */
    private Color textColor;

    /**
     * creates a standard clean button with a text
     *
     * @param text of the button
     */
    public MyButton(String text) {
        this(text, StandardAssetFields.FOREGROUND_COLOR);
    }

    /**
     * creates a standard clean button with a text
     *
     * @param text of the button
     */
    private MyButton(String text, Color textColor) {
        super();
        this.textColor = textColor;
        this.setForeground(textColor);
        this.setFont(new Font("Arial", Font.PLAIN, 30));
        this.setText(text);
    }

    /**
     * @return text color of the button
     */
    public Color getTextColor() {
        return textColor;
    }
}
