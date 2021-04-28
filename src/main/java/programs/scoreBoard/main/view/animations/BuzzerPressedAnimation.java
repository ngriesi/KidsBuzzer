package programs.scoreBoard.main.view.animations;

import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
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
        linearAnimator.fadeOut(viewItems.getLabels()[buzzer - 1], 60).addOnFinishedAction(() -> {
            viewItems.getLabels()[buzzer - 1].changeText(viewItems.getScoreBoardModel().getSaveFile().getTeamNames()[buzzer - 1]
                    + ": " + viewItems.getScoreBoardModel().getScores()[buzzer - 1]);
            linearAnimator.fadeIn(viewItems.getLabels()[buzzer - 1], 60).addOnFinishedAction(animationQueueItem::animationFinished);
        });

        ColorScheme start = new ColorScheme(Color.DARK_GRAY,Color.WHITE,Color.TRANSPARENT, Color.TRANSPARENT);
        ColorScheme middle = new ColorScheme(Color.WHITE,Color.DARK_GRAY,Color.TRANSPARENT, Color.TRANSPARENT);

        exponentialAnimator.fadeColorScheme(middle,viewItems.getTeamMetalBacks()[buzzer - 1].getCentralPlane(),60).addOnFinishedAction(() -> {
            exponentialAnimator.fadeColorScheme(start, viewItems.getTeamMetalBacks()[buzzer - 1].getCentralPlane(), 60);
        });

        ColorScheme startOut = new ColorScheme(Color.WHITE,Color.GREY,Color.TRANSPARENT, Color.TRANSPARENT);
        ColorScheme middleOut = new ColorScheme(Color.GREY,Color.WHITE,Color.TRANSPARENT, Color.TRANSPARENT);

        exponentialAnimator.fadeColorScheme(middleOut,viewItems.getTeamMetalBacks()[buzzer - 1],60).addOnFinishedAction(() -> {
            exponentialAnimator.fadeColorScheme(startOut, viewItems.getTeamMetalBacks()[buzzer - 1], 60);
        });
    }
}
