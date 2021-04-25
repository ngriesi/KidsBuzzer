package programs.quiztime.main.view.virtualBuzzers;

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

    public void changeState(VirtualBuzzerStateHandler.BuzzerState to) {
        switch (to) {
            case VISIBLE_DEFAULT: changeToVisibleDefault(); break;
            case INVISIBLE_DEFAULT: changeToInvisibleDefault(); break;
            case RIGHT: changeToRight(); break;
            case WRONG: changeToWrong(); break;
            case ON_TURN: changeToOnTurn(); break;
            case PRESSED_NOT_ON_TURN: changeToPressedNotOnTurn(); break;
        }
    }

    protected abstract void changeToPressedNotOnTurn();

    protected abstract void changeToOnTurn();

    protected abstract void changeToWrong();

    protected abstract void changeToRight();

    protected abstract void changeToInvisibleDefault();

    protected abstract void changeToVisibleDefault();

    public void setBuzzerCount(int buzzerCount) {
        this.buzzerCount = buzzerCount;
    }
}
