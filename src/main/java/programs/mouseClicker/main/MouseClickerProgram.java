package programs.mouseClicker.main;

import programs.abstractProgram.Program;
import programs.emptyClasses.EmptyPresentationView;
import programs.mouseClicker.control.controller.MouseClickerProgramController;
import programs.mouseClicker.data.MouseClickerProgramModel;
import savedataHandler.languages.Text;

import java.awt.*;
import java.awt.event.InputEvent;

import static programs.mouseClicker.control.controller.MouseClickerProgramController.*;

public class MouseClickerProgram extends Program<MouseClickerProgramController, MouseClickerProgramController, MouseClickerProgramModel, EmptyPresentationView> {

    private float screenScalar;

    /**
     * creates a new program
     */
    public MouseClickerProgram() {
        super(true, Text.MOUSE_CLICKER);

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
        if (getProgramModel().getSaveFile().getBoolean(USE_CLICK + (buzzerNumber - 1))) {
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
            bot.mouseMove((int) (getProgramModel().getSaveFile().getInteger(CLICK_X + (buzzer - 1)) / screenScalar), (int) (getProgramModel().getSaveFile().getInteger(CLICK_Y + (buzzer - 1)) / screenScalar));
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
        switch (getProgramModel().getSaveFile().getInteger(BLOCKING_BEHAVIOUR)) {
            case 0:
                getMainController().getControlModel().getBuzzerControl().unblockBuzzer(buzzerNumber);

                break;
            case 1:

                break;
            case 2:
                new Thread(() -> {
                    try {
                        Thread.sleep(getProgramModel().getSaveFile().getInteger(BLOCKING_TIME));

                        getMainController().getControlModel().getBuzzerControl().unblockBuzzer(buzzerNumber);
                        getMainController().getControlModel().getBuzzerControl().setBlockAllBuzzer(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                break;
            case 3:
                if (buzzerNumber == getProgramModel().getSaveFile().getInteger(UNBLOCK_BUZZER)) {
                    getMainController().getControlModel().getBuzzerControl().unblockBuzzer(1);
                    getMainController().getControlModel().getBuzzerControl().unblockBuzzer(2);
                    getMainController().getControlModel().getBuzzerControl().unblockBuzzer(3);

                }
                break;
        }
    }

    @Override
    public void programSelected() {
        getProgramController().mouseTrackerButtonAction(getProgramModel().getSaveFile().getBoolean(DISPLAY_MOUSE_TRACKER));
    }

    @Override
    public void updateBuzzerCount() {

    }
}
