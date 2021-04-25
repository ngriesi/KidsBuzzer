package programs.testProgram.data;

import utils.saveFile.SaveFile;

public class TestProgramSaveFile extends SaveFile {

    private String sound = "WrongAnswer.wav";

    private String image = "anktarktis.jpg";

    /**
     * creates a save file with a name
     *
     */
    public TestProgramSaveFile() {
        super("test");
    }

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
