package programs.testProgram.main;

import programs.abstractProgram.Program;
import programs.testProgram.control.TestProgramController;
import programs.testProgram.data.TestProgramModel;
import programs.testProgram.settings.TestProgramSettingsController;

/**
 * main class of the test program
 */
public class TestProgram extends Program<TestProgramController, TestProgramSettingsController, TestProgramModel, TestProgramView> {

    /**
     * creates the test program
     *
     * @param name name of the program
     */
    public TestProgram(String name) {
        super(true, name);
    }

    /**
     * @return the created <code>TestProgramModel</code>
     */
    @Override
    public TestProgramModel createModel() {
        return new TestProgramModel();
    }

    /**
     * @return the created <code>TestProgramSettingsController</code>
     */
    @Override
    public TestProgramSettingsController createSettingsController() {
        return new TestProgramSettingsController(this, getProgramModel());
    }

    /**
     * @return the created <code>TestProgramController</code>
     */
    @Override
    public TestProgramController createControlController() {
        return new TestProgramController(this, getProgramModel());
    }

    /**
     * @return the created <code>TestProgramView</code>
     */
    @Override
    public TestProgramView createPresentationView() {
        return new TestProgramView(this);
    }

    /**
     * Action performed on a buzzer press
     *
     * @param buzzerNumber number of the buzzer pressed (starting with 1)
     */
    @Override
    protected void buzzerAction(int buzzerNumber) {
        getProgramPresentationView().press(buzzerNumber);
    }

    @Override
    public void updateBuzzerCount() {

    }

    /**
     * test method
     */
    public void moveQuad() {
        getProgramPresentationView().press(1);
        getProgramPresentationView().press(2);
        getProgramPresentationView().press(3);
    }
}
