package programs.mouseClicker.control.controller;

import assets.combobox.MyComboBox;
import assets.standardAssets.MyCheckBox;
import programs.abstractProgram.ProgramController;
import programs.mouseClicker.control.MouseTrackerWindow;
import assets.control.BlockingBehaviourRow;
import assets.control.BuzzerClickPositionRow;
import assets.control.DisplayMouseTrackerRow;
import programs.mouseClicker.control.view.MouseClickerProgramControlView;
import programs.mouseClicker.data.MouseClickerProgramModel;
import programs.mouseClicker.main.MouseClickerProgram;
import savedataHandler.SaveDataHandler;

import java.awt.event.ActionEvent;

/**
 * Class represents the controller of the control view of the mouse clicker program
 */
public class MouseClickerProgramController extends ProgramController<MouseClickerProgram, MouseClickerProgramControlView, MouseClickerProgramModel> {

    /**
     * enum to identify a coordinate direction
     */
    enum Direction {X, Y}

    /**
     * mouse tracker window used to display the current mouse position
     */
    private MouseTrackerWindow mouseTrackerWindow;

    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public MouseClickerProgramController(MouseClickerProgram program, MouseClickerProgramModel programModel) {
        super(program, programModel);
        mouseTrackerWindow = new MouseTrackerWindow();
        getProgram().addClosedAction(() -> mouseTrackerWindow.setWindowVisibility(false));

    }

    /**
     * creates the view of this controller
     *
     * @return returns the newly created view of this controller
     */
    @Override
    protected MouseClickerProgramControlView createView() {
        return new MouseClickerProgramControlView(this);
    }

    /**
     * Action Performed method for all the components in the control view.
     * <p>
     * Possible action commands:
     * BLOCKING_BEHAVIOUR:(followed by further specification)
     * sets the blocking behaviour of the buzzers for this program, handled in
     * the <code>handleBlockingBehaviourActions</code> method
     * <p>
     * CLICK_POSITION:(followed by further specification)
     * sets the click position for a buzzer, handled by the
     * <code>handleClickPositionActions</code> method
     * <p>
     * MOUSE_TRACKER
     * makes the <code>MouseTrackerWindow</code> invisible/visible
     *
     * @param e <code>ActionEvent</code> created by the view component
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand().split(":")[0]) {
            case BlockingBehaviourRow.BLOCKING_BEHAVIOUR:
                handleBlockingBehaviourActions(e);
                break;
            case BuzzerClickPositionRow.CLICK_POSITION:
                handleClickPositionActions(e);
                break;
            case DisplayMouseTrackerRow.MOUSE_TRACKER:
                mouseTrackerButtonAction(((MyCheckBox) e.getSource()).isSelected());
                break;
        }
    }

    /**
     * Method handles a change to the click positions of the buzzers. The method is called
     * from the <code>actionPerformed</code> method of this class and updates the values
     * of the click positions and the activation assigned to the buzzers
     * <p>
     * Possible action commands:
     * CLICK_POSITION:USE
     * Deactivates or activates the use of a click binding,
     * handled in <code>useBuzzerClick</code>
     * <p>
     * CLICK_POSITION:(X_CORD or Y_CORD)
     * Changes the click position, handled in the
     * <code>changeCords</code> method
     *
     * @param e <code>ActionEvent</code> created by the view component
     */
    private void handleClickPositionActions(ActionEvent e) {
        if (e.getActionCommand().split(":")[1].equals(BuzzerClickPositionRow.USE)) {
            useBuzzerClick(getBuzzerNum(e.getActionCommand().split(":")[2]), ((MyCheckBox) e.getSource()).isSelected());
        } else {
            changeCords(e.getActionCommand());
        }
    }

    /**
     * Changes the cords of a click position. It retrieves the direction of
     * the coordinate (X or Y axis) and the buzzer that the change is linked to
     * from the action command which has the form:
     * CLICK_POSITION:(X_CORD or Y_CORD):(buzzer name form SaveDataHandler.BUZZER_NAMES)
     * To perform the change it calls <code>cordsChanged</code>
     *
     * @param actionCommand action commands used to identify the buzzer and the axis
     *                      of the change
     */
    private void changeCords(String actionCommand) {
        Direction direction = getDirection(actionCommand.split(":")[1]);
        int buzzer = getBuzzerNum(actionCommand.split(":")[2]);
        cordsChanged(direction, buzzer);
    }

    /**
     * method gets the number of a buzzer from its name.
     *
     * @param s name of the buzzer
     * @return index of the buzzer (starting with 1)
     */
    private int getBuzzerNum(String s) {
        for (int i = 0; i < SaveDataHandler.BUZZER_NAMES.length; i++) {
            if (s.equals(SaveDataHandler.BUZZER_NAMES[i])) {
                return i + 1;
            }
        }
        return 0;
    }

    /**
     * Converts the direction or axis from the action command that is in form
     * of a string into an <code>Direction</code> enum
     *
     * @param s direction as a string
     * @return direction as an enum
     */
    private Direction getDirection(String s) {
        return s.equals(BuzzerClickPositionRow.X_CORD) ? Direction.X : Direction.Y;
    }

    /**
     * Method handles the change of the blocking behaviour of the buzzers.
     * The action command is used to identify the change:
     * <p>
     * Possible action commands:
     * BLOCKING_BEHAVIOUR:RESET
     * calls the <code>resetButtonAction</code> method to reset the buzzers
     * <p>
     * BLOCKING_BEHAVIOUR:BLOCK_TIME
     * changes the time the buzzers get blocked when the main selector sits
     * at the option "Block for Time" by calling <code>saveBlockTime</code>
     * <p>
     * BLOCKING_BEHAVIOUR:BUZZER_SELECTOR
     * changes the buzzer that is selected for unblocking in the main selector
     * option "Unblock with Buzzer"
     * <p>
     * BLOCKING_BEHAVIOUR:MAIN_SELECTOR
     * main selector for the blocking behaviour. Class <code>mainBlockingBehaviourSelectorAction</code>
     * to change the selection
     *
     * @param e <code>ActionEvent</code> created by the view component
     */
    private void handleBlockingBehaviourActions(ActionEvent e) {
        switch (e.getActionCommand().split(":")[1]) {
            case BlockingBehaviourRow.RESET:
                resetButtonAction();
                break;
            case BlockingBehaviourRow.BLOCK_TIME:
                saveBlockTime();
                break;
            case BlockingBehaviourRow.BUZZER_SELECTOR:
                buzzerSelectorAction(e.getActionCommand().split(":")[2]);
                break;
            case BlockingBehaviourRow.MAIN_SELECTOR:
                mainBlockingBehaviourSelectorAction(((MyComboBox) e.getSource()).getSelectedIndex());
        }
    }

    /**
     * Action performed when an item of the main blocking behaviour selector of the
     * <code>BlockingBehaviourRow</code> gets clicked.
     *
     * @param item name of the item from the selector that was selected
     */
    private void mainBlockingBehaviourSelectorAction(int item) {
        getProgramModel().getSaveFile().setBlockingBehaviour(item);
        if (getProgram().getMainController() != null)
            getProgram().getMainController().getControlModel().getView().getMyJFrame().getFrame().setVisible(true);
    }

    /**
     * sets the block time for buzzers in the save file
     */
    private void saveBlockTime() {
        getProgramModel().getSaveFile().setBlockingTime(getProgramView().getBlockingBehaviourRow().getBlockTime());
    }

    /**
     * sets the buzzer used for unblocking in the save file
     *
     * @param s name of the buzzer, used with <code>getBuzzerNum</code> to identify the buzzer
     */
    private void buzzerSelectorAction(String s) {
        getProgramModel().getSaveFile().setUnblockBuzzer(getBuzzerNum(s));
    }

    /**
     * action of the reset button that is displayed in the <code>BlockingBehaviourRow</code> if
     * the main selector if this row stands on "Block until Released".
     * Unblocks all buzzers in <code>BuzzerModel</code>
     *
     * @see buzzerHandler.BuzzerModel
     */
    private void resetButtonAction() {
        getProgram().getMainController().getControlModel().getBuzzerControl().unblockBuzzer(1);
        getProgram().getMainController().getControlModel().getBuzzerControl().unblockBuzzer(2);
        getProgram().getMainController().getControlModel().getBuzzerControl().unblockBuzzer(3);
    }

    /**
     * Updates the view (at startup or when a settings was changed by the program and not by the user)
     */
    @Override
    protected void updateView() {
        getProgramView().getDisplayMouseTrackerRow().setValue(getProgramModel().getSaveFile().isDisplayMouseTracker());
        updatePositionRows();
        updateBlockingBehaviourRow();
    }

    /**
     * updates the <code>BlockingBehaviourRow</code> of the view by setting the selection of the
     * main selector, the content of the block time text field and the unblock buzzer
     */
    private void updateBlockingBehaviourRow() {
        getProgramView().getBlockingBehaviourRow().setMainSelectorItem(getProgramModel().getSaveFile().getBlockingBehaviour());
        getProgramView().getBlockingBehaviourRow().setBlockTime(getProgramModel().getSaveFile().getBlockingTime());
        getProgramView().getBlockingBehaviourRow().setUnblockBuzzer(getProgramModel().getSaveFile().getUnblockBuzzer());
    }

    /**
     * updates the mouse clicked rows of the view by setting the positions to the text fields
     * and the value of the activation button
     */
    private void updatePositionRows() {
        for (int i = 0; i < SaveDataHandler.BUZZER_NAMES.length; i++) {
            getProgramView().getPositionRow(i).setXCord(getProgramModel().getSaveFile().getClickX()[i]);
            getProgramView().getPositionRow(i).setYCord(getProgramModel().getSaveFile().getClickY()[i]);
            getProgramView().getPositionRow(i).setActive(getProgramModel().getSaveFile().getUseClick()[i]);
        }
    }

    /**
     * action performed when the mouse tracker button is pressed
     *
     * @param selected state of the mouse tracker button
     */
    public void mouseTrackerButtonAction(boolean selected) {
        getProgramModel().getSaveFile().setDisplayMouseTracker(selected);
        mouseTrackerWindow.setWindowVisibility(selected);
    }

    /**
     * Uses the passed direction to call the method to update the cords in the save file form
     * the content of the view
     *
     * @param i            direction (axis) of the cords
     * @param buzzerNumber number of the buzzer of which the click position gets changed
     */
    private void cordsChanged(Direction i, int buzzerNumber) {
        if (i == Direction.X) {
            changeXCords(buzzerNumber);
        } else {
            changeYCords(buzzerNumber);
        }
    }

    /**
     * Updates the x coordinates of the click position of the buzzer with
     * the passed index
     *
     * @param buzzerNumber number of the buzzer of which the click position gets changed
     */
    private void changeXCords(int buzzerNumber) {
        getProgramModel().getSaveFile().getClickX()[buzzerNumber - 1] = (getProgramView().getPositionRow(buzzerNumber - 1).getXCord());
    }

    /**
     * Updates the y coordinates of the click position of the buzzer with
     * the passed index
     *
     * @param buzzerNumber number of the buzzer of which the click position gets changed
     */
    private void changeYCords(int buzzerNumber) {
        getProgramModel().getSaveFile().getClickY()[buzzerNumber - 1] = (getProgramView().getPositionRow(buzzerNumber - 1).getYCord());
    }

    /**
     * Method updates the flag indicating whether the press of the buzzer with the number
     * <code>buzzerNumber</code> should lead to an action or not.
     *
     * @param buzzerNumber number of the buzzer of which the active value gets changed
     * @param selected     new value for the field
     */
    private void useBuzzerClick(int buzzerNumber, boolean selected) {
        getProgramModel().getSaveFile().getUseClick()[buzzerNumber - 1] = selected;
    }

}
