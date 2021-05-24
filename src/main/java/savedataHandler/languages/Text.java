package savedataHandler.languages;

import savedataHandler.SaveDataHandler;
import utils.resources.Resources;

import java.util.List;

/**
 * class contains all the text that is displayed in the application
 * <p>
 * the text is set from a resource file in the passed language
 * <p>
 * possible languages are: English, Deutsch
 */
public class Text {

    public static String LANGUAGE;
    public static String LANGUAGE_SELECTION;
    public static String EFFECT_COLOR_SELECTION;
    public static String SETTINGS;
    public static String QUIZTIME;
    public static String SHOW;
    public static String WRONG;
    public static String RIGHT;
    public static String NEXT_QUESTION;
    public static String IMAGES;
    public static String CHOOSE_BACKGROUND;
    public static String SELECT_ICON_BUZZER;
    public static String FONT;
    public static String MAIN_FONT;
    public static String BUZZER_FONT;
    public static String BOLD;
    public static String STYLE;
    public static String COLOR;
    public static String AUDIO;
    public static String INTRO_SOUND;
    public static String QUESTION_SOUND;
    public static String RIGHT_SOUND;
    public static String WRONG_SOUND;
    public static String BUZZER_SOUND;
    public static String VOLUME;
    public static String REMOTE;
    public static String NOTHING;
    public static String KEY_PRESS;
    public static String SHOW_OR_HIDE;
    public static String BACK;
    public static String CREDITS;
    public static String CREDITS_TEXT;
    public static String CHOOSE_OUTPUT_SCREEN;
    public static String SELECT_BUZZER_NUMBER;
    public static String USE_NATIVE_KEYS;
    public static String CANCEL;
    public static String SAVE;
    public static String HIDE;
    public static String SCOREBOARD;
    public static String GENERAL;
    public static String SOUND_WHEN_SCORED;
    public static String NAME_OF_TEAM;
    public static String SELECT_ICON_TEAM;
    public static String INSTANT_BUTTON;
    public static String BUZZER;
    public static String SEARCHING;
    public static String SOUND_OF_BUZZER;
    public static String QUIZ_OVERLAY;
    public static String MOUSE_CLICKER;
    public static String DISPLAY_MOUSE_TRACKER;
    public static String BUZZER_CLICK_CORDS;
    public static String BLOCKING_BEHAVIOUR;
    public static String DONT_BLOCK;
    public static String BLOCK_UNTIL_RELEASED;
    public static String RESET_BUZZER;
    public static String BLOCK_FOR_TIME;
    public static String UNBLOCK_WITH_BUZZER;
    public static String KEY_PRESSER;
    public static String BUZZER_PRESS_KEY;
    public static String KEY;
    public static String ACTIVE;
    public static String LIGHT_CONTROL;
    public static String MIDI_RIGHT_DESCRIPTION;
    public static String MIDI_INTRO_DESCRIPTION;
    public static String MIDI_WRONG_DESCRIPTION;
    public static String MIDI_NEXT_DESCRIPTION;
    public static String MIDI_POINT_SCORED;
    public static String MIDI_BUZZER_DESCRIPTION;
    @SuppressWarnings("WeakerAccess")
    public static String RED;
    @SuppressWarnings("WeakerAccess")
    public static String GREEN;
    @SuppressWarnings("WeakerAccess")
    public static String BLACK;

    public static String[] LANGUAGES;


    /**
     * updates the fields of this class with the content of the languages resource file
     *
     * @param language the language defines the column where the text values are red from
     */
    public Text(String language) {

        try {
            List<String> lines = Resources.readAllLines("/languages.txt");
            String[] languageColumns = lines.get(0).split(";");
            LANGUAGES = new String[languageColumns.length - 1];
            System.arraycopy(languageColumns, 1, LANGUAGES, 0, languageColumns.length - 1);
            int languageIndex = 1;
            for (int i = 0; i < languageColumns.length; i++) {
                if (languageColumns[i].equals(language)) {
                    languageIndex = i;
                    break;
                }
            }

            for (String line : lines) {
                String fieldName = line.split(";")[0];
                String value = line.split(";")[languageIndex];
                try {
                    if (!value.equals("")) {
                        this.getClass().getField(fieldName).set(this, value);
                    } else {
                        this.getClass().getField(fieldName).set(this, "NOT_FOUND");
                        System.out.println("Text not found for field " + fieldName + " in language " + languageColumns[languageIndex]);
                    }
                } catch (NoSuchFieldException e) {
                    System.out.println("Cant find field " + fieldName + " for value " + value);
                }
            }

        } catch (IllegalAccessException e) {
            System.out.println("Cant access field, should not appear");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SaveDataHandler.BUZZER_NAMES = new String[]{RED, GREEN, BLACK};

    }
}
