package programs.instantButton.data;

import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.ProgramModel;
import savedataHandler.SaveDataHandler;
import startupApp.LoadingHandler;
import utils.audioSystem.AudioClip;
import utils.saveFile.SaveFile;

public class InstantButtonModel extends ProgramModel<InstantButtonSaveFile> {

    private AudioClip[] audioClips;

    /**
     * creates a new Program model
     */
    public InstantButtonModel() {
        super(InstantButtonSaveFile.class);
    }

    @Override
    public void loadResources(LoadingHandler loadingHandler, OpenGlRenderer openGlRenderer) {
        audioClips = new AudioClip[SaveDataHandler.MAX_BUZZER_COUNT];
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            int finalI = i;
            new Thread(() -> {
                audioClips[finalI] = loadAudio(getSaveFile().getBuzzerSounds()[finalI], loadingHandler, getSaveFile().getVolume()[finalI]);
                if(audioClips[finalI]==null) getSaveFile().getBuzzerSounds()[finalI] = "default";
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

    public void fadeOutSound(int buzzer) {
        if (audioClips[buzzer] != null) {
            audioClips[buzzer].fadeOut(1);
        }
    }

    public AudioClip[] getAudioClips() {
        return audioClips;
    }
}
