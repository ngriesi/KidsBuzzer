package programs.quizOverlay.settings.pages;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsPage;
import assets.settings.rows.AudioSettingRow;
import savedataHandler.languages.Text;

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
    private void createAudioSelectionView(SettingsChangeListener settingsChangeListener) {


        questionSound = new AudioSettingRow(settingsChangeListener, "Question", Text.QUESTION_SOUND);

        super.addRow(questionSound);

        rightSound = new AudioSettingRow(settingsChangeListener, "Right", Text.RIGHT_SOUND);

        super.addRow(rightSound);

        buzzerSound = new AudioSettingRow(settingsChangeListener, "Buzzer", Text.BUZZER_SOUND);

        super.addRow(buzzerSound);

        wrongSound = new AudioSettingRow(settingsChangeListener, "Wrong", Text.WRONG_SOUND);

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
}
