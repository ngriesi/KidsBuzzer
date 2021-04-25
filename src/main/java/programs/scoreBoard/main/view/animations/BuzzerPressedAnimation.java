package programs.scoreBoard.main.view.animations;

import presentationWindow.window.ExponentialAnimator;
import presentationWindow.window.LinearAnimator;
import programs.quiztime.main.view.AnimationQueue;
import programs.scoreBoard.main.view.items.ViewItems;

public class BuzzerPressedAnimation {

    private ViewItems viewItems;

    private LinearAnimator linearAnimator;

    private ExponentialAnimator exponentialAnimator;

    public BuzzerPressedAnimation(ViewItems viewItems, LinearAnimator linearAnimator, ExponentialAnimator exponentialAnimator) {

        this.viewItems = viewItems;
        this.linearAnimator = linearAnimator;
        this.exponentialAnimator = exponentialAnimator;
    }

    public void executeAnimation(AnimationQueue.AnimationQueueItem animationQueueItem, int buzzer) {
        linearAnimator.fadeOut(viewItems.getLabels()[buzzer - 1], 60).setOnFinishedAction(() -> {
            viewItems.getLabels()[buzzer - 1].changeText(viewItems.getScoreBoardModel().getSaveFile().getTeamNames()[buzzer - 1]
                    + ": " + viewItems.getScoreBoardModel().getScores()[buzzer - 1]);
            linearAnimator.fadeIn(viewItems.getLabels()[buzzer - 1], 60).setOnFinishedAction(animationQueueItem::animationFinished);
        });
    }
}
