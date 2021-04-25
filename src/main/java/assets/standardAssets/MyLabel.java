package assets.standardAssets;

import javax.swing.*;
import java.awt.*;

/**
 * creates a custom label with left alignment
 */
public class MyLabel extends JLabel {

    /**
     * creates the label with a text
     *
     * @param text content of the label
     */
    public MyLabel(String text) {
        super(text);

        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.setFont(new Font("Arial", Font.PLAIN, 30));
        this.setForeground(StandardAssetFields.FOREGROUND_COLOR);
    }
}
