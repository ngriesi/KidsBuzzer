package programs.keyPresser.control.view;

import assets.control.BlockingBehaviourRow;
import assets.control.BuzzerKeyPressRow;
import programs.abstractProgram.ProgramView;
import programs.keyPresser.control.controller.KeyPressController;
import savedataHandler.SaveDataHandler;

import java.awt.event.ActionListener;

import static savedataHandler.SaveDataHandler.BUZZER_NAMES;

/**
 * Control view of the <code>KeyPressProgram</code>
 */
public class KeyPressView extends ProgramView {

    /**
     * rows to change the key bindings of a buzzer
     */
    private BuzzerKeyPressRow[] positionRows;

    /**
     * row to change the blocking behaviour of the buzzers
     */
    private BlockingBehaviourRow blockingBehaviourRow;

    /**
     * creates a new view
     *
     * @param programController sets the controller of the view
     */
    public KeyPressView(KeyPressController programController) {
        super(programController);
        createLayout(programController);
    }

    /**
     * Method creates the view
     *
     * @param actionListener action listener for all components in the view
     */
    private void createLayout(ActionListener actionListener) {
        createKeyRows(actionListener);
        createBlockingBehaviourRow(actionListener);
    }

    /**
     * Method creates <code>BuzzerKeyPressRows</code> used to set the keys that are
     * bound to the buzzers
     *
     * @param actionListener action listener for all components in the view
     */
    private void createKeyRows(ActionListener actionListener) {
        positionRows = new BuzzerKeyPressRow[SaveDataHandler.BUZZER_COUNT];
        for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
            positionRows[i] = new BuzzerKeyPressRow(BUZZER_NAMES[i], actionListener);
            this.addComponent(this, positionRows[i], 0, i, 1, 1);

        }
    }

    /**
     * creates the row to set the blocking behavior of the buzzers
     *
     * @param actionListener action listener for all components in the view
     */
    private void createBlockingBehaviourRow(ActionListener actionListener) {
        blockingBehaviourRow = new BlockingBehaviourRow(actionListener);
        this.addComponent(this, blockingBehaviourRow, 0, SaveDataHandler.BUZZER_COUNT + 1, 1, 1);
    }

    /**
     * getter to access or update the values of the <code>BuzzerKeyPressRows</code>
     *
     * @param i index of the row (starting with 0)
     * @return the <code>BuzzerKeyPressRow</code> with the given index
     */
    public BuzzerKeyPressRow getKeyPressRow(int i) {
        return positionRows[i];
    }


    /**
     * getter to access or update the values of the <code>BlockingBehaviourRow</code>
     *
     * @return the <code>BlockingBehaviourRow</code> of this view
     */
    public BlockingBehaviourRow getBlockingBehaviourRow() {
        return blockingBehaviourRow;
    }
}
