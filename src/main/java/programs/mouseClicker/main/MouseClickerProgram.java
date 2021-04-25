package programs.mouseClicker.main;

import programs.abstractProgram.Program;
import programs.mouseClicker.control.controller.MouseClickerProgramController;
import programs.mouseClicker.data.MouseClickerProgramModel;

import java.awt.*;
import java.awt.event.InputEvent;

public class MouseClickerProgram extends Program<MouseClickerProgramController, MouseClickerProgramController, MouseClickerProgramModel, MouseClickerProgramPresentationView> {

    private float screenScalar;

    /**
     * creates a new program
     */
    public MouseClickerProgram() {
        super(true, "Mouse Clicker");

        screenScalar = Toolkit.getDefaultToolkit().getScreenResolution() / 96f;

        addClosedAction(() -> getProgramModel().getSaveFile().saveFile());
    }

    @Override
    public MouseClickerProgramModel createModel() {
        return new MouseClickerProgramModel();
    }

    @Override
    public MouseClickerProgramController createSettingsController() {
        return null;
    }

    @Override
    public MouseClickerProgramController createControlController() {
        return new MouseClickerProgramController(this, getProgramModel());
    }

    @Override
    public MouseClickerProgramPresentationView createPresentationView() {
        return new MouseClickerProgramPresentationView(this);
    }

    @Override
    protected void buzzerAction(int buzzerNumber) {
        if (getProgramModel().getSaveFile().getUseClick()[buzzerNumber - 1]) {
            mousePress(buzzerNumber);
        }
        handleReset(buzzerNumber);



    }

    private void mousePress(int buzzer) {
        Robot bot;
        try {
            bot = new Robot();
            bot.mouseMove(0, 0);
            bot.mouseMove((int) (getProgramModel().getSaveFile().getClickX()[buzzer - 1] / screenScalar), (int) (getProgramModel().getSaveFile().getClickY()[buzzer - 1] / screenScalar));
            bot.mousePress(InputEvent.BUTTON1_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
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
                    getControlModel().getBuzzerControl().unblockBuzzer(1);
                    getControlModel().getBuzzerControl().unblockBuzzer(2);
                    getControlModel().getBuzzerControl().unblockBuzzer(3);

                }
                break;
        }
    }

    @Override
    public void programSelected() {
        getProgramController().mouseTrackerButtonAction(getProgramModel().getSaveFile().isDisplayMouseTracker());
    }

    @Override
    public void updateBuzzerCount() {

    }
}
