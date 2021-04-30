package programs.quiztime.main.view.virtualBuzzers.chageHandlers;

import presentationWindow.animations.AnimationQueue;
import presentationWindow.assets.Color;
import programs.quiztime.main.view.virtualBuzzers.StateHandler;
import programs.quiztime.main.view.virtualBuzzers.VirtualBuzzer;
import savedataHandler.SaveDataHandler;

public class ChangeFromOnTurnTo extends StateHandler {

    public ChangeFromOnTurnTo(VirtualBuzzer virtualBuzzer) {
        super(virtualBuzzer);
    }

    @Override
    protected void changeToPressedNotOnTurn(AnimationQueue.AnimationQueueItem animationQueueItem) {}

    @Override
    protected void changeToOnTurn(AnimationQueue.AnimationQueueItem animationQueueItem) {}

    @Override
    protected void changeToWrong(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzer.moveAndScale((1f + index * 2f) / (buzzerCount * 2f), 0.85f, 1f / buzzerCount, 0.3f, changeAnimationDuration, animationQueueItem);
        Color disabled = new Color(SaveDataHandler.BUZZER_COLORS_DISABLED[index]);
        disabled.setAlpha(unpressedTransparency);
        virtualBuzzer.fadeColor(disabled,changeAnimationDuration, animationQueueItem);
    }

    @Override
    protected void changeToRight(AnimationQueue.AnimationQueueItem animationQueueItem) {

        virtualBuzzer.getColorQuad().setManualDepth(5);
        virtualBuzzer.getNumber().setManualDepth(6);
        virtualBuzzer.getIcon().setManualDepth(7);

        virtualBuzzer.moveAndScale(0.5f,0.65f,0.5f,0.5f,changeAnimationDuration, animationQueueItem);

        virtualBuzzer.fadeOutNumber(changeAnimationDuration/2, animationQueueItem).addOnFinishedAction(() -> {
            virtualBuzzer.getIcon().setOpacity(0);
            virtualBuzzer.fadeInIcon(changeAnimationDuration/2, animationQueueItem);
        });
    }

    @Override
    protected void changeToInvisibleDefault(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzer.fadeOutNumber(changeAnimationDuration, animationQueueItem);
        virtualBuzzer.fadeOutIcon(changeAnimationDuration, animationQueueItem);
        virtualBuzzer.moveAndScale((1f + index * 2f) / (buzzerCount * 2f), 1.15f, 1f / buzzerCount, 0.3f, changeAnimationDuration, animationQueueItem);
        virtualBuzzer.fadeOutQuad(changeAnimationDuration, animationQueueItem).addOnFinishedAction(() -> virtualBuzzer.reset());
    }

    @Override
    protected void changeToVisibleDefault(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzer.moveAndScale((1f + index * 2f) / (buzzerCount * 2f), 0.85f, 1f / buzzerCount, 0.3f, changeAnimationDuration, animationQueueItem);
        virtualBuzzer.fadeOutNumber(changeAnimationDuration / 2, animationQueueItem).addOnFinishedAction(() -> virtualBuzzer.fadeInIcon(changeAnimationDuration/2, animationQueueItem));
        Color disabled = new Color(SaveDataHandler.BUZZER_COLORS_PRESSED[index]);
        disabled.setAlpha(unpressedTransparency);
        virtualBuzzer.fadeColor(disabled,changeAnimationDuration, animationQueueItem);
    }

}
