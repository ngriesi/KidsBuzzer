package savedataHandler;

import controlWindow.settings.SettingsSaveFile;
import utils.audioSystem.AudioSystem;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * save data handler of the main application
 */
public class SaveDataHandler {


    public static final BufferedImage DEFAULT_IMAGE = new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);
    /**
     * number of the buzzers used
     */
    public static int BUZZER_COUNT;

    /**
     * maximum number of buzzers
     */
    public static final int MAX_BUZZER_COUNT = 3;

    /**
     * names of the buzzers
     */
    public static final String[] BUZZER_NAMES = {"red","green","black"};

    /**
     * colors of the buzzers when they are in the unpressed state
     */
    public static Color[] BUZZER_COLORS_UNPRESSED = {new Color(255, 125,125),new Color(125,255,125),new Color(75,75,75)};

    /**
     * colors of the buzzers when they are in the pressed state
     */
    public static Color[] BUZZER_COLORS_PRESSED = {new Color(255, 0,0),new Color(0,255,0),new Color(0,0,0)};

    /**
     * colors of the buzzers when they are disabled
     */
    public static final Color[] BUZZER_COLORS_DISABLED = {new Color(150, 125,125),new Color(125,150,125),new Color(125,125,125)};

    /**
     * audio system of the main application
     */
    private AudioSystem audioSystem;

    /**
     * settings save file of the main settings
     */
    private SettingsSaveFile settings;

    /**
     * creates a new SaveDataHandler
     *
     * @param audioSystem audio system of the main application
     * @param settings settings save file of the main settings
     */
    public SaveDataHandler(AudioSystem audioSystem, SettingsSaveFile settings) {
        this.audioSystem = audioSystem;
        this.settings = settings;
        BUZZER_COUNT = settings.getBuzzerNumber();
    }

    public static int getNumberByName(String name) {
        for (int i = 0; i < BUZZER_NAMES.length; i++) {
            if(BUZZER_NAMES[i].equals(name)) return i;
        }

        return -1;
    }

    /**
     * @return returns audio system of the main application
     */
    @SuppressWarnings("unused")
    public AudioSystem getAudioSystem() {
        return audioSystem;
    }

    /**
     * @return returns settings save file of the main settings
     */
    public SettingsSaveFile getSettings() {
        return settings;
    }
}
