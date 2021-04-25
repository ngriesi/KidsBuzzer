package controlWindow.settings;

import utils.saveFile.SaveFile;

/**
 * save file for the settings of the main application
 */
@SuppressWarnings("WeakerAccess")
public class SettingsSaveFile extends SaveFile {

    /**
     * stores the output screen
     */
    private int outputScreen = 0;

    /**
     * stores the width of the application when it was closed
     */
    private int windowWidth = 1000;

    /**
     * stores the height of the application when it was closed
     */
    private int windowHeight = 1000;

    /**
     * stores the x position of the application when it was closed
     */
    private int windowPositionX = 0;

    /**
     * stores the y position of the application when it was closed
     */
    private int windowPositionY = 0;

    /**
     * sets the number of buzzers used
     */
    private int buzzerNumber = 3;

    /**
     * creates a save file with a name
     */
    public SettingsSaveFile() {
        super("settings");
    }

    /*
    *********************************
            GETTER AND SETTER
    *********************************
     */

    public int getBuzzerNumber() {
        return buzzerNumber;
    }

    public void setBuzzerNumber(int buzzerNumber) {
        this.buzzerNumber = buzzerNumber;
    }

    public int getOutputScreen() {
        return outputScreen;
    }

    public void setOutputScreen(int outputScreen) {
        this.outputScreen = outputScreen;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getWindowPositionX() {
        return windowPositionX;
    }

    public void setWindowPositionX(int windowPositionX) {
        this.windowPositionX = windowPositionX;
    }

    public int getWindowPositionY() {
        return windowPositionY;
    }

    public void setWindowPositionY(int windowPositionY) {
        this.windowPositionY = windowPositionY;
    }
}
