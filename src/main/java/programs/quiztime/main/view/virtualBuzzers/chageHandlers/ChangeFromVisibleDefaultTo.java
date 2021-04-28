package programs.quiztime.main.view.virtualBuzzers.chageHandlers;

import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import programs.quiztime.main.view.AnimationQueue;
import programs.quiztime.main.view.virtualBuzzers.StateHandler;
import programs.quiztime.main.view.virtualBuzzers.VirtualBuzzer;
import savedataHandler.SaveDataHandler;

public class ChangeFromVisibleDefaultTo extends StateHandler {

    public ChangeFromVisibleDefaultTo(VirtualBuzzer virtualBuzzer) {
        super(virtualBuzzer);
    }

    @Override
    protected void changeToPressedNotOnTurn(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzer.getColorQuad().setColorScheme(new ColorScheme(new Color(SaveDataHandler.BUZZER_COLORS_PRESSED[index])));
        virtualBuzzer.getIcon().setOpacity(0f);
        virtualBuzzer.getNumber().setOpacity(1f);
    }

    @Override
    protected void changeToOnTurn(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzer.getColorQuad().setColorScheme(new ColorScheme(new Color(SaveDataHandler.BUZZER_COLORS_PRESSED[index])));
        virtualBuzzer.getIcon().setOpacity(0f);
        virtualBuzzer.getNumber().setOpacity(1f);
        virtualBuzzer.setPositionAndSize((1f + index * 2f) / (buzzerCount * 2f), 0.8f, 1f / buzzerCount, 0.4f);
    }

    @Override
    protected void changeToWrong(AnimationQueue.AnimationQueueItem animationQueueItem) {

    }

    @Override
    protected void changeToRight(AnimationQueue.AnimationQueueItem animationQueueItem) {

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
        virtualBuzzer.moveAndScale((1f + index * 2f) / (buzzerCount * 2f),0.85f,1f/buzzerCount,0.3f,changeAnimationDuration, animationQueueItem).addOnFinishedAction(() -> {
            virtualBuzzer.getColorQuad().deactivateManualDepth();
            virtualBuzzer.getNumber().deactivateManualDepth();
            virtualBuzzer.getIcon().deactivateManualDepth();
        });

        virtualBuzzer.fadeInQuad(changeAnimationDuration, animationQueueItem);
        virtualBuzzer.fadeInIcon(changeAnimationDuration, animationQueueItem);

        Color unpressed = new Color(SaveDataHandler.BUZZER_COLORS_PRESSED[index]);
        unpressed.setAlpha(unpressedTransparency);

        virtualBuzzer.fadeColor(unpressed,changeAnimationDuration, animationQueueItem);
    }

}
