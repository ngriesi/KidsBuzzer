package programs.quizPrograms.settings.pageControllers;

import assets.settings.general.SettingsEvent;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.quizPrograms.settings.QuizSettingsController;
import programs.quizPrograms.settings.pages.MidiSettingsPage;
import savedataHandler.SaveDataHandler;
import utils.save.SaveFile;

import static midi.MidiSettingsRow.MidiSettingsRowData;
import static programs.quizPrograms.data.QuizModel.*;

/**
 * controller for the midi settings page of the quiztime program
 */
public class MidiSettingsController extends ProgramSettingsPageController<QuizSettingsController> {

    /**
     * creates a new controller for the midi settings page of the quiztime program
     *
     * @param mainSettingsController main controller of the quiztime settings
     */
    public MidiSettingsController(QuizSettingsController mainSettingsController) {
        super(mainSettingsController);
    }

    /**
     * method gets called when a setting was changed
     *
     * @param event settings changed event of the setting
     */
    @Override
    public void settingChangedAction(SettingsEvent event) {

        mainSettingsController.getProgramModel().getSaveFile().putMidiSettingsRowData(event.getName(), (MidiSettingsRowData) event.getValue());
    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {
        MidiSettingsPage midiSettingsPage = mainSettingsController.getProgramView().getMidiSettingsPage();

        SaveFile saveFile = mainSettingsController.getProgramModel().getSaveFile();

        midiSettingsPage.getRight().setSetting(saveFile.getMidiSettingsRowData(RIGHT_MIDI));
        midiSettingsPage.getWrong().setSetting(saveFile.getMidiSettingsRowData(WRONG_MIDI));
        midiSettingsPage.getNext().setSetting(saveFile.getMidiSettingsRowData(NEXT_MIDI));

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            midiSettingsPage.getBuzzer()[i].setSetting(saveFile.getMidiSettingsRowData(BUZZER_MIDI + i));
        }
    }
}
