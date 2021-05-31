package programs.quizPrograms.settings.pageControllers;

import assets.settings.general.SettingsEvent;
import assets.settings.rows.FontChooserRow;
import assets.settings.rows.FontData;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.quizPrograms.data.QuizModel;
import programs.quizPrograms.main.control.QuizProgram;
import programs.quizPrograms.main.view.QuizPresentationView;
import programs.quizPrograms.settings.QuizSettingsController;


import java.awt.*;

import static java.awt.Font.BOLD;
import static java.awt.Font.PLAIN;
import static programs.quizPrograms.data.QuizModel.BUZZER_FONT;
import static programs.quizPrograms.data.QuizModel.MAIN_FONT;

/**
 * controller of the font settings page of the quiz overlay program
 */
public class FontSettingsController extends ProgramSettingsPageController<QuizSettingsController> {

    /**
     * creates a new font settings contorller
     *
     * @param mainSettingsController main controller of the quiz overlay settings
     */
    public FontSettingsController(QuizSettingsController mainSettingsController) {
        super(mainSettingsController);
    }

    /**
     * method gets called when a setting was changed
     *
     * @param event settings changed event of the setting
     */
    @Override
    public void settingChangedAction(SettingsEvent event) {
        if (event.getName().equals(MAIN_FONT)) {
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
        QuizProgram program = mainSettingsController.getProgram();
        QuizModel programModel = mainSettingsController.getProgramModel();

        programModel.getSaveFile().putFontData(MAIN_FONT, (FontData) se.getValue());
        program.getRenderer().addActionToOpenGlThread(() -> ((QuizPresentationView) program.getProgramPresentationView()).updateMainFont());
    }

    /**
     * Method changes a setting of the buzzer font
     *
     * @param se settings event created by the settings view element, containing a name to identify the settings and
     */
    private void changeBuzzerFontSettings(SettingsEvent se) {
        QuizProgram program = mainSettingsController.getProgram();
        QuizModel programModel = mainSettingsController.getProgramModel();

        programModel.getSaveFile().putFontData(BUZZER_FONT, (FontData) se.getValue());
        program.getRenderer().addActionToOpenGlThread(() -> ((QuizPresentationView) program.getProgramPresentationView()).updateBuzzerFont());
    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {

        QuizModel programModel = mainSettingsController.getProgramModel();

        mainSettingsController.getProgramView().getFontSettingsPage().getMainFontChooserRow().setSetting(programModel.getSaveFile().getFontData(MAIN_FONT));

        mainSettingsController.getProgramView().getFontSettingsPage().getBuzzerFontChooserRow().setSetting(programModel.getSaveFile().getFontData(BUZZER_FONT));

    }
}
