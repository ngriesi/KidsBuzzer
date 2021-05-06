package programs.testProgram.settings;

import programs.abstractProgram.ProgramController;
import programs.testProgram.main.TestProgram;
import programs.testProgram.data.TestProgramModel;

import java.awt.event.ActionEvent;

/**
 * controller of the settings of the test program
 */
public class TestProgramSettingsController extends ProgramController<TestProgram, TestProgramSettingsView, TestProgramModel> {

    /**
     * creates a new controller
     *
     * @param testProgram      program this controller belongs to
     * @param testProgramModel model of this program
     */
    public TestProgramSettingsController(TestProgram testProgram, TestProgramModel testProgramModel) {
        super(testProgram, testProgramModel);
    }

    /**
     * creates the view of the controller
     *
     * @return returns a newly created <code>TestProgramSettingsView</code>
     */
    @Override
    protected TestProgramSettingsView createView() {
        return new TestProgramSettingsView(this);
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
        if ("back".equals(e.getActionCommand())) {
            getProgram().setView(getProgram().getProgramController().getProgramView());
            getProgram().getProgramModel().getSaveFile().saveFile();
        }
    }
}
