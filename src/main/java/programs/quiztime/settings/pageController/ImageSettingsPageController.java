package programs.quiztime.settings.pageController;

import assets.settings.general.SettingsEvent;
import presentationWindow.items.Texture;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.quiztime.main.control.QuizTimeProgram;
import programs.quiztime.settings.QuizTimeProgramSettingsController;
import savedataHandler.SaveDataHandler;

import java.io.File;

/**
 * controller for the font settings page of the quiztime program
 */
public class ImageSettingsPageController extends ProgramSettingsPageController<QuizTimeProgramSettingsController> {

    /**
     * creates a new controller for the font settings page of the quiztime program
     *
     * @param mainSettingsController main controller of the quiztime settings
     */
    public ImageSettingsPageController(QuizTimeProgramSettingsController mainSettingsController) {
        super(mainSettingsController);
    }

    /**
     * method gets called when a setting was changed
     *
     * @param event settings changed event of the setting
     */
    @Override
    public void settingChangedAction(SettingsEvent event) {
        QuizTimeProgram program = mainSettingsController.getProgram();

        if ("background".equals(event.getName())) {
            mainSettingsController.getProgramModel().getSaveFile().setBackground(((File) event.getValue()).getAbsolutePath());
            program.getRenderer().addActionToOpenGlThread(() -> {
                mainSettingsController.getProgramModel().setBackgroundTexture(Texture.loadTexture(new File(mainSettingsController.getProgramModel().getSaveFile().getBackground())));
                program.getProgramPresentationView().updateBackground();
            });
        }
        if (event.getName().contains("icon")) {
            int index = Integer.parseInt(event.getName().substring(4, 5));
            mainSettingsController.getProgramModel().getSaveFile().getIcons()[index] = ((File) event.getValue()).getAbsolutePath();
            program.getRenderer().addActionToOpenGlThread(() -> {
                mainSettingsController.getProgramModel().setIcons(Texture.loadTexture(new File(mainSettingsController.getProgramModel().getSaveFile().getIcons()[index])), index);
                program.getProgramPresentationView().updateIcon(index);
            });
        }
    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {

        mainSettingsController.getProgramView().getImageSettingsPage().getPresentationBackground().setSetting(mainSettingsController.getProgramModel().getSaveFile().getBackground());

        for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
            mainSettingsController.getProgramView().getImageSettingsPage().getIcons()[i].setSetting(mainSettingsController.getProgramModel().getSaveFile().getIcons()[i]);
        }
    }
}
