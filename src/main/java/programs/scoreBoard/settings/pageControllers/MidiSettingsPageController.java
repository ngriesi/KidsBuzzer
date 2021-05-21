package programs.scoreBoard.settings.pageControllers;

import assets.settings.general.SettingsEvent;
import midi.MidiSettingsRow;
import org.joml.Vector2i;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.scoreBoard.settings.ScoreBoardSettingsController;

/**
 * settings controller for the midi settings page of the score board program
 */
public class MidiSettingsPageController extends ProgramSettingsPageController<ScoreBoardSettingsController> {

    /**
     * creates a new settings controller
     *
     * @param mainSettingsController main controller of the score board settings
     */
    public MidiSettingsPageController(ScoreBoardSettingsController mainSettingsController) {
        super(mainSettingsController);
    }

    /**
     * method gets called when a setting was changed
     *
     * @param event settings changed event of the setting
     */
    @Override
    public void settingChangedAction(SettingsEvent event) {
        if (event.getComponentName().equals(MidiSettingsRow.ACTIVE)) {
            mainSettingsController.getProgramModel().getSaveFile().setMidiActivate((Boolean) event.getValue());
        } else if (event.getComponentName().equals(MidiSettingsRow.X)) {
            mainSettingsController.getProgramModel().getSaveFile().setMidiX((Integer) event.getValue());
        } else {
            mainSettingsController.getProgramModel().getSaveFile().setMidiY((Integer) event.getValue());
        }
    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {
        mainSettingsController.getProgramView().getMidiSettingsPage().getScored().setSetting(
                new MidiSettingsRow.MidiSettingsRowData(
                        new Vector2i(mainSettingsController.getProgramModel().getSaveFile().getMidiX(),
                                mainSettingsController.getProgramModel().getSaveFile().getMidiY()),
                        mainSettingsController.getProgramModel().getSaveFile().isMidiActivate()));
    }
}
