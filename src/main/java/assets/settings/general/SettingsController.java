package assets.settings.general;

import utils.saveFile.SaveFile;
import utils.saveFile.SaveFileHandler;

import java.awt.event.ActionListener;

/**
 * parent class for the settings controllers
 *
 * @param <T> type of the Save File
 * @param <V> type of the settings view
 */
public abstract class SettingsController<T extends SaveFile, V extends SettingsView<T>> implements SettingsChangeListener, ActionListener {


    /**
     * handler for the save file
     */
    protected SaveFileHandler<T> saveFileHandler;

    /**
     * view of these settings
     */
    private V settingsView;

    /**
     * creates a new settings controller
     *
     * @param saveFile     save file of the settings
     * @param settingsView view of the settings
     */
    public SettingsController(T saveFile, V settingsView) {
        this.saveFileHandler = new SaveFileHandler<>(saveFile);
        this.settingsView = settingsView;

        settingsView.setSettingsController(this);

        settingsView.createSettings();
    }

    /**
     * @return returns the save file of the settings
     */
    public T getSettingsSaveFile() {
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
        settingsView.createSettings();
    }
}
