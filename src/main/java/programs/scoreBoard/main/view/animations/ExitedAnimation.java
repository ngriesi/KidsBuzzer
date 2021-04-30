package programs.scoreBoard.main.view.animations;

import presentationWindow.animations.AnimationQueue;
import presentationWindow.renderItems.QuadItem;
import presentationWindow.window.ExponentialAnimator;
import presentationWindow.window.LinearAnimator;
import programs.scoreBoard.main.view.items.ViewItems;
import savedataHandler.SaveDataHandler;

public class ExitedAnimation {

    private ViewItems viewItems;

    private LinearAnimator linearAnimator;

    private ExponentialAnimator exponentialAnimator;

    public ExitedAnimation(ViewItems viewItems, LinearAnimator linearAnimator, ExponentialAnimator exponentialAnimator) {

        this.viewItems = viewItems;
        this.linearAnimator = linearAnimator;
        this.exponentialAnimator = exponentialAnimator;
    }

    public void executeAnimation(AnimationQueue.AnimationQueueItem animationQueueItem) {

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {

            int finalI = i;
            exponentialAnimator.fadeOut(viewItems.getLabels()[i], 30).addOnFinishedAction(() -> {
                exponentialAnimator.moveXTo(1.1f,viewItems.getTeamYellowBacks()[finalI],70);



                exponentialAnimator.scaleWidthTo(0.1f, viewItems.getTeamYellowBacks()[finalI], 70 + (SaveDataHandler.MAX_BUZZER_COUNT-1 - finalI) * 20);

                exponentialAnimator.moveYTo(1.5f, viewItems.getTeamMetalBacks()[finalI], 70 + finalI * 20);

                if(finalI == SaveDataHandler.MAX_BUZZER_COUNT - 1) {
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
