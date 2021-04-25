package programs.quiztime.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.settings.rows.AudioSettingRow;
import assets.settings.rows.FontData;
import presentationWindow.items.Texture;
import programs.abstractProgram.ProgramController;
import programs.quiztime.data.QuizTimeProgramModel;
import programs.quiztime.main.control.QuizTimeProgram;
import savedataHandler.SaveDataHandler;
import utils.audioSystem.AudioClip;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;

/**
 * controller for the settings of the quiz time program
 */
public class QuizTimeProgramSettingsController extends ProgramController<QuizTimeProgram,QuizTimeProgramSettingsView, QuizTimeProgramModel> implements SettingsChangeListener {

    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public QuizTimeProgramSettingsController(QuizTimeProgram program, QuizTimeProgramModel programModel) {
        super(program, programModel);
    }

    /**
     * @return returns the newly created view of the quiz time settings
     */
    @Override
    protected QuizTimeProgramSettingsView createView() {
        return new QuizTimeProgramSettingsView(this);
    }

    @Override
    protected void updateView() {
        getProgramView().getPresentationBackground().setSetting(getProgramModel().getSaveFile().getBackground());

        for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
            getProgramView().getIcons()[i].setSetting(getProgramModel().getSaveFile().getIcons()[i]);
        }

        Font mainFont = new Font(getProgramModel().getSaveFile().getMainFont(),getProgramModel().getSaveFile().isMainTextBold()?BOLD:PLAIN, 200);
        Color mainColor = new Color(getProgram().getProgramModel().getSaveFile().getMainTextColor()[0],getProgram().getProgramModel().getSaveFile().getMainTextColor()[1],getProgram().getProgramModel().getSaveFile().getMainTextColor()[2],getProgram().getProgramModel().getSaveFile().getMainTextColor()[3]);
        getProgramView().getMainFontChooserRow().setSetting(new FontData(mainFont,mainColor));

        Font buzzerFont = new Font(getProgramModel().getSaveFile().getBuzzerFont(),getProgramModel().getSaveFile().isBuzzerTextBold()?BOLD:PLAIN, 200);
        Color buzzerColor = new Color(getProgram().getProgramModel().getSaveFile().getBuzzerTextColor()[0],getProgram().getProgramModel().getSaveFile().getBuzzerTextColor()[1],getProgram().getProgramModel().getSaveFile().getBuzzerTextColor()[2],getProgram().getProgramModel().getSaveFile().getBuzzerTextColor()[3]);
        getProgramView().getBuzzerFontChooserRow().setSetting(new FontData(buzzerFont,buzzerColor));

        getProgramView().getIntroSound().setSetting(new AudioSettingRow.AudioData(new File(getProgramModel().getSaveFile().getIntroSound()), getProgramModel().getSaveFile().getIntroVolume()));
        getProgramView().getQuestionSound().setSetting(new AudioSettingRow.AudioData(new File(getProgramModel().getSaveFile().getQuestionSound()), getProgramModel().getSaveFile().getQuestionVolume()));
        getProgramView().getRightSound().setSetting(new AudioSettingRow.AudioData(new File(getProgramModel().getSaveFile().getRightSound()), getProgramModel().getSaveFile().getRightVolume()));
        getProgramView().getBuzzerSound().setSetting(new AudioSettingRow.AudioData(new File(getProgramModel().getSaveFile().getBuzzerSound()), getProgramModel().getSaveFile().getBuzzerVolume()));
        getProgramView().getWrongSound().setSetting(new AudioSettingRow.AudioData(new File(getProgramModel().getSaveFile().getWrongSound()), getProgramModel().getSaveFile().getWrongVolume()));
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

    @Override
    public void settingChanged(SettingsEvent se) {
        if (se.getName().contains("icon")) {
            int index = Integer.parseInt(se.getName().substring(4,5));
            getProgramModel().getSaveFile().getIcons()[index] = ((File) se.getValue()).getAbsolutePath();
            getProgram().getRenderer().addActionToOpenGlThread(() -> {
                getProgramModel().setIcons(Texture.loadTexture(new File(getProgramModel().getSaveFile().getIcons()[index])),index);
                getProgram().getProgramPresentationView().updateIcon(index);
            });
        }
        if (se.getName().startsWith("font")) {
            changeFontSettings(se);
        }
        if (se.getName().startsWith("sound")) {
            changeSoundSetting(se);
        }
        if ("background".equals(se.getName())) {
            getProgramModel().getSaveFile().setBackground(((File) se.getValue()).getAbsolutePath());
            getProgram().getRenderer().addActionToOpenGlThread(() -> {
                getProgramModel().setBackgroundTexture(Texture.loadTexture(new File(getProgramModel().getSaveFile().getBackground())));
                getProgram().getProgramPresentationView().updateBackground();
            });
        }
    }

    private void changeSoundSetting(SettingsEvent se) {
        if (se.getName().endsWith("File")) {
            switch (se.getName().substring(5)) {
                case "IntroFile":
                    getProgramModel().getSaveFile().setIntroSound(((File) se.getValue()).getAbsolutePath());
                    new Thread(() -> getProgramModel().setIntroSound(AudioClip.load((File) se.getValue()))).start();
                    break;
                case "QuestionFile":
                    getProgramModel().getSaveFile().setQuestionSound(((File) se.getValue()).getAbsolutePath());
                    new Thread(() -> getProgramModel().setQuestionSound(AudioClip.load((File) se.getValue()))).start();
                    break;
                case "RightFile":
                    getProgramModel().getSaveFile().setRightSound(((File) se.getValue()).getAbsolutePath());
                    new Thread(() -> getProgramModel().setRightSound(AudioClip.load((File) se.getValue()))).start();
                    break;
                case "BuzzerFile":
                    getProgramModel().getSaveFile().setBuzzerSound(((File) se.getValue()).getAbsolutePath());
                    new Thread(() -> getProgramModel().setBuzzerSound(AudioClip.load((File) se.getValue()))).start();
                    break;
                case "WrongSound":
                    getProgramModel().getSaveFile().setWrongSound(((File) se.getValue()).getAbsolutePath());
                    new Thread(() -> getProgramModel().setWrongSound(AudioClip.load((File) se.getValue()))).start();
                    break;
            }
        } else {
            switch (se.getName().substring(5)) {
                case "IntroVolume":
                    getProgramModel().getSaveFile().setIntroVolume((int) ((float) se.getValue() * 100));
                    if(getProgramModel().getIntroSound() != null) {
                        getProgramModel().getIntroSound().setGain((float) se.getValue());
                    }
                    break;
                case "QuestionVolume":
                    getProgramModel().getSaveFile().setQuestionVolume((int) ((float) se.getValue() * 100));
                    if(getProgramModel().getQuestionSound() != null) {
                        getProgramModel().getQuestionSound().setGain((float) se.getValue());
                    }
                    break;
                case "RightVolume":
                    getProgramModel().getSaveFile().setRightVolume((int) ((float) se.getValue() * 100));
                    if(getProgramModel().getRightSound() != null) {
                        getProgramModel().getRightSound().setGain((float) se.getValue());
                    }
                    break;
                case "BuzzerVolume":
                    getProgramModel().getSaveFile().setBuzzerVolume((int) ((float) se.getValue() * 100));
                    if(getProgramModel().getBuzzerSound() != null) {
                        getProgramModel().getBuzzerSound().setGain((float) se.getValue());
                    }
                    break;
                case "WrongVolume":
                    getProgramModel().getSaveFile().setWrongVolume((int) ((float) se.getValue() * 100));
                    if(getProgramModel().getWrongSound() != null) {
                        getProgramModel().getWrongSound().setGain((float) se.getValue());
                    }
                    break;
            }
        }
    }

    private void changeFontSettings(SettingsEvent se) {
        if (se.getName().startsWith("fontmain")) {
            changeMainFontSettings(se);
        } else {
            chageBuzzerFontSettings(se);
        }
    }

    private void chageBuzzerFontSettings(SettingsEvent se) {
        switch (se.getName()) {
            case "fontbuzzerfont":
                if(!getProgramModel().getSaveFile().getBuzzerFont().equals(se.getValue())) {
                    getProgramModel().getSaveFile().setBuzzerFont(((String) se.getValue()));
                    getProgram().getRenderer().addActionToOpenGlThread(() -> getProgram().getProgramPresentationView().updateBuzzerFont());
                }
                break;
            case "fontbuzzerstyle":
                if(!getProgramModel().getSaveFile().isBuzzerTextBold() == (boolean)se.getValue()) {
                    getProgramModel().getSaveFile().setBuzzerTextBold(((boolean) se.getValue()));
                    getProgram().getRenderer().addActionToOpenGlThread(() -> getProgram().getProgramPresentationView().updateBuzzerFont());
                }
                break;

            case "fontbuzzercolor":
                Color color = ((Color)se.getValue());
                int[] oldColor = getProgramModel().getSaveFile().getBuzzerTextColor();
                if(color.getRed() != oldColor[0] ||color.getGreen() != oldColor[1] || color.getBlue() != oldColor[2] || color.getAlpha() != oldColor[3]) {
                    getProgramModel().getSaveFile().setBuzzerTextColor(new int[]{color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()});
                    getProgram().getRenderer().addActionToOpenGlThread(() -> getProgram().getProgramPresentationView().updateBuzzerFont());
                }
                break;
        }
    }

    private void changeMainFontSettings(SettingsEvent se) {
        switch (se.getName()) {
            case "fontmainfont":
                if(!getProgramModel().getSaveFile().getMainFont().equals(se.getValue())) {
                    getProgramModel().getSaveFile().setMainFont(((String) se.getValue()));
                    getProgram().getRenderer().addActionToOpenGlThread(() -> getProgram().getProgramPresentationView().updateMainFont());
                }
                break;
            case "fontmainstyle":
                if(!getProgramModel().getSaveFile().isMainTextBold() == (boolean)se.getValue()) {
                    getProgramModel().getSaveFile().setMainTextBold(((boolean)se.getValue()));
                    getProgram().getRenderer().addActionToOpenGlThread(() -> getProgram().getProgramPresentationView().updateMainFont());
                }
                break;

            case "fontmaincolor":

                Color color = ((Color)se.getValue());
                int[] oldColor = getProgramModel().getSaveFile().getMainTextColor();
                if(color.getRed() != oldColor[0] ||color.getGreen() != oldColor[1] || color.getBlue() != oldColor[2] || color.getAlpha() != oldColor[3]) {
                    getProgramModel().getSaveFile().setMainTextColor(new int[]{color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()});
                    getProgram().getRenderer().addActionToOpenGlThread(() -> getProgram().getProgramPresentationView().updateMainFont());
                }
                break;
        }
    }
}
