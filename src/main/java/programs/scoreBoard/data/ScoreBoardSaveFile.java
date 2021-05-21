package programs.scoreBoard.data;

import utils.saveFile.SaveFile;

/**
 * Save file of the score board program
 */
@SuppressWarnings({"unused"})
public class ScoreBoardSaveFile extends SaveFile {

    /**
     * file paths of the icons displayed behind the scores of the teams
     */
    private String[] icons = {"default", "default", "default"};

    /**
     * names of the teams
     */
    private String[] teamNames = {"team 1", "team 2", "team 3"};

    /**
     * font of the team names and score
     */
    private String font = "Arial";

    /**
     * flag indicating if the text of the team names and score is bold
     */
    private boolean textBold = false;

    /**
     * color of the text
     */
    private int[] textColor = {255, 255, 255, 255};

    /**
     * file path of the buzzer press
     */
    private String buzzerSound = "default";

    /**
     * volume of the sound
     */
    private int buzzerSoundVolume;

    /**
     * x coord of the executor button of the midi action
     */
    private int midiX = 1;

    /**
     * y coord of the executor button of the midi action
     */
    private int midiY = 1;

    /**
     * activation flag for the midi action
     */
    private boolean midiActivate = false;

    /**
     * creates a save file with a name
     */
    public ScoreBoardSaveFile() {
        super("scoreBoard");
    }

    /*
    *******************************************
            PUBLIC GETTERS AND SETTERS
    *******************************************
     */

    public String[] getIcons() {
        return icons;
    }

    public void setIcons(String[] icons) {
        this.icons = icons;
    }

    public String[] getTeamNames() {
        return teamNames;
    }

    public void setTeamNames(String[] teamNames) {
        this.teamNames = teamNames;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public boolean isTextBold() {
        return textBold;
    }

    public void setTextBold(boolean textBold) {
        this.textBold = textBold;
    }

    public int[] getTextColor() {
        return textColor;
    }

    public void setTextColor(int[] textColor) {
        this.textColor = textColor;
    }

    public String getBuzzerSound() {
        return buzzerSound;
    }

    public void setBuzzerSound(String buzzerSound) {
        this.buzzerSound = buzzerSound;
    }

    public int getBuzzerSoundVolume() {
        return buzzerSoundVolume;
    }

    public void setBuzzerSoundVolume(int buzzerSoundVolume) {
        this.buzzerSoundVolume = buzzerSoundVolume;
    }

    public int getMidiX() {
        return midiX;
    }

    public void setMidiX(int midiX) {
        this.midiX = midiX;
    }

    public int getMidiY() {
        return midiY;
    }

    public void setMidiY(int midiY) {
        this.midiY = midiY;
    }

    public boolean isMidiActivate() {
        return midiActivate;
    }

    public void setMidiActivate(boolean midiActivate) {
        this.midiActivate = midiActivate;
    }
}
