package assets.settings.rows;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.standardAssets.MyColorSelector;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * creates a settings row with a color selector
 */
public class ColorSelectionRow extends SettingsRow {

    /**
     * check box
     */
    private MyColorSelector colorSelector;

    /**
     * creates the settings row
     *
     * @param settingsChangeListener listener that listens for changes to the setting
     * @param name                   name to identify the setting in the listener
     * @param description            description that gets displayed in the settings
     * @param startValue             start value of the settings
     */
    public ColorSelectionRow(SettingsChangeListener settingsChangeListener, String name, String description, Color startValue) {
        super(description);

        colorSelector = new MyColorSelector(startValue);
        colorSelector.setColor(startValue);

        super.addInteractionElement(colorSelector);

        colorSelector.addItemListener(createItemListener(settingsChangeListener, name));
    }

    /**
     * creates an item listener for this check box to update the setting
     *
     * @param settingsChangeListener settings changed listener
     * @param name                   name to identify the setting in the listener
     * @return ItemListener for the check box
     */
    private ItemListener createItemListener(SettingsChangeListener settingsChangeListener, String name) {
        return e -> {
            int state = e.getStateChange();

            if (state == ItemEvent.SELECTED) {
                settingsChangeListener.settingChanged(new SettingsEvent<>(((MyColorSelector)e.getSource()).getBackground(), name, SettingsEvent.RowKind.COLOR_SELECTION, "", getPageIdentificationName()));
            }
        };
    }

    /**
     * sets the setting
     *
     * @param value new value
     */
    public void setSetting(Color value) {
        colorSelector.setColor(value);
    }
}
