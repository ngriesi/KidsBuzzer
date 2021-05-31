package programs.quizPrograms.settings.pageControllers;

import assets.settings.general.SettingsEvent;
import presentationWindow.items.Texture;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.quizPrograms.data.QuizModel;
import programs.quizPrograms.main.control.QuizProgram;
import programs.quizPrograms.main.view.QuizPresentationView;
import programs.quizPrograms.settings.QuizSettingsController;
import savedataHandler.SaveDataHandler;

import java.io.File;

import static programs.quizPrograms.data.QuizModel.ICON;

/**
 * settings controller for the image selection of the quiz overlay program
 */
public class ImageSettingsController extends ProgramSettingsPageController<QuizSettingsController> {

    /**
     * creates a new controller
     *
     * @param mainSettingsController main settings controller of the quiz overlay program
     */
    public ImageSettingsController(QuizSettingsController mainSettingsController) {
        super(mainSettingsController);
    }

    /**
     * method gets called when a setting was changed
     *
     * @param event settings changed event of the setting
     */
    @Override
    public void settingChangedAction(SettingsEvent event) {
        changeIconSetting(event);
    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {
        for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
            mainSettingsController.getProgramView().getImageSettingsPage().getIcons()[i].setSetting(new File(mainSettingsController.getProgramModel().getSaveFile().getString(ICON + i)));
        }
    }

    /**
     * changes an icon setting
     *
     * @param se settings event created by the settings view element, containing a name to identify the settings and
     */
    protected void changeIconSetting(SettingsEvent se) {
        QuizProgram program = mainSettingsController.getProgram();
        QuizModel programModel = mainSettingsController.getProgramModel();
        int index = Integer.parseInt(se.getName().substring(4, 5));
        programModel.getSaveFile().putString(ICON + index, ((File) se.getValue()).getAbsolutePath());
        program.getRenderer().addActionToOpenGlThread(() -> {
            programModel.setIcons(Texture.loadTexture(new File(programModel.getSaveFile().getString(ICON + index))), index);
            ((QuizPresentationView) program.getProgramPresentationView()).updateIcon(index);
        });
    }
}
