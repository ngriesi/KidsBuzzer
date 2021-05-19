package programs.quiztime.main.control;

import controlWindow.MainController;
import presentationWindow.animations.AnimationQueue;
import programs.abstractProgram.Program;
import programs.quiztime.control.control.QuizTimeProgramControlController;
import programs.quiztime.control.control.SimpleOutputView;
import programs.quiztime.data.QuizTimeProgramModel;
import programs.quiztime.main.view.QuizTimeProgramPresentationView;
import programs.quiztime.settings.QuizTimeProgramSettingsController;
import remoteHandler.RemoteHandler;
import remoteHandler.actions.RemoteAction;
import savedataHandler.languages.Text;

/**
 * main class of the quiz time program
 */
public class QuizTimeProgram extends Program<QuizTimeProgramControlController, QuizTimeProgramSettingsController, QuizTimeProgramModel, QuizTimeProgramPresentationView> {


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
    public QuizTimeProgram() {
        super(true, Text.QUIZTIME);
        generalState = new GeneralState();
        stateChanger = new StateChanger(this, generalState);
    }

    /**
     * @return returns a newly created quiz time model
     */
    @Override
    public QuizTimeProgramModel createModel() {
        return new QuizTimeProgramModel();
    }

    /**
     * @return returns the newly created settings controller for the quiz time program
     */
    @Override
    public QuizTimeProgramSettingsController createSettingsController() {
        return new QuizTimeProgramSettingsController(this, getProgramModel());
    }

    /**
     * @return returns the newly created control controller for the quiz time program
     */
    @Override
    public QuizTimeProgramControlController createControlController() {
        return new QuizTimeProgramControlController(this, getProgramModel());
    }

    /**
     * @return returns the newly created presentation view of the quiz time app
     */
    @Override
    public QuizTimeProgramPresentationView createPresentationView() {
        return new QuizTimeProgramPresentationView(this);
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
     * starts the intro animation
     */
    public void introAnimation() {
        if (generalState.checkAndPerformAction(GeneralState.QuizAction.SHOW_TITLE)) {
            stateChanger.changeToIntro();
        }
    }

    /**
     * sets the question number
     *
     * @param number new question number
     */
    public void setQuestionNumber(int number) {
        generalState.setQuestionNumber(number);
    }

    /**
     * next button action
     */
    public void nextQuestion() {
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        if (generalState.checkAndPerformAction(GeneralState.QuizAction.NEXT_QUESTION, animationQueueItem)) {
            stateChanger.nextQuestion(animationQueueItem, () -> getProgramController().setQuestionNumber(generalState.getQuestionNumber() + 1));
        }
    }

    /**
     * fades out the presentation view
     *
     * @return returns the result of the check of the TO_INVISIBLE action
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
     * creates the actions that can be bound to the
     * buttons of the remote
     *
     * @param remoteHandler <code>RemoteHandler</code> of this program
     */
    @Override
    protected void createRemoteActions(RemoteHandler remoteHandler) {
        super.createRemoteActions(remoteHandler);

        remoteHandler.addRemoteAction(Text.NEXT_QUESTION, new RemoteAction(this::nextQuestion));
        remoteHandler.addRemoteAction(Text.RIGHT, new RemoteAction(this::rightAnswer));
        remoteHandler.addRemoteAction(Text.WRONG, new RemoteAction(this::wrongAnswer));
        remoteHandler.addRemoteAction(Text.SHOW_OR_HIDE, new RemoteAction(() -> getProgramController().showHideAction()));
    }
}
