package utils.audioSystem;

import startupApp.LoadingHandler;

import java.io.File;

/**
 * audio system of the application, contains standard sounds
 */
public class AudioSystem {

    /**
     * standard buzzer sound
     */
    private AudioClip buzzerSound;

    /**
     * loads the standard buzzer sound
     *
     * @param loadingHandler loading handler
     */
    public AudioSystem (LoadingHandler loadingHandler) {
        new Thread(() -> buzzerSound = AudioClip.load(new File("WrongAnswer.wav"), loadingHandler)).start();
    }

    /**
     * @return returns the standard buzzer sound
     */
    public AudioClip getBuzzerSound() {
        return buzzerSound;
    }

}
