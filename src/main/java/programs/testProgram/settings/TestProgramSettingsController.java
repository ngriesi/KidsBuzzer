package programs.testProgram.settings;

import programs.abstractProgram.ProgramController;
import programs.testProgram.main.TestProgram;
import programs.testProgram.data.TestProgramModel;

import java.awt.event.ActionEvent;

public class TestProgramSettingsController extends ProgramController<TestProgram, TestProgramSettingsView, TestProgramModel> {

    public TestProgramSettingsController(TestProgram testProgram, TestProgramModel testProgramModel) {
        super(testProgram,testProgramModel);
    }

    @Override
    protected TestProgramSettingsView createView() {
        return new TestProgramSettingsView(this);
    }

    @Override
    protected void updateView() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "back":
                getProgram().setView(getProgram().getProgramController().getProgramView());
                getProgram().getProgramModel().getSaveFile().saveFile();
                break;
        }
    }
}
