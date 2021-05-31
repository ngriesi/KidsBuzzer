package programs.abstractProgram;

import assets.settings.rows.AudioSettingRow;
import presentationWindow.engine.Action;
import presentationWindow.window.OpenGlRenderer;
import startupApp.LoadingHandler;
import startupApp.LoadingMonitor;
import utils.audioSystem.AudioClip;
import utils.save.SaveFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * program model handling the data of a program
 */
public abstract class ProgramModel {

    /**
     * save file of this model
     */
    private SaveFile saveFile;

    /**
     * name of the save file
     */
    private String name;

    /**
     * change listener for the save file
     */
    private List<Action> changeListeners;

    /**
     * creates a new Program model
     *
     * @param name of the save file of this model
     */
    public ProgramModel(String name) {
        this.name = name;
        changeListeners = new ArrayList<>();
    }

    /**
     * loads the save file of this model
     *
     * @param loadingHandler to track the loading
     * @param openGlRenderer to load open gl resources
     */
    void loadModel(LoadingHandler loadingHandler, OpenGlRenderer openGlRenderer) {

        LoadingMonitor loadingMonitor = new LoadingMonitor(name + " saveFile");
        loadingHandler.addLoadingProcess(loadingMonitor);
        saveFile = new SaveFile(name);
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

    protected AudioClip loadAudio(String saveFileFieldName, LoadingHandler loadingHandler) {
        AudioSettingRow.AudioData audioData = getSaveFile().getAudioData(saveFileFieldName);
        AudioClip audioClip = AudioClip.load(new File(audioData.getFile().getAbsolutePath()), loadingHandler);
        if (audioClip == null)
            getSaveFile().putAudioData(saveFileFieldName, new AudioSettingRow.AudioData(new File("default"), 1));
        else audioClip.setGain(audioData.getVolume());
        return audioClip;
    }

    /**
     * @return returns the save file of this model
     */
    public SaveFile getSaveFile() {
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
