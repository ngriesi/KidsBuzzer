package programs.quizOverlay.settings.pageControllers;

import assets.settings.general.SettingsEvent;
import assets.settings.rows.AudioSettingRow;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.quizOverlay.data.QuizOverlayModel;
import programs.quizOverlay.settings.QuizOverlaySettingsController;
import utils.audioSystem.AudioClip;

import java.io.File;

/**
 * controller for the audio settings page of the quiz overlay applicaiton
 */
public class AudioSettingsPageController extends ProgramSettingsPageController<QuizOverlaySettingsController> {

    /**
     * creates a new controller for the audio settings page
     *
     * @param mainSettingsController main controller of the quiz overlay settings
     */
    public AudioSettingsPageController(QuizOverlaySettingsController mainSettingsController) {
        super(mainSettingsController);
    }

    /**
     * method gets called when a setting was changed
     *
     * @param event settings changed event of the setting
     */
    @Override
    public void settingChangedAction(SettingsEvent event) {
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
    private void changeFile(SettingsEvent se) {
        QuizOverlayModel programModel = mainSettingsController.getProgramModel();
        switch (se.getName()) {
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
    }

    /**
     * changes a volume setting
     *
     * @param se settings changed event of the setting
     */
    private void changeVolume(SettingsEvent se) {
        QuizOverlayModel programModel = mainSettingsController.getProgramModel();
        switch (se.getName()) {
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
            case "Wrong":
                programModel.getSaveFile().setWrongVolume((int) ((float) se.getValue() * 100));
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

        QuizOverlayModel quizOverlayModel = mainSettingsController.getProgramModel();
        mainSettingsController.getProgramView().getAudioSettingsPage().getQuestionSound().setSetting(new AudioSettingRow.AudioData(new File(quizOverlayModel.getSaveFile().getQuestionSound()), quizOverlayModel.getSaveFile().getQuestionVolume()));
        mainSettingsController.getProgramView().getAudioSettingsPage().getRightSound().setSetting(new AudioSettingRow.AudioData(new File(quizOverlayModel.getSaveFile().getRightSound()), quizOverlayModel.getSaveFile().getRightVolume()));
        mainSettingsController.getProgramView().getAudioSettingsPage().getBuzzerSound().setSetting(new AudioSettingRow.AudioData(new File(quizOverlayModel.getSaveFile().getBuzzerSound()), quizOverlayModel.getSaveFile().getBuzzerVolume()));
        mainSettingsController.getProgramView().getAudioSettingsPage().getWrongSound().setSetting(new AudioSettingRow.AudioData(new File(quizOverlayModel.getSaveFile().getWrongSound()), quizOverlayModel.getSaveFile().getWrongVolume()));
    }
}
