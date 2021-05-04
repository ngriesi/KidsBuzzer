package programs.instantButton.data;

import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.ProgramModel;
import savedataHandler.SaveDataHandler;
import startupApp.LoadingHandler;
import utils.audioSystem.AudioClip;
import utils.saveFile.SaveFile;

/**
 * Program model of the instant button program containing the audio clips for the program
 */
public class InstantButtonModel extends ProgramModel<InstantButtonSaveFile> {

    /**
     * audio clips that get played when a buzzer is pressed
     */
    private AudioClip[] audioClips;

    /**
     * creates a new Program model
     */
    public InstantButtonModel() {
        super(InstantButtonSaveFile.class);
    }

    /**
     * loads the resources for this model
     *
     * @param loadingHandler loading handler to track the loading
     * @param openGlRenderer to load open gl resources
     */
    @Override
    public void loadResources(LoadingHandler loadingHandler, OpenGlRenderer openGlRenderer) {
        audioClips = new AudioClip[SaveDataHandler.MAX_BUZZER_COUNT];
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            int finalI = i;
            new Thread(() -> {
                audioClips[finalI] = loadAudio(getSaveFile().getBuzzerSounds()[finalI], loadingHandler, getSaveFile().getVolume()[finalI]);
                if (audioClips[finalI] == null) getSaveFile().getBuzzerSounds()[finalI] = "default";
            }).start();
        }
    }

    /**
     * plays the sound if it exists
     */
    public boolean playSound(int buzzer) {
        if (audioClips[buzzer] != null) {
            audioClips[buzzer].play();
            return true;
        }
        return false;
    }

    /**
     * @return returns the array of <code>AudioClips</code> of the buzzers
     */
    public AudioClip[] getAudioClips() {
        return audioClips;
    }
}
