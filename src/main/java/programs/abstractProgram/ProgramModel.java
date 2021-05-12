package programs.abstractProgram;

import presentationWindow.engine.Action;
import presentationWindow.window.OpenGlRenderer;
import startupApp.LoadingHandler;
import startupApp.LoadingMonitor;
import utils.audioSystem.AudioClip;
import utils.saveFile.SaveFile;
import utils.saveFile.SaveFileLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * program model handling the data of a program
 *
 * @param <S> type of the save file of this model
 */
public abstract class ProgramModel<S extends SaveFile> {

    /**
     * save file of this model
     */
    private S saveFile;

    /**
     * type of the save file of this model
     */
    private Class<S> type;

    /**
     * change listener for the save file
     */
    private List<Action> changeListeners;

    /**
     * creates a new Program model
     *
     * @param type type of the save file of this model
     */
    public ProgramModel(Class<S> type) {
        this.type = type;
        changeListeners = new ArrayList<>();
    }

    /**
     * loads the save file of this model
     *
     * @param loadingHandler to track the loading
     * @param openGlRenderer to load open gl resources
     */
    void loadModel(LoadingHandler loadingHandler, OpenGlRenderer openGlRenderer) {

        LoadingMonitor loadingMonitor = new LoadingMonitor(type.getName() + " saveFile");
        loadingHandler.addLoadingProcess(loadingMonitor);
        try {
            saveFile = SaveFileLoader.loadFile(type.newInstance().getName(), type);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        loadingMonitor.finishedProcess(loadingHandler);

        loadResources(loadingHandler, openGlRenderer);


    }

    /**
     * loads the resources for the program
     *
     * @param loadingModel   to track the loading
     * @param openGlRenderer to load open gl resources
     */
    public abstract void loadResources(LoadingHandler loadingModel, OpenGlRenderer openGlRenderer);

    /**
     * method should be called in an extra Thread to load a audio file
     *
     * @param path           path of the audio file
     * @param loadingHandler <code>LoadingHandler</code> of the program
     * @param volume         volume of the audio in the range form 0 to 100
     * @return the loaded audio clip
     */
    protected AudioClip loadAudio(String path, LoadingHandler loadingHandler, int volume) {
        AudioClip audioClip = AudioClip.load(new File(path), loadingHandler);
        if (audioClip == null) return null;
        else audioClip.setGain(volume / 100f);
        return audioClip;
    }

    /**
     * @return returns the save file of this model
     */
    public S getSaveFile() {
        return saveFile;
    }

    /**
     * updates the change listener
     */
    void updateChangeListener() {
        for (Action action : changeListeners) {
            action.execute();
        }
    }

    /**
     * adds a change listener
     *
     * @param changeListener new change listener
     */
    void addChangeListener(Action changeListener) {
        changeListeners.add(changeListener);
    }

}
