package programs.scoreBoard.control;

import assets.standardAssets.MyTextField;
import programs.abstractProgram.ProgramController;
import programs.scoreBoard.data.ScoreBoardModel;
import programs.scoreBoard.main.ScoreBoardProgram;
import savedataHandler.SaveDataHandler;

import java.awt.event.ActionEvent;

import static programs.scoreBoard.data.ScoreBoardModel.TEAM_NAMES;

/**
 * controller for the control view of the score board program
 */
public class ScoreBoardControlController extends ProgramController<ScoreBoardProgram, ScoreBoardControlView, ScoreBoardModel> {

    /**
     * blocks the text change action of the <code>TextFields</code> of the view to prevent
     * the calling of the action when the content of the text field updated by something
     * else that the user
     */
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

    /**
     * creates the view of the controller
     *
     * @return returns a newly created <code>ScoreBoardControlView</code>
     */
    @Override
    protected ScoreBoardControlView createView() {
        return new ScoreBoardControlView(this);
    }

    /**
     * updates the team names in the view
     */
    @Override
    protected void updateView() {
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            getProgramView().getTeamNames()[i].setText(getProgramModel().getSaveFile().getString(TEAM_NAMES + i, "Team " + (i + 1)));
        }
    }

    /**
     * action performed method called when an action occurs on any of the components
     * of the view. The actions are identified using the acton command of the
     * <code>ActionEvent</code>
     *
     * @param e <code>ActionEvent</code> created by the component
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().startsWith("score")) {
            if (!blockTextChangeAction) {
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

    /**
     * handles the action that the user manually changed the score of one team
     *
     * @param index index of the team
     * @param value new value for the score
     */
    private void handleScoreChange(int index, int value) {
        getProgram().getMainScoreBoardController().setBuzzerScore(index + 1, value);
    }

    /**
     * updates all the score input fields
     */
    public void updateScores() {
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            blockTextChangeAction = true;
            getProgramView().getScore()[i].setText("" + getProgramModel().getScores()[i]);
        }
    }

    /**
     * native key action, called when a key gets released and the native key listener is
     * activated
     *
     * @param keyCode code of the released key
     */
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

    /**
     * updates the names of the teams in the control view
     */
    public void updateNames() {
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            getProgramView().getTeamNames()[i].setText(getProgramModel().getSaveFile().getString(TEAM_NAMES + i));
        }
    }
}
