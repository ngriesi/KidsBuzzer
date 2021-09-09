package programs.scoreBoard.settings.pageControllers;

import assets.settings.general.SettingsEvent;
import assets.settings.rows.AudioSettingRow;
import assets.settings.rows.FontData;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.scoreBoard.data.ScoreBoardModel;
import programs.scoreBoard.main.ScoreBoardProgram;
import programs.scoreBoard.settings.ScoreBoardSettingsController;
import programs.scoreBoard.settings.ScoreBoardSettingsView;
import savedataHandler.SaveDataHandler;
import utils.audioSystem.AudioClip;

import static programs.scoreBoard.data.ScoreBoardModel.*;

/**
 * Controller for the general settings page
 */
public class GeneralSettingsPageController extends ProgramSettingsPageController<ScoreBoardSettingsController> {

    /**
     * creates a new controller for the general settings page
     *
     * @param mainSettingsController main controller of the score board settings
     */
    public GeneralSettingsPageController(ScoreBoardSettingsController mainSettingsController) {
        super(mainSettingsController);
    }

    /**
     * method gets called when a setting was changed
     *
     * @param se settings changed event of the setting
     */
    @Override
    public void settingChangedAction(SettingsEvent se) {
        if (se.getRowKind().equals(SettingsEvent.RowKind.TEXT_FIELD)) {
            updateTeamNames(se);
        }

        if (se.getRowKind().equals(SettingsEvent.RowKind.AUDIO)) {
            changeAudioSetting(se);
        }

        if (se.getRowKind().equals(SettingsEvent.RowKind.FONT)) {
            changeFontSettings(se);
        }
    }

    /**
     * method changes the font setting
     *
     * @param se settings changed event of the setting
     */
    private void changeFontSettings(SettingsEvent se) {
        ScoreBoardProgram program = mainSettingsController.getProgram();
        ScoreBoardModel programModel = mainSettingsController.getProgramModel();
        programModel.getSaveFile().putFontData(FONT, (FontData) se.getValue());
        program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateFont());
    }

    /**
     * updaets an audio settings for the buzzer audio
     *
     * @param se settings changed event of the setting
     */
    private void changeAudioSetting(SettingsEvent se) {
        mainSettingsController.getProgramModel().getSaveFile().putAudioData(BUZZER_SOUND, (AudioSettingRow.AudioData) se.getValue());
        if (se.getComponentName().equals(AudioSettingRow.VOLUME)) {
            if (mainSettingsController.getProgramModel().getBuzzerSound() != null) {
                mainSettingsController.getProgramModel().getBuzzerSound().setGain(((AudioSettingRow.AudioData) se.getValue()).getVolume());
            }
        } else {
            new Thread(() -> mainSettingsController.getProgramModel().setBuzzerSound(AudioClip.load(((AudioSettingRow.AudioData)se.getValue()).getFile()))).start();
        }
    }

    /**
     * updates the names of the teams in the save file and the view
     *
     * @param settingsEvent settings event created by the settings view element, containing a name to identify the settings and
     */
    private void updateTeamNames(SettingsEvent settingsEvent) {
        ScoreBoardProgram program = mainSettingsController.getProgram();
        int buzzerNumber = SaveDataHandler.getNumberByName(settingsEvent.getName().substring(4));
        program.getProgramModel().getSaveFile().putString(TEAM_NAMES + buzzerNumber, (String) settingsEvent.getValue());
        program.getProgramController().updateNames();
        program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateText());
    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {

        ScoreBoardModel programModel = mainSettingsController.getProgramModel();
        ScoreBoardSettingsView programView = mainSettingsController.getProgramView();

        programView.getGeneralSettingsPage().getTeamsFontChooserRow().setSetting(programModel.getSaveFile().getFontData(FONT));

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            programView.getGeneralSettingsPage().getTeamNames()[i].setSetting(programModel.getSaveFile().getString(TEAM_NAMES + i));
        }

        programView.getGeneralSettingsPage().getBuzzerPressedSound().setSetting(programModel.getSaveFile().getAudioData(BUZZER_SOUND));
    }
}
