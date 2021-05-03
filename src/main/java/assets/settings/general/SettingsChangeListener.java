package assets.settings.general;

/**
 * interface implemented by the class that wants to get notified when a setting has changed
 */
public interface SettingsChangeListener {

    /**
     * Method called when a settings has changed
     *
     * @param se settings event created by the settings view element, containing a name to identify the settings and
     *           the new value of the setting
     */
    void settingChanged(SettingsEvent se);
}
