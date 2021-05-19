package programs.keyPresser.control.controller;

import assets.combobox.MyComboBox;
import assets.control.BlockingBehaviourRow;
import assets.control.BuzzerKeyPressRow;
import assets.standardAssets.MyCheckBox;
import programs.abstractProgram.ProgramController;
import programs.keyPresser.control.view.KeyPressView;
import programs.keyPresser.data.KeyPressModel;
import programs.keyPresser.main.KeyPressProgram;
import savedataHandler.SaveDataHandler;

import java.awt.event.ActionEvent;

/**
 * Class represents the controller of the control view of the key press program
 */
public class KeyPressController extends ProgramController<KeyPressProgram, KeyPressView, KeyPressModel> {

    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public KeyPressController(KeyPressProgram program, KeyPressModel programModel) {
        super(program, programModel);

    }

    /**
     * creates the view of this controller
     *
     * @return returns the newly created view of this controller
     */
    @Override
    protected KeyPressView createView() {
        return new KeyPressView(this);
    }

    /**
     * Action Performed method for all the components in the control view.
     * <p>
     * Possible action commands:
     * BLOCKING_BEHAVIOUR:(followed by further specification)
     * sets the blocking behaviour of the buzzers for this program, handled in
     * the <code>handleBlockingBehaviourActions</code> method
     * <p>
     * PRESS_KEY:(followed by further specification)
     * sets a key binding of a buzzer, handled in the
     * <code>handleKeyChange</code> method
     *
     * @param e <code>ActionEvent</code> created by the view component
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand().split(":")[0]) {
            case BlockingBehaviourRow.BLOCKING_BEHAVIOUR:
                handleBlockingBehaviourActions(e);
                break;
            case BuzzerKeyPressRow.PRESS_KEY:
                handleKeyChange(e);
                break;
        }
    }

    /**
     * Method handles a change to the key bindings of the buzzers. The method is called
     * from the <code>actionPerformed</code> method of this class and updates the values
     * of the keys assigned to the buzzers
     * <p>
     * Possible action commands:
     * PRESS_KEY:USE
     * Deactivates or activates the use of a key binding
     * <p>
     * PRESS_KEY:KEY
     * Changes the key binding to another key, handled in the
     * <code>changeKey</code> method
     *
     * @param e <code>ActionEvent</code> created by the view component
     */
    private void handleKeyChange(ActionEvent e) {
        if (e.getActionCommand().split(":")[1].equals(BuzzerKeyPressRow.USE)) {
            useKeyPress(getBuzzerNum(e.getActionCommand().split(":")[2]), ((MyCheckBox) e.getSource()).isSelected());
        } else {
            changeKey(e.getActionCommand());
        }
    }

    /**
     * Changes the key that is bound to a buzzer. The method uses the passed action command
     * (which has the form: PRESS_KEY:KEY:(name of the buzzer)).
     * <p>
     * It calls <code>changeKey</code> to change to key binding
     *
     * @param actionCommand action command used to identify the buzzer
     */
    private void changeKey(String actionCommand) {
        int buzzer = getBuzzerNum(actionCommand.split(":")[2]);
        changeKey(buzzer);
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
        updateKeyPressRows();
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
     * updates the key pressed rows of the view by setting the char of the bound key to the text field
     * and the value of the activation button
     */
    private void updateKeyPressRows() {
        for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
            getProgramView().getKeyPressRow(i).setKey(getProgramModel().getSaveFile().getKey()[i]);
            getProgramView().getKeyPressRow(i).setActive(getProgramModel().getSaveFile().getUseKey()[i]);
        }
    }

    /**
     * Method changes the key bound to a buzzer by changing the value in the save file class of
     * this program. It retrieves the value from the <code>TextField</code> of the <code>BuzzerKeyPressRow</code>
     *
     * @param buzzerNumber number of the buzzer of which the key binding has changed
     */
    private void changeKey(int buzzerNumber) {
        getProgramModel().getSaveFile().getKey()[buzzerNumber - 1] = (getProgramView().getKeyPressRow(buzzerNumber - 1).getKey());
    }

    /**
     * Method updates the flag indicating whether the press of the buzzer with the number
     * <code>buzzerNumber</code> should lead to an action or not.
     *
     * @param buzzerNumber number of the buzzer of which the active value gets changed
     * @param selected     new value for the field
     */
    private void useKeyPress(int buzzerNumber, boolean selected) {
        getProgramModel().getSaveFile().getUseKey()[buzzerNumber - 1] = selected;
    }

}
