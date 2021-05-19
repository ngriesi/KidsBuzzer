package buzzerHandler;

import controlWindow.MainController;

/**
 * Logic class behind the buzzer overlay at the bottom of the window
 */
public class BuzzerModel {

    /**
     * reference to the main class of the Control Window
     */
    private MainController mainController;

    /**
     * View that contains the virtual buzzers
     */
    private BuzzerControlView buzzerControlView;

    /**
     * number of buzzers used
     */
    private int buzzerCount;

    /**
     * saves the state of the buzzers
     */
    private boolean[] buzzerPressed;

    /**
     * blocks all buzzers
     */
    private boolean blockAllBuzzer;

    /**
     * initialises the view of the virtual buzzers
     *
     * @param mainController reference to the main class of the control window
     */
    public BuzzerModel(MainController mainController) {
        this.mainController = mainController;

        buzzerCount = mainController.getControlModel().getSettingsController().getSettingsSaveFile().getBuzzerNumber();
        buzzerControlView = new BuzzerControlView(this);
        buzzerPressed = new boolean[buzzerCount];
        resetBuzzers();
    }

    /**
     * @return returns the buzzer view
     */
    public BuzzerControlView getView() {
        return buzzerControlView;
    }

    /**
     * @return returns the number of buzzers
     */
    int getCount() {
        return buzzerCount;
    }

    /**
     * resets the state of the buzzers and calls the method to reset its virtual representations
     */
    public void resetBuzzers() {
        buzzerControlView.resetBuzzers();
        blockAllBuzzer = false;
        buzzerPressed = new boolean[buzzerCount];
    }

    /**
     * resets a single buzzer
     *
     * @param number buzzer to reset
     */
    public void unblockBuzzer(int number) {
        buzzerPressed[number - 1] = false;
        buzzerControlView.resetBuzzer(number);
    }

    /**
     * method called when a buzzer gets pressed
     *
     * @param number number of the pressed buzzer
     */
    public void pressBuzzer(int number) {

        if (!buzzerPressed[number - 1] && !blockAllBuzzer) {
            buzzerControlView.pressBuzzer(number);
            buzzerPressed[number - 1] = true;
        }
    }

    /**
     * to check if a buzzer is currently locked
     *
     * @param buzzerNumber number of the buzzer to check
     * @return result of the check, true if the buzzer is pressed
     */
    public boolean isBuzzerBlocked(int buzzerNumber) {
        return buzzerPressed[buzzerNumber - 1];
    }

    /**
     * @return returns the number of buzzers used
     */
    public int getBuzzerCount() {
        return buzzerCount;
    }

    /**
     * changes the text of the right button
     *
     * @param text new button text
     */
    public void changeButtonText(String text) {
        getView().getButton().setText(text);
    }

    /**
     * checks if the boolean that blocks all buzzers at once is true
     *
     * @return returns true if the block all buzzers flag is true
     */
    public boolean isBlockAllBuzzer() {
        return blockAllBuzzer;
    }

    /**
     * sets the flag to block all buzzers. this is independent from the normal blocking behaviour
     *
     * @param blockAllBuzzer new value for block all buzzers
     */
    public void setBlockAllBuzzer(boolean blockAllBuzzer) {
        this.blockAllBuzzer = blockAllBuzzer;
    }

    /**
     * returns the main controller of the application
     *
     * @return main controller of the application
     */
    public MainController getMainController() {
        return mainController;
    }

    /**
     * updates the view of the bottom bar
     */
    public void updateView() {
        buzzerControlView = new BuzzerControlView(this);
    }
}
