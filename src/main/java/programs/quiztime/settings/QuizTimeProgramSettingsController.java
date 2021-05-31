package programs.quiztime.settings;

import programs.quizPrograms.settings.QuizSettingsController;
import programs.quizPrograms.settings.pageControllers.FontSettingsController;
import programs.quiztime.data.QuizTimeProgramModel;
import programs.quiztime.main.control.QuizTimeProgram;
import programs.quiztime.settings.pageController.AudioSettingsPageController;
import programs.quiztime.settings.pageController.ImageSettingsPageController;
import programs.quiztime.settings.pageController.MidiSettingsPageController;

/**
 * controller for the settings of the quiz time program
 */
public class QuizTimeProgramSettingsController extends QuizSettingsController {

    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public QuizTimeProgramSettingsController(QuizTimeProgram program, QuizTimeProgramModel programModel) {
        super(program, programModel);
    }

    /**
     * @return returns the newly created view of the quiz time settings
     */
    @Override
    protected QuizTimeProgramSettingsView createView() {
        audioSettingsPageController = new AudioSettingsPageController(this);
        fontSettingsPageController = new FontSettingsController(this);
        imageSettingsPageController = new ImageSettingsPageController(this);
        midiSettingsPageController = new MidiSettingsPageController(this);
        return new QuizTimeProgramSettingsView(this);
    }
}
