package programs.quiztime.settings.pageController;

import programs.abstractProgram.ProgramSettingsPageController;
import programs.quizPrograms.settings.QuizSettingsController;
import programs.quizPrograms.settings.pageControllers.MidiSettingsController;
import programs.quiztime.settings.pages.MidiSettingsPage;

import static programs.quiztime.data.QuizTimeProgramModel.MIDI_INTRO;

/**
 * controller for the midi settings page of the quiztime program
 */
public class MidiSettingsPageController extends MidiSettingsController {

    /**
     * creates a new controller for the midi settings page of the quiztime program
     *
     * @param mainSettingsController main controller of the quiztime settings
     */
    public MidiSettingsPageController(QuizSettingsController mainSettingsController) {
        super(mainSettingsController);
    }


    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {
        MidiSettingsPage midiSettingsPage = (MidiSettingsPage) mainSettingsController.getProgramView().getMidiSettingsPage();

        midiSettingsPage.getIntro().setSetting(mainSettingsController.getProgramModel().getSaveFile().getMidiSettingsRowData(MIDI_INTRO));
    }
}
