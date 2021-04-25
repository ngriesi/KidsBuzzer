package programs.keyPresser.control.view;

import assets.control.BlockingBehaviourRow;
import assets.control.BuzzerKeyPressRow;
import assets.standardAssets.StandardAssetFields;
import programs.abstractProgram.ProgramView;
import programs.keyPresser.control.controller.KeyPressController;
import savedataHandler.SaveDataHandler;

import java.awt.event.ActionListener;

import static savedataHandler.SaveDataHandler.BUZZER_NAMES;

public class KeyPressView extends ProgramView {

    private BuzzerKeyPressRow[] positionRows;

    private BlockingBehaviourRow blockingBehaviourRow;

    /**
     * creates a new view
     *
     * @param programController sets the controller of the view
     */
    public KeyPressView(KeyPressController programController) {
        super(programController);

        this.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        createLayout(programController);
    }

    private void createLayout(ActionListener actionListener) {
        createKeyRows(actionListener);
        createBlockingBehaviourRow(actionListener);
    }

    private void createKeyRows(ActionListener actionListener) {
        positionRows = new BuzzerKeyPressRow[SaveDataHandler.BUZZER_COUNT];
        for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
            positionRows[i] = new BuzzerKeyPressRow(BUZZER_NAMES[i],actionListener);
            this.addComponent(this, positionRows[i], 0, i, 1, 1);

        }
    }

    private void createBlockingBehaviourRow(ActionListener actionListener) {
        blockingBehaviourRow = new BlockingBehaviourRow(actionListener);
        this.addComponent(this, blockingBehaviourRow, 0, SaveDataHandler.BUZZER_COUNT + 1, 1, 1);
    }

    public BuzzerKeyPressRow getPositionRow(int i) {
        return positionRows[i];
    }


    public BlockingBehaviourRow getBlockingBehaviourRow() {
        return blockingBehaviourRow;
    }
}
