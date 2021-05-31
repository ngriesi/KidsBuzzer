package utils.save;

/**
 * Handles save files and makes a rollback possible
 */
public class SaveFileHandler {

    /**
     * the data that actually gets saved
     */
    private SaveFile mainSaveFile;

    /**
     * temp data for rollback
     */
    private SaveFile rollbackSaveFile;

    /**
     * creates a save file handler
     *
     * @param saveFile save file
     */
    public SaveFileHandler(SaveFile saveFile) {
        this.mainSaveFile = saveFile;
        this.rollbackSaveFile = new SaveFile(mainSaveFile);
    }

    /**
     * @return returns the save file
     */
    public SaveFile getSaveFile() {
        return rollbackSaveFile;
    }

    /**
     * moves the changes to the main save file
     */
    public void applyChanges() {
        mainSaveFile = new SaveFile(rollbackSaveFile);
    }

    /**
     * rolls back the changes
     */
    public void rollbackChanges() {
        rollbackSaveFile = new SaveFile(mainSaveFile);
    }

    /**
     * saves the data in a file
     */
    public void saveToFile() {
        mainSaveFile.saveFile();
    }

    public SaveFile getMainSaveFile() {
        return mainSaveFile;
    }
}
