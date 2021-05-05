package programs.keyPresser.main;

import programs.abstractProgram.Program;
import programs.emptyClasses.EmptyPresentationView;
import programs.keyPresser.control.controller.KeyPressController;
import programs.keyPresser.data.KeyPressModel;
import savedataHandler.SaveDataHandler;

import java.awt.*;

/**
 * main class of the key press program handling the buzzer input
 */
public class KeyPressProgram extends Program<KeyPressController, KeyPressController, KeyPressModel, EmptyPresentationView> {

    /**
     * creates a new program
     */
    public KeyPressProgram() {
        super(true, "Key Presser");
    }

    /**
     * @return the created <code>KeyPressModel</code>
     */
    @Override
    public KeyPressModel createModel() {
        return new KeyPressModel();
    }

    /**
     * @return the created <code>KeyPressController</code>
     */
    @Override
    public KeyPressController createSettingsController() {
        return new KeyPressController(this, getProgramModel());
    }

    /**
     * @return the created <code>KeyPressController</code>
     */
    @Override
    public KeyPressController createControlController() {
        return new KeyPressController(this, getProgramModel());
    }

    /**
     * @return the created <code>EmptyPresentationView</code>
     */
    @Override
    public EmptyPresentationView createPresentationView() {
        return new EmptyPresentationView(this);
    }

    /**
     * action performed when an unblocked buzzer gets pressed
     *
     * @param buzzerNumber number of the buzzer pressed (starting with 1)
     */
    @Override
    protected void buzzerAction(int buzzerNumber) {
        keyPress(buzzerNumber);
        handleReset(buzzerNumber);
    }

    @Override
    public void updateBuzzerCount() {

    }

    /**
     * method performs the key press when a buzzer was pressed
     *
     * @param buzzer number of the pressed buzzer (starting with 1)
     */
    private void keyPress(int buzzer) {
        if (getProgramModel().getSaveFile().getUseKey()[buzzer - 1]) {

            new Thread(() -> {
                Robot bot;
                try {
                    bot = new Robot();
                    bot.keyPress(getProgramModel().getSaveFile().getKey()[buzzer - 1]);
                    Thread.sleep(100);
                    bot.keyRelease(getProgramModel().getSaveFile().getKey()[buzzer - 1]);
                } catch (AWTException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * Method handles the reset of the buzzers according to the settings made to
     * the blocking behaviour
     *
     * @param buzzerNumber number of the buzzer pressed (starting with 1)
     */
    private void handleReset(int buzzerNumber) {
        switch (getProgramModel().getSaveFile().getBlockingBehaviour()) {
            //noinspection SpellCheckingInspection
            case "Dont block":
                getMainController().getControlModel().getBuzzerControl().unblockBuzzer(buzzerNumber);

                break;
            case "Block until Released":

                break;
            case "Block for Time":
                new Thread(() -> {
                    try {
                        Thread.sleep(getProgramModel().getSaveFile().getBlockingTime());

                        getMainController().getControlModel().getBuzzerControl().unblockBuzzer(buzzerNumber);
                        getMainController().getControlModel().getBuzzerControl().setBlockAllBuzzer(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                break;
            case "Unblock with Buzzer":
                if (buzzerNumber == getProgramModel().getSaveFile().getUnblockBuzzer()) {
                    for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
                        getMainController().getControlModel().getBuzzerControl().unblockBuzzer(i + 1);
                    }
                }
                break;
        }
    }
}
