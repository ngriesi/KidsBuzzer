package programs.emptyClasses;

import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.ProgramModel;
import startupApp.LoadingHandler;
import utils.saveFile.SaveFile;

public class EmptyModel extends ProgramModel<SaveFile> {
    /**
     * creates a new Program model
     */
    public EmptyModel() {
        super(SaveFile.class);
    }

    @Override
    public void loadResources(LoadingHandler loadingModel, OpenGlRenderer openGlRenderer) {

    }
}
