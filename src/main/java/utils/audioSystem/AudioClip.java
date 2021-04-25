package utils.audioSystem;

import startupApp.LoadingHandler;
import startupApp.LoadingMonitor;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * class for loading and playing audios
 */
@SuppressWarnings("unused")
public class AudioClip {

    /**
     * audio clip
     */
    private Clip audioClip;

    /**
     * volume controller for the audio
     */
    private FloatControl gainControl;

    /**
     * creates a new audio clip
     *
     * @param audioClip audio clip
     */
    private AudioClip(Clip audioClip) {
        this.audioClip = audioClip;
        gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
    }

    /**
     * loads a audio file
     *
     * @param file audio file
     * @param loadingHandler loading handler
     * @return loaded audio clip
     */
    public static AudioClip load(File file, LoadingHandler loadingHandler) {

        LoadingMonitor loadingMonitor = new LoadingMonitor(file.getName());
        loadingHandler.addLoadingProcess(loadingMonitor);

        Clip result = null;

        AudioInputStream audioInputStream;
        try {
            audioInputStream = javax.sound.sampled.AudioSystem.getAudioInputStream(file.getAbsoluteFile());

            result = javax.sound.sampled.AudioSystem.getClip();
            result.open(audioInputStream);
            pre_playAudio(result);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException ignored) {}

        loadingMonitor.finishedProcess(loadingHandler);
        if (result != null) {
            return new AudioClip(result);
        }

        return null;
    }

    /**
     * loads a audio file without handler
     *
     * @param file audio file
     * @return loaded audio clip
     */
    public static AudioClip load(File file) {

        Clip result = null;

        AudioInputStream audioInputStream;
        try {
            audioInputStream = javax.sound.sampled.AudioSystem.getAudioInputStream(file.getAbsoluteFile());

            result = javax.sound.sampled.AudioSystem.getClip();
            result.open(audioInputStream);
            pre_playAudio(result);

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (result != null) {
            return new AudioClip(result);
        }

        return null;
    }


    /**
     * starts the audio in the loading process so it can be started without delay
     *
     * @param clip audio clip
     * @throws InterruptedException when the sleeping of the thread was interrupted
     */
    private static void pre_playAudio(Clip clip) throws InterruptedException {
        ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(0);
        clip.start();
        Thread.sleep(10);
        clip.stop();
        clip.flush();
        clip.setFramePosition(0);
        ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(1);
    }

    /**
     * restarts the audio from the start
     */
    public void play() {
        audioClip.stop();
        audioClip.setFramePosition(0);
        audioClip.start();
    }

    /**
     * sets the volume of the audio
     *
     * @param value new volume (between 0 and 1)
     */
    public void setGain(float value) {
        gainControl.setValue(value);
    }
}
