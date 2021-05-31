package programs.scoreBoard.settings.pageControllers;

import assets.settings.general.SettingsEvent;
import midi.MidiSettingsRow;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.scoreBoard.settings.ScoreBoardSettingsController;

import static programs.scoreBoard.data.ScoreBoardModel.MIDI;

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
        mainSettingsController.getProgramModel().getSaveFile().putMidiSettingsRowData(MIDI, (MidiSettingsRow.MidiSettingsRowData) event.getValue());
    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {
        mainSettingsController.getProgramView().getMidiSettingsPage().getScored().setSetting(mainSettingsController.getProgramModel().getSaveFile().getMidiSettingsRowData(MIDI));
    }
}
