package programs.quiztime.main.view.virtualBuzzers;

import programs.quiztime.main.view.AnimationQueue;
import programs.quiztime.main.view.virtualBuzzers.chageHandlers.*;

class VirtualBuzzerStateHandler {

    public enum BuzzerState {
        INVISIBLE_DEFAULT, VISIBLE_DEFAULT, ON_TURN, PRESSED_NOT_ON_TURN, WRONG, RIGHT
    }

    private BuzzerState currentState = BuzzerState.INVISIBLE_DEFAULT;

    private ChangeFromInvisibleState changeFromInvisibleState;

    private ChangeFromVisibleDefaultTo changeFromVisibleDefaultTo;

    private ChangeFromOnTurnTo changeFromOnTurnTo;

    private ChangeFromPressedNotOnTurnTo changeFromPressedNotOnTurnTo;

    private ChangeFromWrongTo changeFromWrongTo;

    private ChangeFromRightTo changeFromRightTo;

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


    VirtualBuzzerStateHandler(VirtualBuzzer virtualBuzzer) {
        changeFromInvisibleState = new ChangeFromInvisibleState(virtualBuzzer);
        changeFromVisibleDefaultTo = new ChangeFromVisibleDefaultTo(virtualBuzzer);
        changeFromOnTurnTo = new ChangeFromOnTurnTo(virtualBuzzer);
        changeFromPressedNotOnTurnTo = new ChangeFromPressedNotOnTurnTo(virtualBuzzer);
        changeFromWrongTo = new ChangeFromWrongTo(virtualBuzzer);
        changeFromRightTo = new ChangeFromRightTo(virtualBuzzer);
    }

    public void setBuzzerCount(int buzzerCount) {
        changeFromInvisibleState.setBuzzerCount(buzzerCount);
        changeFromVisibleDefaultTo.setBuzzerCount(buzzerCount);
        changeFromRightTo.setBuzzerCount(buzzerCount);
        changeFromWrongTo.setBuzzerCount(buzzerCount);
        changeFromPressedNotOnTurnTo.setBuzzerCount(buzzerCount);
        changeFromOnTurnTo.setBuzzerCount(buzzerCount);
    }
}
