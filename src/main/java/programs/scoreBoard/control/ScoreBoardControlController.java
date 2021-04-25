package programs.scoreBoard.control;

import programs.abstractProgram.ProgramController;
import programs.scoreBoard.data.ScoreBoardModel;
import programs.scoreBoard.main.ScoreBoardProgram;

import java.awt.event.ActionEvent;

public class ScoreBoardControlController extends ProgramController<ScoreBoardProgram,ScoreBoardControlView, ScoreBoardModel> {

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

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "show": getProgram().getMainController().show(); break;
            case "hide": getProgram().getMainController().hide(); break;
            case "settings": getProgram().setView(getProgram().getSettingsController().getProgramView());
        }
    }
}
