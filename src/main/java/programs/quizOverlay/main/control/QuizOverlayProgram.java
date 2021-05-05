package programs.quizOverlay.main.control;

import controlWindow.MainController;
import programs.abstractProgram.Program;
import programs.quizOverlay.control.control.QuizOverlayProgramController;
import programs.quizOverlay.control.control.SimpleOutputView;
import programs.quizOverlay.data.QuizOverlayModel;
import presentationWindow.animations.AnimationQueue;
import programs.quizOverlay.main.view.QuizOverlayPresentationView;
import programs.quizOverlay.settings.QuizOverlaySettingsController;

/**
 * main class of the quiz time program
 */
public class QuizOverlayProgram extends Program<QuizOverlayProgramController, QuizOverlaySettingsController, QuizOverlayModel, QuizOverlayPresentationView> {


    /**
     * handles the general state of the program
     */
    private GeneralState generalState;

    /**
     * handles the state changing
     */
    private StateChanger stateChanger;

    /**
     * creates a new program
     */
    public QuizOverlayProgram() {
        //noinspection SpellCheckingInspection
        super(true, "Quizoverlay");
        generalState = new GeneralState();
        stateChanger = new StateChanger(this, generalState);
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
    public QuizOverlaySettingsController createSettingsController() {
        return new QuizOverlaySettingsController(this, getProgramModel());
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
     * action of the buzzer
     *
     * @param buzzerNumber number of the buzzer pressed
     */
    @Override
    protected void buzzerAction(int buzzerNumber) {
        if (generalState.checkAndPerformAction(GeneralState.QuizAction.BUZZER_PRESS)) {
            stateChanger.buzzerPressed(buzzerNumber, new AnimationQueue.AnimationQueueItem());
        }
    }

    /**
     * action of the wrong button
     */
    public void wrongAnswer() {
        if (generalState.checkAndPerformAction(GeneralState.QuizAction.WRONG)) {
            stateChanger.wrongAnswerGiven();
        }
    }

    /**
     * action of the right button
     */
    public void rightAnswer() {
        if (generalState.checkAndPerformAction(GeneralState.QuizAction.RIGHT)) {
            stateChanger.rightAnswerGiven();
        }
    }

    /**
     * next button action
     */
    public void nextQuestion() {
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        if (generalState.checkAndPerformAction(GeneralState.QuizAction.NEXT_QUESTION, animationQueueItem)) {
            stateChanger.nextQuestion(animationQueueItem);
        }
    }

    /**
     * fade in animation
     */
    public void fadeIn() {
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        if (generalState.checkAndPerformAction(GeneralState.QuizAction.FADE_IN, animationQueueItem)) {
            stateChanger.nextQuestion(animationQueueItem);
        }
    }

    /**
     * fades out the presentation view
     */
    public boolean fadeOut() {
        boolean result = generalState.checkAndPerformAction(GeneralState.QuizAction.TO_INVISIBLE);
        if (result) {
            stateChanger.fadeToInvisible();
        }
        return result;
    }

    /**
     * updates the controlModel of the stateChanger
     *
     * @param mainController main controller of the control window
     */
    @Override
    public void setMainController(MainController mainController) {
        super.setMainController(mainController);
        stateChanger.setMainController(mainController);
    }

    /**
     * updates the number of buzzers
     */
    @Override
    public void updateBuzzerCount() {
        getProgramController().setSimpleOutputView(new SimpleOutputView(getProgramController()));
        getProgramController().getProgramView().repaint();
        getProgramPresentationView().updateBuzzerCount();
    }

    /**
     * method called through the naive key listener when a key gets released
     *
     * @param keyCode code of the key that was released
     */
    @Override
    public void nativeKeyAction(int keyCode) {
        getProgramController().nativeKeyAction(keyCode);
    }
}
