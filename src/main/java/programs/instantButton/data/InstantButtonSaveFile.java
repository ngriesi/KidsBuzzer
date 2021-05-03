package programs.instantButton.data;

import utils.saveFile.SaveFile;

public class InstantButtonSaveFile extends SaveFile {

    private String[] buzzerSounds = {"default", "default", "default"};

    private int[] volume = {100, 100, 100};

    /**
     * creates a save file with a name
     */
    public InstantButtonSaveFile() {
        super("instantButton");
    }

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
