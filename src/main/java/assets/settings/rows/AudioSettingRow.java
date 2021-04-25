package assets.settings.rows;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.standardAssets.MyButton;
import assets.standardAssets.MyLabel;
import assets.standardAssets.MyPanel;
import assets.standardAssets.MySlider;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

import static java.awt.GridBagConstraints.*;

public class AudioSettingRow extends SettingsRow<AudioSettingRow.AudioData> {


    /**
     * button of the chooser
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
     * @param name name to identify the setting in the listener
     * @param description description that gets displayed in the settings
     * @param startValue start value of the setting
     */
    public AudioSettingRow(SettingsChangeListener settingsChangeListener, String name, String description, AudioData startValue) {
        super(description);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.resetChoosableFileFilters();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Waves","wav"));

        MyPanel interaction = new MyPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.gridy = 0;

        button = new MyButton(startValue.getFile().getName());
        button.addActionListener(e -> buttonAction(fileChooser,button,settingsChangeListener,name));
        interaction.add(button, c);

        MyPanel bottom = new MyPanel(new BorderLayout());

        MyLabel label = new MyLabel("Volume");

        bottom.add(label, BorderLayout.LINE_START);

        bottom.add(new MyLabel("   "), BorderLayout.CENTER);

        slider = new MySlider(100);
        slider.addActionListener((e) -> settingsChangeListener.settingChanged(new SettingsEvent<>(((MySlider)e.getSource()).getRelativeValue(), name + "Volume")));
        bottom.add(slider, BorderLayout.LINE_END);


        c.gridy = 1;
        c.fill = BOTH;
        interaction.add(bottom, c);

        super.addInteractionElement(interaction);
    }

    private void buttonAction(JFileChooser fileChooser, MyButton button, SettingsChangeListener settingsChangeListener, String name) {
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            button.setText(fileChooser.getSelectedFile().getName());
            settingsChangeListener.settingChanged(new SettingsEvent<>(fileChooser.getSelectedFile(),name + "File"));
        }
    }

    @Override
    public void setSetting(AudioData value) {
        button.setText(value.getFile().getName());
        slider.setRelativeValue(value.getVolume());
    }

    public static class AudioData {
        
        private File filePath;
        
        private float volume;

        public AudioData(File filePath, float volume) {
            this.filePath = filePath;
            this.volume = volume;
        }

        public File getFile() {
            return filePath;
        }

        float getVolume() {
            return volume;
        }
    }
}
