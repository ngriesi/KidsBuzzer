package programs.mouseClicker.control.view;

import assets.control.BlockingBehaviourRow;
import assets.control.BuzzerClickPositionRow;
import assets.control.DisplayMouseTrackerRow;
import programs.abstractProgram.ProgramView;
import programs.mouseClicker.control.controller.MouseClickerProgramController;

import java.awt.event.ActionListener;

import static savedataHandler.SaveDataHandler.BUZZER_NAMES;

/**
 * Control view of the <code>MouseClickerProgram</code>
 */
public class MouseClickerProgramControlView extends ProgramView {

    /**
     * row to show/hide the mouse tracker window
     */
    private DisplayMouseTrackerRow displayMouseTrackerRow;

    /**
     * rows to change the mouse click positions bindings of a buzzer
     */
    private BuzzerClickPositionRow[] positionRows;

    /**
     * row to change the blocking behaviour of the buzzers
     */
    private BlockingBehaviourRow blockingBehaviourRow;

    /**
     * creates a new view
     *
     * @param programController sets the controller of the view
     */
    public MouseClickerProgramControlView(MouseClickerProgramController programController) {
        super(programController);
        createLayout(programController);
    }

    /**
     * Method creates the view
     *
     * @param actionListener action listener for all components in the view
     */
    private void createLayout(ActionListener actionListener) {
        createDisplayTrackerRow(actionListener);
        createPositionRows(actionListener);
        createBlockingBehaviourRow(actionListener);
    }

    /**
     * creates the row used to show and hide the mouse tracker window
     *
     * @param actionListener action listener for all components in the view
     */
    private void createDisplayTrackerRow(ActionListener actionListener) {
        displayMouseTrackerRow = new DisplayMouseTrackerRow(actionListener);
        this.addComponent(this, displayMouseTrackerRow, 0, 0, 1, 1);
    }

    /**
     * Method creates <code>BuzzerClickPositionRow</code> used to set the click positions that are
     * bound to the buzzers
     *
     * @param actionListener action listener for all components in the view
     */
    private void createPositionRows(ActionListener actionListener) {
        positionRows = new BuzzerClickPositionRow[3];
        for (int i = 0; i < BUZZER_NAMES.length; i++) {
            positionRows[i] = new BuzzerClickPositionRow(BUZZER_NAMES[i], actionListener);
            this.addComponent(this, positionRows[i], 0, 1 + i, 1, 1);

        }
    }

    /**
     * creates the row to set the blocking behavior of the buzzers
     *
     * @param actionListener action listener for all components in the view
     */
    private void createBlockingBehaviourRow(ActionListener actionListener) {
        blockingBehaviourRow = new BlockingBehaviourRow(actionListener);
        this.addComponent(this, blockingBehaviourRow, 0, 4, 1, 1);
    }

    /**
     * getter to access or update the value of the <code>DisplayMouseTrackerRow</code>
     *
     * @return returns the <code>DisplayMouseTrackerRow</code>
     */
    public DisplayMouseTrackerRow getDisplayMouseTrackerRow() {
        return displayMouseTrackerRow;
    }

    /**
     * getter to access or update the values of the <code>BuzzerClickPositionRow</code>
     *
     * @param i index of the row (starting with 0)
     * @return the <code>BuzzerClickPositionRow</code> with the given index
     */
    public BuzzerClickPositionRow getPositionRow(int i) {
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
