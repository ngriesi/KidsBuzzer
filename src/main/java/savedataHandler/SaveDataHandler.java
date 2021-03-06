package savedataHandler;

import controlWindow.settings.SettingsController;
import utils.save.SaveFile;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * save data handler of the main application
 */
public class SaveDataHandler {


    public static final BufferedImage DEFAULT_IMAGE = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
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
    public static String[] BUZZER_NAMES = {"red", "green", "black"};

    /**
     * colors of the buzzers when they are in the unpressed state
     */
    public static Color[] BUZZER_COLORS_UNPRESSED = {new Color(255, 125, 125), new Color(125, 125, 255), new Color(175, 175, 175)};

    /**
     * colors of the buzzers when they are in the pressed state
     */
    public static Color[] BUZZER_COLORS_PRESSED = {new Color(255, 0, 0), new Color(0, 0, 255), new Color(255, 255, 255)};

    /**
     * colors of the buzzers when they are disabled
     */
    public static final Color[] BUZZER_COLORS_DISABLED = {new Color(150, 125, 125), new Color(125, 125, 150), new Color(125, 125, 125)};

    /**
     * settings save file of the main settings
     */
    private SaveFile settings;

    /**
     * creates a new SaveDataHandler
     *
     * @param settings settings save file of the main settings
     */
    public SaveDataHandler(SaveFile settings) {
        this.settings = settings;
        BUZZER_COUNT = settings.getInteger(SettingsController.BUZZER_COUNT, 3);
    }

    /**
     * returns the number of a buzzer from its name
     *
     * @param name name of the buzzer
     * @return number of the buzzer
     */
    public static int getNumberByName(String name) {
        for (int i = 0; i < BUZZER_NAMES.length; i++) {
            if (BUZZER_NAMES[i].equals(name)) return i;
        }

        return -1;
    }

    /**
     * changes the color of one of the buzzers
     *
     * @param value new color of the buzzer
     * @param buzzerNumber buzzer which color gets changed
     */
    public static void changeBuzzerColor(Color value, int buzzerNumber) {
        BUZZER_COLORS_PRESSED[buzzerNumber - 1] = value;
        BUZZER_COLORS_UNPRESSED[buzzerNumber - 1] = new Color((int) (value.getRed() * 0.8f + 0.5f), (int)(value.getGreen() * 0.8f + 0.5f), (int)(value.getBlue() * 0.8f + 0.5f));
        BUZZER_COLORS_DISABLED[buzzerNumber - 1] = new Color((int) (value.getRed() * 0.6f + 0.5f), (int)(value.getGreen() * 0.6f + 0.5f), (int)(value.getBlue() * 0.6f + 0.5f));
    }

    /**
     * @return returns settings save file of the main settings
     */
    public SaveFile getSettings() {
        return settings;
    }
}
