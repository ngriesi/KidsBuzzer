package programs.scoreBoard.settings.pageControllers;

import assets.settings.general.SettingsEvent;
import midi.MidiSettingsRow;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.scoreBoard.settings.ScoreBoardSettingsController;

import static programs.scoreBoard.data.ScoreBoardModel.*;

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
        switch (event.getName()) {
            case "score":
                mainSettingsController.getProgramModel().getSaveFile().putMidiSettingsRowData(MIDI_POINT, (MidiSettingsRow.MidiSettingsRowData) event.getValue());
                break;
            case "show":
                mainSettingsController.getProgramModel().getSaveFile().putMidiSettingsRowData(MIDI_SHOW, (MidiSettingsRow.MidiSettingsRowData) event.getValue());
                break;
            case "hide":
                mainSettingsController.getProgramModel().getSaveFile().putMidiSettingsRowData(MIDI_HIDE, (MidiSettingsRow.MidiSettingsRowData) event.getValue());
                break;
        }

    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {
        mainSettingsController.getProgramView().getMidiSettingsPage().getScored().setSetting(mainSettingsController.getProgramModel().getSaveFile().getMidiSettingsRowData(MIDI_POINT));
        mainSettingsController.getProgramView().getMidiSettingsPage().getHide().setSetting(mainSettingsController.getProgramModel().getSaveFile().getMidiSettingsRowData(MIDI_HIDE));
        mainSettingsController.getProgramView().getMidiSettingsPage().getShow().setSetting(mainSettingsController.getProgramModel().getSaveFile().getMidiSettingsRowData(MIDI_SHOW));
    }
}
