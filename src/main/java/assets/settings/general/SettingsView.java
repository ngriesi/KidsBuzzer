package assets.settings.general;

import assets.standardAssets.LayerPanel;
import assets.standardAssets.StandardAssetFields;
import utils.save.SaveFile;

/**
 * abstract super class for settings views
 */
public abstract class SettingsView extends LayerPanel {

    private SettingsController settingsController;

    /**
     * creates a new settings view
     */
    public SettingsView(SettingsController settingsController) {
        super(settingsController, settingsController, new String[0]);
        this.settingsController = settingsController;
        this.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
    }

    /**
     * setts the settings controller
     *
     * @param settingsController controller for the settings
     */
    void setSettingsController(SettingsController settingsController) {
        this.settingsController = settingsController;
    }

    /**
     * updates the settings view with the save file
     *
     * @param saveFile save file containing the current state of the settings
     */
    public abstract void updateSettings(SaveFile saveFile);

}
