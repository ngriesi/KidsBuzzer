package programs.quiztime.main.view.virtualBuzzers.chageHandlers;

import presentationWindow.assets.Color;
import programs.quiztime.main.view.virtualBuzzers.StateHandler;
import programs.quiztime.main.view.virtualBuzzers.VirtualBuzzer;
import savedataHandler.SaveDataHandler;

public class ChangeFromOnTurnTo extends StateHandler {

    public ChangeFromOnTurnTo(VirtualBuzzer virtualBuzzer) {
        super(virtualBuzzer);
    }

    @Override
    protected void changeToPressedNotOnTurn() {}

    @Override
    protected void changeToOnTurn() {}

    @Override
    protected void changeToWrong() {
        virtualBuzzer.moveAndScale((1f + index * 2f) / (buzzerCount * 2f), 0.85f, 1f / buzzerCount, 0.3f, changeAnimationDuration);
        Color disabled = new Color(SaveDataHandler.BUZZER_COLORS_DISABLED[index]);
        disabled.setAlpha(unpressedTransparency);
        virtualBuzzer.fadeColor(disabled,changeAnimationDuration);
    }

    @Override
    protected void changeToRight() {

        virtualBuzzer.getColorQuad().setManualDepth(5);
        virtualBuzzer.getNumber().setManualDepth(6);
        virtualBuzzer.getIcon().setManualDepth(7);

        virtualBuzzer.moveAndScale(0.5f,0.65f,0.5f,0.5f,changeAnimationDuration);

        virtualBuzzer.fadeOutNumber(changeAnimationDuration/2).setOnFinishedAction(() -> {
            virtualBuzzer.getIcon().setOpacity(0);
            virtualBuzzer.fadeInIcon(changeAnimationDuration/2);
        });
    }

    @Override
    protected void changeToInvisibleDefault() {
        virtualBuzzer.fadeOutNumber(changeAnimationDuration);
        virtualBuzzer.fadeOutIcon(changeAnimationDuration);
        virtualBuzzer.moveAndScale((1f + index * 2f) / (buzzerCount * 2f), 1.15f, 1f / buzzerCount, 0.3f, changeAnimationDuration);
        virtualBuzzer.fadeOutQuad(changeAnimationDuration).setOnFinishedAction(() -> virtualBuzzer.reset());
    }

    @Override
    protected void changeToVisibleDefault() {
        virtualBuzzer.moveAndScale((1f + index * 2f) / (buzzerCount * 2f), 0.85f, 1f / buzzerCount, 0.3f, changeAnimationDuration);
        virtualBuzzer.fadeOutNumber(changeAnimationDuration / 2).setOnFinishedAction(() -> virtualBuzzer.fadeInIcon(changeAnimationDuration/2));
        Color disabled = new Color(SaveDataHandler.BUZZER_COLORS_PRESSED[index]);
        disabled.setAlpha(unpressedTransparency);
        virtualBuzzer.fadeColor(disabled,changeAnimationDuration);
    }

}
