package programs.testProgram.data;

import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.ProgramModel;
import startupApp.LoadingHandler;

/**
 * Empty model of the text program
 */
public class TestProgramModel extends ProgramModel {


    public TestProgramModel() {
        super("Test");
    }

    @Override
    public void loadResources(LoadingHandler loadingHandler, OpenGlRenderer openGlRenderer) {

    }

}
