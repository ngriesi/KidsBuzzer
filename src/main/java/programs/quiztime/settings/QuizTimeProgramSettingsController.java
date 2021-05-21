package programs.quiztime.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import programs.abstractProgram.ProgramController;
import programs.quiztime.data.QuizTimeProgramModel;
import programs.quiztime.main.control.QuizTimeProgram;
import programs.quiztime.settings.pageController.AudioSettingsPageController;
import programs.quiztime.settings.pageController.FontSettingsPageController;
import programs.quiztime.settings.pageController.ImageSettingsPageController;
import programs.quiztime.settings.pageController.MidiSettingsPageController;

import java.awt.event.ActionEvent;

/**
 * controller for the settings of the quiz time program
 */
public class QuizTimeProgramSettingsController extends ProgramController<QuizTimeProgram, QuizTimeProgramSettingsView, QuizTimeProgramModel> implements SettingsChangeListener {

    /**
     * Settings controller of the audio page
     */
    private AudioSettingsPageController audioSettingsPageController;

    /**
     * Settings controller of the font settings page
     */
    private FontSettingsPageController fontSettingsPageController;

    /**
     * Settings controller of the image settings page
     */
    private ImageSettingsPageController imageSettingsPageController;

    /**
     * Settings controller of the midi settings page
     */
    private MidiSettingsPageController midiSettingsPageController;

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
        fontSettingsPageController = new FontSettingsPageController(this);
        imageSettingsPageController = new ImageSettingsPageController(this);
        midiSettingsPageController = new MidiSettingsPageController(this);
        return new QuizTimeProgramSettingsView(this);
    }

    /**
     * method updates all elements of the view with values form the save file
     */
    @Override
    protected void updateView() {

        imageSettingsPageController.updateView();
        fontSettingsPageController.updateView();
        audioSettingsPageController.updateView();
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
        } else if(se.getName().startsWith("midi")) {
            midiSettingsPageController.settingChangedAction(se);
        } else {
            imageSettingsPageController.settingChangedAction(se);
        }

    }
}
