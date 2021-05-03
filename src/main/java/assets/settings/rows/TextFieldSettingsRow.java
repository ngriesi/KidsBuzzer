package assets.settings.rows;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.standardAssets.MyTextField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * creates a settings row with a text field to enter a string
 */
public class TextFieldSettingsRow extends SettingsRow {

    /**
     * text field of the row
     */
    private MyTextField textField;

    /**
     * creates the settings row
     *
     * @param settingsChangeListener listener that listens for changes to the setting
     * @param name                   name to identify the setting in the listener
     * @param description            description that gets displayed in the settings
     * @param startValue             start value of the setting
     */
    public TextFieldSettingsRow(SettingsChangeListener settingsChangeListener, String name, String description, String startValue) {
        super(description);


        textField = new MyTextField(startValue);
        textField.getDocument().addDocumentListener(createDocumentListener(settingsChangeListener, textField, name));

        super.addInteractionElement(textField);
    }

    /**
     * creates a Document Listener to inform the Settings Listener when the TextField was changed
     *
     * @param settingsChangeListener listener that listens for changes to the setting
     * @param textField              text field to input the new value for the setting
     * @param name                   name to identify the setting in the listener
     * @return returns the document listener for the text field
     */
    private DocumentListener createDocumentListener(SettingsChangeListener settingsChangeListener, JTextField textField, String name) {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateValue();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateValue();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateValue();
            }

            private void updateValue() {
                settingsChangeListener.settingChanged(new SettingsEvent<>(textField.getText(), name));
            }
        };
    }

    /**
     * set the value of the setting
     *
     * @param value new value
     */
    public void setSetting(String value) {
        textField.setText(value);
    }
}
