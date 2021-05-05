package assets.virtualBuzzers.chageHandlers;

import assets.virtualBuzzers.StateHandler;
import assets.virtualBuzzers.VirtualBuzzer;
import presentationWindow.animations.AnimationQueue;
import presentationWindow.assets.Color;
import savedataHandler.SaveDataHandler;

/**
 * <code>StateHandler</code> that changes the state of the buzzer from the <code>INVISIBLE_DEFAULT</code> state
 * to any other state
 */
public class ChangeFromWrongTo extends StateHandler {

    public ChangeFromWrongTo(VirtualBuzzer virtualBuzzer) {
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

    /**
     * Method called when the <code>VirtualBuzzer</code> changes its state from <code>WRONG</code>
     * to <code>INVISIBLE_DEFAULT</code>
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    @Override
    protected void changeToInvisibleDefault(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzer.fadeOutNumber(changeAnimationDuration, animationQueueItem);
        virtualBuzzer.fadeOutIcon(changeAnimationDuration, animationQueueItem);
        virtualBuzzer.moveAndScale((1f + index * 2f) / (buzzerCount * 2f), 1.15f, 1f / buzzerCount, 0.3f, changeAnimationDuration, animationQueueItem);
        virtualBuzzer.fadeOutQuad(changeAnimationDuration, animationQueueItem).addOnFinishedAction(() -> virtualBuzzer.reset());
    }

    /**
     * Method called when the <code>VirtualBuzzer</code> changes its state from <code>WRONG</code>
     * to <code>VISIBLE_DEFAULT</code>
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    @Override
    protected void changeToVisibleDefault(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzer.moveAndScale((1f + index * 2f) / (buzzerCount * 2f), 0.85f, 1f / buzzerCount, 0.3f, changeAnimationDuration, animationQueueItem);

        virtualBuzzer.fadeInQuad(changeAnimationDuration, animationQueueItem);
        virtualBuzzer.fadeOutNumber(changeAnimationDuration / 2, animationQueueItem).addOnFinishedAction(() -> virtualBuzzer.fadeInIcon(changeAnimationDuration / 2, animationQueueItem));

        Color unpressed = new Color(SaveDataHandler.BUZZER_COLORS_PRESSED[index]);
        unpressed.setAlpha(unpressedTransparency);

        virtualBuzzer.fadeColor(unpressed, changeAnimationDuration, animationQueueItem);
    }
}
