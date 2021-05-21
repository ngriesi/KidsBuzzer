package assets.settings.rows;

import assets.combobox.MyComboBox;
import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;

import javax.swing.*;
import java.awt.event.ItemEvent;

/**
 * creates a settings row with a combo box
 */
public class ComboBoxSettingsRow<T> extends SettingsRow {

    /**
     * combo box
     */
    private MyComboBox<T> comboBox;

    /**
     * creates the settings row
     *
     * @param settingsChangeListener listener that listens for changes to the setting
     * @param name                   name to identify the setting in the listener
     * @param description            description that gets displayed in the settings
     * @param startValue             start value of the settings
     * @param values                 possible values of the settings row
     */
    public ComboBoxSettingsRow(SettingsChangeListener settingsChangeListener, String name, String description, T startValue, T[] values) {
        super(description);

        comboBox = new MyComboBox<>(values);
        comboBox.setSelectedItem(startValue);
        comboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                settingsChangeListener.settingChanged(new SettingsEvent<>(e.getItem(), name, SettingsEvent.RowKind.COMBO_BOX, "", getPageIdentificationName()));
            }
        });

        super.addInteractionElement(comboBox);
    }

    /**
     * updates the displayed value of this settings row
     *
     * @param value selected value
     */
    public void setSetting(T value) {
        comboBox.setSelectedItem(value);
    }

    /**
     * sets the possible values of this settings row
     *
     * @param values possible values
     */
    public void setPossibleValues(T[] values) {
        comboBox.setModel(new DefaultComboBoxModel<>(values));
    }
}
