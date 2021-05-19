package remoteHandler;

import utils.saveFile.SaveFile;

@SuppressWarnings({"unused", "WeakerAccess"})
public class RemoteSaveFile extends SaveFile {

    /**
     * Action of the top left button
     */
    private int topLeftButtonActionIndex = 0;

    /**
     * Action of the top right button
     */
    private int topRightButtonActionIndex = 0;

    /**
     * Action of the bottom left button
     */
    private int bottomLeftButtonActionIndex = 0;

    /**
     * Action of the bottom right button
     */
    private int bottomRightButtonActionIndex = 0;

    /**
     * Selected Keys of the key selector
     */
    private String[] keys = {"A", "A", "A", "A"};

    /**
     * creates a save file with a name
     *
     * @param name name of the save file
     */
    RemoteSaveFile(String name) {
        super(name);
    }

    /**
     * Default constructor for file loading
     */
    public RemoteSaveFile() {
        super("");
    }

    /*
    *******************************************
            PUBLIC GETTERS AND SETTERS
    *******************************************
     */

    public int getTopLeftButtonActionIndex() {
        return topLeftButtonActionIndex;
    }

    public void setTopLeftButtonActionIndex(int topLeftButtonActionIndex) {
        this.topLeftButtonActionIndex = topLeftButtonActionIndex;
    }

    public int getTopRightButtonActionIndex() {
        return topRightButtonActionIndex;
    }

    public void setTopRightButtonActionIndex(int topRightButtonActionIndex) {
        this.topRightButtonActionIndex = topRightButtonActionIndex;
    }

    public int getBottomLeftButtonActionIndex() {
        return bottomLeftButtonActionIndex;
    }

    public void setBottomLeftButtonActionIndex(int bottomLeftButtonActionIndex) {
        this.bottomLeftButtonActionIndex = bottomLeftButtonActionIndex;
    }

    public int getBottomRightButtonActionIndex() {
        return bottomRightButtonActionIndex;
    }

    public void setBottomRightButtonActionIndex(int bottomRightButtonActionIndex) {
        this.bottomRightButtonActionIndex = bottomRightButtonActionIndex;
    }

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }
}
