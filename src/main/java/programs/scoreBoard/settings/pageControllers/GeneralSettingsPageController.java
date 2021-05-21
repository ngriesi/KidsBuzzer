package programs.scoreBoard.settings.pageControllers;

import assets.settings.general.SettingsEvent;
import assets.settings.rows.AudioSettingRow;
import assets.settings.rows.FontChooserRow;
import assets.settings.rows.FontData;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.scoreBoard.data.ScoreBoardModel;
import programs.scoreBoard.main.ScoreBoardProgram;
import programs.scoreBoard.settings.ScoreBoardSettingsController;
import programs.scoreBoard.settings.ScoreBoardSettingsView;
import savedataHandler.SaveDataHandler;
import utils.audioSystem.AudioClip;

import java.awt.*;
import java.io.File;

import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;

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
        switch (se.getComponentName()) {
            case FontChooserRow.COLOR:
                Color color = ((Color) se.getValue());
                int[] oldColor = programModel.getSaveFile().getTextColor();
                if (color.getRed() != oldColor[0] || color.getGreen() != oldColor[1] || color.getBlue() != oldColor[2] || color.getAlpha() != oldColor[3]) {
                    programModel.getSaveFile().setTextColor(new int[]{color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()});
                    program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateFont());
                }
            case FontChooserRow.STYLE:
                if (!programModel.getSaveFile().isTextBold() == (boolean) se.getValue()) {
                    programModel.getSaveFile().setTextBold(((boolean) se.getValue()));
                    program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateFont());
                }
            case FontChooserRow.FONT:
                if (!programModel.getSaveFile().getFont().equals(se.getValue())) {
                    programModel.getSaveFile().setFont(((String) se.getValue()));
                    program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateFont());
                }
        }
    }

    /**
     * updaets an audio settings for the buzzer audio
     *
     * @param se settings changed event of the setting
     */
    private void changeAudioSetting(SettingsEvent se) {
        if (se.getComponentName().equals(AudioSettingRow.VOLUME)) {
            mainSettingsController.getProgramModel().getSaveFile().setBuzzerSoundVolume((int) ((float) se.getValue() * 100));
            if (mainSettingsController.getProgramModel().getBuzzerSound() != null) {
                mainSettingsController.getProgramModel().getBuzzerSound().setGain((float) se.getValue());
            }
        } else {
            mainSettingsController.getProgramModel().getSaveFile().setBuzzerSound(((File) se.getValue()).getAbsolutePath());
            new Thread(() -> mainSettingsController.getProgramModel().setBuzzerSound(AudioClip.load((File) se.getValue()))).start();
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
        program.getProgramModel().getSaveFile().getTeamNames()[buzzerNumber] = (String) settingsEvent.getValue();
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


        Font font = new Font(programModel.getSaveFile().getFont(), programModel.getSaveFile().isTextBold() ? BOLD : PLAIN, 200);
        Color textColor = new Color(programModel.getSaveFile().getTextColor()[0], programModel.getSaveFile().getTextColor()[1], programModel.getSaveFile().getTextColor()[2], programModel.getSaveFile().getTextColor()[3]);
        programView.getGeneralSettingsPage().getTeamsFontChooserRow().setSetting(new FontData(font, textColor));

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            programView.getGeneralSettingsPage().getTeamNames()[i].setSetting(programModel.getSaveFile().getTeamNames()[i]);
        }

        programView.getGeneralSettingsPage().getBuzzerPressedSound().setSetting(new AudioSettingRow.AudioData(new File(programModel.getSaveFile().getBuzzerSound()), programModel.getSaveFile().getBuzzerSoundVolume()));
    }
}
