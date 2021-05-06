package programs.testProgram.data;

import utils.saveFile.SaveFile;

/**
 * Save file of the test program
 */
public class TestProgramSaveFile extends SaveFile {

    /**
     * unused saved audio file path
     */
    private String sound = "WrongAnswer.wav";

    /**
     * unused saved image file path
     */
    @SuppressWarnings("SpellCheckingInspection")
    private String image = "anktarktis.jpg";

    /**
     * creates a save file with a name
     */
    public TestProgramSaveFile() {
        super("test");
    }

    /*
    *******************************************
            PUBLIC GETTERS AND SETTERS
    *******************************************
     */
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

}
