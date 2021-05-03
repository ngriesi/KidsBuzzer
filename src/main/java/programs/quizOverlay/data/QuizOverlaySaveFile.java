package programs.quizOverlay.data;

import utils.saveFile.SaveFile;

/**
 * contains the save data of the quiztime program
 */
@SuppressWarnings("unused")
public class QuizOverlaySaveFile extends SaveFile {

    private String[] icons = {"default", "default", "default"};

    private int[] mainTextColor = {255, 255, 0, 255};

    private boolean mainTextBold = false;

    private String mainFont = "Arial";

    private int[] buzzerTextColor = {255, 255, 255, 255};

    private boolean buzzerTextBold = true;

    private String buzzerFont = "Arial";

    private String questionSound = "default";

    private int questionVolume = 100;

    private String rightSound = "default";

    private int rightVolume = 100;

    private String buzzerSound = "default";

    private int buzzerVolume = 100;

    private String wrongSound = "default";

    private int wrongVolume = 100;

    /**
     * creates a save file with a name
     */
    public QuizOverlaySaveFile() {
        super("quizoverlay");
    }

    public String getBuzzerSound() {
        return buzzerSound;
    }

    public void setBuzzerSound(String buzzerSound) {
        this.buzzerSound = buzzerSound;
    }

    public int getBuzzerVolume() {
        return buzzerVolume;
    }

    public void setBuzzerVolume(int buzzerVolume) {
        this.buzzerVolume = buzzerVolume;
    }

    public String getWrongSound() {
        return wrongSound;
    }

    public void setWrongSound(String wrongSound) {
        this.wrongSound = wrongSound;
    }

    public int getWrongVolume() {
        return wrongVolume;
    }

    public void setWrongVolume(int wrongVolume) {
        this.wrongVolume = wrongVolume;
    }

    public String getQuestionSound() {
        return questionSound;
    }

    public void setQuestionSound(String questionSound) {
        this.questionSound = questionSound;
    }

    public int getQuestionVolume() {
        return questionVolume;
    }

    public void setQuestionVolume(int questionVolume) {
        this.questionVolume = questionVolume;
    }

    public String getRightSound() {
        return rightSound;
    }

    public void setRightSound(String rightSound) {
        this.rightSound = rightSound;
    }

    public int getRightVolume() {
        return rightVolume;
    }

    public void setRightVolume(int rightVolume) {
        this.rightVolume = rightVolume;
    }

    public String[] getIcons() {
        return icons;
    }

    public void setIcons(String[] icons) {
        this.icons = icons;
    }

    public int[] getMainTextColor() {
        return mainTextColor;
    }

    public void setMainTextColor(int[] mainTextColor) {
        this.mainTextColor = mainTextColor;
    }

    public boolean isMainTextBold() {
        return mainTextBold;
    }

    public void setMainTextBold(boolean mainTextBold) {
        this.mainTextBold = mainTextBold;
    }

    public String getMainFont() {
        return mainFont;
    }

    public void setMainFont(String mainFont) {
        this.mainFont = mainFont;
    }

    public int[] getBuzzerTextColor() {
        return buzzerTextColor;
    }

    public void setBuzzerTextColor(int[] buzzerTextColor) {
        this.buzzerTextColor = buzzerTextColor;
    }

    public boolean isBuzzerTextBold() {
        return buzzerTextBold;
    }

    public void setBuzzerTextBold(boolean buzzerTextBold) {
        this.buzzerTextBold = buzzerTextBold;
    }

    public String getBuzzerFont() {
        return buzzerFont;
    }

    public void setBuzzerFont(String buzzerFont) {
        this.buzzerFont = buzzerFont;
    }
}
