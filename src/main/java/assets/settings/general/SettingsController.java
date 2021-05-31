package assets.settings.general;

import utils.save.SaveFile;
import utils.save.SaveFileHandler;

import java.awt.event.ActionListener;

/**
 * parent class for the settings controllers
 *
 * @param <V> type of the settings view
 */
public abstract class SettingsController<V extends SettingsView> implements SettingsChangeListener, ActionListener {


    /**
     * handler for the save file
     */
    protected SaveFileHandler saveFileHandler;

    /**
     * view of these settings
     */
    private V settingsView;

    /**
     * creates a new settings controller
     *
     * @param saveFile     save file of the settings
     */
    public SettingsController(SaveFile saveFile) {
        this.saveFileHandler = new SaveFileHandler(saveFile);
    }

    /**
     * @return returns the save file of the settings
     */
    public SaveFile getSettingsSaveFile() {
        return saveFileHandler.getSaveFile();
    }

    /**
     * @return returns the view of these settings
     */
    public V getSettingsView() {

        settingsView.updateSettings(saveFileHandler.getSaveFile());
        return settingsView;
    }

    /**
     * @return returns the view without updating it
     */
    protected V getViewWithoutUpdate() {
        return settingsView;
    }

    protected void setSettingsView(V settingsView) {
        this.settingsView = settingsView;
        settingsView.setSettingsController(this);
    }
}
