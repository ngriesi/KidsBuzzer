package programs.quizOverlay.settings.pageControllers;

import assets.settings.general.SettingsEvent;
import presentationWindow.items.Texture;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.quizOverlay.data.QuizOverlayModel;
import programs.quizOverlay.main.control.QuizOverlayProgram;
import programs.quizOverlay.settings.QuizOverlaySettingsController;
import savedataHandler.SaveDataHandler;

import java.io.File;

/**
 * settings controller for the image selection of the quiz overlay program
 */
public class ImageSettingsPageController extends ProgramSettingsPageController<QuizOverlaySettingsController> {

    /**
     * creates a new controller
     *
     * @param mainSettingsController main settings controller of the quiz overlay program
     */
    public ImageSettingsPageController(QuizOverlaySettingsController mainSettingsController) {
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
            mainSettingsController.getProgramView().getImageSettingsPage().getIcons()[i].setSetting(mainSettingsController.getProgramModel().getSaveFile().getIcons()[i]);
        }
    }

    /**
     * changes an icon setting
     *
     * @param se settings event created by the settings view element, containing a name to identify the settings and
     */
    private void changeIconSetting(SettingsEvent se) {
        QuizOverlayProgram program = mainSettingsController.getProgram();
        QuizOverlayModel programModel = mainSettingsController.getProgramModel();
        int index = Integer.parseInt(se.getName().substring(4, 5));
        programModel.getSaveFile().getIcons()[index] = ((File) se.getValue()).getAbsolutePath();
        program.getRenderer().addActionToOpenGlThread(() -> {
            programModel.setIcons(Texture.loadTexture(new File(programModel.getSaveFile().getIcons()[index])), index);
            program.getProgramPresentationView().updateIcon(index);
        });
    }
}
