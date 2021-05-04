package programs.emptyClasses;

import programs.abstractProgram.Program;
import programs.abstractProgram.ProgramController;
import programs.abstractProgram.ProgramModel;
import programs.abstractProgram.ProgramView;

import java.awt.event.ActionEvent;

/**
 * empty controller class for unused generics
 */
public class EmptyController extends ProgramController<Program,ProgramView,ProgramModel> {

    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public EmptyController(Program program, ProgramModel programModel) {
        super(program, programModel);
    }

    @Override
    protected ProgramView createView() {
        return new EmptyView(this);
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
