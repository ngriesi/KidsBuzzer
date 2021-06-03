package utils.save;

import assets.settings.rows.AudioSettingRow;
import assets.settings.rows.FontData;
import midi.MidiSettingsRow;
import org.joml.Vector2i;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static java.awt.Font.BOLD;

/**
 * Save file class which contains a Hash Map to save Strings, Integers and Boolean values
 */
public class SaveFile {

    /**
     * Name of the save file in the file system
     */
    private String name;

    /**
     * Map containing all the Strings saved by this save file object
     */
    private Map<String, String> strings;

    /**
     * Map containing all the Integer saved by this save file object
     */
    private Map<String, Integer> integers;

    /**
     * Map containing all the Boolean saved by this save file object
     */
    private Map<String, Boolean> booleans;

    /**
     * Creates a new Save file with a name. The constructor also creates the maps for the
     * values that were saved. After creating the maps it tries to load the file with the name
     * and write the values form the file to the Hash Maps.
     *
     * @param name name of the save file in the File System
     */
    public SaveFile(String name) {
        this.name = name;

        strings = new HashMap<>();
        integers = new HashMap<>();
        booleans = new HashMap<>();

        SaveFileLoader.tryLoadingSave(this);
    }

    /**
     * copy constructor
     *
     * @param saveFile save file that gets copied
     */
    public SaveFile(SaveFile saveFile) {
        this.strings = new HashMap<>(saveFile.getStrings());
        this.integers = new HashMap<>(saveFile.getIntegers());
        this.booleans = new HashMap<>(saveFile.getBooleans());
        this.name = saveFile.getName();
    }

    /**
     * used to access a string from the map without risking a null pointer exception
     *
     * @param key key of the string that was retrieved
     * @param defaultValue default value if the Field cant be found
     * @return the value for key or "default" if there was no String for that key
     */
    private String getStringWithCheck(String key, String defaultValue) {
        String result = strings.get(key);
        if (result != null) {
            return result;
        }
        return defaultValue;
    }

    /**
     * used to access an int from the map without risking a null pointer exception
     *
     * @param key key of the int that was retrieved
     * @param defaultValue default value if the Field cant be found
     * @return the value for key or "1" if there was no int for that key
     */
    private int getIntWithCheck(String key, int defaultValue) {
        Integer result = integers.get(key);
        if (result != null) {
            return result;
        }
        return defaultValue;
    }

    /**
     * used to access a boolean from the map without risking a null pointer exception
     *
     * @param key key of the boolean that was retrieved
     * @param defaultValue default value if the Field cant be found
     * @return the value for key or "false" if there was no boolean for that key
     */
    private boolean getBooleanWithCheck(String key, boolean defaultValue) {
        Boolean result = booleans.get(key);
        if (result != null) {
            return result;
        }
        return defaultValue;
    }

    /**
     * gets the value of from a key
     *
     * @param key to access the value
     */
    public String getString(String key) {
        return getStringWithCheck(key, "default");
    }

    /**
     * gets the value of from a key
     *
     * @param key to access the value
     */
    public int getInteger(String key) {
        return getIntWithCheck(key, 1);
    }

    /**
     * gets the value of from a key
     *
     * @param key to access the value
     */
    public boolean getBoolean(String key) {
        return getBooleanWithCheck(key, false);
    }

    /**
     * gets the value of from a key
     *
     * @param key to access the value
     * @param defaultValue default value if the Field cant be found
     */
    public String getString(String key, String defaultValue) {
        return getStringWithCheck(key, defaultValue);
    }

    /**
     * gets the value of from a key
     *
     * @param key to access the value
     * @param defaultValue default value if the Field cant be found
     */
    public int getInteger(String key, int defaultValue) {
        return getIntWithCheck(key, defaultValue);
    }

    /**
     * gets the value of from a key
     *
     * @param key to access the value
     * @param defaultValue default value if the Field cant be found
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return getBooleanWithCheck(key, defaultValue);
    }

    /**
     * Adds a value to the <code>strings</code> HashMap
     *
     * @param key   key to identify the value
     * @param value value stored in the map and saved to a file when <code>saveFile</code>
     *              is called
     */
    public void putString(String key, String value) {
        strings.put(key, value);
    }

    /**
     * Adds a value to the <code>integers</code> HashMap
     *
     * @param key   key to identify the value
     * @param value value stored in the map and saved to a file when <code>saveFile</code>
     *              is called
     */
    public void putInteger(String key, int value) {
        integers.put(key, value);
    }

    /**
     * Adds a value to the <code>booleans</code> HashMap
     *
     * @param key   key to identify the value
     * @param value value stored in the map and saved to a file when <code>saveFile</code>
     *              is called
     */
    public void putBoolean(String key, boolean value) {
        booleans.put(key, value);
    }

    /**
     * @return returns the name of the save file
     */
    public String getName() {
        return name;
    }

    /**
     * Saves the File to the Folder SaveFiles (creates the Folder if it
     * does not exist)
     */
    public void saveFile() {
        SaveFileSaver.saveFile(this);
    }

    /**
     * @return returns the <code>strings HashMap</code>
     */
    Map<String, String> getStrings() {
        return strings;
    }

    /**
     * @return returns the <code>integers HashMap</code>
     */
    Map<String, Integer> getIntegers() {
        return integers;
    }

    /**
     * @return returns the <code>booleans HashMap</code>
     */
    Map<String, Boolean> getBooleans() {
        return booleans;
    }

    /**
     * puts an int array into the <code>integers HashMap</code>
     *
     * @param key    key of the array
     * @param values values put into the map
     */
    public void putIntArray(String key, int[] values) {
        for (int i = 0; i < values.length; i++) {
            integers.put(key + i, values[i]);
        }
    }

    /**
     * creates an array form a set of values from the integers map
     * with the same key with index
     *
     * @param key    key of the array
     * @param length length of the array
     * @return the int array
     */
    public int[] getIntArray(String key, int length) {
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            result[i] = integers.get(key + i);
        }
        return result;
    }

    /*
        Private Fields to separate the Parts of the objects
     */

    private final String red = "red";
    private final String green = "green";
    private final String blue = "blue";
    private final String alpha = "alpha";
    private final String active = "active";
    private final String x = "x";
    private final String y = "y";
    private final String font = "font";
    private final String color = "color";
    private final String style = "style";
    private final String volume = "volume";
    private final String file = "file";

    /*
     **************************************************
     *  java.awt.COLOR SAVING METHODS
     **************************************************
     */

    /**
     * Puts the rgba values of a <code>Color</code> into the maps
     * with the passed key and an addition to the name to identify
     * the individual part
     *
     * @param key   key for the color that gets saved
     * @param color color that gets saved
     */
    public void putColor(String key, Color color) {
        putInteger(key + red, color.getRed());
        putInteger(key + green, color.getGreen());
        putInteger(key + blue, color.getBlue());
        putInteger(key + alpha, color.getAlpha());
    }

    /**
     * Returns a color from a key
     *
     * @param key key to identify the color
     * @return the color from the save
     */
    public Color getColor(String key) {
        return new Color(
                getInteger(key + red, 255),
                getInteger(key + green, 255),
                getInteger(key + blue, 255),
                getInteger(key + alpha, 255)
        );
    }

    /*
     **********************************************************
     *  MidiSettingsRow.MidiSettingsRowData SAVING METHODS
     **********************************************************
     */

    /**
     * Puts values of a <code>MidiSettingsRowData</code> into the maps
     * with the passed key and an addition to the name to identify
     * the individual part
     *
     * @param key                 key for the <code>MidiSettingsRowData</code> that gets saved
     * @param midiSettingsRowData <code>MidiSettingsRowData</code> that gets saved
     */
    public void putMidiSettingsRowData(String key, MidiSettingsRow.MidiSettingsRowData midiSettingsRowData) {
        putBoolean(key + active, midiSettingsRowData.isActive());
        putInteger(key + x, midiSettingsRowData.getButton().x);
        putInteger(key + y, midiSettingsRowData.getButton().y);
    }

    /**
     * Returns a <code>MidiSettingsRowData</code> from a key
     *
     * @param key key to identify the <code>MidiSettingsRowData</code>
     * @return the <code>MidiSettingsRowData</code> from the save
     */
    public MidiSettingsRow.MidiSettingsRowData getMidiSettingsRowData(String key) {
        return new MidiSettingsRow.MidiSettingsRowData(
                new Vector2i(
                        getInteger(key + x),
                        getInteger(key + y)
                ),
                getBoolean(key + active)
        );
    }

    /*
     **********************************************************
     *  FontData SAVING METHODS
     **********************************************************
     */

    /**
     * Puts values of a <code>FontData</code> into the maps
     * with the passed key and an addition to the name to identify
     * the individual part
     *
     * @param key      key for the <code>FontData</code> that gets saved
     * @param fontData <code>FontData</code> that gets saved
     */
    public void putFontData(String key, FontData fontData) {
        putString(key + font, fontData.getFont().getName());
        putBoolean(key + style, fontData.getFont().isBold());
        putColor(key + color, fontData.getColor());
    }

    /**
     * Returns a <code>FontData</code> from a key
     *
     * @param key key to identify the <code>FontData</code>
     * @return the <code>FontData</code> from the save
     */
    public FontData getFontData(String key) {
        return new FontData(
                new Font(
                        getString(key + font, "Arial"),
                        getBoolean(key + style) ? BOLD : Font.PLAIN,
                        200
                ),
                getColor(key + color)
        );
    }

    /*
     **********************************************************
     *  AudioData SAVING METHODS
     **********************************************************
     */

    /**
     * Puts values of a <code>AudioData</code> into the maps
     * with the passed key and an addition to the name to identify
     * the individual part
     *
     * @param key       key for the <code>AudioData</code> that gets saved
     * @param audioData <code>AudioData</code> that gets saved
     */
    public void putAudioData(String key, AudioSettingRow.AudioData audioData) {
        putString(key + file, audioData.getFile().getAbsolutePath());
        putInteger(key + volume, (int) (audioData.getVolume() * 100));
    }

    /**
     * Returns a <code>AudioData</code> from a key
     *
     * @param key key to identify the <code>AudioData</code>
     * @return the <code>AudioData</code> from the save
     */
    public AudioSettingRow.AudioData getAudioData(String key) {
        return new AudioSettingRow.AudioData(
                new File(getString(key + file)),
                getInteger(key + volume, 100) / 100f
        );
    }
}
