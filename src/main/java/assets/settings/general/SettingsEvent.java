package assets.settings.general;

/**
 * event created when a settings changed. This event gets passed to the Settings change listener of the settings view element
 *
 * @param <T> type of the new value of the setting
 * @see SettingsChangeListener
 */
public class SettingsEvent<T> {

    /**
     * enums for the possible settings rows
     */
    public enum RowKind {
        MIDI, AUDIO, FILECHOOSER, CHECK_BOX, COLOR_SELECTION, COMBO_BOX, FONT, TEXT_FIELD
    }

    /**
     * name to identify the setting in the settings changed method
     */
    private String name;

    /**
     * Kind of the settings row this event was generated at
     */
    private RowKind rowKind;

    /**
     * Component inside the row this event was created in
     */
    private String componentName;

    /**
     * Name of the settings page the row that created this event is inside
     */
    private String pageName;

    /**
     * new value of the setting
     */
    private T value;

    /**
     * constructor creating the settings event
     *
     * @param newValue      new value of the setting
     * @param name          name of the setting
     * @param rowKind       kind of the row that created the settings event
     * @param componentName name of the component that created the settings event
     * @param pageName      name of the page
     */
    public SettingsEvent(T newValue, String name, RowKind rowKind, String componentName, String pageName) {
        this.value = newValue;
        this.name = name;
        this.rowKind = rowKind;
        this.componentName = componentName;
        this.pageName = pageName;
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

    /**
     * getter to access the row kind of the setting
     *
     * @return returns the row kind of the setting
     */
    public RowKind getRowKind() {
        return rowKind;
    }

    /**
     * getter to access the component name of the setting
     *
     * @return returns the component name of the setting
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * getter to access the page name of the setting
     *
     * @return returns the page name of the setting
     */
    public String getPageName() {
        return pageName;
    }
}
