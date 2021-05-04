package programs.instantButton.data;

import utils.saveFile.SaveFile;

/**
 * save file class for the instant button program
 */
@SuppressWarnings("unused")
public class InstantButtonSaveFile extends SaveFile {

    /**
     * file paths for the sounds of the buzzers
     */
    private String[] buzzerSounds = {"default", "default", "default"};

    /**
     * volumes for the sounds of the buzzers
     */
    private int[] volume = {100, 100, 100};

    /**
     * creates a save file with a name
     */
    public InstantButtonSaveFile() {
        super("instantButton");
    }

    /*
    *******************************************
            PUBLIC GETTERS AND SETTERS
    *******************************************
     */

    public String[] getBuzzerSounds() {
        return buzzerSounds;
    }

    public void setBuzzerSounds(String[] buzzerSounds) {
        this.buzzerSounds = buzzerSounds;
    }

    public int[] getVolume() {
        return volume;
    }

    public void setVolume(int[] volume) {
        this.volume = volume;
    }
}
