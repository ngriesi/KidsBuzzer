package assets.virtualBuzzers;

import assets.virtualBuzzers.chageHandlers.*;
import presentationWindow.animations.AnimationQueue;

/**
 * state handler for the virtual buzzer
 */
class VirtualBuzzerStateHandler {

    /**
     * possible states
     */
    public enum BuzzerState {
        INVISIBLE_DEFAULT, VISIBLE_DEFAULT, ON_TURN, PRESSED_NOT_ON_TURN, WRONG, RIGHT
    }

    /**
     * current state of the buzzer
     */
    private BuzzerState currentState = BuzzerState.INVISIBLE_DEFAULT;

    /**
     * handles state changes if the current state is <code>INVISIBLE_DEFAULT</code>
     */
    private ChangeFromInvisibleState changeFromInvisibleState;

    /**
     * handles state changes if the current state is <code>VISIBLE_DEFAULT</code>
     */
    private ChangeFromVisibleDefaultTo changeFromVisibleDefaultTo;

    /**
     * handles state changes if the current state is <code>ON_TURN</code>
     */
    private ChangeFromOnTurnTo changeFromOnTurnTo;

    /**
     * handles state changes if the current state is <code>PRESSED_NOT_ON_TURN</code>
     */
    private ChangeFromPressedNotOnTurnTo changeFromPressedNotOnTurnTo;

    /**
     * handles state changes if the current state is <code>WRONG</code>
     */
    private ChangeFromWrongTo changeFromWrongTo;

    /**
     * handles state changes if the current state is <code>RIGHT</code>
     */
    private ChangeFromRightTo changeFromRightTo;

    /**
     * passes a state change on to the specific state changer depending on the current
     * state
     *
     * @param to                 state if changes to
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    void changeState(BuzzerState to, AnimationQueue.AnimationQueueItem animationQueueItem) {
        switch (currentState) {
            case INVISIBLE_DEFAULT:
                changeFromInvisibleState.changeState(to, animationQueueItem);
                break;
            case VISIBLE_DEFAULT:
                changeFromVisibleDefaultTo.changeState(to, animationQueueItem);
                break;
            case ON_TURN:
                changeFromOnTurnTo.changeState(to, animationQueueItem);
                break;
            case PRESSED_NOT_ON_TURN:
                changeFromPressedNotOnTurnTo.changeState(to, animationQueueItem);
                break;
            case WRONG:
                changeFromWrongTo.changeState(to, animationQueueItem);
                break;
            case RIGHT:
                changeFromRightTo.changeState(to, animationQueueItem);
                break;
        }

        currentState = to;
    }


    /**
     * creates the buzzer state handlers for the virtual buzzer
     *
     * @param virtualBuzzer virtual buzzer this handler belongs to
     */
    VirtualBuzzerStateHandler(VirtualBuzzer virtualBuzzer) {
        changeFromInvisibleState = new ChangeFromInvisibleState(virtualBuzzer);
        changeFromVisibleDefaultTo = new ChangeFromVisibleDefaultTo(virtualBuzzer);
        changeFromOnTurnTo = new ChangeFromOnTurnTo(virtualBuzzer);
        changeFromPressedNotOnTurnTo = new ChangeFromPressedNotOnTurnTo(virtualBuzzer);
        changeFromWrongTo = new ChangeFromWrongTo(virtualBuzzer);
        changeFromRightTo = new ChangeFromRightTo(virtualBuzzer);
    }

    /**
     * updates the buzzer count of the state handlers
     *
     * @param buzzerCount new buzzer count
     */
    public void setBuzzerCount(int buzzerCount) {
        changeFromInvisibleState.setBuzzerCount(buzzerCount);
        changeFromVisibleDefaultTo.setBuzzerCount(buzzerCount);
        changeFromRightTo.setBuzzerCount(buzzerCount);
        changeFromWrongTo.setBuzzerCount(buzzerCount);
        changeFromPressedNotOnTurnTo.setBuzzerCount(buzzerCount);
        changeFromOnTurnTo.setBuzzerCount(buzzerCount);
    }
}
