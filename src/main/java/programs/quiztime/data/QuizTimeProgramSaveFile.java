package programs.quiztime.data;

import programs.quizPrograms.data.QuizSaveFile;

/**
 * contains the save data of the quiztime program
 */
@SuppressWarnings("unused")
public class QuizTimeProgramSaveFile extends QuizSaveFile {

    /**
     * file path of the background
     */
    private String background = "default";

    /**
     * file path of the intro sound
     */
    private String introSound = "default";

    /**
     * volume of the intro sound
     */
    private int introVolume = 100;

    /**
     * x coord of the executor button of the intro midi action
     */
    private int introMidiX = 1;

    /**
     * y coord of the executor button of the intro midi action
     */
    private int introMidiY = 1;

    /**
     * activation flag for the intro midi action
     */
    private boolean introMidiActivate = false;

    /**
     * creates a save file with a name
     */
    public QuizTimeProgramSaveFile() {
        super("quiztime");
    }

    /*
    *******************************************
            PUBLIC GETTERS AND SETTERS
    *******************************************
     */

    public String getIntroSound() {
        return introSound;
    }

    public void setIntroSound(String introSound) {
        this.introSound = introSound;
    }

    public int getIntroVolume() {
        return introVolume;
    }

    public void setIntroVolume(int introVolume) {
        this.introVolume = introVolume;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }


    public int getIntroMidiX() {
        return introMidiX;
    }

    public void setIntroMidiX(int introMidiX) {
        this.introMidiX = introMidiX;
    }

    public int getIntroMidiY() {
        return introMidiY;
    }

    public void setIntroMidiY(int introMidiY) {
        this.introMidiY = introMidiY;
    }

    public boolean isIntroMidiActivate() {
        return introMidiActivate;
    }

    public void setIntroMidiActivate(boolean introMidiActivate) {
        this.introMidiActivate = introMidiActivate;
    }
}
