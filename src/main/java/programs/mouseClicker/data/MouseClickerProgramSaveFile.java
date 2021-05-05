package programs.mouseClicker.data;

import utils.saveFile.SaveFile;

/**
 * save file of the <code>MouseClickerProgram</code>
 */
@SuppressWarnings("unused")
public class MouseClickerProgramSaveFile extends SaveFile {

    /**
     * flag storing the visibility of the mouse tracker window
     */
    private boolean displayMouseTracker = true;

    /**
     * array storing the flags that determine if the mouse clicks should be used
     */
    private boolean[] useClick = {false, false, false};

    /**
     * array storing the x positions of the clicks
     */
    private int[] clickX = {0, 0, 0};

    /**
     * array storing the y position of the clicks
     */
    private int[] clickY = {0, 0, 0};

    /**
     * Positions of the main selector of the blocking behaviour contorl view row
     */
    @SuppressWarnings("SpellCheckingInspection")
    private String blockingBehaviour = "Dont block";

    /**
     * Blocking time for the buzzers
     */
    private int blockingTime = 1;

    /**
     * buzzer used for unblocking
     */
    private int unblockBuzzer = 3;


    /**
     * creates a save file with a name
     */
    public MouseClickerProgramSaveFile() {
        super("mouseClicker");
    }

    /*
    *******************************************
            PUBLIC GETTERS AND SETTERS
    *******************************************
     */

    public boolean isDisplayMouseTracker() {
        return displayMouseTracker;
    }

    public void setDisplayMouseTracker(boolean displayMouseTracker) {
        this.displayMouseTracker = displayMouseTracker;
    }

    public boolean[] getUseClick() {
        return useClick;
    }

    public void setUseClick(boolean[] useClick) {
        this.useClick = useClick;
    }

    public int[] getClickX() {
        return clickX;
    }

    public void setClickX(int[] clickX) {
        this.clickX = clickX;
    }

    public int[] getClickY() {
        return clickY;
    }

    public void setClickY(int[] clickY) {
        this.clickY = clickY;
    }

    public String getBlockingBehaviour() {
        return blockingBehaviour;
    }

    public void setBlockingBehaviour(String blockingBehaviour) {
        this.blockingBehaviour = blockingBehaviour;
    }

    public int getBlockingTime() {
        return blockingTime;
    }

    public void setBlockingTime(int blockingTime) {
        this.blockingTime = blockingTime;
    }

    public int getUnblockBuzzer() {
        return unblockBuzzer;
    }

    public void setUnblockBuzzer(int unblockBuzzer) {
        this.unblockBuzzer = unblockBuzzer;
    }
}
