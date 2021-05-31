package programs.instantButton.data;

import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.ProgramModel;
import savedataHandler.SaveDataHandler;
import startupApp.LoadingHandler;
import utils.audioSystem.AudioClip;

/**
 * Program model of the instant button program containing the audio clips for the program
 */
public class InstantButtonModel extends ProgramModel {

    /**
     * Strings to identify the save file values
     */
    public static String BUZZER_SOUND = "BUZZER_SOUND";

    /**
     * audio clips that get played when a buzzer is pressed
     */
    private AudioClip[] audioClips;

    /**
     * creates a new Program model
     */
    public InstantButtonModel() {
        super("InstantButton");
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
            new Thread(() -> audioClips[finalI] = loadAudio(BUZZER_SOUND + finalI, loadingHandler)).start();
        }
    }

    /**
     * plays the sound if it exists
     *
     * @param buzzer buzzer for which the sound gets played
     * @return true if the sound exists
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
