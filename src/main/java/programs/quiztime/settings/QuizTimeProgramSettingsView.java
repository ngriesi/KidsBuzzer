package programs.quiztime.settings;

import assets.settings.general.SettingsChangeListener;
import programs.abstractProgram.ProgramSettingsView;
import programs.quiztime.settings.pages.AudioSettingsPage;
import programs.quiztime.settings.pages.FontSettingsPage;
import programs.quiztime.settings.pages.ImageSettingsPage;
import programs.quiztime.settings.pages.MidiSettingsPage;
import savedataHandler.languages.Text;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * view of the settings of the quiz time program
 */
public class QuizTimeProgramSettingsView extends ProgramSettingsView {

    /**
     * Settings Page for the fonts
     */
    private FontSettingsPage fontSettingsPage;

    /**
     * Settings Page for the audio
     */
    private AudioSettingsPage audioSettingsPage;

    /**
     * Settings Page for the Images
     */
    private ImageSettingsPage imageSettingsPage;

    /**
     * Settings Page for the Midi controls
     */
    private MidiSettingsPage midiSettingsPage;

    /**
     * creates a new view
     *
     * @param programController sets the controller of the view
     */
    QuizTimeProgramSettingsView(QuizTimeProgramSettingsController programController) {
        super(programController, programController, new String[]{Text.IMAGES, Text.FONT, Text.AUDIO, Text.LIGHT_CONTROL});
    }

    /**
     * Method creates the panel that a re the different pages of the settings
     *
     * @param actionListener         <code>ActionListener</code> of the view
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     * @return an array of panels representing the pages of the settings
     */
    @Override
    protected JPanel[] createPanels(ActionListener actionListener, SettingsChangeListener settingsChangeListener) {
        audioSettingsPage = new AudioSettingsPage(settingsChangeListener);
        fontSettingsPage = new FontSettingsPage(settingsChangeListener);
        imageSettingsPage = new ImageSettingsPage(settingsChangeListener);
        midiSettingsPage = new MidiSettingsPage(settingsChangeListener);
        return new JPanel[]{imageSettingsPage, fontSettingsPage, audioSettingsPage, midiSettingsPage};
    }

    /**
     * @return returns the audio settings page containing the settings rows to select
     * the audios of the program
     */
    public AudioSettingsPage getAudioSettingsPage() {
        return audioSettingsPage;
    }

    /**
     * @return returns the font settings page containing the settings rows to select
     * the fonts of the program
     */
    public FontSettingsPage getFontSettingsPage() {
        return fontSettingsPage;
    }

    /**
     * @return returns the image settings page containing the settings rows to select
     * the images of the program
     */
    public ImageSettingsPage getImageSettingsPage() {
        return imageSettingsPage;
    }

    /**
     * @return returns the midi settings page containing the settings rows to select
     * the midi actions of the program
     */
    public MidiSettingsPage getMidiSettingsPage() {
        return midiSettingsPage;
    }
}
