package programs.quiztime.settings.pages;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsPage;
import assets.settings.rows.AudioSettingRow;
import savedataHandler.languages.Text;

/**
 * Settings Page of the audio selection of the quiztime program
 */
public class AudioSettingsPage extends SettingsPage {

    /**
     * settings rows to select the audio file and change their volume
     */
    private AudioSettingRow introSound, questionSound, rightSound, buzzerSound, wrongSound;

    /**
     * creates the settings page
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    public AudioSettingsPage(SettingsChangeListener settingsChangeListener) {
        super(Text.AUDIO, "sound");
        createAudioSelectionView(settingsChangeListener);
    }

    /**
     * creates the audio settings rows
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    private void createAudioSelectionView(SettingsChangeListener settingsChangeListener) {


        introSound = new AudioSettingRow(settingsChangeListener, "Intro", Text.INTRO_SOUND);

        super.addRow(introSound);

        questionSound = new AudioSettingRow(settingsChangeListener, "Question", Text.QUESTION_SOUND);

        super.addRow(questionSound);

        rightSound = new AudioSettingRow(settingsChangeListener, "Right", Text.RIGHT_SOUND);

        super.addRow(rightSound);

        buzzerSound = new AudioSettingRow(settingsChangeListener, "Buzzer", Text.BUZZER_SOUND);

        super.addRow(buzzerSound);

        wrongSound = new AudioSettingRow(settingsChangeListener, "soundWrong", Text.WRONG_SOUND);

        super.addRow(wrongSound);
    }

    /**
     * @return returns the settings row for the question sound
     */
    public AudioSettingRow getQuestionSound() {
        return questionSound;
    }

    /**
     * @return returns the settings row for the right sound
     */
    public AudioSettingRow getRightSound() {
        return rightSound;
    }

    /**
     * @return returns the settings row for the buzzer sound
     */
    public AudioSettingRow getBuzzerSound() {
        return buzzerSound;
    }

    /**
     * @return returns the settings row for the wrong sound
     */
    public AudioSettingRow getWrongSound() {
        return wrongSound;
    }

    /**
     * @return returns the settings row for the intro sound
     */
    public AudioSettingRow getIntroSound() {
        return introSound;
    }
}
