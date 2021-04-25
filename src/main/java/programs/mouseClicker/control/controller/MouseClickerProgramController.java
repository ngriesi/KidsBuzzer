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

public class MouseClickerProgramController extends ProgramController<MouseClickerProgram, MouseClickerProgramControlView, MouseClickerProgramModel> {

    enum Direction {X,Y}

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

    @Override
    protected MouseClickerProgramControlView createView() {
        return new MouseClickerProgramControlView(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand().split(":")[0]) {
            case BlockingBehaviourRow.BLOCKING_BEHAVIOUR: handleBlockingBehaviourActions(e); break;
            case BuzzerClickPositionRow.CLICK_POSITION: handleClickPositionActions(e); break;
            case DisplayMouseTrackerRow.MOUSE_TRACKER: mouseTrackerButtonAction(((MyCheckBox)e.getSource()).isSelected()); break;
        }
    }

    private void handleClickPositionActions(ActionEvent e) {
        if (e.getActionCommand().split(":")[1].equals(BuzzerClickPositionRow.USE)) {
            useBuzzerClick(getBuzzerNum(e.getActionCommand().split(":")[2]),((MyCheckBox)e.getSource()).isSelected());
        } else {
            changeCords(e.getActionCommand());
        }
    }

    private void changeCords(String actionCommand) {
        Direction direction = getDirection(actionCommand.split(":")[1]);
        int buzzer = getBuzzerNum(actionCommand.split(":")[2]);
        cordsChanged(direction,buzzer);
    }

    private int getBuzzerNum(String s) {
        for (int i = 0; i < SaveDataHandler.BUZZER_NAMES.length; i++) {
            if (s.equals(SaveDataHandler.BUZZER_NAMES[i])) {
                return i + 1;
            }
        }
        return 0;
    }

    private Direction getDirection(String s) {
        return s.equals(BuzzerClickPositionRow.X_CORD)?Direction.X:Direction.Y;
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

        getProgramView().getDisplayMouseTrackerRow().setValue(getProgramModel().getSaveFile().isDisplayMouseTracker());
        updatePositionRows();
        updateBlockingBehaviourRow();
    }

    private void updateBlockingBehaviourRow() {
        getProgramView().getBlockingBehaviourRow().setMainSelectorItem(getProgramModel().getSaveFile().getBlockingBehaviour());
        getProgramView().getBlockingBehaviourRow().setBlockTime(getProgramModel().getSaveFile().getBlockingTime());
        getProgramView().getBlockingBehaviourRow().setUnblockBuzzer(getProgramModel().getSaveFile().getUnblockBuzzer());
    }

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

    private void cordsChanged(Direction i, int buzzerNumber) {
        if (i == Direction.X) {
            changeXCords(buzzerNumber);
        } else {
            changeYCords(buzzerNumber);
        }
    }

    private void changeXCords(int buzzerNumber) {
        getProgramModel().getSaveFile().getClickX()[buzzerNumber - 1] = (getProgramView().getPositionRow(buzzerNumber - 1).getXCord());
    }

    private void changeYCords(int buzzerNumber) {
        getProgramModel().getSaveFile().getClickY()[buzzerNumber - 1] = (getProgramView().getPositionRow(buzzerNumber - 1).getYCord());
    }

    private void useBuzzerClick(int buzzerNumber, boolean selected) {
        getProgramModel().getSaveFile().getUseClick()[buzzerNumber - 1] = selected;
    }

}
