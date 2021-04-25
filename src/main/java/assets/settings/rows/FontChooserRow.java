package assets.settings.rows;

import assets.combobox.MyComboBox;
import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.standardAssets.*;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.NONE;

public class FontChooserRow extends SettingsRow<FontData> {

    /**
     * combo box
     */
    private MyComboBox<String> comboBox;

    /**
     * check box
     */
    private MyCheckBox bold;

    /**
     * color selector
     */
    private MyColorSelector colorSelector;

    /**
     * creates the settings row
     *
     * @param settingsChangeListener listener that listens for changes to the setting
     * @param name name to identify the setting in the listener
     * @param description description that gets displayed in the settings
     * @param startValue start value of the settings
     */
    public FontChooserRow(SettingsChangeListener settingsChangeListener, String name, String description, FontData startValue) {
        super(description);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        comboBox = new MyComboBox<>(fonts);
        comboBox.setSelectedItem(startValue.getFont().getName());
        comboBox.addItemListener(e -> settingsChangeListener.settingChanged(new SettingsEvent<>(comboBox.getSelectedItem(),name + "font")));

        bold = new MyCheckBox();
        bold.setSelected(startValue.getFont().isBold());
        bold.addItemListener(e -> settingsChangeListener.settingChanged(new SettingsEvent<>(bold.isSelected(),name + "style")));

        colorSelector = new MyColorSelector(startValue.getColor());
        colorSelector.addItemListener(e -> settingsChangeListener.settingChanged(new SettingsEvent<>(colorSelector.getPressedColor(), name + "color")));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 10, 0, 10);
        c.fill = BOTH;
        panel.add(new MyLabel("Fett"),c);
        panel.add(bold,c);
        panel.add(new MyLabel("Style"));
        panel.add(comboBox,c);
        panel.add(new MyLabel("Farbe"));
        panel.add(colorSelector, c);
        this.add(panel, BorderLayout.LINE_END);
    }

    @Override
    public void setSetting(FontData value) {
        comboBox.setSelectedItem(value.getFont().getName());
        bold.setSelected(value.getFont().isBold());
        colorSelector.setColor(value.getColor());
        repaint();

    }
}
