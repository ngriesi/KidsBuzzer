package programs.keyPresser.main;

import programs.abstractProgram.Program;
import programs.keyPresser.control.controller.KeyPressController;
import programs.keyPresser.data.KeyPressModel;
import savedataHandler.SaveDataHandler;

import java.awt.*;

public class KeyPressProgram extends Program<KeyPressController, KeyPressController, KeyPressModel, KeyPressPresentationView> {

    /**
     * creates a new program
     */
    public KeyPressProgram() {
        super(true, "Key Presser");
    }

    @Override
    public KeyPressModel createModel() {
        return new KeyPressModel();
    }

    @Override
    public KeyPressController createSettingsController() {
        return new KeyPressController(this, getProgramModel());
    }

    @Override
    public KeyPressController createControlController() {
        return new KeyPressController(this, getProgramModel());
    }

    @Override
    public KeyPressPresentationView createPresentationView() {
        return new KeyPressPresentationView(this);
    }

    @Override
    protected void buzzerAction(int buzzerNumber) {
        keyPress(buzzerNumber);
        handleReset(buzzerNumber);
    }

    @Override
    public void updateBuzzerCount() {

    }

    private void keyPress(int buzzer) {
        if (getProgramModel().getSaveFile().getUseClick()[buzzer - 1]) {

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

    private void handleReset(int buzzerNumber) {
        switch (getProgramModel().getSaveFile().getBlockingBehaviour()) {
            case "Dont block":
                getControlModel().getBuzzerControl().unblockBuzzer(buzzerNumber);

                break;
            case "Block until Released":

                break;
            case "Block for Time":
                new Thread(() -> {
                    try {
                        Thread.sleep(getProgramModel().getSaveFile().getBlockingTime());

                        getControlModel().getBuzzerControl().unblockBuzzer(buzzerNumber);
                        getControlModel().getBuzzerControl().setBlockAllBuzzer(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                break;
            case "Unblock with Buzzer":
                if (buzzerNumber == getProgramModel().getSaveFile().getUnblockBuzzer()) {
                    for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
                        getControlModel().getBuzzerControl().unblockBuzzer(i + 1);
                    }
                }
                break;
        }
    }
}
