package programs.quizOverlay.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import presentationWindow.items.Texture;
import programs.abstractProgram.ProgramController;
import programs.quizOverlay.data.QuizOverlayModel;
import programs.quizOverlay.main.control.QuizOverlayProgram;
import programs.quizOverlay.settings.pageControllers.AudioSettingsPageController;
import programs.quizOverlay.settings.pageControllers.FontSettingsController;
import programs.quizOverlay.settings.pageControllers.ImageSettingsPageController;
import savedataHandler.SaveDataHandler;

import java.awt.event.ActionEvent;
import java.io.File;

/**
 * controller for the settings of the quiz time program
 */
public class QuizOverlaySettingsController extends ProgramController<QuizOverlayProgram, QuizOverlaySettingsView, QuizOverlayModel> implements SettingsChangeListener {

    /**
     * controller of the audio settings page
     */
    private AudioSettingsPageController audioSettingsPageController;

    /**
     * controller of the font settings page
     */
    private FontSettingsController fontSettingsPageController;

    /**
     * controller of the image settings view
     */
    private ImageSettingsPageController imageSettingsPageController;

    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public QuizOverlaySettingsController(QuizOverlayProgram program, QuizOverlayModel programModel) {
        super(program, programModel);
        audioSettingsPageController = new AudioSettingsPageController(this);
        fontSettingsPageController = new FontSettingsController(this);
        imageSettingsPageController = new ImageSettingsPageController(this);
    }

    /**
     * @return returns the newly created view of the quiz time settings
     */
    @Override
    protected QuizOverlaySettingsView createView() {
        return new QuizOverlaySettingsView(this);
    }

    /**
     * method updates all elements of the view with values form the save file
     */
    @Override
    protected void updateView() {

        imageSettingsPageController.updateView();

        audioSettingsPageController.updateView();

        fontSettingsPageController.updateView();
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
        if (se.getName().contains("icon")) {
            imageSettingsPageController.settingChangedAction(se);
        }
        if (se.getPageName().equals("font")) {
            audioSettingsPageController.settingChangedAction(se);
        }
        if (se.getPageName().equals("sound")) {
            audioSettingsPageController.settingChangedAction(se);
        }
    }

}
