package assets.standardAssets;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * a button with a clean layout. it has one color for the pressed, rollover and normal state; no border
 * or painted focus
 * can have text
 */
@SuppressWarnings({"unused", "FieldCanBeLocal", "WeakerAccess"})
public class CleanButton extends JButton {

    /**
     * colors for the different states of the button
     */
    private Color pressedColor, rolloverColor, normalColor;

    /**
     * creates a button with the default blue layout of the clean buttons
     */
    protected CleanButton() {
        this(StandardAssetFields.PRESSED_COLOR, StandardAssetFields.ROLLOVER_COLOR, StandardAssetFields.NORMAL_COLOR);
    }

    /**
     * creates a button but sets the colors for the three button states
     *
     * @param pressedColor  color hen button is pressed
     * @param rolloverColor color when the mouse is inside the button
     * @param normalColor   normal color of the button
     */
    protected CleanButton(Color pressedColor, Color rolloverColor, Color normalColor) {

        this.pressedColor = pressedColor;
        this.rolloverColor = rolloverColor;
        this.normalColor = normalColor;

        setLayoutValues(normalColor);

        addChangeListener(createCleanButtonChangeListener());
    }

    /**
     * sets the values for the layout of the button
     *
     * @param normalColor color of the button in its normal state
     */
    private void setLayoutValues(Color normalColor) {
        this.setBorderPainted(false);
        this.setBackground(normalColor);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setOpaque(true);
    }

    /**
     * creates a change listener for the button to set its colors
     *
     * @return change listener for this button
     */
    private ChangeListener createCleanButtonChangeListener() {
        return evt -> {
            if (getModel().isPressed()) {
                setBackground(pressedColor);
            } else if (getModel().isRollover()) {
                setBackground(rolloverColor);
            } else {
                setBackground(normalColor);
            }
        };
    }

    /**
     * @return the color when the button gets pressed
     */
    public Color getPressedColor() {
        return pressedColor;
    }

    /**
     * @return the color of the button when the mouse is inside the button
     */
    protected Color getRolloverColor() {
        return rolloverColor;
    }

    /**
     * @return the color of the button when it is normal
     */
    protected Color getNormalColor() {
        return normalColor;
    }

    /**
     * sets the color the button has in its pressed state
     *
     * @param pressedColor new pressed color of the button
     */
    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }

    /**
     * sets the color the button has in its rollover state
     *
     * @param rolloverColor new rollover color of the button
     */
    public void setRolloverColor(Color rolloverColor) {
        this.rolloverColor = rolloverColor;
    }

    /**
     * sets the color the button has in its normal state
     *
     * @param normalColor new normal color of the button
     */
    public void setNormalColor(Color normalColor) {
        this.normalColor = normalColor;
    }
}
