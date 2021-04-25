package programs.testProgram.data;

import presentationWindow.items.Texture;
import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.ProgramModel;
import startupApp.LoadingHandler;
import utils.audioSystem.AudioClip;

import java.io.File;
import java.io.FileNotFoundException;

public class TestProgramModel extends ProgramModel<TestProgramSaveFile> {

    private Texture textTexture;

    private AudioClip testClip;


    public TestProgramModel() {
        super(TestProgramSaveFile.class);
    }

    @Override
    public void loadResources(LoadingHandler loadingHandler, OpenGlRenderer openGlRenderer) {

        new Thread(() -> testClip = AudioClip.load(new File(getSaveFile().getSound()), loadingHandler)).start();

        openGlRenderer.addActionToOpenGlThread(() -> {
            try {
                textTexture = Texture.loadTexture(new File(getSaveFile().getImage()),loadingHandler);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public Texture getTextTexture() {
        return textTexture;
    }

    public AudioClip getTestClip() {
        return testClip;
    }
}
