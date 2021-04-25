package programs.quiztime.main.view;

import presentationWindow.engine.Action;

import java.util.LinkedList;
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
            animationQueueItem.startAnimation(this);
        } else {
            animating = false;
        }
    }


    public static class AnimationQueueItem {

        private Action animationAction;

        private AnimationQueue animationQueue;

        private Action onFinishedAction;

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
            if (onFinishedAction != null) {
                onFinishedAction.execute();
            }
            animationQueue.nextAnimation();
        }

        public void setOnFinishedAction(Action action) {
            onFinishedAction = action;
        }
    }
}
