package programs.mouseClicker.data;

import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.ProgramModel;
import startupApp.LoadingHandler;

/**
 * Model of the <code>MouseClickerProgram</code>. Holds a reference to the save file
 */
public class MouseClickerProgramModel extends ProgramModel {

    /**
     * creates a new Program model
     */
    public MouseClickerProgramModel() {
        super("MouseClicker");

    }

    @Override
    public void loadResources(LoadingHandler loadingModel, OpenGlRenderer openGlRenderer) {

    }
}
