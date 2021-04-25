package utils.saveFile;

/**
 * handles save files and makes a rollback possible
 *
 * @param <T> type of the save file
 */
public class SaveFileHandler <T extends SaveFile> {

    /**
     * the data that actually gets saved
     */
    private T mainSaveFile;

    /**
     * temp data for rollback
     */
    private T rollbackSaveFile;

    /**
     * creates a save file handler
     *
     * @param saveFile save file
     */
    public SaveFileHandler(T saveFile) {
        this.mainSaveFile = saveFile;
        this.rollbackSaveFile = SaveFileCopy.copySaveFile(mainSaveFile);
    }

    /**
     * @return returns the save file
     */
    public T getSaveFile() {
        return rollbackSaveFile;
    }

    /**
     * moves the changes to the main save file
     */
    public void applyChanges() {
        mainSaveFile = SaveFileCopy.copySaveFile(rollbackSaveFile);
    }

    /**
     * rolls back the changes
     */
    public void rollbackChanges() {
        rollbackSaveFile = SaveFileCopy.copySaveFile(mainSaveFile);
    }

    /**
     * saves the data in a file
     */
    public void saveToFile() {
        mainSaveFile.saveFile();
    }

}
