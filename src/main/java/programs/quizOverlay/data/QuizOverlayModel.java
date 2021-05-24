package programs.quizOverlay.data;

import presentationWindow.window.OpenGlRenderer;
import programs.quizPrograms.data.QuizModel;
import programs.quizPrograms.data.QuizSaveFile;
import programs.quizPrograms.main.control.MidiHandler;
import startupApp.LoadingHandler;

/**
 * handles the resources of the quiztime program
 */
public class QuizOverlayModel extends QuizModel<QuizSaveFile> {

    /**
     * creates a new Program model
     */
    public QuizOverlayModel() {
        super(QuizSaveFile.class);
    }

    @Override
    public void loadResources(LoadingHandler loadingHandler, OpenGlRenderer openGlRenderer) {
        midiHandler = new MidiHandler(getSaveFile());
        super.loadResources(loadingHandler, openGlRenderer);
    }
}
