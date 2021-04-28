package programs.quiztime.main.view.virtualBuzzers.chageHandlers;

import programs.quiztime.main.view.AnimationQueue;
import programs.quiztime.main.view.virtualBuzzers.StateHandler;
import programs.quiztime.main.view.virtualBuzzers.VirtualBuzzer;

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

    @Override
    protected void changeToVisibleDefault(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzer.moveAndScale((1f + index * 2f) / (buzzerCount * 2f), 0.85f, 1f / buzzerCount, 0.3f, changeAnimationDuration, animationQueueItem);
        virtualBuzzer.fadeInIcon(virtualBuzzer.getChangeAnimationDuration(), animationQueueItem);
        virtualBuzzer.fadeInQuad(virtualBuzzer.getChangeAnimationDuration(), animationQueueItem);
    }
}
