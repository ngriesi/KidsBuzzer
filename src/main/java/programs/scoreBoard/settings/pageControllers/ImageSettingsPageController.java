package programs.scoreBoard.settings.pageControllers;

import assets.settings.general.SettingsEvent;
import presentationWindow.items.Texture;
import programs.abstractProgram.ProgramSettingsPageController;
import programs.scoreBoard.settings.ScoreBoardSettingsController;
import savedataHandler.SaveDataHandler;

import java.io.File;

import static programs.scoreBoard.data.ScoreBoardModel.ICON;

/**
 * Controller of the image settings page of the score boarc program
 */
public class ImageSettingsPageController extends ProgramSettingsPageController<ScoreBoardSettingsController> {

    /**
     * creates a new controller
     *
     * @param mainSettingsController main controller of the settings of the score board program
     */
    public ImageSettingsPageController(ScoreBoardSettingsController mainSettingsController) {
        super(mainSettingsController);
    }

    /**
     * method gets called when a setting was changed
     *
     * @param settingsEvent settings changed event of the setting
     */
    @Override
    public void settingChangedAction(SettingsEvent settingsEvent) {
        int buzzerNumber = SaveDataHandler.getNumberByName(settingsEvent.getName().substring(4));
        mainSettingsController.getProgramModel().getSaveFile().putString(ICON + buzzerNumber, ((File) settingsEvent.getValue()).getAbsolutePath());
        mainSettingsController.getProgram().getRenderer().addActionToOpenGlThread(() -> {
            mainSettingsController.getProgramModel().setIcon(Texture.loadTexture(new File(mainSettingsController.getProgramModel().getSaveFile().getString(ICON + buzzerNumber))), buzzerNumber);
            mainSettingsController.getProgram().getProgramPresentationView().updateIcon(buzzerNumber);
        });
    }

    /**
     * updates the view with the values from the save file
     */
    @Override
    public void updateView() {
        for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
            mainSettingsController.getProgramView().getImageSettingsPage().getIconSettingRows()[i].setSetting(new File(mainSettingsController.getProgramModel().getSaveFile().getString(ICON + i)));
        }
    }
}
