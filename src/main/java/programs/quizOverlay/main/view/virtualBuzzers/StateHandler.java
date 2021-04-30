package programs.quizOverlay.main.view.virtualBuzzers;

import presentationWindow.animations.AnimationQueue;
import savedataHandler.SaveDataHandler;

public abstract class StateHandler {

    protected VirtualBuzzer virtualBuzzer;

    protected int index;

    protected float unpressedTransparency = 0.5f;

    protected int changeAnimationDuration = 60;

    protected int buzzerCount = SaveDataHandler.BUZZER_COUNT;

    public StateHandler(VirtualBuzzer virtualBuzzer) {
        this.virtualBuzzer = virtualBuzzer;
        index = virtualBuzzer.getIndex();
    }

    public void changeState(VirtualBuzzerStateHandler.BuzzerState to, AnimationQueue.AnimationQueueItem animationQueueItem) {
        switch (to) {
            case VISIBLE_DEFAULT: changeToVisibleDefault(animationQueueItem); break;
            case INVISIBLE_DEFAULT: changeToInvisibleDefault(animationQueueItem); break;
            case RIGHT: changeToRight(animationQueueItem); break;
            case WRONG: changeToWrong(animationQueueItem); break;
            case ON_TURN: changeToOnTurn(animationQueueItem); break;
            case PRESSED_NOT_ON_TURN: changeToPressedNotOnTurn(animationQueueItem); break;
        }
    }

    protected abstract void changeToPressedNotOnTurn(AnimationQueue.AnimationQueueItem animationQueueItem);

    protected abstract void changeToOnTurn(AnimationQueue.AnimationQueueItem animationQueueItem);

    protected abstract void changeToWrong(AnimationQueue.AnimationQueueItem animationQueueItem);

    protected abstract void changeToRight(AnimationQueue.AnimationQueueItem animationQueueItem);

    protected abstract void changeToInvisibleDefault(AnimationQueue.AnimationQueueItem animationQueueItem);

    protected abstract void changeToVisibleDefault(AnimationQueue.AnimationQueueItem animationQueueItem);

    public void setBuzzerCount(int buzzerCount) {
        this.buzzerCount = buzzerCount;
    }
}
