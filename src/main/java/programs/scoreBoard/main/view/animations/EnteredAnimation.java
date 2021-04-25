package programs.scoreBoard.main.view.animations;

import presentationWindow.window.ExponentialAnimator;
import presentationWindow.window.LinearAnimator;
import programs.quiztime.main.view.AnimationQueue;
import programs.scoreBoard.main.view.items.ViewItems;
import savedataHandler.SaveDataHandler;


public class EnteredAnimation {

    private ViewItems viewItems;

    private LinearAnimator linearAnimator;

    private ExponentialAnimator exponentialAnimator;

    public EnteredAnimation(ViewItems viewItems, LinearAnimator linearAnimator, ExponentialAnimator exponentialAnimator) {

        this.viewItems = viewItems;
        this.linearAnimator = linearAnimator;
        this.exponentialAnimator = exponentialAnimator;
    }

    public void executeAnimation(AnimationQueue.AnimationQueueItem animationQueueItem) {
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            exponentialAnimator.moveXTo((1/(SaveDataHandler.BUZZER_COUNT * 2f) * (1 + 2 * i)),viewItems.getTeamYellowBacks()[i],70);
            int finalI = i;
            if (i == SaveDataHandler.MAX_BUZZER_COUNT - 1) {
                exponentialAnimator.scaleWidthTo(0.25f, viewItems.getTeamYellowBacks()[i], 70 + i * 20).setOnFinishedAction(() -> exponentialAnimator.fadeIn(viewItems.getLabels()[finalI], 30).setOnFinishedAction(animationQueueItem::animationFinished));
            } else {
                exponentialAnimator.scaleWidthTo(0.25f, viewItems.getTeamYellowBacks()[i], 70 + i * 20).setOnFinishedAction(() -> exponentialAnimator.fadeIn(viewItems.getLabels()[finalI], 30));
            }

            exponentialAnimator.moveYTo(1, viewItems.getTeamMetalBacks()[i], 70 + i * 20);

            exponentialAnimator.moveYTo(1, viewItems.getIcons()[i], 70 + i * 20);
        }
    }
}
