package programs.scoreBoard.main.view.animations;

import presentationWindow.animations.AnimationQueue;
import presentationWindow.renderItems.QuadItem;
import presentationWindow.window.ExponentialAnimator;
import programs.scoreBoard.main.view.items.ViewItems;
import savedataHandler.SaveDataHandler;

/**
 * Animation played when the score board exits the screen
 */
public class ExitedAnimation {

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
    public ExitedAnimation(ViewItems viewItems, ExponentialAnimator exponentialAnimator) {

        this.viewItems = viewItems;
        this.exponentialAnimator = exponentialAnimator;
    }

    /**
     * Method executes the animation this class is for
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> to queue the animation
     */
    public void executeAnimation(AnimationQueue.AnimationQueueItem animationQueueItem) {

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {

            int finalI = i;
            exponentialAnimator.fadeOut(viewItems.getLabels()[i], 30).addOnFinishedAction(() -> {
                exponentialAnimator.moveXTo(1.1f, viewItems.getTeamYellowBacks()[finalI], 70);


                exponentialAnimator.scaleWidthTo(0.1f, viewItems.getTeamYellowBacks()[finalI], 70 + (SaveDataHandler.MAX_BUZZER_COUNT - 1 - finalI) * 20);

                exponentialAnimator.moveYTo(1.5f, viewItems.getTeamMetalBacks()[finalI], 70 + finalI * 20);

                if (finalI == SaveDataHandler.MAX_BUZZER_COUNT - 1) {
                    exponentialAnimator.moveYTo(1.5f, viewItems.getIcons()[finalI], 70 + (finalI) * 20).addOnFinishedAction(() -> {
                        resetToStart();
                        animationQueueItem.animationFinished();
                    });
                } else {
                    exponentialAnimator.moveYTo(1.5f, viewItems.getIcons()[finalI], 70 + (finalI) * 20);
                }
            });
        }
    }

    private void resetToStart() {
        for (QuadItem quadItem : viewItems.getTeamYellowBacks()) {
            quadItem.setXPosition(-0.1f);
        }
    }
}
