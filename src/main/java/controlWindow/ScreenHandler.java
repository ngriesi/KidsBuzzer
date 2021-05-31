package controlWindow;

import controlWindow.settings.SettingsController;
import utils.save.SaveFile;

import java.awt.*;

import static controlWindow.settings.SettingsController.DESIRED_OUTPUT_SCREEN;
import static controlWindow.settings.SettingsController.OUTPUT_SCREEN;

/**
 * class listens for changes to the number of screens
 */
class ScreenHandler {

    /**
     * reference to the main controller of the Application
     */
    private MainController mainController;

    /**
     * stores the number of screens that were present in the previous
     * check
     */
    private int oldScreenNum = 0;

    /**
     * creates a new screen handler and starts the thread listening for screen changes
     *
     * @param mainController reference to the main controller of the Application
     */
    ScreenHandler(MainController mainController) {
        this.mainController = mainController;

        listenForScreenChanges();
    }

    /**
     * Method gets called when the number of screens changes and an update
     * needs to be made
     *
     * @param screens the number of screens that are currently connected
     */
    private void updateScreen(int screens) {
        mainController.getControlModel().getSettingsController().setPossibleScreens(createScreensArray(screens));
        SaveFile saveFile = mainController.getControlModel().getSettingsController().getSettingsSaveFile();
        if (saveFile.getInteger(OUTPUT_SCREEN) > screens) {
            saveFile.putInteger(OUTPUT_SCREEN, 1);
            mainController.updateOutputScreen(1);
        } else if (
                saveFile.getInteger(DESIRED_OUTPUT_SCREEN) <= screens && saveFile.getInteger(OUTPUT_SCREEN) != saveFile.getInteger(DESIRED_OUTPUT_SCREEN)
        ) {
            saveFile.putInteger(OUTPUT_SCREEN, saveFile.getInteger(DESIRED_OUTPUT_SCREEN));
            mainController.updateOutputScreen(saveFile.getInteger(OUTPUT_SCREEN));
        }
    }

    /**
     * Method checks the number of screens every second and makes updates if a
     * change occurs
     */
    private void listenForScreenChanges() {
        new Thread(() -> {
            while (mainController.getControlModel().isApplicationRunning()) {

                int screens = getNumberOfScreens();
                if (screens != oldScreenNum) {
                    updateScreen(screens);
                }

                oldScreenNum = screens;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    /**
     * creates an array of integers where the values are the indexes plus 1
     *
     * @param size size of the array
     * @return returns the created array
     */
    private Integer[] createScreensArray(int size) {
        Integer[] result = new Integer[size];
        for (int i = 0; i < size; i++) {
            result[i] = i + 1;
        }
        return result;
    }

    /**
     * method uses the <code>GraphicsEnvironment</code> to get the number of
     * screens
     *
     * @return th number of screens
     */
    private int getNumberOfScreens() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = env.getScreenDevices();
        return devices.length;
    }


}
