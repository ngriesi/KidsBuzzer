package programs.quizOverlay.main.control;

import presentationWindow.animations.AnimationQueue;

/**
 * general state of the program
 */
class GeneralState {

    /**
     * possible actions that get checked with the current state
     */
    enum QuizAction {
        BUZZER_PRESS, RIGHT, WRONG, NEXT_QUESTION, TO_INVISIBLE, FADE_IN
    }

    /**
     * true if the program is currently in the question view
     */
    private boolean question = true;

    /**
     * true if the output screen is invisible
     */
    private boolean invisible = true;

    /**
     * true if the right view is currently displayed
     */
    private boolean right= false;

    /**
     * true if a buzzer is currently pressed and active
     */
    private int buzzerActive = 0;

    /**
     * true if buzzers can currently be pressed
     */
    private boolean buzzerReady = false;

    /**
     * checks an action if it should be performed
     *
     * @param action action to check
     * @return true if the action can be performed
     */
    private boolean checkAction(QuizAction action) {
        switch (action) {
            case BUZZER_PRESS:
                return !right && !invisible  && buzzerReady;
            case WRONG:
                return !right && !invisible && buzzerActive > 0;
            case RIGHT:
                return !invisible && buzzerActive > 0 && !right;
            case NEXT_QUESTION:
                return (right || question) && !invisible;
            case TO_INVISIBLE:
                return !invisible;
            case FADE_IN:
                return invisible;

        }

        return false;
    }

    /**
     * checks the action and performs it if the check returns true
     *
     * @param action action to check and perform
     * @param animationQueueItem animation queue item
     */
    boolean checkAndPerformAction(QuizAction action, AnimationQueue.AnimationQueueItem animationQueueItem) {
        boolean result = checkAction(action);
        if(result) {
            performAction(action, animationQueueItem);
        }
        return result;
    }

    /**
     * checks the action and performs it if the check returns true
     *
     * @param action action to check and perform
     */
    boolean checkAndPerformAction(QuizAction action) {
        return checkAndPerformAction(action, null);
    }

    /**
     * performs the given action in the general state
     *
     * @param action action to be performed
     * @param animationQueueItem animation queue item
     */
    private void performAction(QuizAction action, AnimationQueue.AnimationQueueItem animationQueueItem) {
        switch (action) {
            case BUZZER_PRESS:
                addActiveBuzzer(); break;
            case WRONG:
                removeActiveBuzzer(); break;
            case RIGHT:
                changeToRightState(); break;
            case NEXT_QUESTION:
            case FADE_IN:
                changeToQuestionState(animationQueueItem); break;
        }
    }

    /**
     * changes the state to the invisible state
     *
     * @param animationQueueItem animation queue item in which on finished action this gets performed
     * @param stateChanger state changer of the quiztime program
     */
    void changeToInvisibleState(AnimationQueue.AnimationQueueItem animationQueueItem, StateChanger stateChanger) {
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
        invisible = false;
        animationQueueItem.addOnFinishedAction(() -> {
            question = true;
            right = false;
            buzzerActive = 0;
            buzzerReady = true;
        });
    }

    /**
     * changes the state to the right state
     */
    private void changeToRightState() {
        question = false;
        right = true;
        buzzerReady = false;
    }

    /**
     * adds an active buzzer
     */
    private void addActiveBuzzer() {
        buzzerActive++;
    }

    /**
     * removes an active buzzer
     */
    private void removeActiveBuzzer() {
        buzzerActive--;
    }
}
