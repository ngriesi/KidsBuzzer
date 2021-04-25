package programs.scoreBoard.data;

import utils.saveFile.SaveFile;

@SuppressWarnings({"unused"})
public class ScoreBoardSaveFile extends SaveFile {

    private String[] icons = {"team12.png", "team2.png", "team3.png"};

    private String[] teamNames = {"team 1", "team 2", "team 3"};

    private String font = "Arial";

    private boolean textBold = false;

    private int[] textColor = {255, 255, 255, 255};

    private String buzzerSound = "default";

    private int buzzerSoundVolume;

    /**
     * creates a save file with a name
     */
    public ScoreBoardSaveFile() {
        super("scoreBoard");
    }

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
}
