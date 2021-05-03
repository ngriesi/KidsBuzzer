package assets.settings.general;

import assets.standardAssets.MyPanel;
import assets.standardAssets.StandardAssetFields;
import utils.saveFile.SaveFile;

import javax.swing.*;
import java.awt.*;

/**
 * abstract super class for settings views
 *
 * @param <T> type of the save file
 */
public abstract class SettingsView<T extends SaveFile> extends MyPanel {

    protected SettingsController settingsController;

    /**
     * creates a new settings view
     */
    public SettingsView() {
        super(new GridBagLayout());
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
    public abstract void updateSettings(T saveFile);

    /**
     * creates the layout for the settings view
     */
    void createSettings() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        this.addComponent(this, mainPanel, 1, 3, 0, 0, 1f, 1f);

        createSettingsRows(mainPanel);
    }

    /**
     * creates the rows with the different settings
     *
     * @param mainPanel main panel for the settings rows
     */
    protected abstract void createSettingsRows(JPanel mainPanel);
}
