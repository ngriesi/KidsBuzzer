package assets.settings.rows;

import assets.combobox.MyComboBox;
import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.standardAssets.MyCheckBox;
import assets.standardAssets.MyColorSelector;
import assets.standardAssets.MyLabel;
import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;

/**
 * settings row used to layout a font used in the program. It can update the
 * font itself as well as its color and make it bold
 * <p>
 * Possible Names of the Settings Events:
 *
 * <code>name</code> + "font"
 * Settings event used to set the font
 * this settings event contains a String as value
 *
 * <code>name</code> + "style"
 * Settings event used to make the font bold or plain
 * this settings event contains a boolean as value
 *
 * <code>name</code> + "style"
 * Settings event used to set the color of the font
 * this settings event contains an awt.Color as value
 */
public class FontChooserRow extends SettingsRow {

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
     * @param name                   name to identify the setting in the listener
     * @param description            description that gets displayed in the settings
     * @param startValue             start value of the settings
     */
    public FontChooserRow(SettingsChangeListener settingsChangeListener, String name, String description, FontData startValue) {
        super(description);

        createFontSelector(settingsChangeListener, name, startValue);
        createBoldCheckBox(settingsChangeListener, name, startValue);
        createColorSelector(settingsChangeListener, name, startValue);

        this.add(createInteractionPanel(), BorderLayout.LINE_END);
    }

    /**
     * creates the panel that contains all the interaction elements of this view element
     *
     * @return return the completely build panel containing all the interaction elements
     */
    @SuppressWarnings("SpellCheckingInspection")
    private JPanel createInteractionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 1, 1, CENTER, BOTH, new Insets(0, 10, 0, 10), 0, 0);
        panel.add(new MyLabel("Fett"), c);
        panel.add(bold, c);
        panel.add(new MyLabel("Style"));
        panel.add(comboBox, c);
        panel.add(new MyLabel("Farbe"));
        panel.add(colorSelector, c);
        return panel;
    }

    /**
     * creates the color selector to select the font color
     *
     * @param settingsChangeListener change listener of the settings row
     * @param name                   of the settings to identify the settings event in the settings changed method in
     *                               the settings changed listener
     * @param startValue             start value of the color selector
     */
    private void createColorSelector(SettingsChangeListener settingsChangeListener, String name, FontData startValue) {
        colorSelector = new MyColorSelector(startValue.getColor());
        colorSelector.addItemListener(e -> settingsChangeListener.settingChanged(new SettingsEvent<>(colorSelector.getPressedColor(), name + "color")));
    }

    /**
     * creates the check box used to make the font bold
     *
     * @param settingsChangeListener change listener of the settings row
     * @param name                   of the settings to identify the settings event in the settings changed method in
     *                               the settings changed listener
     * @param startValue             start value of the check box
     */
    private void createBoldCheckBox(SettingsChangeListener settingsChangeListener, String name, FontData startValue) {
        bold = new MyCheckBox();
        bold.setSelected(startValue.getFont().isBold());
        bold.addItemListener(e -> settingsChangeListener.settingChanged(new SettingsEvent<>(bold.isSelected(), name + "style")));
    }

    /**
     * creates a combo box containing all the fonts of the system to be selected
     *
     * @param settingsChangeListener change listener of the settings row
     * @param name                   name of the settings to identify the settings event in the settings changed method in
     *                               the settings changed listener
     * @param startValue             start value of the font selector
     */
    private void createFontSelector(SettingsChangeListener settingsChangeListener, String name, FontData startValue) {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        comboBox = new MyComboBox<>(fonts);
        comboBox.setSelectedItem(startValue.getFont().getName());
        comboBox.addItemListener(e -> settingsChangeListener.settingChanged(new SettingsEvent<>(comboBox.getSelectedItem(), name + "font")));
    }


    /**
     * Method used to update the view with a value for the settings
     *
     * @param value new value that gets set to the view
     */
    public void setSetting(FontData value) {
        comboBox.setSelectedItem(value.getFont().getName());
        bold.setSelected(value.getFont().isBold());
        colorSelector.setColor(value.getColor());
        repaint();

    }
}
