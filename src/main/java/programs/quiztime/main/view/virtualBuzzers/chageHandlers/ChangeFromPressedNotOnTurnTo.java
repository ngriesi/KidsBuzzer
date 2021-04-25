package programs.quiztime.main.view.virtualBuzzers.chageHandlers;

import presentationWindow.assets.Color;
import programs.quiztime.main.view.virtualBuzzers.StateHandler;
import programs.quiztime.main.view.virtualBuzzers.VirtualBuzzer;
import savedataHandler.SaveDataHandler;

public class ChangeFromPressedNotOnTurnTo extends StateHandler {

    public ChangeFromPressedNotOnTurnTo(VirtualBuzzer virtualBuzzer) {
        super(virtualBuzzer);
    }

    @Override
    protected void changeToPressedNotOnTurn() {}

    @Override
    protected void changeToOnTurn() {
        virtualBuzzer.moveAndScale((1f + index * 2f) / (buzzerCount * 2f), 0.8f, 1f / buzzerCount, 0.4f, changeAnimationDuration);
    }

    @Override
    protected void changeToWrong() {

    }

    @Override
    protected void changeToRight() {

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
        virtualBuzzer.moveAndScale((1f + index * 2f) / (buzzerCount * 2f),0.85f,1f/buzzerCount,0.3f,changeAnimationDuration).setOnFinishedAction(() -> {
            virtualBuzzer.getColorQuad().deactivateManualDepth();
            virtualBuzzer.getNumber().deactivateManualDepth();
            virtualBuzzer.getIcon().deactivateManualDepth();
        });

        virtualBuzzer.fadeInQuad(changeAnimationDuration);
        virtualBuzzer.fadeOutNumber(changeAnimationDuration / 2).setOnFinishedAction(() -> virtualBuzzer.fadeInIcon(changeAnimationDuration / 2));

        Color unpressed = new Color(SaveDataHandler.BUZZER_COLORS_PRESSED[index]);
        unpressed.setAlpha(unpressedTransparency);

        virtualBuzzer.fadeColor(unpressed,changeAnimationDuration);
    }


}
