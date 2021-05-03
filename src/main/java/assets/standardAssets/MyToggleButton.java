package assets.standardAssets;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * custom check box
 */
public class MyToggleButton extends JToggleButton {

    /**
     * colors for the different states of the button
     */
    @SuppressWarnings("FieldCanBeLocal")
    private Color pressedColor = StandardAssetFields.PRESSED_COLOR, selectedColor = StandardAssetFields.ROLLOVER_COLOR, normalColor = StandardAssetFields.NORMAL_COLOR, textColor = StandardAssetFields.FOREGROUND_COLOR;

    /**
     * creates a custom check box and sets the attributes
     */
    public MyToggleButton(String text) {

        this.setBackground(StandardAssetFields.NORMAL_COLOR);
        this.setForeground(textColor);
        this.setFont(new Font("Arial", Font.PLAIN, 30));
        this.setText(text);
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
}
