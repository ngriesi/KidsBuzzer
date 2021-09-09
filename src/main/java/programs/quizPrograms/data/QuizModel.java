package programs.quizPrograms.data;

import presentationWindow.items.Texture;
import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.ProgramModel;
import programs.quizPrograms.main.control.MidiHandler;
import savedataHandler.SaveDataHandler;
import startupApp.LoadingHandler;
import utils.audioSystem.AudioClip;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * handles the resources of the quiz programs program
 */
public class QuizModel extends ProgramModel {

    /**
     * Identification Strings for the settings fields
     */
    public static final String ICON = "Icon";
    public static final String QUESTION_SOUND = "Question Sound", RIGHT_SOUND = "Right Sound", WRONG_SOUND = "Wrong Sound", BUZZER_SOUND = "Buzzer Sound";
    public static final String RIGHT_MIDI = "Right Midi", WRONG_MIDI = "Wrong Midi", BUZZER_MIDI = "Buzzer Midi", NEXT_MIDI = "Next Midi";
    public static final String MAIN_FONT = "Main Font", BUZZER_FONT = "Buzzer Font";

    /**
     * icons displayed over the virtual buzzers
     */
    private Texture[] icons;

    /**
     * question sound
     */
    private AudioClip questionSound;

    /**
     * right sound
     */
    private AudioClip rightSound;

    /**
     * buzzer sound
     */
    private AudioClip buzzerSound;

    /**
     * wrong sound
     */
    private AudioClip wrongSound;

    /**
     * Midi controller of the program
     */
    protected MidiHandler midiHandler;

    /**
     * creates a new Program model
     */
    public QuizModel(String name) {
        super(name);

        icons = new Texture[SaveDataHandler.MAX_BUZZER_COUNT];
    }


    /**
     * loads the resources of the quiztime program
     *
     * @param loadingHandler to track the loading
     * @param openGlRenderer to load open gl resources
     */
    @Override
    public void loadResources(LoadingHandler loadingHandler, OpenGlRenderer openGlRenderer) {

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            int finalI = i;
            openGlRenderer.addActionToOpenGlThread(() -> {
                try {
                    icons[finalI] = Texture.loadTexture(new File(getSaveFile().getString(ICON + finalI)), loadingHandler);
                } catch (FileNotFoundException e) {
                    icons[finalI] = new Texture(SaveDataHandler.DEFAULT_IMAGE);
                    getSaveFile().putString(ICON + finalI, "default");
                }

            });
        }

        new Thread(() -> questionSound = loadAudio(QUESTION_SOUND, loadingHandler)).start();
        new Thread(() -> rightSound = loadAudio(RIGHT_SOUND, loadingHandler)).start();
        new Thread(() -> buzzerSound = loadAudio(BUZZER_SOUND, loadingHandler)).start();
        new Thread(() -> wrongSound = loadAudio(WRONG_SOUND, loadingHandler)).start();
    }

    /**
     * plays the question sound if it exists
     */
    public void playQuestionSound() {
        if (questionSound != null) {
            questionSound.play();
        }
    }

    /**
     * fades out <code>questionSound</code>
     */
    public void fadeOutQuestionSound() {
        if (questionSound != null) {
            questionSound.fadeOut(1);
        }
    }

    /**
     * plays the right sound if it exists
     */
    public void playRightSound() {
        if (rightSound != null) {
            rightSound.play();
        }
    }

    /**
     * fades out <code>rightSound</code>
     */
    public void fadeOutRightSound() {
        if (rightSound != null) {
            rightSound.fadeOut(1);
        }
    }

    /**
     * plays the buzzer sound if it exists
     */
    public void playBuzzerSound() {
        if (buzzerSound != null) {
            buzzerSound.play();
        }
    }

    /**
     * fades out <code>rightSound</code>
     */
    public void fadeOutBuzzerSound() {
        if (buzzerSound != null) {
            buzzerSound.fadeOut(1);
        }
    }

    /**
     * plays the wrong sound if it exists
     */
    public void playWrongSound() {
        if (wrongSound != null) {
            wrongSound.play();
        }
    }

    /**
     * fades out <code>wrongSound</code>
     */
    public void fadeOutWrongSound() {
        if (wrongSound != null) {
            wrongSound.fadeOut(1);
        }
    }

    /**
     * @return returns the midi handler of the program
     */
    public MidiHandler getQuizMidiHandler() {
        return midiHandler;
    }

    /*
    *******************************************
            PUBLIC GETTERS AND SETTERS
    *******************************************
     */

    public Texture getIcon(int buzzer) {
        return icons[buzzer - 1];
    }

    public void setIcons(Texture icon, int number) {
        this.icons[number] = icon;
    }

    public void setQuestionSound(AudioClip questionSound) {
        this.questionSound = questionSound;
    }

    public void setRightSound(AudioClip rightSound) {
        this.rightSound = rightSound;
    }

    public AudioClip getBuzzerSound() {
        return buzzerSound;
    }

    public void setBuzzerSound(AudioClip buzzerSound) {
        this.buzzerSound = buzzerSound;
    }

    public AudioClip getWrongSound() {
        return wrongSound;
    }

    public void setWrongSound(AudioClip wrongSound) {
        this.wrongSound = wrongSound;
    }

    public AudioClip getQuestionSound() {
        return questionSound;
    }

    public AudioClip getRightSound() {
        return rightSound;
    }
}