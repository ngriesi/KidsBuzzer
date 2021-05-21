package programs.scoreBoard.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import programs.abstractProgram.ProgramController;
import programs.scoreBoard.data.ScoreBoardModel;
import programs.scoreBoard.main.ScoreBoardProgram;
import programs.scoreBoard.settings.pageControllers.GeneralSettingsPageController;
import programs.scoreBoard.settings.pageControllers.ImageSettingsPageController;
import programs.scoreBoard.settings.pageControllers.MidiSettingsPageController;
import savedataHandler.languages.Text;

import java.awt.event.ActionEvent;

/**
 * controller for the settings of the score board program
 */
public class ScoreBoardSettingsController extends ProgramController<ScoreBoardProgram, ScoreBoardSettingsView, ScoreBoardModel> implements SettingsChangeListener {

    /**
     * controller for the image settings page
     */
    private ImageSettingsPageController imageSettingsPageController;

    /**
     * controller for the general settings page
     */
    private GeneralSettingsPageController generalSettingsPageController;

    /**
     * controller of the midi settings page
     */
    private MidiSettingsPageController midiSettingsPageController;

    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public ScoreBoardSettingsController(ScoreBoardProgram program, ScoreBoardModel programModel) {
        super(program, programModel);
        imageSettingsPageController = new ImageSettingsPageController(this);
        generalSettingsPageController = new GeneralSettingsPageController(this);
        midiSettingsPageController = new MidiSettingsPageController(this);
    }

    /**
     * method called when a settings has changed
     *
     * @param se settings event created by the settings view element, containing a name to identify the settings and
     */
    @Override
    public void settingChanged(SettingsEvent se) {

        if (se.getPageName().equals("images")) {
            imageSettingsPageController.settingChangedAction(se);
        } else if (se.getPageName().equals("general")) {
            generalSettingsPageController.settingChangedAction(se);
        } else {
            midiSettingsPageController.settingChangedAction(se);
        }
    }

    /**
     * @return returns the newly created view of the quiz time settings
     */
    @Override
    protected ScoreBoardSettingsView createView() {
        return new ScoreBoardSettingsView(this, this, new String[]{Text.GENERAL, Text.IMAGES});
    }

    /**
     * method updates all elements of the view with values form the save file
     */
    @Override
    protected void updateView() {
        imageSettingsPageController.updateView();
        generalSettingsPageController.updateView();
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
            getProgram().setView(getProgram().getProgramController().getProgramView());
        }
    }
}
