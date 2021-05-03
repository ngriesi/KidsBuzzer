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
        ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-80);
        clip.start();
        Thread.sleep(10);
        clip.stop();
        clip.flush();
        clip.setFramePosition(0);
        ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(0);
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
     * fades out the audio
     */
    public void fadeOut(int time) {
        new Thread(() -> {
            int steps = 100;
            int progress = 0;
            float step = time/(float) steps;

            float saveGain = (float) Math.pow(Math.E, (gainControl.getValue() / 20.0 * Math.log(10)));

            System.out.println(saveGain);

            while (progress <= steps) {

                setGain(saveGain - (saveGain / steps) * progress);

                try {
                    Thread.sleep((long) (step * 1000f));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                progress++;
            }

            audioClip.stop();
            audioClip.setFramePosition(0);
            setGain(saveGain);

        }).start();
    }

    /**
     * Set the volume to a value between 0 and 1.
     */
    public void setGain(double value) {
        value = (value<=0.0)? 0.0001 : (Math.min(value, 1.0));
        try {
            float dB = (float)(Math.log(value)/Math.log(10.0)*20.0);
            gainControl.setValue(dB);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
