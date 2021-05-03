package assets.control;

import assets.standardAssets.MyLabel;
import assets.standardAssets.MySeparator;
import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;

/**
 * abstract super class for all ControlViewRows
 */
abstract class ControlViewRow extends JPanel {

    /**
     * spacing used in the rows for different application
     */
    static int spacing = 10;

    /**
     * creates teh default layout elements
     *
     * @param description description displayed in the row
     */
    ControlViewRow(String description) {
        super(new BorderLayout());
        this.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        createSeparator();
        createLabel(description);
    }

    /**
     * creates the separator at the bottom of a row
     */
    private void createSeparator() {
        MySeparator separator = new MySeparator();
        this.add(separator, BorderLayout.PAGE_END);
    }

    /**
     * creates the label to display the description
     *
     * @param description description that gets displayed
     */
    private void createLabel(String description) {
        MyLabel label = new MyLabel(description);
        label.setBorder(BorderFactory.createLineBorder(StandardAssetFields.PANEL_BACKGROUND_COLOR, spacing));
        this.add(label, BorderLayout.LINE_START);
    }
}
