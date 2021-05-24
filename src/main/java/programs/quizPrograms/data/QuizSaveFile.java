package programs.quizPrograms.data;

import utils.saveFile.SaveFile;

/**
 * contains the save data of the quiztime program
 */
@SuppressWarnings("unused")
public class QuizSaveFile extends SaveFile {

    /**
     * file paths of the icons of the teams
     */
    private String[] icons = {"default", "default", "default"};

    /**
     * color of the main text (right, question and title)
     */
    private int[] mainTextColor = {255, 255, 255, 255};

    /**
     * flag determines if the main text is bold
     */
    private boolean mainTextBold = false;

    /**
     * font of the main text
     */
    private String mainFont = "Arial";

    /**
     * color of the buzzer text (numbers)
     */
    private int[] buzzerTextColor = {255, 255, 255, 255};

    /**
     * flag determines if the numbers are bold
     */
    private boolean buzzerTextBold = true;

    /**
     * font of the numbers
     */
    private String buzzerFont = "Arial";

    /**
     * file path of the question sound
     */
    private String questionSound = "default";

    /**
     * volume of the question sound
     */
    private int questionVolume = 100;

    /**
     * file path of the right sound
     */
    private String rightSound = "default";

    /**
     * volume of the right sound
     */
    private int rightVolume = 100;

    /**
     * file path of the buzzer sound
     */
    private String buzzerSound = "default";

    /**
     * volume of the buzzer sound
     */
    private int buzzerVolume = 100;

    /**
     * file path of the wrong sound
     */
    private String wrongSound = "default";

    /**
     * volume of the wrongs sound
     */
    private int wrongVolume = 100;

    /**
     * x coord of the executor button of the right midi action
     */
    private int rightMidiX = 1;

    /**
     * y coord of the executor button of the right midi action
     */
    private int rightMidiY = 1;

    /**
     * activation flag for the right midi action
     */
    private boolean rightMidiActivate = false;

    /**
     * x coord of the executor button of the wrong midi action
     */
    private int wrongMidiX = 1;

    /**
     * y coord of the executor button of the wrong midi action
     */
    private int wrongMidiY = 1;

    /**
     * activation flag for the wrong midi action
     */
    private boolean wrongMidiActivate = false;

    /**
     * x coord of the executor button of the next midi action
     */
    private int nextMidiX = 1;

    /**
     * y coord of the executor button of the next midi action
     */
    private int nextMidiY = 1;

    /**
     * activation flag for the next midi action
     */
    private boolean nextMidiActivate = false;

    /**
     * x coord of the executor button of the buzzer midi action
     */
    private int[] buzzerMidiX = new int[]{1, 1, 1};

    /**
     * y coord of the executor button of the buzzer midi action
     */
    private int[] buzzerMidiY = new int[]{1, 1, 1};

    /**
     * activation flag for the buzzer midi action
     */
    private boolean[] buzzerMidiActivate = new boolean[]{false, false, false};

    /**
     * creates a save file with a name
     */
    public QuizSaveFile() {
        super("quizoverlay");
    }

    /**
     * creates a save file with a name passed form a sub class
     */
    public QuizSaveFile(String name) {
        super(name);
    }

    /*
    *******************************************
            PUBLIC GETTERS AND SETTERS
    *******************************************
     */

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

    public int getRightMidiX() {
        return rightMidiX;
    }

    public void setRightMidiX(int rightMidiX) {
        this.rightMidiX = rightMidiX;
    }

    public int getRightMidiY() {
        return rightMidiY;
    }

    public void setRightMidiY(int rightMidiY) {
        this.rightMidiY = rightMidiY;
    }

    public boolean isRightMidiActivate() {
        return rightMidiActivate;
    }

    public void setRightMidiActivate(boolean rightMidiActivate) {
        this.rightMidiActivate = rightMidiActivate;
    }

    public int getWrongMidiX() {
        return wrongMidiX;
    }

    public void setWrongMidiX(int wrongMidiX) {
        this.wrongMidiX = wrongMidiX;
    }

    public int getWrongMidiY() {
        return wrongMidiY;
    }

    public void setWrongMidiY(int wrongMidiY) {
        this.wrongMidiY = wrongMidiY;
    }

    public boolean isWrongMidiActivate() {
        return wrongMidiActivate;
    }

    public void setWrongMidiActivate(boolean wrongMidiActivate) {
        this.wrongMidiActivate = wrongMidiActivate;
    }

    public int getNextMidiX() {
        return nextMidiX;
    }

    public void setNextMidiX(int nextMidiX) {
        this.nextMidiX = nextMidiX;
    }

    public int getNextMidiY() {
        return nextMidiY;
    }

    public void setNextMidiY(int nextMidiY) {
        this.nextMidiY = nextMidiY;
    }

    public boolean isNextMidiActivate() {
        return nextMidiActivate;
    }

    public void setNextMidiActivate(boolean nextMidiActivate) {
        this.nextMidiActivate = nextMidiActivate;
    }

    public int[] getBuzzerMidiX() {
        return buzzerMidiX;
    }

    public void setBuzzerMidiX(int[] buzzerMidiX) {
        this.buzzerMidiX = buzzerMidiX;
    }

    public int[] getBuzzerMidiY() {
        return buzzerMidiY;
    }

    public void setBuzzerMidiY(int[] buzzerMidiY) {
        this.buzzerMidiY = buzzerMidiY;
    }

    public boolean[] getBuzzerMidiActivate() {
        return buzzerMidiActivate;
    }

    public void setBuzzerMidiActivate(boolean[] buzzerMidiActivate) {
        this.buzzerMidiActivate = buzzerMidiActivate;
    }
}
