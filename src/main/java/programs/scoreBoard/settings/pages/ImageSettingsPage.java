package programs.scoreBoard.settings.pages;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsPage;
import assets.settings.rows.FileChooserSettingsRow;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;

import java.io.File;

/**
 * Settings page for the iamge selection of the score board program
 */
public class ImageSettingsPage extends SettingsPage {

    /**
     * settings rows to choose the icons of the teams
     */
    private FileChooserSettingsRow[] iconSettingRows;

    /**
     * creates the image settings page
     *
     *  @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    public ImageSettingsPage(SettingsChangeListener settingsChangeListener) {
        super(Text.IMAGES, "images");
        createIconsSettingsPanel(settingsChangeListener);
    }

    /**
     * creates the settings rows to select the icons
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    private void createIconsSettingsPanel(SettingsChangeListener settingsChangeListener) {

        iconSettingRows = new FileChooserSettingsRow[SaveDataHandler.MAX_BUZZER_COUNT];

        for (int i = 0; i < iconSettingRows.length; i++) {
            iconSettingRows[i] = new FileChooserSettingsRow(settingsChangeListener, "icon:" + SaveDataHandler.BUZZER_NAMES[i], Text.SELECT_ICON_TEAM + " " + (i + 1), new File("default"), "Bilder", "png", "jpg");

            super.addRow(iconSettingRows[i]);
        }

        for (int i = iconSettingRows.length; i < 5; i++) {
            addEmpty();
        }
    }

    /**
     * @return returns the settings rows that are used to select the icons of the teams
     */
    public FileChooserSettingsRow[] getIconSettingRows() {
        return iconSettingRows;
    }
}
