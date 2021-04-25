package programs.testProgram.main;

import programs.abstractProgram.Program;
import programs.testProgram.control.TestProgramController;
import programs.testProgram.data.TestProgramModel;
import programs.testProgram.settings.TestProgramSettingsController;

public class TestProgram extends Program<TestProgramController,TestProgramSettingsController, TestProgramModel, TestProgramView> {

    public TestProgram(String name) {
        super(true, name);
    }

    @Override
    public TestProgramModel createModel() {
        return new TestProgramModel();
    }

    @Override
    public TestProgramSettingsController createSettingsController() {
        return new TestProgramSettingsController(this,getProgramModel());
    }

    @Override
    public TestProgramController createControlController() {
        return new TestProgramController(this,getProgramModel());
    }

    @Override
    public TestProgramView createPresentationView() {
        return new TestProgramView(this);
    }

    @Override
    protected void buzzerAction(int buzzerNumber) {
        System.out.println("number " + buzzerNumber);
        getProgramPresentationView().press(buzzerNumber);
    }

    @Override
    public void updateBuzzerCount() {

    }

    public void moveQuad() {
        getProgramPresentationView().press(1);
        getProgramPresentationView().press(2);
        getProgramPresentationView().press(3);
    }
}
