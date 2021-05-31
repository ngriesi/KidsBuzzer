package assets.settings.rows;

import assets.combobox.MyComboBox;
import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.standardAssets.MyCheckBox;
import assets.standardAssets.MyColorSelector;
import assets.standardAssets.MyLabel;
import assets.standardAssets.StandardAssetFields;
import savedataHandler.languages.Text;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.*;

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
public class FontChooserRow extends SettingsRow<FontData> {

    /**
     * Identification names of the components of this row
     */
    public static final String COLOR = "Color", STYLE = "Style", FONT = "Font";

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
        super(name, description);

        createFontSelector(settingsChangeListener, startValue);
        createBoldCheckBox(settingsChangeListener, startValue);
        createColorSelector(settingsChangeListener, startValue);

        this.add(createInteractionPanel(), BorderLayout.LINE_END);
    }

    /**
     * creates the panel that contains all the interaction elements of this view element
     *
     * @return return the completely build panel containing all the interaction elements
     */
    private JPanel createInteractionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 1, 1, CENTER, NONE, new Insets(0, 10, 0, 10), 0, 0);
        panel.add(new MyLabel(Text.BOLD), c);
        c.gridx = 1;
        panel.add(bold, c);
        c.gridx = 2;
        panel.add(new MyLabel(Text.STYLE));
        c.gridx = 3;
        panel.add(comboBox, c);
        c.gridx = 4;
        panel.add(new MyLabel(Text.COLOR));
        c.gridx = 5;
        panel.add(colorSelector, c);
        return panel;
    }

    /**
     * creates the color selector to select the font color
     *
     * @param settingsChangeListener change listener of the settings row
     * @param startValue             start value of the color selector
     */
    private void createColorSelector(SettingsChangeListener settingsChangeListener, FontData startValue) {
        colorSelector = new MyColorSelector(startValue.getColor());
        colorSelector.addItemListener(e -> settingsChangeListener.settingChanged(createSettingsEvent(COLOR, new FontData(currentValue.getFont(), colorSelector.getPressedColor()), SettingsEvent.RowKind.FONT)));
    }

    /**
     * creates the check box used to make the font bold
     *
     * @param settingsChangeListener change listener of the settings row
     * @param startValue             start value of the check box
     */
    private void createBoldCheckBox(SettingsChangeListener settingsChangeListener, FontData startValue) {
        bold = new MyCheckBox();
        bold.setSelected(startValue.getFont().isBold());
        bold.addItemListener(e -> settingsChangeListener.settingChanged(
                createSettingsEvent(STYLE,
                        new FontData(new Font(currentValue.getFont().getFontName(), bold.isSelected() ? Font.BOLD : Font.PLAIN, 200), currentValue.getColor()), SettingsEvent.RowKind.FONT)));
    }

    /**
     * creates a combo box containing all the fonts of the system to be selected
     *
     * @param settingsChangeListener change listener of the settings row
     * @param startValue             start value of the font selector
     */
    private void createFontSelector(SettingsChangeListener settingsChangeListener, FontData startValue) {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        comboBox = new MyComboBox<>(fonts);
        comboBox.setSelectedItem(startValue.getFont().getName());
        comboBox.addItemListener(e -> settingsChangeListener.settingChanged(
                createSettingsEvent(FONT,
                        new FontData(new Font((String) comboBox.getSelectedItem(), currentValue.getFont().getStyle(), 200), currentValue.getColor()), SettingsEvent.RowKind.FONT)));
    }


    /**
     * Method used to update the view with a value for the settings
     *
     * @param value new value that gets set to the view
     */
    public void setSetting(FontData value) {
        super.setSetting(value);
        comboBox.setSelectedItem(value.getFont().getName());
        bold.setSelected(value.getFont().isBold());
        colorSelector.setColor(value.getColor());
        repaint();

    }
}
