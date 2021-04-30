package presentationWindow.animations;

import presentationWindow.engine.Action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AnimationQueue {

    private Queue<AnimationQueueItem> animationQueue;

    private boolean animating = false;

    public AnimationQueue() {
        animationQueue = new LinkedList<>();
    }

    public void addAnimation(AnimationQueueItem animationQueueItem) {
        if (animating) {
            animationQueue.add(animationQueueItem);
        } else {
            animating = true;
            animationQueueItem.startAnimation(this);
        }
    }

    private void nextAnimation() {
        AnimationQueueItem animationQueueItem = animationQueue.poll();

        if (animationQueueItem != null) {
            animating = true;
            animationQueueItem.startAnimation(this);
        } else {
            animating = false;
        }
    }


    public static class AnimationQueueItem {

        private Action animationAction;

        private AnimationQueue animationQueue;

        private List<Action> onFinishedActions;

        private int animationsToFinish;

        private int finishedAnimations;

        public AnimationQueueItem() {
            onFinishedActions = new ArrayList<>();
        }

        public void setAnimationsToFinish(int animationsToFinish) {
            this.animationsToFinish = animationsToFinish;
        }

        public void setAnimationAction(Action animationAction) {
            this.animationAction = animationAction;
        }

        void startAnimation(AnimationQueue animationQueue) {
            this.animationQueue = animationQueue;
            if (animationAction != null) {
                animationAction.execute();
            } else {
                animationFinished();
            }
        }

        public void animationFinished() {
            for (Action action : onFinishedActions) {
                action.execute();
            }
            animationQueue.nextAnimation();
        }

        public void addOnFinishedAction(Action action) {
            onFinishedActions.add(action);
        }

        public void addFinishedAnimation() {
            finishedAnimations++;
            if(finishedAnimations==animationsToFinish) animationFinished();
        }

        public void addUnfinishedAnimation() {
            animationsToFinish++;
        }
    }
}
