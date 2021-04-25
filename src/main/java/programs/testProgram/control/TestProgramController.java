package programs.testProgram.control;

import programs.abstractProgram.ProgramController;
import programs.testProgram.main.TestProgram;
import programs.testProgram.data.TestProgramModel;

import java.awt.event.ActionEvent;

public class TestProgramController extends ProgramController<TestProgram,TestProgramControlView,TestProgramModel> {


    public TestProgramController(TestProgram testProgram, TestProgramModel testProgramModel) {
        super(testProgram,testProgramModel);
    }

    @Override
    protected TestProgramControlView createView() {
        return new TestProgramControlView(this);
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "settings":
                getProgram().setView(getProgram().getSettingsController().getProgramView());
                break;
            case "show":
                if (getProgram().getControlModel().isShowingPresentation()) {
                    getProgram().getControlModel().hidePresentationWindow();
                } else {
                    getProgram().getControlModel().showPresentationWindow();
                }
                break;
            case "move":
                getProgram().moveQuad();
        }
    }
}
