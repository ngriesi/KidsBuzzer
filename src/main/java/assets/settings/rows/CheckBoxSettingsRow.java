package assets.settings.rows;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.standardAssets.MyCheckBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * creates a settings row with a check box for boolean values
 */
public class CheckBoxSettingsRow extends SettingsRow {

    /**
     * check box
     */
    private MyCheckBox checkBox;

    /**
     * creates the settings row
     *
     * @param settingsChangeListener listener that listens for changes to the setting
     * @param name                   name to identify the setting in the listener
     * @param description            description that gets displayed in the settings
     * @param startValue             start value of the settings
     */
    public CheckBoxSettingsRow(SettingsChangeListener settingsChangeListener, String name, String description, boolean startValue) {
        super(description);

        checkBox = new MyCheckBox();
        checkBox.setSelected(startValue);

        super.addInteractionElement(checkBox);

        checkBox.addItemListener(createItemListener(settingsChangeListener, name));
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
                settingsChangeListener.settingChanged(new SettingsEvent<>(true, name));
            } else {
                settingsChangeListener.settingChanged(new SettingsEvent<>(false, name));
            }
        };
    }

    /**
     * sets the setting
     *
     * @param value new value
     */
    public void setSetting(Boolean value) {
        checkBox.setSelected(value);
    }
}
