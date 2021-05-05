package programs.keyPresser.data;

import utils.saveFile.SaveFile;

/**
 * save file of the <code>KeyPressProgram</code>
 */
@SuppressWarnings("unused")
public class KeyPressSaveFile extends SaveFile {

    /**
     * array storing the flags that determine if the key presses should be used
     */
    private boolean[] useKey = {false, false, false};

    /**
     * code of the keys assigned to the buzzers
     */
    private int[] key = {0, 0, 0};

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
    public KeyPressSaveFile() {
        super("keyPress");
    }

    /*
    *******************************************
            PUBLIC GETTERS AND SETTERS
    *******************************************
     */

    public boolean[] getUseKey() {
        return useKey;
    }

    public void setUseKey(boolean[] useKey) {
        this.useKey = useKey;
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
