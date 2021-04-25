package assets.settings.rows;

import assets.combobox.MyComboBox;
import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;

/**
 * creates a settings row with a combo box
 */
public class ComboBoxSettingsRow<T> extends SettingsRow<T> {

    /**
     * combo box
     */
    private MyComboBox<T> comboBox;

    /**
     * creates the settings row
     *
     * @param settingsChangeListener listener that listens for changes to the setting
     * @param name name to identify the setting in the listener
     * @param description description that gets displayed in the settings
     * @param startValue start value of the settings
     */
    public ComboBoxSettingsRow(SettingsChangeListener settingsChangeListener, String name, String description, T startValue, T[] values) {
        super(description);


        comboBox = new MyComboBox<>(values);
        comboBox.setSelectedItem(startValue);
        comboBox.addItemListener(e -> settingsChangeListener.settingChanged(new SettingsEvent<>(e.getItem(),name)));

        super.addInteractionElement(comboBox);
    }

    @Override
    public void setSetting(T value) {
        comboBox.setSelectedItem(value);
    }
}
