package programs.scoreBoard.main.view.animations;

import presentationWindow.animations.AnimationQueue;
import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.window.ExponentialAnimator;
import presentationWindow.window.LinearAnimator;
import programs.scoreBoard.main.view.items.ViewItems;

/**
 * Animation, played if a buzzer was pressed
 */
public class BuzzerPressedAnimation {

    /**
     * view items class that contains the items that get animated
     */
    private ViewItems viewItems;

    /**
     * <code>LinearAnimator</code> of the <code>OpenGlRenderer</code> to create linear animations
     */
    private LinearAnimator linearAnimator;

    /**
     * <code>ExponentialAnimator</code> of the <code>OpenGlRenderer</code> to create exponential animations
     */
    private ExponentialAnimator exponentialAnimator;

    /**
     * Constructor sets teh references for the animators and the <code>ViewItem</code> object
     *
     * @param viewItems           reference to all the components that get animated
     * @param linearAnimator      linear animator of the renderer
     * @param exponentialAnimator exponential animator of the renderer
     */
    public BuzzerPressedAnimation(ViewItems viewItems, LinearAnimator linearAnimator, ExponentialAnimator exponentialAnimator) {
        this.viewItems = viewItems;
        this.linearAnimator = linearAnimator;
        this.exponentialAnimator = exponentialAnimator;
    }

    /**
     * Method executes the animation this class is for
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> to queue the animation
     * @param buzzer             buzzer that gets animated
     */
    public void executeAnimation(AnimationQueue.AnimationQueueItem animationQueueItem, int buzzer) {
        linearAnimator.fadeOut(viewItems.getLabels()[buzzer - 1], 60).addOnFinishedAction(() -> {
            viewItems.getLabels()[buzzer - 1].changeText(viewItems.getScoreBoardModel().getSaveFile().getTeamNames()[buzzer - 1]
                    + ": " + viewItems.getScoreBoardModel().getScores()[buzzer - 1]);
            linearAnimator.fadeIn(viewItems.getLabels()[buzzer - 1], 60).addOnFinishedAction(animationQueueItem::animationFinished);
        });

        ColorScheme start = new ColorScheme(Color.DARK_GRAY, Color.WHITE, Color.TRANSPARENT, Color.TRANSPARENT);
        ColorScheme middle = new ColorScheme(Color.WHITE, Color.DARK_GRAY, Color.TRANSPARENT, Color.TRANSPARENT);

        exponentialAnimator.fadeColorScheme(middle, viewItems.getTeamMetalBacks()[buzzer - 1].getCentralPlane(), 60).addOnFinishedAction(() ->
                exponentialAnimator.fadeColorScheme(start, viewItems.getTeamMetalBacks()[buzzer - 1].getCentralPlane(), 60));

        ColorScheme startOut = new ColorScheme(Color.WHITE, Color.GREY, Color.TRANSPARENT, Color.TRANSPARENT);
        ColorScheme middleOut = new ColorScheme(Color.GREY, Color.WHITE, Color.TRANSPARENT, Color.TRANSPARENT);

        exponentialAnimator.fadeColorScheme(middleOut, viewItems.getTeamMetalBacks()[buzzer - 1], 60).addOnFinishedAction(() ->
                exponentialAnimator.fadeColorScheme(startOut, viewItems.getTeamMetalBacks()[buzzer - 1], 60));
    }
}
