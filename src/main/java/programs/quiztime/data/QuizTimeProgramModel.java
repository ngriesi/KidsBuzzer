package programs.quiztime.data;

import presentationWindow.items.Texture;
import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.ProgramModel;
import savedataHandler.SaveDataHandler;
import startupApp.LoadingHandler;
import utils.audioSystem.AudioClip;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * handles the resources of the quiztime program
 */
public class QuizTimeProgramModel extends ProgramModel<QuizTimeProgramSaveFile> {

    /**
     * icons displayed over the virtual buzzers
     */
    private Texture[] icons;

    /**
     * background of the output screen
     */
    private Texture backgroundTexture;

    /**
     * intro sound
     */
    private AudioClip introSound;

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
     * creates a new Program model
     */
    public QuizTimeProgramModel() {
        super(QuizTimeProgramSaveFile.class);

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
                    icons[finalI] = Texture.loadTexture(new File(getSaveFile().getIcons()[finalI]), loadingHandler);
                } catch (FileNotFoundException e) {
                    icons[finalI] = new Texture(SaveDataHandler.DEFAULT_IMAGE);
                    getSaveFile().getIcons()[finalI] = "default";
                }

            });
        }

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
        new Thread(() -> {
            questionSound = loadAudio(getSaveFile().getQuestionSound(), loadingHandler, getSaveFile().getQuestionVolume());
            if (questionSound == null) getSaveFile().setQuestionSound("default");
        }).start();
        new Thread(() -> {
            rightSound = loadAudio(getSaveFile().getRightSound(), loadingHandler, getSaveFile().getRightVolume());
            if (rightSound == null) getSaveFile().setRightSound("default");
        }).start();
        new Thread(() -> {
            buzzerSound = loadAudio(getSaveFile().getBuzzerSound(), loadingHandler, getSaveFile().getBuzzerVolume());
            if (buzzerSound == null) getSaveFile().setBuzzerSound("default");
        }).start();
        new Thread(() -> {
            wrongSound = loadAudio(getSaveFile().getWrongSound(), loadingHandler, getSaveFile().getWrongVolume());
            if (wrongSound == null) getSaveFile().setWrongSound("default");
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
     * plays the wrong sound if it exists
     */
    public void playWrongSound() {
        System.out.println(wrongSound);
        if (wrongSound != null) {
            wrongSound.play();
        }
    }

    /*
    *******************************************
            PUBLIC GETTERS AND SETTERS
    *******************************************
     */

    public Texture getIcon(int buzzer) {
        return icons[buzzer - 1];
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public void setIcons(Texture icon, int number) {
        this.icons[number] = icon;
    }

    public void setBackgroundTexture(Texture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
    }

    public void setIntroSound(AudioClip introSound) {
        this.introSound = introSound;
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

    public AudioClip getIntroSound() {
        return introSound;
    }

    public AudioClip getQuestionSound() {
        return questionSound;
    }

    public AudioClip getRightSound() {
        return rightSound;
    }
}
