package programs.testProgram.data;

import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.ProgramModel;
import startupApp.LoadingHandler;

/**
 * Empty model of the text program
 */
public class TestProgramModel extends ProgramModel<TestProgramSaveFile> {


    public TestProgramModel() {
        super(TestProgramSaveFile.class);
    }

    @Override
    public void loadResources(LoadingHandler loadingHandler, OpenGlRenderer openGlRenderer) {

    }

}
