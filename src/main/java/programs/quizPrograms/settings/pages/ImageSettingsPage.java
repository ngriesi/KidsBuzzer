package programs.quizPrograms.settings.pages;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsPage;
import assets.settings.rows.FileChooserSettingsRow;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;

import java.io.File;

import static programs.quizPrograms.data.QuizModel.ICON;

/**
 * settings page of the image selection of the quiz overlay program
 */
public class ImageSettingsPage extends SettingsPage {

    /**
     * Settings rows to select the images for the icons
     */
    private FileChooserSettingsRow[] icons;

    /**
     * creates Panel with Layout
     *
     * @param settingsChangeListener change listener of this settings page
     */
    public ImageSettingsPage(SettingsChangeListener settingsChangeListener) {
        super(Text.IMAGES, "images");
        createImageSelectionView(settingsChangeListener);

    }

    /**
     * creates the settings rows for the image settings
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    protected void createImageSelectionView(SettingsChangeListener settingsChangeListener) {

        icons = new FileChooserSettingsRow[SaveDataHandler.BUZZER_COUNT];

        for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
            icons[i] = new FileChooserSettingsRow(settingsChangeListener, ICON + i, Text.SELECT_ICON_BUZZER + " " + (i + 1), new File("default"), Text.IMAGES, "png", "jpg");

            super.addRow(icons[i]);
        }

        super.addEmptyTo(5);
    }

    /**
     * @return returns the array of <code>FileChooserSettingsRows</code> for the icons
     */
    public FileChooserSettingsRow[] getIcons() {
        return icons;
    }

}
