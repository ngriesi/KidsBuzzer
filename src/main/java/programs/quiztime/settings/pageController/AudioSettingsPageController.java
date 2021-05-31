package programs.quiztime.settings.pageController;

import assets.settings.general.SettingsEvent;
import programs.quizPrograms.settings.QuizSettingsController;
import programs.quizPrograms.settings.pageControllers.AudioSettingsController;
import programs.quiztime.data.QuizTimeProgramModel;
import programs.quiztime.settings.pages.AudioSettingsPage;
import utils.audioSystem.AudioClip;

import java.io.File;

import static programs.quiztime.data.QuizTimeProgramModel.INTRO_SOUND;

/**
 * controller for the audio settings page of the quiztime program
 */
public class AudioSettingsPageController extends AudioSettingsController {

    /**
     * creates a new controller for the audio settings page of the quiztime program
     *
     * @param quizSettingsController main controller of the quiztime settings
     */
    public AudioSettingsPageController(QuizSettingsController quizSettingsController) {
        super(quizSettingsController);
    }

    /**
     * method gets called when a setting was changed
     *
     * @param event settings changed event of the setting
     */
    @Override
    public void settingChangedAction(SettingsEvent event) {
        super.settingChangedAction(event);
    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {

        QuizTimeProgramModel programModel = (QuizTimeProgramModel) mainSettingsController.getProgramModel();
        AudioSettingsPage audioSettingsPage = (programs.quiztime.settings.pages.AudioSettingsPage) mainSettingsController.getProgramView().getAudioSettingsPage();

        audioSettingsPage.getIntroSound().setSetting(programModel.getSaveFile().getAudioData(INTRO_SOUND));
    }

    /**
     * change the file of an audio
     *
     * @param se settings changed event of the setting
     */
    @Override
    protected void changeFile(SettingsEvent se) {
        if ("Intro".equals(se.getName())) {
            new Thread(() -> ((QuizTimeProgramModel)mainSettingsController.getProgramModel()).setIntroSound(AudioClip.load((File) se.getValue()))).start();
        }
        super.changeFile(se);
    }

    /**
     * changes a volume setting
     *
     * @param se settings changed event of the setting
     */
    @Override
    protected void changeVolume(SettingsEvent se) {
        QuizTimeProgramModel programModel = (QuizTimeProgramModel) mainSettingsController.getProgramModel();
        if ("Intro".equals(se.getName().substring(5))) {
            if (programModel.getIntroSound() != null) {
                programModel.getIntroSound().setGain((float) se.getValue());
            }
        }
        super.changeVolume(se);
    }
}
