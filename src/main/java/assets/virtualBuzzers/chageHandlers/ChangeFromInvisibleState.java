package assets.virtualBuzzers.chageHandlers;

import assets.virtualBuzzers.StateHandler;
import assets.virtualBuzzers.VirtualBuzzer;
import presentationWindow.animations.AnimationQueue;

/**
 * <code>StateHandler</code> that changes the state of the buzzer from the <code>INVISIBLE_DEFAULT</code> state
 * to any other state
 */
public class ChangeFromInvisibleState extends StateHandler {

    public ChangeFromInvisibleState(VirtualBuzzer virtualBuzzer) {
        super(virtualBuzzer);
    }

    @Override
    protected void changeToPressedNotOnTurn(AnimationQueue.AnimationQueueItem animationQueueItem) {

    }

    @Override
    protected void changeToOnTurn(AnimationQueue.AnimationQueueItem animationQueueItem) {

    }

    @Override
    protected void changeToWrong(AnimationQueue.AnimationQueueItem animationQueueItem) {

    }

    @Override
    protected void changeToRight(AnimationQueue.AnimationQueueItem animationQueueItem) {

    }

    @Override
    protected void changeToInvisibleDefault(AnimationQueue.AnimationQueueItem animationQueueItem) {

    }

    /**
     * Method called when the <code>VirtualBuzzer</code> changes its state from <code>INVISIBLE_DEFAULT</code>
     * to <code>VISIBLE_DEFAULT</code>
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    @Override
    protected void changeToVisibleDefault(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzer.moveAndScale((1f + index * 2f) / (buzzerCount * 2f), 0.85f, 1f / buzzerCount, 0.3f, changeAnimationDuration, animationQueueItem);
        virtualBuzzer.fadeInIcon(virtualBuzzer.getChangeAnimationDuration(), animationQueueItem);
        virtualBuzzer.fadeInQuad(virtualBuzzer.getChangeAnimationDuration(), animationQueueItem);
    }
}
