package programs.scoreBoard.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.settings.rows.AudioSettingRow;
import assets.settings.rows.FontData;
import presentationWindow.items.Texture;
import programs.abstractProgram.ProgramController;
import programs.scoreBoard.data.ScoreBoardModel;
import programs.scoreBoard.main.ScoreBoardProgram;
import savedataHandler.SaveDataHandler;
import utils.audioSystem.AudioClip;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;

/**
 * controller for the settings of the score board program
 */
public class ScoreBoardSettingsController extends ProgramController<ScoreBoardProgram, ScoreBoardSettingsView, ScoreBoardModel> implements SettingsChangeListener {

    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public ScoreBoardSettingsController(ScoreBoardProgram program, ScoreBoardModel programModel) {
        super(program, programModel);
    }

    /**
     * method called when a settings has changed
     *
     * @param se settings event created by the settings view element, containing a name to identify the settings and
     */
    @Override
    public void settingChanged(SettingsEvent se) {

        if (se.getName().startsWith("name")) {
            updateTeamNames(se);
        } else if (se.getName().startsWith("icon")) {
            updateTeamIcons(se);
        }

        switch (se.getName()) {
            case "buzzerSoundFile":
                getProgramModel().getSaveFile().setBuzzerSound(((File) se.getValue()).getAbsolutePath());
                new Thread(() -> getProgramModel().setBuzzerSound(AudioClip.load((File) se.getValue()))).start();
                break;
            case "buzzerSoundVolume":
                getProgramModel().getSaveFile().setBuzzerSoundVolume((int) ((float) se.getValue() * 100));
                if (getProgramModel().getBuzzerSound() != null) {
                    getProgramModel().getBuzzerSound().setGain((float) se.getValue());
                }
                break;
            case "fontfont":
                if (!getProgramModel().getSaveFile().getFont().equals(se.getValue())) {
                    getProgramModel().getSaveFile().setFont(((String) se.getValue()));
                    getProgram().getRenderer().addActionToOpenGlThread(() -> getProgram().getProgramPresentationView().updateFont());
                }
                break;
            case "fontstyle":
                if (!getProgramModel().getSaveFile().isTextBold() == (boolean) se.getValue()) {
                    getProgramModel().getSaveFile().setTextBold(((boolean) se.getValue()));
                    getProgram().getRenderer().addActionToOpenGlThread(() -> getProgram().getProgramPresentationView().updateFont());
                }
                break;

            case "fontcolor":
                Color color = ((Color) se.getValue());
                int[] oldColor = getProgramModel().getSaveFile().getTextColor();
                if (color.getRed() != oldColor[0] || color.getGreen() != oldColor[1] || color.getBlue() != oldColor[2] || color.getAlpha() != oldColor[3]) {
                    getProgramModel().getSaveFile().setTextColor(new int[]{color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()});
                    getProgram().getRenderer().addActionToOpenGlThread(() -> getProgram().getProgramPresentationView().updateFont());
                }
                break;
        }
    }

    /**
     * changes an icon setting and updates he icons in the output view
     *
     * @param settingsEvent settings event created by the settings view element, containing a name to identify the settings and
     */
    private void updateTeamIcons(SettingsEvent settingsEvent) {
        int buzzerNumber = SaveDataHandler.getNumberByName(settingsEvent.getName().substring(4));
        getProgramModel().getSaveFile().getIcons()[buzzerNumber] = ((File) settingsEvent.getValue()).getAbsolutePath();
        getProgram().getRenderer().addActionToOpenGlThread(() -> {
            getProgramModel().setIcon(Texture.loadTexture(new File(getProgramModel().getSaveFile().getIcons()[buzzerNumber])), buzzerNumber);
            getProgram().getProgramPresentationView().updateIcon(buzzerNumber);
        });
    }

    /**
     * updates the names of the teams in the save file and the view
     *
     * @param settingsEvent settings event created by the settings view element, containing a name to identify the settings and
     */
    private void updateTeamNames(SettingsEvent settingsEvent) {
        int buzzerNumber = SaveDataHandler.getNumberByName(settingsEvent.getName().substring(4));
        getProgramModel().getSaveFile().getTeamNames()[buzzerNumber] = (String) settingsEvent.getValue();
        getProgram().getProgramController().updateNames();
        getProgram().getRenderer().addActionToOpenGlThread(() -> getProgram().getProgramPresentationView().updateText());
    }

    /**
     * @return returns the newly created view of the quiz time settings
     */
    @Override
    protected ScoreBoardSettingsView createView() {
        return new ScoreBoardSettingsView(this, this, new String[]{"Generell", "Icons"});
    }

    /**
     * method updates all elements of the view with values form the save file
     */
    @Override
    protected void updateView() {
        Font font = new Font(getProgramModel().getSaveFile().getFont(), getProgramModel().getSaveFile().isTextBold() ? BOLD : PLAIN, 200);
        Color textColor = new Color(getProgram().getProgramModel().getSaveFile().getTextColor()[0], getProgram().getProgramModel().getSaveFile().getTextColor()[1], getProgram().getProgramModel().getSaveFile().getTextColor()[2], getProgram().getProgramModel().getSaveFile().getTextColor()[3]);
        getProgramView().getTeamsFontChooserRow().setSetting(new FontData(font, textColor));

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            getProgramView().getIconSettingRows()[i].setSetting(getProgramModel().getSaveFile().getIcons()[i]);
            getProgramView().getTeamNames()[i].setSetting(getProgramModel().getSaveFile().getTeamNames()[i]);
        }

        getProgramView().getBuzzerPressedSound().setSetting(new AudioSettingRow.AudioData(new File(getProgramModel().getSaveFile().getBuzzerSound()), getProgramModel().getSaveFile().getBuzzerSoundVolume()));
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
