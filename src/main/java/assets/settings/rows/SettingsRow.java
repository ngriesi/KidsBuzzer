package assets.settings.rows;

import assets.settings.general.SettingsEvent;
import assets.standardAssets.MyLabel;
import assets.standardAssets.MyPanel;
import assets.standardAssets.MySeparator;
import assets.standardAssets.StandardAssetFields;
import programs.quiztime.settings.pages.AudioSettingsPage;

import javax.swing.*;
import java.awt.*;

/**
 * abstract super class for the settings rows
 */
public abstract class SettingsRow<V> extends MyPanel {

    /**
     * thickness of the border
     */
    private final int BORDER_THICKNESS = 10;

    /**
     * identification name of the page this settings row is inside
     */
    private String pageIdentificationName;

    /**
     * name of this settings row
     */
    private String name;

    /**
     * current value of the setting
     */
    protected V currentValue;

    /**
     * creates an empty settings row
     */
    SettingsRow(String name) {
        super(new BorderLayout());
        this.name = name;
        this.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        this.getInsets().set(BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS);
        this.setPreferredSize(new Dimension(10, 10));
    }

    /**
     * creates a settings row with description and separator line
     *
     * @param description description of the setting displayed in the view
     */
    protected SettingsRow(String name, String description) {
        this(name);
        createStandardElements(description);
    }

    /**
     * sets the setting of this row
     *
     * @param value settings value
     */
    public void setSetting(V value) {
        this.currentValue = value;
    }

    /**
     * creates a settings event if one of the settings in this row changed
     *
     * @param component name of the component in the settings row that created the event
     * @param value new value for this settings row
     * @param rowKind the kind of row this is
     * @return returns the build settings event
     */
    protected SettingsEvent<V> createSettingsEvent(String component, V value, SettingsEvent.RowKind rowKind) {
        currentValue = value;
        return new SettingsEvent<>(value,name, rowKind , component, getPageIdentificationName());
    }

    /**
     * adds the interaction element to the setting
     *
     * @param component interaction element to change the setting
     */
    protected void addInteractionElement(JComponent component) {
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

    /**
     * @return returns the identification name of the page this row is inside
     */
    private String getPageIdentificationName() {
        return pageIdentificationName;
    }

    /**
     * sets a new identification name
     *
     * @param pageIdentificationName new identification name
     */
    public void setPageIdentificationName(String pageIdentificationName) {
        this.pageIdentificationName = pageIdentificationName;
    }
}
