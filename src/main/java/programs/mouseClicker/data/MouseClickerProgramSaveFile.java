package programs.mouseClicker.data;

import utils.saveFile.SaveFile;

public class MouseClickerProgramSaveFile extends SaveFile {

    private boolean displayMouseTracker = true;

    private boolean[] useClick = {false, false, false};

    private int[] clickX = {0, 0, 0};

    private int[] clickY = {0, 0, 0};

    private String blockingBehaviour = "Dont block";

    private int blockingTime = 1;

    private int unblockBuzzer = 3;


    /**
     * creates a save file with a name
     */
    public MouseClickerProgramSaveFile() {
        super("mouseClicker");
    }

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
