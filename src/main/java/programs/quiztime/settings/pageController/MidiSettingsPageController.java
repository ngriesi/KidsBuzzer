package programs.quiztime.settings.pageController;

import assets.settings.general.SettingsEvent;
import midi.MidiSettingsRow;
import org.joml.Vector2i;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.quiztime.data.QuizTimeProgramSaveFile;
import programs.quiztime.settings.QuizTimeProgramSettingsController;
import programs.quiztime.settings.pages.MidiSettingsPage;
import savedataHandler.SaveDataHandler;

import static midi.MidiSettingsRow.*;

/**
 * controller for the midi settings page of the quiztime program
 */
public class MidiSettingsPageController extends ProgramSettingsPageController<QuizTimeProgramSettingsController> {

    /**
     * creates a new controller for the midi settings page of the quiztime program
     *
     * @param mainSettingsController main controller of the quiztime settings
     */
    public MidiSettingsPageController(QuizTimeProgramSettingsController mainSettingsController) {
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
            case "Intro":
                changeIntroSettings(event.getComponentName(), event.getValue());
                break;
            case "Right":
                changeRightSettings(event.getComponentName(), event.getValue());
                break;
            case "Wrong":
                changeWrongSettings(event.getComponentName(), event.getValue());
                break;
            case "Next":
                changeNextSettings(event.getComponentName(), event.getValue());
                break;
            case "Buzzer":
                changeBuzzerSettings(event.getComponentName(), Integer.parseInt(event.getName().split(":")[1]), event.getValue());
                break;
        }
    }

    /**
     * Method changes the midi settings for the Buzzer action
     *
     * @param componentName name of the component the event is from
     * @param index         index of the buzzer
     * @param value         value of the setting
     */
    private void changeBuzzerSettings(String componentName, int index, Object value) {
        switch (componentName) {
            case ACTIVE:
                mainSettingsController.getProgramModel().getSaveFile().getBuzzerMidiActivate()[index] = (boolean) value;
                break;
            case X:
                mainSettingsController.getProgramModel().getSaveFile().getBuzzerMidiX()[index] = (int) value;
                break;
            case Y:
                mainSettingsController.getProgramModel().getSaveFile().getBuzzerMidiY()[index] = (int) value;
        }
    }

    /**
     * Method changes the midi settings for the Next action
     *
     * @param componentName name of the component the event is from
     * @param value         value of the setting
     */
    private void changeNextSettings(String componentName, Object value) {
        switch (componentName) {
            case ACTIVE:
                mainSettingsController.getProgramModel().getSaveFile().setNextMidiActivate((Boolean) value);
                break;
            case X:
                mainSettingsController.getProgramModel().getSaveFile().setNextMidiX((Integer) value);
                break;
            case Y:
                mainSettingsController.getProgramModel().getSaveFile().setNextMidiY((Integer) value);
        }
    }

    /**
     * Method changes the midi settings for the Wrong action
     *
     * @param componentName name of the component the event is from
     * @param value         value of the setting
     */
    private void changeWrongSettings(String componentName, Object value) {
        switch (componentName) {
            case ACTIVE:
                mainSettingsController.getProgramModel().getSaveFile().setWrongMidiActivate((Boolean) value);
                break;
            case X:
                mainSettingsController.getProgramModel().getSaveFile().setWrongMidiX((Integer) value);
                break;
            case Y:
                mainSettingsController.getProgramModel().getSaveFile().setWrongMidiY((Integer) value);
        }
    }

    /**
     * Method changes the midi settings for the Right action
     *
     * @param componentName name of the component the event is from
     * @param value         value of the setting
     */
    private void changeRightSettings(String componentName, Object value) {
        switch (componentName) {
            case ACTIVE:
                mainSettingsController.getProgramModel().getSaveFile().setRightMidiActivate((Boolean) value);
                break;
            case X:
                mainSettingsController.getProgramModel().getSaveFile().setRightMidiX((Integer) value);
                break;
            case Y:
                mainSettingsController.getProgramModel().getSaveFile().setRightMidiY((Integer) value);
        }
    }

    /**
     * Method changes the midi settings for the intro action
     *
     * @param componentName name of the component the event is from
     * @param value         value of the setting
     */
    private void changeIntroSettings(String componentName, Object value) {
        switch (componentName) {
            case ACTIVE:
                mainSettingsController.getProgramModel().getSaveFile().setIntroMidiActivate((Boolean) value);
                break;
            case X:
                mainSettingsController.getProgramModel().getSaveFile().setIntroMidiX((Integer) value);
                break;
            case Y:
                mainSettingsController.getProgramModel().getSaveFile().setIntroMidiY((Integer) value);
        }
    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {
        MidiSettingsPage midiSettingsPage = mainSettingsController.getProgramView().getMidiSettingsPage();

        QuizTimeProgramSaveFile saveFile = mainSettingsController.getProgramModel().getSaveFile();

        midiSettingsPage.getRight().setSetting(new MidiSettingsRow.MidiSettingsRowData(new Vector2i(saveFile.getRightMidiX(), saveFile.getRightMidiY()), saveFile.isRightMidiActivate()));
        midiSettingsPage.getWrong().setSetting(new MidiSettingsRow.MidiSettingsRowData(new Vector2i(saveFile.getWrongMidiX(), saveFile.getWrongMidiY()), saveFile.isWrongMidiActivate()));
        midiSettingsPage.getIntro().setSetting(new MidiSettingsRow.MidiSettingsRowData(new Vector2i(saveFile.getIntroMidiX(), saveFile.getIntroMidiY()), saveFile.isIntroMidiActivate()));
        midiSettingsPage.getNext().setSetting(new MidiSettingsRow.MidiSettingsRowData(new Vector2i(saveFile.getNextMidiX(), saveFile.getNextMidiY()), saveFile.isNextMidiActivate()));

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            midiSettingsPage.getBuzzer()[i].setSetting(new MidiSettingsRow.MidiSettingsRowData(new Vector2i(saveFile.getBuzzerMidiX()[i], saveFile.getBuzzerMidiY()[i]), saveFile.getBuzzerMidiActivate()[i]));
        }
    }
}
