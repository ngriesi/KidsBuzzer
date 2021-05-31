package programs.keyPresser.data;

import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.ProgramModel;
import startupApp.LoadingHandler;

/**
 * Model of the <code>KeyPressProgram</code>. Holds a reference to the save file
 */
public class KeyPressModel extends ProgramModel {

    /**
     * creates a new Program model
     */
    public KeyPressModel() {
        super("KeyPresser");
    }


    @Override
    public void loadResources(LoadingHandler loadingModel, OpenGlRenderer openGlRenderer) {

    }
}
