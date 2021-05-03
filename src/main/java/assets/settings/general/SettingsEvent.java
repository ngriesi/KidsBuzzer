package assets.settings.general;

/**
 * event created when a settings changed. This event gets passed to the Settings change listener of the settings view element
 *
 * @param <T> type of the new value of the setting
 * @see SettingsChangeListener
 */
public class SettingsEvent<T> {

    /**
     * name to identify the setting in the settings changed method
     */
    private String name;

    /**
     * new value of the setting
     */
    private T value;

    /**
     * constructor creating the settings event
     *
     * @param newValue new value of the setting
     * @param name     name of the setting
     */
    public SettingsEvent(T newValue, String name) {
        this.value = newValue;
        this.name = name;

    }

    /**
     * getter to access the value of the setting
     *
     * @return returns the new value of the setting
     */
    public T getValue() {
        return value;
    }

    /**
     * getter to access the name of the setting
     *
     * @return returns the name of the setting
     */
    public String getName() {
        return name;
    }
}
