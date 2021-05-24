package programs.quizOverlay.control.control;

import programs.quizOverlay.data.QuizOverlayModel;
import programs.quizOverlay.main.control.QuizOverlayProgram;
import programs.quizPrograms.control.control.QuizControlController;
import programs.quizPrograms.control.control.SimpleOutputView;
import programs.quizPrograms.control.view.QuizControlView;

/**
 * controller of the control panel of the quiz time program
 */
public class QuizOverlayProgramController extends QuizControlController<QuizOverlayProgram, QuizControlView, QuizOverlayModel> {


    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public QuizOverlayProgramController(QuizOverlayProgram program, QuizOverlayModel programModel) {
        super(program, programModel);
    }

    /**
     * @return returns the newly created view
     */
    @Override
    protected QuizControlView createQuizView() {
        if (simpleOutputView == null) {
            simpleOutputView = new SimpleOutputView(this);
        }
        return new QuizControlView<>(this);
    }
}
