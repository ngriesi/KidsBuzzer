package programs.quiztime.main.control;

import presentationWindow.animations.AnimationQueue;
import savedataHandler.SaveDataHandler;

/**
 * class containing the buzzer states and
 */
public class BuzzerStateHandler {

    /**
     * states of the buzzers
     */
    private BuzzerState[] buzzerStates;

    /**
     * current press position
     */
    private int buzzerPositionPressed = 1;

    /**
     * press position that is on turn
     */
    private int buzzerOnTurn = 1;

    /**
     * view updater for updating both presentation and control view
     */
    private ViewUpdater viewUpdater;

    /**
     * creates a new buzzer state handler
     *
     * @param viewUpdater view updater used by the handler
     */
    BuzzerStateHandler(ViewUpdater viewUpdater) {
        this.viewUpdater = viewUpdater;
        buzzerStates = new BuzzerState[SaveDataHandler.MAX_BUZZER_COUNT];
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            buzzerStates[i] = new BuzzerState(i + 1);
        }
    }

    /**
     * action performed when a buzzer was pressed
     *
     * @param buzzerNumber number of the pressed buzzer
     * @param animationQueueItem
     */
    void press(int buzzerNumber, AnimationQueue.AnimationQueueItem animationQueueItem) {
        buzzerStates[buzzerNumber - 1].setPressed(true);
        buzzerStates[buzzerNumber - 1].setPosition(buzzerPositionPressed);
        buzzerPositionPressed++;

        for (BuzzerState buzzerState : buzzerStates) {
            if (buzzerState.isPressed() && buzzerState.getPosition() == (buzzerPositionPressed - 1)) {
                if (buzzerState.getPosition() == buzzerOnTurn) {
                    viewUpdater.firstBuzzerPress(buzzerState.getBuzzerNumber(),buzzerState.getPosition(), animationQueueItem);
                } else {
                    viewUpdater.followBuzzerPress(buzzerState.getBuzzerNumber(), buzzerState.getPosition(), animationQueueItem);
                }
            }
        }
    }

    /**
     * action performed when the wrong button gets pressed
     *
     * @param animationQueueItem item to queue the animation
     */
    void wrong(AnimationQueue.AnimationQueueItem animationQueueItem) {
        for (BuzzerState buzzerState : buzzerStates) {
            if (buzzerState.getPosition() == buzzerOnTurn) {
                viewUpdater.wrongAnswerGiven(buzzerState.getBuzzerNumber(), animationQueueItem);
                buzzerOnTurn++;
                break;
            }
        }

        for (BuzzerState buzzerState : buzzerStates) {
            if (buzzerState.getPosition() == buzzerOnTurn) {
                viewUpdater.newBuzzerOnTurn(buzzerState.getBuzzerNumber(),buzzerState.getPosition(), animationQueueItem);
            }
        }
    }

    /**
     * action performed when the right button gets pressed
     *
     * @param animationQueueItem item to queue the animation
     */
    void right(AnimationQueue.AnimationQueueItem animationQueueItem) {
        for (BuzzerState buzzerState : buzzerStates) {
            if (buzzerState.getPosition() == buzzerOnTurn) {
                viewUpdater.rightAnswerGiven(buzzerState.getBuzzerNumber(),animationQueueItem);
            }
        }
    }

    /**
     * resets the handler and all the buzzer states
     */
    public void reset() {
        buzzerPositionPressed = 1;
        buzzerOnTurn = 1;
        for (BuzzerState buzzerState : buzzerStates) {
            buzzerState.reset();
        }
    }
}
