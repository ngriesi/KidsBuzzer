package programs.quizOverlay.settings.pageControllers;

import assets.settings.general.SettingsEvent;
import assets.settings.rows.FontChooserRow;
import assets.settings.rows.FontData;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.quizOverlay.data.QuizOverlayModel;
import programs.quizOverlay.main.control.QuizOverlayProgram;
import programs.quizOverlay.settings.QuizOverlaySettingsController;

import java.awt.*;

import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;

/**
 * controller of the font settings page of the quiz overlay program
 */
public class FontSettingsController extends ProgramSettingsPageController<QuizOverlaySettingsController> {

    /**
     * creates a new font settings contorller
     *
     * @param mainSettingsController main controller of the quiz overlay settings
     */
    public FontSettingsController(QuizOverlaySettingsController mainSettingsController) {
        super(mainSettingsController);
    }

    /**
     * method gets called when a setting was changed
     *
     * @param event settings changed event of the setting
     */
    @Override
    public void settingChangedAction(SettingsEvent event) {
        if (event.getName().equals("main")) {
            changeMainFontSettings(event);
        } else {
            changeBuzzerFontSettings(event);
        }
    }


    /**
     * Method changes a setting of the main font.
     *
     * @param se settings event created by the settings view element, containing a name to identify the settings and
     */
    private void changeMainFontSettings(SettingsEvent se) {
        QuizOverlayProgram program = mainSettingsController.getProgram();
        QuizOverlayModel programModel = mainSettingsController.getProgramModel();

        switch (se.getComponentName()) {
            case FontChooserRow.FONT:
                if (!programModel.getSaveFile().getMainFont().equals(se.getValue())) {
                    programModel.getSaveFile().setMainFont(((String) se.getValue()));
                    program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateMainFont());
                }
                break;
            case FontChooserRow.STYLE:
                if (!programModel.getSaveFile().isMainTextBold() == (boolean) se.getValue()) {
                    programModel.getSaveFile().setMainTextBold(((boolean) se.getValue()));
                    program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateMainFont());
                }
                break;

            case FontChooserRow.COLOR:

                Color color = ((Color) se.getValue());
                int[] oldColor = programModel.getSaveFile().getMainTextColor();
                if (color.getRed() != oldColor[0] || color.getGreen() != oldColor[1] || color.getBlue() != oldColor[2] || color.getAlpha() != oldColor[3]) {
                    programModel.getSaveFile().setMainTextColor(new int[]{color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()});
                    program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateMainFont());
                }
                break;
        }
    }

    /**
     * Method changes a setting of the buzzer font
     *
     * @param se settings event created by the settings view element, containing a name to identify the settings and
     */
    private void changeBuzzerFontSettings(SettingsEvent se) {
        QuizOverlayProgram program = mainSettingsController.getProgram();
        QuizOverlayModel programModel = mainSettingsController.getProgramModel();

        switch (se.getComponentName()) {
            case FontChooserRow.FONT:
                if (!programModel.getSaveFile().getBuzzerFont().equals(se.getValue())) {
                    programModel.getSaveFile().setBuzzerFont(((String) se.getValue()));
                    program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateBuzzerFont());
                }
                break;
            case FontChooserRow.STYLE:
                if (!programModel.getSaveFile().isBuzzerTextBold() == (boolean) se.getValue()) {
                    programModel.getSaveFile().setBuzzerTextBold(((boolean) se.getValue()));
                    program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateBuzzerFont());
                }
                break;

            case FontChooserRow.COLOR:
                Color color = ((Color) se.getValue());
                int[] oldColor = programModel.getSaveFile().getBuzzerTextColor();
                if (color.getRed() != oldColor[0] || color.getGreen() != oldColor[1] || color.getBlue() != oldColor[2] || color.getAlpha() != oldColor[3]) {
                    programModel.getSaveFile().setBuzzerTextColor(new int[]{color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()});
                    program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().updateBuzzerFont());
                }
                break;
        }
    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {

        QuizOverlayModel programModel = mainSettingsController.getProgramModel();

        Font mainFont = new Font(programModel.getSaveFile().getMainFont(), programModel.getSaveFile().isMainTextBold() ? BOLD : PLAIN, 200);
        Color mainColor = new Color(programModel.getSaveFile().getMainTextColor()[0], programModel.getSaveFile().getMainTextColor()[1], programModel.getSaveFile().getMainTextColor()[2], programModel.getSaveFile().getMainTextColor()[3]);
        mainSettingsController.getProgramView().getFontSettingsPage().getMainFontChooserRow().setSetting(new FontData(mainFont, mainColor));

        Font buzzerFont = new Font(programModel.getSaveFile().getBuzzerFont(), programModel.getSaveFile().isBuzzerTextBold() ? BOLD : PLAIN, 200);
        Color buzzerColor = new Color(programModel.getSaveFile().getBuzzerTextColor()[0], programModel.getSaveFile().getBuzzerTextColor()[1], programModel.getSaveFile().getBuzzerTextColor()[2], programModel.getSaveFile().getBuzzerTextColor()[3]);
        mainSettingsController.getProgramView().getFontSettingsPage().getBuzzerFontChooserRow().setSetting(new FontData(buzzerFont, buzzerColor));

    }
}
