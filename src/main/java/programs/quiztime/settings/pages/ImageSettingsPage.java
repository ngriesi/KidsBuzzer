package programs.quiztime.settings.pages;

import assets.settings.general.SettingsChangeListener;
import assets.settings.rows.FileChooserSettingsRow;
import savedataHandler.languages.Text;

import java.io.File;

import static programs.quiztime.data.QuizTimeProgramModel.BACKGROUND;


/**
 * Settings Page of the image selection of the quiztime program
 */
public class ImageSettingsPage extends programs.quizPrograms.settings.pages.ImageSettingsPage {

    /**
     * Settings row to select the image for the background
     */
    private FileChooserSettingsRow presentationBackground;


    /**
     * creates the settings page
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    public ImageSettingsPage(SettingsChangeListener settingsChangeListener) {
        super(settingsChangeListener);
    }

    /**
     * creates the settings rows for the image settings
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    protected void createImageSelectionView(SettingsChangeListener settingsChangeListener) {

        presentationBackground = new FileChooserSettingsRow(settingsChangeListener, BACKGROUND, Text.CHOOSE_BACKGROUND, new File("default"), Text.IMAGES, "png", "jpg");

        super.addRow(presentationBackground);

        super.createImageSelectionView(settingsChangeListener);
    }

    /**
     * @return returns the settings row for the background
     */
    public FileChooserSettingsRow getPresentationBackground() {
        return presentationBackground;
    }
}
