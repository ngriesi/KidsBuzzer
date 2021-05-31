package programs.quizPrograms.settings.pageControllers;

import assets.settings.general.SettingsEvent;
import assets.settings.rows.AudioSettingRow;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.quizPrograms.data.QuizModel;
import programs.quizPrograms.settings.QuizSettingsController;
import utils.audioSystem.AudioClip;

import java.io.File;

import static programs.quizPrograms.data.QuizModel.*;

/**
 * controller for the audio settings page of the quiz overlay applicaiton
 */
public class AudioSettingsController extends ProgramSettingsPageController<QuizSettingsController> {

    /**
     * creates a new controller for the audio settings page
     *
     * @param mainSettingsController main controller of the quiz overlay settings
     */
    public AudioSettingsController(QuizSettingsController mainSettingsController) {
        super(mainSettingsController);
    }

    /**
     * method gets called when a setting was changed
     *
     * @param event settings changed event of the setting
     */
    @Override
    public void settingChangedAction(SettingsEvent event) {

        mainSettingsController.getProgramModel().getSaveFile().putAudioData(event.getName(), (AudioSettingRow.AudioData) event.getValue());

        if (event.getComponentName().equals(AudioSettingRow.VOLUME)) {
            changeVolume(event);
        } else {
            changeFile(event);
        }
    }

    /**
     * change the file of an audio
     *
     * @param se settings changed event of the setting
     */
    protected void changeFile(SettingsEvent se) {
        QuizModel programModel = mainSettingsController.getProgramModel();
        switch (se.getName()) {
            case QUESTION_SOUND:
                new Thread(() -> programModel.setQuestionSound(AudioClip.load((File) se.getValue()))).start();
                break;
            case RIGHT_SOUND:
                new Thread(() -> programModel.setRightSound(AudioClip.load((File) se.getValue()))).start();
                break;
            case BUZZER_SOUND:
                new Thread(() -> programModel.setBuzzerSound(AudioClip.load((File) se.getValue()))).start();
                break;
            case WRONG_SOUND:
                new Thread(() -> programModel.setWrongSound(AudioClip.load((File) se.getValue()))).start();
                break;
        }
    }

    /**
     * changes a volume setting
     *
     * @param se settings changed event of the setting
     */
    protected void changeVolume(SettingsEvent se) {
        QuizModel programModel = mainSettingsController.getProgramModel();
        switch (se.getName()) {
            case QUESTION_SOUND:
                if (programModel.getQuestionSound() != null) {
                    programModel.getQuestionSound().setGain((float) se.getValue());
                }
                break;
            case RIGHT_SOUND:
                if (programModel.getRightSound() != null) {
                    programModel.getRightSound().setGain((float) se.getValue());
                }
                break;
            case BUZZER_SOUND:
                if (programModel.getBuzzerSound() != null) {
                    programModel.getBuzzerSound().setGain((float) se.getValue());
                }
                break;
            case WRONG_SOUND:
                if (programModel.getWrongSound() != null) {
                    programModel.getWrongSound().setGain((float) se.getValue());
                }
                break;
        }
    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {

        QuizModel quizModel = mainSettingsController.getProgramModel();
        mainSettingsController.getProgramView().getAudioSettingsPage().getQuestionSound().setSetting(quizModel.getSaveFile().getAudioData(QUESTION_SOUND));
        mainSettingsController.getProgramView().getAudioSettingsPage().getRightSound().setSetting(quizModel.getSaveFile().getAudioData(RIGHT_SOUND));
        mainSettingsController.getProgramView().getAudioSettingsPage().getBuzzerSound().setSetting(quizModel.getSaveFile().getAudioData(BUZZER_SOUND));
        mainSettingsController.getProgramView().getAudioSettingsPage().getWrongSound().setSetting(quizModel.getSaveFile().getAudioData(WRONG_SOUND));
    }
}
