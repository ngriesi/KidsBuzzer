package programs.quiztime.settings.pageController;

import assets.settings.general.SettingsEvent;
import presentationWindow.items.Texture;
import programs.quizPrograms.settings.QuizSettingsController;
import programs.quizPrograms.settings.pageControllers.ImageSettingsController;
import programs.quiztime.data.QuizTimeProgramModel;
import programs.quiztime.main.control.QuizTimeProgram;
import programs.quiztime.settings.pages.ImageSettingsPage;

import java.io.File;

import static programs.quiztime.data.QuizTimeProgramModel.BACKGROUND;

/**
 * controller for the font settings page of the quiztime program
 */
public class ImageSettingsPageController extends ImageSettingsController {

    /**
     * creates a new controller for the font settings page of the quiztime program
     *
     * @param mainSettingsController main controller of the quiztime settings
     */
    public ImageSettingsPageController(QuizSettingsController mainSettingsController) {
        super(mainSettingsController);
    }

    /**
     * method gets called when a setting was changed
     *
     * @param event settings changed event of the setting
     */
    @Override
    public void settingChangedAction(SettingsEvent event) {
        QuizTimeProgram program = (QuizTimeProgram) mainSettingsController.getProgram();
        QuizTimeProgramModel programModel = (QuizTimeProgramModel) mainSettingsController.getProgramModel();

        if ("background".equals(event.getName())) {
            mainSettingsController.getProgramModel().getSaveFile().putString(BACKGROUND, ((File) event.getValue()).getAbsolutePath());
            program.getRenderer().addActionToOpenGlThread(() -> {
                programModel.setBackgroundTexture(Texture.loadTexture(new File(mainSettingsController.getProgramModel().getSaveFile().getString(BACKGROUND))));
                program.getProgramPresentationView().updateBackground();
            });
        }
        if (event.getName().contains("icon")) {
            super.changeIconSetting(event);
        }
    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {

        ((ImageSettingsPage) mainSettingsController.getProgramView().getImageSettingsPage()).getPresentationBackground().setSetting(new File(mainSettingsController.getProgramModel().getSaveFile().getString(BACKGROUND)));

        super.updateView();
    }
}
