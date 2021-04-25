package programs.keyPresser.data;

import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.ProgramModel;
import startupApp.LoadingHandler;

public class KeyPressModel extends ProgramModel<KeyPressSaveFile> {
    /**
     * creates a new Program model
     */
    public KeyPressModel() {
        super(KeyPressSaveFile.class);
    }

    @Override
    public void loadResources(LoadingHandler loadingModel, OpenGlRenderer openGlRenderer) {

    }
}
