package programs.scoreBoard.main.view.animations;

import presentationWindow.animations.AnimationQueue;
import presentationWindow.window.ExponentialAnimator;
import programs.scoreBoard.main.view.items.ViewItems;
import savedataHandler.SaveDataHandler;

/**
 * Animation played when the score board enters the screen
 */
public class EnteredAnimation {

    /**
     * view items class that contains the items that get animated
     */
    private ViewItems viewItems;

    /**
     * <code>ExponentialAnimator</code> of the <code>OpenGlRenderer</code> to create exponential animations
     */
    private ExponentialAnimator exponentialAnimator;

    /**
     * Constructor sets teh references for the animators and the <code>ViewItem</code> object
     *
     * @param viewItems           reference to all the components that get animated
     * @param exponentialAnimator exponential animator of the renderer
     */
    public EnteredAnimation(ViewItems viewItems, ExponentialAnimator exponentialAnimator) {

        this.viewItems = viewItems;
        this.exponentialAnimator = exponentialAnimator;
    }

    /**
     * Method executes the animation this class is for
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> to queue the animation
     */
    public void executeAnimation(AnimationQueue.AnimationQueueItem animationQueueItem) {
        for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
            exponentialAnimator.moveXTo((1/(SaveDataHandler.BUZZER_COUNT * 2f) * (1 + 2 * i)),viewItems.getTeamYellowBacks()[i],70);
            int finalI = i;
            if (i == SaveDataHandler.BUZZER_COUNT - 1) {
                exponentialAnimator.scaleWidthTo(0.25f, viewItems.getTeamYellowBacks()[i], 70 + i * 20).addOnFinishedAction(() -> exponentialAnimator.fadeIn(viewItems.getLabels()[finalI], 30).addOnFinishedAction(animationQueueItem::animationFinished));
            } else {
                exponentialAnimator.scaleWidthTo(0.25f, viewItems.getTeamYellowBacks()[i], 70 + i * 20).addOnFinishedAction(() -> exponentialAnimator.fadeIn(viewItems.getLabels()[finalI], 30));
            }

            exponentialAnimator.moveYTo(1, viewItems.getTeamMetalBacks()[i], 70 + i * 20);

            exponentialAnimator.moveYTo(0.95f, viewItems.getIcons()[i], 70 + i * 20);
        }
    }
}
