package programs.scoreBoard.data;

import presentationWindow.items.Texture;
import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.ProgramModel;
import savedataHandler.SaveDataHandler;
import startupApp.LoadingHandler;
import utils.audioSystem.AudioClip;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Model of the score board program
 */
public class ScoreBoardModel extends ProgramModel<ScoreBoardSaveFile> {

    /**
     * Icons of the teams displayed behind their scores
     */
    private Texture[] icons;

    /**
     * Scores of the individual teams
     */
    private int[] scores;

    /**
     * sound played when a score gets increased
     */
    private AudioClip buzzerSound;

    /**
     * creates a new Program model
     */
    public ScoreBoardModel() {
        super(ScoreBoardSaveFile.class);
        scores = new int[SaveDataHandler.MAX_BUZZER_COUNT];
    }

    /**
     * loads the resources for this program
     *
     * @param loadingHandler loading handler to track the loading
     * @param openGlRenderer to load open gl resources
     */
    @Override
    public void loadResources(LoadingHandler loadingHandler, OpenGlRenderer openGlRenderer) {
        icons = new Texture[SaveDataHandler.MAX_BUZZER_COUNT];
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

        new Thread(() -> {
            buzzerSound = AudioClip.load(new File(getSaveFile().getBuzzerSound()), loadingHandler);
            if (buzzerSound == null) getSaveFile().setBuzzerSound("default");
            else buzzerSound.setGain(getSaveFile().getBuzzerSoundVolume() / 100f);
        }).start();
    }

    /**
     * plays the buzzer sound sound if it exists
     */
    public void playBuzzerSound() {
        if (buzzerSound != null) {
            buzzerSound.play();
        }
    }

    /*
    *******************************************
            PUBLIC GETTERS AND SETTERS
    *******************************************
     */

    public Texture[] getIcons() {
        return icons;
    }

    public int[] getScores() {
        return scores;
    }

    public void setBuzzerSound(AudioClip buzzerSound) {
        this.buzzerSound = buzzerSound;
    }

    public AudioClip getBuzzerSound() {
        return buzzerSound;
    }

    public void setIcon(Texture texture, int index) {
        icons[index] = texture;
    }
}
