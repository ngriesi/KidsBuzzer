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

    @Override
    protected KeyPressView createView() {
        return new KeyPressView(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand().split(":")[0]) {
            case BlockingBehaviourRow.BLOCKING_BEHAVIOUR: handleBlockingBehaviourActions(e); break;
            case BuzzerKeyPressRow.PRESS_KEY: handleKeyChange(e); break;
        }
    }

    private void handleKeyChange(ActionEvent e) {
        if (e.getActionCommand().split(":")[1].equals(BuzzerKeyPressRow.USE)) {
            useKeyPress(getBuzzerNum(e.getActionCommand().split(":")[2]),((MyCheckBox)e.getSource()).isSelected());
        } else {
            changeKey(e.getActionCommand());
        }
    }

    private void changeKey(String actionCommand) {
        int buzzer = getBuzzerNum(actionCommand.split(":")[2]);
        changeKey(buzzer);
    }

    private int getBuzzerNum(String s) {
        for (int i = 0; i < SaveDataHandler.BUZZER_NAMES.length; i++) {
            if (s.equals(SaveDataHandler.BUZZER_NAMES[i])) {
                return i + 1;
            }
        }
        return 0;
    }

    private void handleBlockingBehaviourActions(ActionEvent e) {
        switch (e.getActionCommand().split(":")[1]) {
            case BlockingBehaviourRow.RESET: resetButtonAction();break;
            case BlockingBehaviourRow.BLOCK_TIME: saveBlockTime();break;
            case BlockingBehaviourRow.BUZZER_SELECTOR: buzzerSelectorAction(e.getActionCommand().split(":")[2]); break;
            case BlockingBehaviourRow.MAIN_SELECTOR: mainBlockingBehaviourSelectorAction((String)((MyComboBox)e.getSource()).getSelectedItem());
        }
    }

    private void mainBlockingBehaviourSelectorAction(String item) {
        getProgramModel().getSaveFile().setBlockingBehaviour(item);
        if (item != null) getProgramView().getBlockingBehaviourRow().changeBlockingBehaviour(item);
        if (getProgram().getControlModel() != null) getProgram().getControlModel().getView().getMyJFrame().getFrame().setVisible(true);
    }

    private void saveBlockTime() {
        getProgramModel().getSaveFile().setBlockingTime(getProgramView().getBlockingBehaviourRow().getBlockTime());
    }

    private void buzzerSelectorAction(String s) {
        getProgramModel().getSaveFile().setUnblockBuzzer(getBuzzerNum(s));
        getProgramView().getBlockingBehaviourRow().buzzerAction(s);
    }

    private void resetButtonAction() {
        getProgram().getControlModel().getBuzzerControl().unblockBuzzer(1);
        getProgram().getControlModel().getBuzzerControl().unblockBuzzer(2);
        getProgram().getControlModel().getBuzzerControl().unblockBuzzer(3);
    }

    @Override
    protected void updateView() {
        updateKeyPressRows();
        updateBlockingBehaviourRow();
    }

    private void updateBlockingBehaviourRow() {
        getProgramView().getBlockingBehaviourRow().setMainSelectorItem(getProgramModel().getSaveFile().getBlockingBehaviour());
        getProgramView().getBlockingBehaviourRow().setBlockTime(getProgramModel().getSaveFile().getBlockingTime());
        getProgramView().getBlockingBehaviourRow().setUnblockBuzzer(getProgramModel().getSaveFile().getUnblockBuzzer());
    }

    private void updateKeyPressRows() {
        for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
            getProgramView().getPositionRow(i).setKey(getProgramModel().getSaveFile().getKey()[i]);
            getProgramView().getPositionRow(i).setActive(getProgramModel().getSaveFile().getUseClick()[i]);
        }
    }


    private void changeKey(int buzzerNumber) {
        getProgramModel().getSaveFile().getKey()[buzzerNumber - 1] = (getProgramView().getPositionRow(buzzerNumber - 1).getKey());
    }

    private void useKeyPress(int buzzerNumber, boolean selected) {
        getProgramModel().getSaveFile().getUseClick()[buzzerNumber - 1] = selected;
    }

}
