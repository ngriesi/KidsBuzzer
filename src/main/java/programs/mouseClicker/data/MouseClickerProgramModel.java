package programs.mouseClicker.data;

import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.ProgramModel;
import startupApp.LoadingHandler;

public class MouseClickerProgramModel extends ProgramModel<MouseClickerProgramSaveFile> {

    /**
     * creates a new Program model
     */
    public MouseClickerProgramModel() {
        super(MouseClickerProgramSaveFile.class);

    }

    @Override
    public void loadResources(LoadingHandler loadingModel, OpenGlRenderer openGlRenderer) {

    }
}
