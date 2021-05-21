package assets.standardAssets;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * custom check box
 */
public class MyCheckBox extends JToggleButton {

    /**
     * colors for the different states of the button
     */
    private Color pressedColor = StandardAssetFields.PRESSED_COLOR, selectedColor = StandardAssetFields.ROLLOVER_COLOR, normalColor = StandardAssetFields.NORMAL_COLOR;

    /**
     * creates a custom check box and sets the attributes
     */
    public MyCheckBox() {

        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        this.setBackground(StandardAssetFields.NORMAL_COLOR);
        this.setPreferredSize(new Dimension(40, 40));
        this.setMinimumSize(new Dimension(40,40));
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setOpaque(true);

        addChangeListener(createCheckBoxChangeListener());
    }

    /**
     * @return change listener for the check box
     */
    private ChangeListener createCheckBoxChangeListener() {
        return evt -> {
            if (getModel().isPressed()) {
                setBackground(pressedColor);
            } else if (getModel().isSelected()) {
                setBackground(selectedColor);
            } else {
                setBackground(normalColor);
            }
        };
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
     * sets the color the button has in its selected state
     *
     * @param selectedColor new selected color of the button
     */
    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    /**
     * sets the color the button has in its normal state
     *
     * @param normalColor new normal color of the button
     */
    @SuppressWarnings("unused")
    public void setNormalColor(Color normalColor) {
        this.normalColor = normalColor;
    }
}
