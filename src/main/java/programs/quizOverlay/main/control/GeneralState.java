package programs.quizOverlay.main.control;

import presentationWindow.animations.AnimationQueue;
import programs.quizPrograms.main.control.QuizGeneralState;
import programs.quizPrograms.main.control.QuizStateChanger;

/**
 * general state of the program
 */
class GeneralState extends QuizGeneralState {

    @Override
    protected boolean fadeInCheck() {
        return invisible;
    }

    @Override
    protected boolean nextQuestionCheck() {
        return (right || question) && !invisible;
    }

    @Override
    protected boolean rightCheck() {
        return !invisible && buzzerActive > 0 && !right;
    }

    @Override
    protected boolean wrongCheck() {
        return !right && !invisible && buzzerActive > 0;
    }

    @Override
    protected boolean buzzerPressCheck() {
        return !right && !invisible && buzzerReady;
    }

    @Override
    protected void nextQuestionAction(AnimationQueue.AnimationQueueItem animationQueueItem) {
        changeToQuestionState(animationQueueItem);
    }

    @Override
    protected void fadeInAction(AnimationQueue.AnimationQueueItem animationQueueItem) {
        changeToQuestionState(animationQueueItem);
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
            stateChanger.reset();
        });
    }

    /**
     * change the state to the question state
     *
     * @param animationQueueItem animation queue item in which on finished action this gets performed
     */
    private void changeToQuestionState(AnimationQueue.AnimationQueueItem animationQueueItem) {
        buzzerReady = false;
        animationQueueItem.addOnFinishedAction(() -> {
            question = true;
            right = false;
            buzzerActive = 0;
            buzzerReady = true;
            invisible = false;
        });
    }
}
