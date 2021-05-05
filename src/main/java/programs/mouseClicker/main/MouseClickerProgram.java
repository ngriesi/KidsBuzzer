package programs.mouseClicker.main;

import programs.abstractProgram.Program;
import programs.emptyClasses.EmptyPresentationView;
import programs.mouseClicker.control.controller.MouseClickerProgramController;
import programs.mouseClicker.data.MouseClickerProgramModel;

import java.awt.*;
import java.awt.event.InputEvent;

public class MouseClickerProgram extends Program<MouseClickerProgramController, MouseClickerProgramController, MouseClickerProgramModel, EmptyPresentationView> {

    private float screenScalar;

    /**
     * creates a new program
     */
    public MouseClickerProgram() {
        super(true, "Mouse Clicker");

        screenScalar = Toolkit.getDefaultToolkit().getScreenResolution() / 96f;
    }

    /**
     * @return the created <code>MouseClickerProgramModel</code>
     */
    @Override
    public MouseClickerProgramModel createModel() {
        return new MouseClickerProgramModel();
    }

    /**
     * @return the created <code>MouseClickerProgramController</code>
     */
    @Override
    public MouseClickerProgramController createSettingsController() {
        return null;
    }

    /**
     * @return the created <code>MouseClickerProgramController</code>
     */
    @Override
    public MouseClickerProgramController createControlController() {
        return new MouseClickerProgramController(this, getProgramModel());
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
        if (getProgramModel().getSaveFile().getUseClick()[buzzerNumber - 1]) {
            mousePress(buzzerNumber);
        }
        handleReset(buzzerNumber);
    }

    /**
     * method performs the mouse click when a buzzer was pressed
     *
     * @param buzzer number of the pressed buzzer (starting with 1)
     */
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

    /**
     * Method handles the reset of the buzzers according to the settings made to
     * the blocking behaviour
     *
     * @param buzzerNumber number of the buzzer pressed (starting with 1)
     */
    private void handleReset(int buzzerNumber) {
        switch (getProgramModel().getSaveFile().getBlockingBehaviour()) {
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
                    getMainController().getControlModel().getBuzzerControl().unblockBuzzer(1);
                    getMainController().getControlModel().getBuzzerControl().unblockBuzzer(2);
                    getMainController().getControlModel().getBuzzerControl().unblockBuzzer(3);

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
