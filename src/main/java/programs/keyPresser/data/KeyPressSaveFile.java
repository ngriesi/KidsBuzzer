package programs.keyPresser.data;

import utils.saveFile.SaveFile;

@SuppressWarnings("unused")
public class KeyPressSaveFile extends SaveFile {

    private boolean displayMouseTracker = true;

    private boolean[] useClick = {false, false, false};

    private int[] key = {0, 0, 0};

    @SuppressWarnings("SpellCheckingInspection")
    private String blockingBehaviour = "Dont block";

    private int blockingTime = 1;

    private int unblockBuzzer = 3;

    /**
     * creates a save file with a name
     */
    public KeyPressSaveFile() {
        super("keyPress");
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

    public int[] getKey() {
        return key;
    }

    public void setKey(int[] key) {
        this.key = key;
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
