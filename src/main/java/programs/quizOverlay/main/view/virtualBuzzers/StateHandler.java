package programs.quizOverlay.main.view.virtualBuzzers;

import presentationWindow.animations.AnimationQueue;
import savedataHandler.SaveDataHandler;

/**
 * abstract super class for all state handlers of the <code>VirtualBuzzer</code>
 */
public abstract class StateHandler {

    /**
     * <code>VirtualBuzzer</code> this state handler belongs to
     */
    protected VirtualBuzzer virtualBuzzer;

    /**
     * index of the buzzer this handler belongs to
     */
    protected int index;

    /**
     * transparency of the color quad of the buzzer in its unpressed state
     */
    protected float unpressedTransparency = 0.5f;

    /**
     * the duration of all animations of the buzzer
     */
    protected int changeAnimationDuration = 60;

    /**
     * number of buzzers displayed in the view
     */
    protected int buzzerCount = SaveDataHandler.BUZZER_COUNT;

    /**
     * creates a new <code>StateHandler</code> and sets the <code>VirtualBuzzer</code>
     *
     * @param virtualBuzzer <code>VirtualBuzzer</code> this <code>StateHandler</code> belongs to
     */
    public StateHandler(VirtualBuzzer virtualBuzzer) {
        this.virtualBuzzer = virtualBuzzer;
        index = virtualBuzzer.getIndex();
    }

    /**
     * method handles the state changing by calling the specific methods for the individual state changes
     *
     * @param to                 the state the buzzer changes to
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    public void changeState(VirtualBuzzerStateHandler.BuzzerState to, AnimationQueue.AnimationQueueItem animationQueueItem) {
        switch (to) {
            case VISIBLE_DEFAULT:
                changeToVisibleDefault(animationQueueItem);
                break;
            case INVISIBLE_DEFAULT:
                changeToInvisibleDefault(animationQueueItem);
                break;
            case RIGHT:
                changeToRight(animationQueueItem);
                break;
            case WRONG:
                changeToWrong(animationQueueItem);
                break;
            case ON_TURN:
                changeToOnTurn(animationQueueItem);
                break;
            case PRESSED_NOT_ON_TURN:
                changeToPressedNotOnTurn(animationQueueItem);
                break;
        }
    }

    /**
     * Method called when the <code>VirtualBuzzer</code> changes its state to <code>PRESSED_NOT_ON_TURN</code>
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    protected abstract void changeToPressedNotOnTurn(AnimationQueue.AnimationQueueItem animationQueueItem);

    /**
     * Method called when the <code>VirtualBuzzer</code> changes its state to <code>ON_TURN</code>
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    protected abstract void changeToOnTurn(AnimationQueue.AnimationQueueItem animationQueueItem);

    /**
     * Method called when the <code>VirtualBuzzer</code> changes its state to <code>WRONG</code>
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    protected abstract void changeToWrong(AnimationQueue.AnimationQueueItem animationQueueItem);

    /**
     * Method called when the <code>VirtualBuzzer</code> changes its state to <code>RIGHT</code>
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    protected abstract void changeToRight(AnimationQueue.AnimationQueueItem animationQueueItem);

    /**
     * Method called when the <code>VirtualBuzzer</code> changes its state to <code>INVISIBLE_DEFAULT</code>
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    protected abstract void changeToInvisibleDefault(AnimationQueue.AnimationQueueItem animationQueueItem);

    /**
     * Method called when the <code>VirtualBuzzer</code> changes its state to <code>VISIBLE_DEFAULT</code>
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    protected abstract void changeToVisibleDefault(AnimationQueue.AnimationQueueItem animationQueueItem);

    /**
     * updates the buzzer count of this <code>StateHandler</code>
     *
     * @param buzzerCount new buzzer count
     */
    public void setBuzzerCount(int buzzerCount) {
        this.buzzerCount = buzzerCount;
    }
}
