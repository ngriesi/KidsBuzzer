package assets.settings.rows;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.standardAssets.MyButton;
import assets.standardAssets.MyLabel;
import assets.standardAssets.MyPanel;
import assets.standardAssets.MySlider;
import savedataHandler.languages.Text;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * Settings row used to set the settings for an audio used in the application.
 * With the settings row the file and the volume of the audio can be set
 * <p>
 * Possible Names of the Settings Events:
 *
 * <code>name</code> + "File"
 * Settings event used to set the file of the audio
 * this settings event contains a file as value
 *
 * <code>name</code> + "Volume"
 * Settings event used to set the volume of the audio
 * this settings event contains an int as value (between 0 and 100)
 */
public class AudioSettingRow extends SettingsRow<AudioSettingRow.AudioData> {

    /**
     * component identifications
     */
    @SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
    public static String VOLUME = "Volume", FILE = "File";

    /**
     * button, opening the file chooser for the audio file
     */
    private MyButton button;

    /**
     * volume slider
     */
    private MySlider slider;

    /**
     * creates the settings row
     *
     * @param settingsChangeListener listener that listens for changes to the setting
     * @param name                   name to identify the setting in the listener
     * @param description            description that gets displayed in the settings
     */
    public AudioSettingRow(SettingsChangeListener settingsChangeListener, String name, String description) {
        super(name, description);

        currentValue = new AudioData(new File("default"), 0f);

        JFileChooser fileChooser = createFileChooser();

        MyPanel interaction = createInteractionPanel(settingsChangeListener, fileChooser);

        super.addInteractionElement(interaction);
    }

    /**
     * creates the panel that contains all the interaction elements of this view element
     *
     * @param settingsChangeListener change Listener of the settings that get notified when the settings has changed
     * @param fileChooser            file chooser used to select the audio file
     * @return return the completely build panel containing all the interaction elements
     */
    private MyPanel createInteractionPanel(SettingsChangeListener settingsChangeListener, JFileChooser fileChooser) {
        MyPanel interaction = new MyPanel(new GridBagLayout());
        interaction.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 5, Toolkit.getDefaultToolkit().getScreenSize().height / 10));
        interaction.addComponent(interaction, createFileChooserButton(settingsChangeListener, fileChooser), 1, 2, 0, 0, 1, 1);
        interaction.addComponent(interaction, createLabel(), 0, 1, 1, 1);
        interaction.addComponent(interaction, createSlider(settingsChangeListener), 1, 1, 1, 1);
        return interaction;
    }

    /**
     * creates the label containing the word "Volume"
     *
     * @return returns the volume label
     */
    private MyLabel createLabel() {
        MyLabel label = new MyLabel(Text.VOLUME + "   ");
        label.setPreferredSize(new Dimension(1, 1));
        return label;
    }

    /**
     * creates the slide that sets the volume of the audio
     *
     * @param settingsChangeListener change listener of this settings row
     * @return returns the slider
     */
    private MySlider createSlider(SettingsChangeListener settingsChangeListener) {
        slider = new MySlider(100);
        slider.addActionListener((e) -> settingsChangeListener.settingChanged(createSettingsEvent(VOLUME, new AudioData(currentValue.filePath, slider.getRelativeValue()), SettingsEvent.RowKind.AUDIO)));
        slider.setPreferredSize(new Dimension(1, 1));
        return slider;
    }

    /**
     * creates the button that opens the file chooser view
     *
     * @param settingsChangeListener change listener of the settings row
     * @param fileChooser            file chooser used to select the audio file
     * @return returns the button that opens the file chooser
     */
    private MyButton createFileChooserButton(SettingsChangeListener settingsChangeListener, JFileChooser fileChooser) {
        button = new MyButton("default");
        button.addActionListener(e -> buttonAction(fileChooser, button, settingsChangeListener));
        button.setPreferredSize(new Dimension(1, 1));
        button.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 5, Toolkit.getDefaultToolkit().getScreenSize().height / 20));
        return button;
    }

    /**
     * creates the file chooser for wave files to select an audio file
     *
     * @return returns the file chooser
     */
    private JFileChooser createFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.resetChoosableFileFilters();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Waves", "wav"));
        return fileChooser;
    }

    /**
     * action of the button that opens the file chooser
     *
     * @param fileChooser            file chooser, that gets opened by the button
     * @param button                 button this is the action of
     * @param settingsChangeListener change listener of the settings row
     */
    private void buttonAction(JFileChooser fileChooser, MyButton button, SettingsChangeListener settingsChangeListener) {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            button.setText(fileChooser.getSelectedFile().getName());
            settingsChangeListener.settingChanged(createSettingsEvent(FILE, new AudioData(fileChooser.getSelectedFile(), currentValue.volume), SettingsEvent.RowKind.AUDIO));
        }
    }

    /**
     * Method used to update the view of the settings
     *
     * @param value new value that gets set to this view
     */
    public void setSetting(AudioData value) {
        super.setSetting(value);
        button.setText(value.getFile().getName());
        slider.setRelativeValue(value.getVolume());
    }

    /**
     * class containing the data of an audio settings row
     * <p>
     * consisting of the File and the volume
     */
    public static class AudioData {

        /**
         * file of the audio file
         */
        private File filePath;

        /**
         * volume of the audio file
         */
        private float volume;

        /**
         * creates a new AudioData object from a file and a volume
         *
         * @param filePath file of the audio
         * @param volume   volume of the audio
         */
        public AudioData(File filePath, float volume) {
            this.filePath = filePath;
            this.volume = volume;
        }

        /**
         * getter to access the file
         *
         * @return returns the file of the audio
         */
        public File getFile() {
            return filePath;
        }

        /**
         * getter to access the volume
         *
         * @return returns the volume of the audio
         */
        public float getVolume() {
            return volume;
        }
    }
}
