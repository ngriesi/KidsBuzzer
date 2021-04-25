package programs.mouseClicker.control.view;

import assets.control.BlockingBehaviourRow;
import assets.control.BuzzerClickPositionRow;
import assets.control.DisplayMouseTrackerRow;
import assets.standardAssets.StandardAssetFields;
import programs.abstractProgram.ProgramView;
import programs.mouseClicker.control.controller.MouseClickerProgramController;

import java.awt.event.ActionListener;

import static savedataHandler.SaveDataHandler.BUZZER_NAMES;

public class MouseClickerProgramControlView extends ProgramView {

    private DisplayMouseTrackerRow displayMouseTrackerRow;

    private BuzzerClickPositionRow[] positionRows;

    private BlockingBehaviourRow blockingBehaviourRow;

    /**
     * creates a new view
     *
     * @param programController sets the controller of the view
     */
    public MouseClickerProgramControlView(MouseClickerProgramController programController) {
        super(programController);

        this.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        createLayout(programController);
    }

    private void createLayout(ActionListener actionListener) {
        createDisplayTrackerRow(actionListener);
        createPositionRows(actionListener);
        createBlockingBehaviourRow(actionListener);
    }

    private void createDisplayTrackerRow(ActionListener actionListener) {
        displayMouseTrackerRow = new DisplayMouseTrackerRow(actionListener);
        this.addComponent(this, displayMouseTrackerRow, 0, 0, 1, 1);
    }

    private void createPositionRows(ActionListener actionListener) {
        positionRows = new BuzzerClickPositionRow[3];
        for (int i = 0; i < BUZZER_NAMES.length; i++) {
            positionRows[i] = new BuzzerClickPositionRow(BUZZER_NAMES[i],actionListener);
            this.addComponent(this, positionRows[i], 0, 1 + i, 1, 1);

        }
    }

    private void createBlockingBehaviourRow(ActionListener actionListener) {
        blockingBehaviourRow = new BlockingBehaviourRow(actionListener);
        this.addComponent(this, blockingBehaviourRow, 0, 4, 1, 1);
    }

    public DisplayMouseTrackerRow getDisplayMouseTrackerRow() {
        return displayMouseTrackerRow;
    }

    public BuzzerClickPositionRow getPositionRow(int i) {
        return positionRows[i];
    }


    public BlockingBehaviourRow getBlockingBehaviourRow() {
        return blockingBehaviourRow;
    }
}
