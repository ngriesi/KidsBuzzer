package programs.quiztime.settings.pageController;

import assets.settings.general.SettingsEvent;
import assets.settings.rows.FontChooserRow;
import assets.settings.rows.FontData;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.quiztime.data.QuizTimeProgramModel;
import programs.quiztime.main.control.QuizTimeProgram;
import programs.quiztime.settings.QuizTimeProgramSettingsController;
import programs.quiztime.settings.pages.FontSettingsPage;

import java.awt.*;

import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;

/**
 * controller for the font settings page of the quiztime program
 */
public class FontSettingsPageController extends ProgramSettingsPageController<QuizTimeProgramSettingsController> {

    /**
     * creates a new controller for the font settings page of the quiztime program
     *
     * @param mainSettingsController main controller of the quiztime settings
     */
    public FontSettingsPageController(QuizTimeProgramSettingsController mainSettingsController) {
        super(mainSettingsController);
    }

    /**
     * method gets called when a setting was changed
     *
     * @param event settings changed event of the setting
     */
    @Override
    public void settingChangedAction(SettingsEvent event) {

    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {

        QuizTimeProgramModel programModel = mainSettingsController.getProgramModel();
        FontSettingsPage fontSettingsPage = mainSettingsController.getProgramView().getFontSettingsPage();

        Font mainFont = new Font(programModel.getSaveFile().getMainFont(), programModel.getSaveFile().isMainTextBold() ? BOLD : PLAIN, 200);
        Color mainColor = new Color(programModel.getSaveFile().getMainTextColor()[0], programModel.getSaveFile().getMainTextColor()[1], programModel.getSaveFile().getMainTextColor()[2], programModel.getSaveFile().getMainTextColor()[3]);
        fontSettingsPage.getMainFontChooserRow().setSetting(new FontData(mainFont, mainColor));

        Font buzzerFont = new Font(programModel.getSaveFile().getBuzzerFont(), programModel.getSaveFile().isBuzzerTextBold() ? BOLD : PLAIN, 200);
        Color buzzerColor = new Color(programModel.getSaveFile().getBuzzerTextColor()[0], programModel.getSaveFile().getBuzzerTextColor()[1], programModel.getSaveFile().getBuzzerTextColor()[2], programModel.getSaveFile().getBuzzerTextColor()[3]);
        fontSettingsPage.getBuzzerFontChooserRow().setSetting(new FontData(buzzerFont, buzzerColor));

    }

    /**
     * Method changes a font setting.
     *
     * @param se settings event created by the settings view element, containing a name to identify the settings and
     */
    private void changeFontSettings(SettingsEvent se) {
        if (se.getName().startsWith("main")) {
            changeMainFontSettings(se);
        } else {
            changeBuzzerFontSettings(se);
        }
    }

    /**
     * Method changes a setting of the buzzer font
     *
     * @param se settings event created by the settings view element, containing a name to identify the settings and
     */
    private void changeBuzzerFontSettings(SettingsEvent se) {
        QuizTimeProgram program = mainSettingsController.getProgram();
        switch (se.getComponentName()) {
            case FontChooserRow.FONT:
                program.getProgramModel().getSaveFile().setBuzzerFont(((String) se.getValue()));
                program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateBuzzerFont());

                break;
            case FontChooserRow.STYLE:
                program.getProgramModel().getSaveFile().setBuzzerTextBold(((boolean) se.getValue()));
                program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateBuzzerFont());

                break;

            case FontChooserRow.COLOR:
                Color color = ((Color) se.getValue());
                program.getProgramModel().getSaveFile().setBuzzerTextColor(new int[]{color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()});
                program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateBuzzerFont());

                break;
        }
    }

    /**
     * Method changes a setting of the main font.
     *
     * @param se settings event created by the settings view element, containing a name to identify the settings and
     */
    private void changeMainFontSettings(SettingsEvent se) {
        QuizTimeProgram program = mainSettingsController.getProgram();
        switch (se.getName()) {
            case FontChooserRow.FONT:
                program.getProgramModel().getSaveFile().setMainFont(((String) se.getValue()));
                program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateMainFont());

                break;
            case FontChooserRow.STYLE:
                program.getProgramModel().getSaveFile().setMainTextBold(((boolean) se.getValue()));
                program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateMainFont());

                break;

            case FontChooserRow.COLOR:

                Color color = ((Color) se.getValue());
                program.getProgramModel().getSaveFile().setMainTextColor(new int[]{color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()});
                program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateMainFont());

                break;
        }
    }
}
