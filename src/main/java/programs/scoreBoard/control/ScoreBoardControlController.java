package programs.scoreBoard.control;

import assets.standardAssets.MyTextField;
import programs.abstractProgram.ProgramController;
import programs.scoreBoard.data.ScoreBoardModel;
import programs.scoreBoard.main.ScoreBoardProgram;
import savedataHandler.SaveDataHandler;

import java.awt.event.ActionEvent;

public class ScoreBoardControlController extends ProgramController<ScoreBoardProgram,ScoreBoardControlView, ScoreBoardModel> {


    private boolean blockTextChangeAction = false;

    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public ScoreBoardControlController(ScoreBoardProgram program, ScoreBoardModel programModel) {
        super(program, programModel);
    }

    @Override
    protected ScoreBoardControlView createView() {
        return new ScoreBoardControlView(this);
    }

    @Override
    protected void updateView() {
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            getProgramView().getTeamNames()[i].setText(getProgramModel().getSaveFile().getTeamNames()[i]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().startsWith("score")) {
            if(!blockTextChangeAction) {
                handleScoreChange(Integer.parseInt(e.getActionCommand().substring(5)), Integer.parseInt(((MyTextField) e.getSource()).getText()));
            } else {
                blockTextChangeAction = false;
            }
        } else {

            switch (e.getActionCommand()) {
                case "show":
                    getProgram().getMainScoreBoardController().show();
                    break;
                case "hide":
                    getProgram().getMainScoreBoardController().hide();
                    break;
                case "settings":
                    getProgram().setView(getProgram().getSettingsController().getProgramView());
                    break;
            }
        }
    }

    private void handleScoreChange(int index, int value) {
        getProgram().getMainScoreBoardController().setBuzzerScore(index + 1, value);
    }

    public void updateScores() {
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            blockTextChangeAction = true;
            getProgramView().getScore()[i].setText("" + getProgramModel().getScores()[i]);
        }
    }

    public void nativeKeyAction(int keyCode) {
        switch (keyCode) {
            case 30:
                getProgram().getMainScoreBoardController().show();
                break;
            case 47:
                getProgram().getMainScoreBoardController().hide();
                break;
        }
    }
}
