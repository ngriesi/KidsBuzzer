package programs.quiztime.settings.pages;

import assets.settings.general.SettingsChangeListener;
import assets.settings.rows.AudioSettingRow;
import savedataHandler.languages.Text;

import static programs.quiztime.data.QuizTimeProgramModel.INTRO_SOUND;

/**
 * Settings Page of the audio selection of the quiztime program
 */
public class AudioSettingsPage extends programs.quizPrograms.settings.pages.AudioSettingsPage {

    /**
     * settings rows to select the audio file and change their volume
     */
    private AudioSettingRow introSound;

    /**
     * creates the settings page
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    public AudioSettingsPage(SettingsChangeListener settingsChangeListener) {
        super(settingsChangeListener);
    }

    /**
     * creates the audio settings rows
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    @Override
    protected void createAudioSelectionView(SettingsChangeListener settingsChangeListener) {


        introSound = new AudioSettingRow(settingsChangeListener, INTRO_SOUND, Text.INTRO_SOUND);

        super.addRow(introSound);

        super.createAudioSelectionView(settingsChangeListener);
    }

    /**
     * @return returns the settings row for the intro sound
     */
    public AudioSettingRow getIntroSound() {
        return introSound;
    }
}
