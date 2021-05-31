package programs.quizOverlay.main.control;

import programs.quizOverlay.control.control.QuizOverlayProgramController;
import programs.quizOverlay.data.QuizOverlayModel;
import presentationWindow.animations.AnimationQueue;
import programs.quizOverlay.main.view.QuizOverlayPresentationView;
import programs.quizPrograms.main.control.QuizProgram;
import programs.quizPrograms.settings.QuizSettingsController;
import savedataHandler.languages.Text;

/**
 * main class of the quiz time program
 */
public class QuizOverlayProgram extends QuizProgram<QuizOverlayProgramController, QuizSettingsController, QuizOverlayModel, QuizOverlayPresentationView> {

    /**
     * creates a new program
     */
    public QuizOverlayProgram() {
        super(true, Text.QUIZ_OVERLAY);
        quizGeneralState = new GeneralState();
        quizStateChanger = new StateChanger(this, quizGeneralState);
    }

    /**
     * @return returns a newly created quiz time model
     */
    @Override
    public QuizOverlayModel createModel() {
        return new QuizOverlayModel();
    }

    /**
     * @return returns the newly created settings controller for the quiz time program
     */
    @Override
    public QuizSettingsController createSettingsController() {
        return new QuizSettingsController(this, getProgramModel());
    }

    /**
     * @return returns the newly created control controller for the quiz time program
     */
    @Override
    public QuizOverlayProgramController createControlController() {
        return new QuizOverlayProgramController(this, getProgramModel());
    }

    /**
     * @return returns the newly created presentation view of the quiz time app
     */
    @Override
    public QuizOverlayPresentationView createPresentationView() {
        return new QuizOverlayPresentationView(this);
    }

    /**
     * next button action
     */
    @Override
    public void nextQuestion() {
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        if (quizGeneralState.checkAndPerformAction(GeneralState.QuizAction.NEXT_QUESTION, animationQueueItem)) {
            quizStateChanger.nextQuestion(animationQueueItem, null);
        }
    }

    /**
     * fade in animation
     */
    public void fadeIn() {
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        if (quizGeneralState.checkAndPerformAction(GeneralState.QuizAction.FADE_IN, animationQueueItem)) {
            quizStateChanger.nextQuestion(animationQueueItem, null);
        }
    }




}
