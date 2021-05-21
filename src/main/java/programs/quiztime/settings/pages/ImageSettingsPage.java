package programs.quiztime.settings.pages;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsPage;
import assets.settings.rows.EmptySettingsRow;
import assets.settings.rows.FileChooserSettingsRow;
import assets.standardAssets.StandardAssetFields;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;

import java.io.File;


/**
 * Settings Page of the image selection of the quiztime program
 */
public class ImageSettingsPage extends SettingsPage {

    /**
     * Settings row to select the image for the background
     */
    private FileChooserSettingsRow presentationBackground;

    /**
     * Settings rows to select the images for the icons
     */
    private FileChooserSettingsRow[] icons;

    /**
     * creates the settings page
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
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
    private void createImageSelectionView(SettingsChangeListener settingsChangeListener) {

        presentationBackground = new FileChooserSettingsRow(settingsChangeListener, "background", Text.CHOOSE_BACKGROUND, new File("default"), Text.IMAGES, "png", "jpg");

        super.addRow(presentationBackground);

        icons = new FileChooserSettingsRow[SaveDataHandler.MAX_BUZZER_COUNT];

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            icons[i] = new FileChooserSettingsRow(settingsChangeListener, "icon" + i, Text.SELECT_ICON_BUZZER + " " + (i + 1), new File("default"), Text.IMAGES, "png", "jpg");

            super.addRow(icons[i]);
        }

        super.addEmpty();
    }

    /**
     * @return returns the settings row for the background
     */
    public FileChooserSettingsRow getPresentationBackground() {
        return presentationBackground;
    }

    /**
     * @return returns the array of <code>FileChooserSettingsRows</code> for the icons
     */
    public FileChooserSettingsRow[] getIcons() {
        return icons;
    }

}
