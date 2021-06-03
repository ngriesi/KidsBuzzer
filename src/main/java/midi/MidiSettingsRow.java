package midi;

import assets.combobox.MyComboBox;
import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.settings.rows.SettingsRow;
import assets.standardAssets.MyCheckBox;
import assets.standardAssets.MyLabel;
import assets.standardAssets.StandardAssetFields;
import org.joml.Vector2i;
import savedataHandler.languages.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.NONE;

/**
 * class used to set the light button executed for a specific action
 */
public class MidiSettingsRow extends SettingsRow<MidiSettingsRow.MidiSettingsRowData> {

    /**
     * Identification names of the components of this row
     */
    @SuppressWarnings({"WeakerAccess", "RedundantSuppression", "unused"})
    public static final String X = "x", Y = "y", ACTIVE = "active";

    /**
     * combo box for the x coordinate
     */
    private MyComboBox<Integer> xCord;

    /**
     * combo box for the y coordinate
     */
    private MyComboBox<Integer> yCord;

    /**
     * check box to activate the midi action
     */
    private MyCheckBox active;

    /**
     * creates the settings row
     *
     * @param settingsChangeListener listener that listens for changes to the setting
     * @param name                   name to identify the setting in the listener
     * @param description            description that gets displayed in the settings
     * @param startValue             start value of the settings
     */
    public MidiSettingsRow(SettingsChangeListener settingsChangeListener, String name, String description, MidiSettingsRowData startValue) {
        super(name, description);

        currentValue = startValue;

        JPanel interaction = new JPanel(new GridBagLayout());
        interaction.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 1, 1, CENTER, NONE, new Insets(0, 10, 0, 10), 0, 0);

        interaction.add(new MyLabel(Text.ACTIVE + ": "), c);

        c.gridx = 1;

        active = new MyCheckBox();
        active.setMinimumSize(new Dimension(40, 40));
        active.setSelected(startValue.active);
        active.addItemListener(createItemListener(settingsChangeListener));
        interaction.add(active, c);
        c.gridx = 2;
        interaction.add(new MyLabel("X: "), c);


        xCord = new MyComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
        xCord.setPreferredSize(new Dimension(100, 40));
        xCord.setMinimumSize(new Dimension(100, 40));
        xCord.setSelectedItem(startValue.button.x);
        xCord.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                settingsChangeListener.settingChanged(createSettingsEvent(X, new MidiSettingsRowData(new Vector2i((Integer) e.getItem(), currentValue.button.y), currentValue.active), SettingsEvent.RowKind.MIDI));
            }
        });

        c.gridx = 3;
        interaction.add(xCord, c);
        c.gridx = 4;

        interaction.add(new MyLabel("Y: "), c);

        yCord = new MyComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6});
        yCord.setPreferredSize(new Dimension(100, 40));
        yCord.setMinimumSize(new Dimension(100, 40));
        yCord.setSelectedItem(startValue.button.y);
        yCord.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                settingsChangeListener.settingChanged(createSettingsEvent(Y, new MidiSettingsRowData(new Vector2i(currentValue.button.x, (Integer) e.getItem()), currentValue.active), SettingsEvent.RowKind.MIDI));
            }
        });

        c.gridx = 5;

        interaction.add(yCord, c);

        super.addInteractionElement(interaction);
    }

    /**
     * creates an item listener for the check box to update the setting
     *
     * @param settingsChangeListener settings changed listener
     * @return ItemListener for the check box
     */
    private ItemListener createItemListener(SettingsChangeListener settingsChangeListener) {
        return e -> {
            int state = e.getStateChange();
            settingsChangeListener.settingChanged(createSettingsEvent(ACTIVE, new MidiSettingsRowData(new Vector2i(currentValue.button.x, currentValue.button.y), state == ItemEvent.SELECTED), SettingsEvent.RowKind.MIDI));
        };
    }

    /**
     * updates the displayed value of this settings row
     *
     * @param value selected value
     */
    public void setSetting(MidiSettingsRowData value) {
        xCord.setSelectedItem(value.button.x);
        yCord.setSelectedItem(value.button.y);
        active.setSelected(value.active);
    }

    /**
     * settings data for a <code>MidiSettingsRow</code>
     */
    public static class MidiSettingsRowData {

        /**
         * Position of the executioner button this row refers to
         */
        private Vector2i button;

        /**
         * flag indicating if the midi action is used
         */
        private boolean active;

        /**
         * Creates a new Settings row data for a midi settings row
         *
         * @param button executioner button position
         * @param active activation flag
         */
        public MidiSettingsRowData(Vector2i button, boolean active) {
            this.button = button;
            this.active = active;
        }

        /**
         * @return returns the psotiion set in the midi settings row
         */
        public Vector2i getButton() {
            return button;
        }

        /**
         * @return returns a flag that indicates whether the action is used or not
         */
        public boolean isActive() {
            return active;
        }
    }
}
