package assets.settings.rows;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.standardAssets.MyButton;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;



/**
 * settings row used to choose a file
 */
public class FileChooserSettingsRow extends SettingsRow<String> {

    /**
     * button of the chooser
     */
    private MyButton button;

    /**
     * creates the settings row
     *
     * @param settingsChangeListener listener that listens for changes to the setting
     * @param name name to identify the setting in the listener
     * @param description description that gets displayed in the settings
     * @param startValue start value of the setting
     * @param fileExtensions extensions of the files visible in the file chooser
     */
    public FileChooserSettingsRow(SettingsChangeListener settingsChangeListener, String name, String description, File startValue, String filterName, String... fileExtensions) {
        super(description);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.resetChoosableFileFilters();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter(filterName,fileExtensions));


        button = new MyButton(startValue.getName());
        button.addActionListener(e -> buttonAction(fileChooser,button,settingsChangeListener,name));
        super.addInteractionElement(button);
    }

    /**
     * action of the button: opens a file chooser and fires the settings changed event
     *
     * @param fileChooser file chooser to select a file
     * @param button button that opens the file chooser, used to update its text
     * @param settingsChangeListener listener that listens for changes to the setting
     * @param name name to identify the setting in the listener
     */
    private void buttonAction(JFileChooser fileChooser, MyButton button, SettingsChangeListener settingsChangeListener, String name) {
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            button.setText(fileChooser.getSelectedFile().getName());
            settingsChangeListener.settingChanged(new SettingsEvent<>(fileChooser.getSelectedFile(),name));
        }
    }

    /**
     * set the value of the setting
     *
     * @param value new value
     */
    @Override
    public void setSetting(String value) {
        button.setText(new File(value).getName());
    }
}
