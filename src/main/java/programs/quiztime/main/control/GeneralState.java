package programs.quiztime.main.control;

import presentationWindow.animations.AnimationQueue;
import programs.quizPrograms.main.control.QuizGeneralState;
import programs.quizPrograms.main.control.QuizStateChanger;

/**
 * general state of the program
 */
class GeneralState extends QuizGeneralState {

    /**
     * true if the title is currently visible
     */
    private boolean title = false;

    /**
     * tracks the question number
     */
    private int questionNumber = 1;

    /**
     * constructor settings the default value for the question flag
     */
    public GeneralState() {
        question = false;
    }

    @Override
    protected boolean nextQuestionCheck() {
        return (right || question || title) && !invisible;
    }

    @Override
    protected boolean showTitleCheck() {
        return invisible;
    }

    @Override
    protected boolean rightCheck() {
        return !title && !invisible && buzzerActive > 0 && !right;
    }

    @Override
    protected boolean wrongCheck() {
        return !right && !invisible && !title && buzzerActive > 0;
    }

    @Override
    protected boolean buzzerPressCheck() {
        return !right && !invisible && !title && buzzerReady;
    }

    @Override
    protected void nextQuestionAction(AnimationQueue.AnimationQueueItem animationQueueItem) {
        buzzerReady = false;
        animationQueueItem.addOnFinishedAction(() -> {
            question = true;
            title = false;
            right = false;
            buzzerActive = 0;
            questionNumber++;
            buzzerReady = true;
        });
    }

    @Override
    protected void rightAction() {
        question = false;
        right = true;
        buzzerReady = false;
    }

    @Override
    protected void wrongAction() {
        buzzerActive--;
    }

    @Override
    protected void buzzerPressAction() {
        buzzerActive++;
    }

    /**
     * changes the state to the invisible state
     *
     * @param animationQueueItem animation queue item in which on finished action this gets performed
     * @param stateChanger       state changer of the quiztime program
     */
    @Override
    public void changeToInvisibleState(AnimationQueue.AnimationQueueItem animationQueueItem, QuizStateChanger stateChanger) {
        buzzerReady = false;
        animationQueueItem.addOnFinishedAction(() -> {
            invisible = true;
            question = false;
            right = false;
            title = false;
            stateChanger.reset();
        });
    }

    /**
     * changes the state to the intro state
     */
    void changeToIntroState() {
        title = true;
        invisible = false;
    }

    /**
     * @return returns the question number for the next question
     */
    int getQuestionNumber() {
        return questionNumber;
    }

    /**
     * sets a new question number
     *
     * @param questionNumber new question number
     */
    void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }
}
