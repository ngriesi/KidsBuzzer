package programs.quiztime.main.view.virtualBuzzers.chageHandlers;

import programs.quiztime.main.view.virtualBuzzers.StateHandler;
import programs.quiztime.main.view.virtualBuzzers.VirtualBuzzer;

public class ChangeFromInvisibleState extends StateHandler {

    public ChangeFromInvisibleState(VirtualBuzzer virtualBuzzer) {
        super(virtualBuzzer);
    }

    @Override
    protected void changeToPressedNotOnTurn() {

    }

    @Override
    protected void changeToOnTurn() {

    }

    @Override
    protected void changeToWrong() {

    }

    @Override
    protected void changeToRight() {

    }

    @Override
    protected void changeToInvisibleDefault() {

    }

    @Override
    protected void changeToVisibleDefault() {
        virtualBuzzer.moveAndScale((1f + index * 2f) / (buzzerCount * 2f), 0.85f, 1f / buzzerCount, 0.3f, changeAnimationDuration);
        virtualBuzzer.fadeInIcon(virtualBuzzer.getChangeAnimationDuration());
        virtualBuzzer.fadeInQuad(virtualBuzzer.getChangeAnimationDuration());
    }
}
