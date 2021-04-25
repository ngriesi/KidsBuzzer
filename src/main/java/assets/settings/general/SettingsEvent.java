package assets.settings.general;

public class SettingsEvent<T> {

    private String name;

    private T value;

    public SettingsEvent(T newValue, String name) {
        this.value = newValue;
        this.name = name;

    }

    public T getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
