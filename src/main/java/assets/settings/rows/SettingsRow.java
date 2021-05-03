package assets.settings.rows;

import assets.standardAssets.MyLabel;
import assets.standardAssets.MyPanel;
import assets.standardAssets.MySeparator;
import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;

/**
 * abstract super class for the settings rows
 */
abstract class SettingsRow extends MyPanel {

    /**
     * thickness of the border
     */
    private final int BORDER_THICKNESS = 10;

    /**
     * creates an empty settings row
     */
    SettingsRow() {
        super(new BorderLayout());
        this.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        this.getInsets().set(BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS);
        this.setPreferredSize(new Dimension(10, 10));
    }

    /**
     * creates a settings row with description and separator line
     *
     * @param description description of the setting displayed in the view
     */
    SettingsRow(String description) {
        this();
        createStandardElements(description);
    }

    /**
     * adds the interaction element to the setting
     *
     * @param component interaction element to change the setting
     */
    void addInteractionElement(JComponent component) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(StandardAssetFields.PANEL_BACKGROUND_COLOR, BORDER_THICKNESS * 2));
        panel.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        panel.add(component);
        this.add(panel, BorderLayout.LINE_END);
    }

    /**
     * creates the standard elements (description label and separator line)
     *
     * @param description description of the setting displayed in the view
     */
    private void createStandardElements(String description) {
        this.add(createLabel(description), BorderLayout.LINE_START);


        MySeparator bottomSeparator = new MySeparator();
        this.add(bottomSeparator, BorderLayout.PAGE_END);
    }

    /**
     * creates the description label
     *
     * @param description description of the setting displayed in the view
     * @return creates the description label
     */
    private MyLabel createLabel(String description) {
        MyLabel label = new MyLabel(description);
        label.setBorder(BorderFactory.createLineBorder(StandardAssetFields.PANEL_BACKGROUND_COLOR, BORDER_THICKNESS));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setAlignmentX(LEFT_ALIGNMENT);
        return label;
    }

}
