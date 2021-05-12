package remoteHandler;

import utils.saveFile.SaveFile;

@SuppressWarnings({"unused", "WeakerAccess"})
public class RemoteSaveFile extends SaveFile {

    /**
     * Action of the top left button
     */
    private String topLeftButtonActionName = "Nichts";

    /**
     * Action of the top right button
     */
    private String topRightButtonActionName = "Nichts";

    /**
     * Action of the bottom left button
     */
    private String bottomLeftButtonActionName = "Nichts";

    /**
     * Action of the bottom right button
     */
    private String bottomRightButtonActionName = "Nichts";

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

    public String getTopLeftButtonActionName() {
        return topLeftButtonActionName;
    }

    public void setTopLeftButtonActionName(String topLeftButtonActionName) {
        this.topLeftButtonActionName = topLeftButtonActionName;
    }

    public String getTopRightButtonActionName() {
        return topRightButtonActionName;
    }

    public void setTopRightButtonActionName(String topRightButtonActionName) {
        this.topRightButtonActionName = topRightButtonActionName;
    }

    public String getBottomLeftButtonActionName() {
        return bottomLeftButtonActionName;
    }

    public void setBottomLeftButtonActionName(String bottomLeftButtonActionName) {
        this.bottomLeftButtonActionName = bottomLeftButtonActionName;
    }

    public String getBottomRightButtonActionName() {
        return bottomRightButtonActionName;
    }

    public void setBottomRightButtonActionName(String bottomRightButtonActionName) {
        this.bottomRightButtonActionName = bottomRightButtonActionName;
    }
}
