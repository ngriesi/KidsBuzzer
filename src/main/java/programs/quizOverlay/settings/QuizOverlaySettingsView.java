package programs.quizOverlay.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.rows.*;
import assets.standardAssets.MyPanel;
import assets.standardAssets.StandardAssetFields;
import programs.abstractProgram.ProgramSettingsView;
import programs.quizOverlay.settings.pages.AudioSettingsPage;
import programs.quizOverlay.settings.pages.FontSettingsPage;
import programs.quizOverlay.settings.pages.ImageSettingsPage;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * view of the settings of the quiz time program
 */
public class QuizOverlaySettingsView extends ProgramSettingsView {

    /**
     * Settings page for the audio settings
     */
    private AudioSettingsPage audioSettingsPage;

    /**
     * settings page for the font selection
     */
    private FontSettingsPage fontSettingsPage;

    /**
     * settings page for the image selection
     */
    private ImageSettingsPage imageSettingsPage;

    /**
     * creates a new view
     *
     * @param programController sets the controller of the view
     */
    QuizOverlaySettingsView(QuizOverlaySettingsController programController) {
        super(programController, programController, new String[]{Text.IMAGES, Text.FONT, Text.AUDIO});
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
        return new JPanel[]{imageSettingsPage, fontSettingsPage, audioSettingsPage};
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
}
