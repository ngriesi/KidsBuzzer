package programs.quiztime.settings.pageController;

import assets.settings.general.SettingsEvent;
import assets.settings.rows.AudioSettingRow;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.quiztime.data.QuizTimeProgramModel;
import programs.quiztime.settings.QuizTimeProgramSettingsController;
import programs.quiztime.settings.pages.AudioSettingsPage;
import utils.audioSystem.AudioClip;

import java.io.File;

/**
 * controller for the audio settings page of the quiztime program
 */
public class AudioSettingsPageController extends ProgramSettingsPageController<QuizTimeProgramSettingsController> {

    /**
     * creates a new controller for the audio settings page of the quiztime program
     *
     * @param quizTimeProgramSettingsController main controller of the quiztime settings
     */
    public AudioSettingsPageController(QuizTimeProgramSettingsController quizTimeProgramSettingsController) {
        super(quizTimeProgramSettingsController);
    }

    /**
     * method gets called when a setting was changed
     *
     * @param event settings changed event of the setting
     */
    @Override
    public void settingChangedAction(SettingsEvent event) {
        changeSoundSetting(event);
    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {

        QuizTimeProgramModel programModel = mainSettingsController.getProgramModel();
        AudioSettingsPage audioSettingsPage = mainSettingsController.getProgramView().getAudioSettingsPage();

        audioSettingsPage.getIntroSound().setSetting(new AudioSettingRow.AudioData(new File(programModel.getSaveFile().getIntroSound()), programModel.getSaveFile().getIntroVolume()));
        audioSettingsPage.getQuestionSound().setSetting(new AudioSettingRow.AudioData(new File(programModel.getSaveFile().getQuestionSound()), programModel.getSaveFile().getQuestionVolume()));
        audioSettingsPage.getRightSound().setSetting(new AudioSettingRow.AudioData(new File(programModel.getSaveFile().getRightSound()), programModel.getSaveFile().getRightVolume()));
        audioSettingsPage.getBuzzerSound().setSetting(new AudioSettingRow.AudioData(new File(programModel.getSaveFile().getBuzzerSound()), programModel.getSaveFile().getBuzzerVolume()));
        audioSettingsPage.getWrongSound().setSetting(new AudioSettingRow.AudioData(new File(programModel.getSaveFile().getWrongSound()), programModel.getSaveFile().getWrongVolume()));
    }

    /**
     * Method changes a sound setting.
     *
     * @param se settings event created by the settings view element, containing a name to identify the settings and
     */
    private void changeSoundSetting(SettingsEvent se) {
        QuizTimeProgramModel programModel = mainSettingsController.getProgramModel();

        if (se.getComponentName().equals(AudioSettingRow.FILE)) {
            switch (se.getName()) {
                case "Intro":
                    programModel.getSaveFile().setIntroSound(((File) se.getValue()).getAbsolutePath());
                    new Thread(() -> programModel.setIntroSound(AudioClip.load((File) se.getValue()))).start();
                    break;
                case "Question":
                    programModel.getSaveFile().setQuestionSound(((File) se.getValue()).getAbsolutePath());
                    new Thread(() -> programModel.setQuestionSound(AudioClip.load((File) se.getValue()))).start();
                    break;
                case "Right":
                    programModel.getSaveFile().setRightSound(((File) se.getValue()).getAbsolutePath());
                    new Thread(() -> programModel.setRightSound(AudioClip.load((File) se.getValue()))).start();
                    break;
                case "Buzzer":
                    programModel.getSaveFile().setBuzzerSound(((File) se.getValue()).getAbsolutePath());
                    new Thread(() -> programModel.setBuzzerSound(AudioClip.load((File) se.getValue()))).start();
                    break;
                case "Wrong":
                    programModel.getSaveFile().setWrongSound(((File) se.getValue()).getAbsolutePath());
                    new Thread(() -> programModel.setWrongSound(AudioClip.load((File) se.getValue()))).start();
                    break;
            }
        } else {
            switch (se.getName().substring(5)) {
                case "Intro":
                    programModel.getSaveFile().setIntroVolume((int) ((float) se.getValue() * 100));
                    if (programModel.getIntroSound() != null) {
                        programModel.getIntroSound().setGain((float) se.getValue());
                    }
                    break;
                case "Question":
                    programModel.getSaveFile().setQuestionVolume((int) ((float) se.getValue() * 100));
                    if (programModel.getQuestionSound() != null) {
                        programModel.getQuestionSound().setGain((float) se.getValue());
                    }
                    break;
                case "Right":
                    programModel.getSaveFile().setRightVolume((int) ((float) se.getValue() * 100));
                    if (programModel.getRightSound() != null) {
                        programModel.getRightSound().setGain((float) se.getValue());
                    }
                    break;
                case "Buzzer":
                    programModel.getSaveFile().setBuzzerVolume((int) ((float) se.getValue() * 100));
                    if (programModel.getBuzzerSound() != null) {
                        programModel.getBuzzerSound().setGain((float) se.getValue());
                    }
                    break;
                case "WrongVolume":
                    programModel.getSaveFile().setWrongVolume((int) ((float) se.getValue() * 100));
                    if (programModel.getWrongSound() != null) {
                        programModel.getWrongSound().setGain((float) se.getValue());
                    }
                    break;
            }
        }
    }
}
