package programs.quizPrograms.settings.pages;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsPage;
import assets.settings.rows.AudioSettingRow;
import assets.settings.rows.AudioWarningMessageSettingsRow;
import savedataHandler.languages.Text;

import static programs.quizPrograms.data.QuizModel.*;

/**
 * settings page for the audio settings
 */
public class AudioSettingsPage extends SettingsPage {

    /**
     * settings rows to select the audio file and change their volume
     */
    private AudioSettingRow questionSound, rightSound, buzzerSound, wrongSound;

    /**
     * creates Panel with Layout
     *
     * @param settingsChangeListener change listener of the audio settings page of the score board program
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
    protected void createAudioSelectionView(SettingsChangeListener settingsChangeListener) {


        questionSound = new AudioSettingRow(settingsChangeListener, QUESTION_SOUND, Text.QUESTION_SOUND);

        super.addRow(questionSound);

        rightSound = new AudioSettingRow(settingsChangeListener, RIGHT_SOUND, Text.RIGHT_SOUND);

        super.addRow(rightSound);

        buzzerSound = new AudioSettingRow(settingsChangeListener, BUZZER_SOUND, Text.BUZZER_SOUND);

        super.addRow(buzzerSound);

        wrongSound = new AudioSettingRow(settingsChangeListener, WRONG_SOUND, Text.WRONG_SOUND);

        super.addRow(wrongSound);

        super.addRow(new AudioWarningMessageSettingsRow());

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
}
