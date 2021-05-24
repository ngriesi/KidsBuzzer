package programs.quizPrograms.main.control;

import presentationWindow.animations.AnimationQueue;

public abstract class QuizGeneralState {

    /**
     * true if the program is currently in the question view
     */
    protected boolean question = true;

    /**
     * true if the output screen is invisible
     */
    protected boolean invisible = true;

    /**
     * true if the right view is currently displayed
     */
    protected boolean right = false;

    /**
     * true if a buzzer is currently pressed and active
     */
    protected int buzzerActive = 0;

    /**
     * true if buzzers can currently be pressed
     */
    protected boolean buzzerReady = false;

    /**
     * possible actions that get checked with the current state
     */
    public enum QuizAction {
        BUZZER_PRESS, SHOW_TITLE, RIGHT, WRONG, NEXT_QUESTION, TO_INVISIBLE, FADE_IN
    }

    public abstract void changeToInvisibleState(AnimationQueue.AnimationQueueItem animationQueueItem, QuizStateChanger stateChanger);

    /**
     * checks the action and performs it if the check returns true
     *
     * @param action             action to check and perform
     * @param animationQueueItem animation queue item
     * @return the result of the check of the action
     */
    public boolean checkAndPerformAction(QuizAction action, AnimationQueue.AnimationQueueItem animationQueueItem) {
        boolean result = checkAction(action);
        if (result) {
            performAction(action, animationQueueItem);
        }
        return result;
    }

    /**
     * performs the given action in the general state
     *
     * @param action             action to be performed
     * @param animationQueueItem animation queue item
     */
    private void performAction(QuizAction action, AnimationQueue.AnimationQueueItem animationQueueItem) {
        switch (action) {
            case BUZZER_PRESS:
                buzzerPressAction();
                break;
            case WRONG:
                wrongAction();
                break;
            case RIGHT:
                rightAction();
                break;
            case SHOW_TITLE:
                showTitleAction();
            case TO_INVISIBLE:
                toInvisibleAction();
                break;
            case NEXT_QUESTION:
                nextQuestionAction(animationQueueItem);
                break;
            case FADE_IN:
                fadeInAction(animationQueueItem);

        }
    }

    protected void fadeInAction(AnimationQueue.AnimationQueueItem animationQueueItem) {

    }

    protected void nextQuestionAction(AnimationQueue.AnimationQueueItem animationQueueItem) {

    }

    protected void toInvisibleAction() {

    }

    protected void showTitleAction() {

    }

    protected void rightAction() {

    }

    protected void wrongAction() {

    }

    protected void buzzerPressAction() {

    }

    /**
     * checks the action and performs it if the check returns true
     *
     * @param action action to check and perform
     * @return the result of the action check
     */
    public boolean checkAndPerformAction(QuizAction action) {
        return checkAndPerformAction(action, null);
    }

    /**
     * checks an action if it should be performed
     *
     * @param action action to check
     * @return true if the action can be performed
     */
    protected boolean checkAction(QuizAction action) {
        switch (action) {
            case BUZZER_PRESS:
                return buzzerPressCheck();
            case WRONG:
                return wrongCheck();
            case RIGHT:
                return rightCheck();
            case SHOW_TITLE:
                return showTitleCheck();
            case NEXT_QUESTION:
                return nextQuestionCheck();
            case TO_INVISIBLE:
                return toInvisibleCheck();
            case FADE_IN:
                return fadeInCheck();
        }

        return false;
    }

    protected boolean fadeInCheck() {
        return false;
    }

    protected boolean toInvisibleCheck() {
        return !invisible;
    }

    protected boolean nextQuestionCheck() {
        return false;
    }

    protected boolean showTitleCheck() {
        return false;
    }

    protected boolean rightCheck() {
        return false;
    }

    protected boolean wrongCheck() {
        return false;
    }

    protected boolean buzzerPressCheck() {
        return false;
    }
}
