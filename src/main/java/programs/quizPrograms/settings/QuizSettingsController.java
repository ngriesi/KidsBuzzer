package programs.quizPrograms.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import programs.abstractProgram.ProgramController;
import programs.quizPrograms.data.QuizModel;
import programs.quizPrograms.main.control.QuizProgram;
import programs.quizPrograms.settings.pageControllers.AudioSettingsController;
import programs.quizPrograms.settings.pageControllers.FontSettingsController;
import programs.quizPrograms.settings.pageControllers.ImageSettingsController;
import programs.quizPrograms.settings.pageControllers.MidiSettingsController;

import java.awt.event.ActionEvent;

/**
 * controller for the settings of the quiz time program
 */
public class QuizSettingsController extends ProgramController<QuizProgram, QuizSettingsView, QuizModel> implements SettingsChangeListener {

    /**
     * Settings controller of the audio page
     */
    protected AudioSettingsController audioSettingsPageController;

    /**
     * Settings controller of the font settings page
     */
    protected FontSettingsController fontSettingsPageController;

    /**
     * Settings controller of the image settings page
     */
    protected ImageSettingsController imageSettingsPageController;

    /**
     * Settings controller of the midi settings page
     */
    protected MidiSettingsController midiSettingsPageController;

    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public QuizSettingsController(QuizProgram program, QuizModel programModel) {
        super(program, programModel);
    }

    /**
     * @return returns the newly created view of the quiz time settings
     */
    @Override
    protected QuizSettingsView createView() {
        audioSettingsPageController = new AudioSettingsController(this);
        fontSettingsPageController = new FontSettingsController(this);
        imageSettingsPageController = new ImageSettingsController(this);
        midiSettingsPageController = new MidiSettingsController(this);
        return new QuizSettingsView(this);
    }

    /**
     * method updates all elements of the view with values form the save file
     */
    @Override
    protected void updateView() {

        imageSettingsPageController.updateView();
        fontSettingsPageController.updateView();
        audioSettingsPageController.updateView();
        midiSettingsPageController.updateView();
    }

    /**
     * actions performed by the buttons of the settings
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("back")) {
            getProgramModel().getSaveFile().saveFile();
            getProgram().setView(getProgram().getProgramController().getProgramView());
        }
    }

    /**
     * method called when a settings has changed
     *
     * @param se settings event created by the settings view element, containing a name to identify the settings and
     */
    @Override
    public void settingChanged(SettingsEvent se) {
        if (se.getPageName().equals("font")) {
            fontSettingsPageController.settingChangedAction(se);
        } else if (se.getPageName().startsWith("sound")) {
            audioSettingsPageController.settingChangedAction(se);
        } else if(se.getPageName().startsWith("midi")) {
            midiSettingsPageController.settingChangedAction(se);
        } else {
            imageSettingsPageController.settingChangedAction(se);
        }

    }
}
