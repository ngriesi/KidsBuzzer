package programs.quiztime.data;

import presentationWindow.items.Texture;
import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.ProgramModel;
import programs.quizPrograms.data.QuizModel;
import programs.quiztime.main.control.MidiHandler;
import savedataHandler.SaveDataHandler;
import startupApp.LoadingHandler;
import utils.audioSystem.AudioClip;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * handles the resources of the quiztime program
 */
public class QuizTimeProgramModel extends QuizModel<QuizTimeProgramSaveFile> {

    /**
     * background of the output screen
     */
    private Texture backgroundTexture;

    /**
     * intro sound
     */
    private AudioClip introSound;


    /**
     * creates a new Program model
     */
    public QuizTimeProgramModel() {
        super(QuizTimeProgramSaveFile.class);
    }

    /**
     * loads the resources of the quiztime program
     *
     * @param loadingHandler to track the loading
     * @param openGlRenderer to load open gl resources
     */
    @Override
    public void loadResources(LoadingHandler loadingHandler, OpenGlRenderer openGlRenderer) {

        midiHandler = new MidiHandler(getSaveFile());

        super.loadResources(loadingHandler,openGlRenderer);

        openGlRenderer.addActionToOpenGlThread(() -> {
            try {
                backgroundTexture = Texture.loadTexture(new File(getSaveFile().getBackground()), loadingHandler);
            } catch (FileNotFoundException e) {
                backgroundTexture = new Texture(SaveDataHandler.DEFAULT_IMAGE);
                getSaveFile().setBackground("default");
            }
        });

        new Thread(() -> {
            introSound = loadAudio(getSaveFile().getIntroSound(), loadingHandler, getSaveFile().getIntroVolume());
            if (introSound == null) getSaveFile().setIntroSound("default");
        }).start();
    }

    /**
     * plays the intro sound if it exists
     */
    public void playIntroSound() {
        if (introSound != null) {
            introSound.play();
        }
    }

    /**
     * fades out <code>introSound</code>
     */
    public void fadeOutIntroSound() {
        if (introSound != null) {
            introSound.fadeOut(1);
        }
    }


    /*
    *******************************************
            PUBLIC GETTERS AND SETTERS
    *******************************************
     */

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public void setBackgroundTexture(Texture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public void setIntroSound(AudioClip introSound) {
        this.introSound = introSound;
    }

    public AudioClip getIntroSound() {
        return introSound;
    }
}
