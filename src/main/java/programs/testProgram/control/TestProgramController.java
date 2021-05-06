package programs.testProgram.control;

import programs.abstractProgram.ProgramController;
import programs.testProgram.main.TestProgram;
import programs.testProgram.data.TestProgramModel;

import java.awt.event.ActionEvent;

/**
 * controller for the control view of the text program
 */
public class TestProgramController extends ProgramController<TestProgram, TestProgramControlView, TestProgramModel> {

    /**
     * creates a new controller
     *
     * @param testProgram      program this controller belongs to
     * @param testProgramModel model of this program
     */
    public TestProgramController(TestProgram testProgram, TestProgramModel testProgramModel) {
        super(testProgram, testProgramModel);
    }

    /**
     * creates the view of the controller
     *
     * @return returns a newly created <code>TestProgramControlView</code>
     */
    @Override
    protected TestProgramControlView createView() {
        return new TestProgramControlView(this);
    }

    @Override
    protected void updateView() {

    }

    /**
     * Action performed method that gets called for every action that happens on the view
     * of this controller
     *
     * @param e <code>ActionEvent</code> created by the component
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "settings":
                getProgram().setView(getProgram().getSettingsController().getProgramView());
                break;
            case "show":
                if (getProgram().getMainController().isShowingPresentation()) {
                    getProgram().getMainController().hidePresentationWindow();
                } else {
                    getProgram().getMainController().showPresentationWindow();
                }
                break;
            case "move":
                getProgram().moveQuad();
        }
    }
}
