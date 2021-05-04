package presentationWindow.animations;

import presentationWindow.engine.Action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Class used to queue animations. Multiple Animations (or one) can be part of one <code>AnimationItem</code>
 * and the next element in the queue will start if all of those animations are finished
 */
public class AnimationQueue {

    /**
     * queue of <code>AnimationQueueItem</code>
     */
    private Queue<AnimationQueueItem> animationQueue;

    /**
     * flag indicating that there is currently an <code>AnimationQueueItem</code> which gets animated
     */
    private boolean animating = false;

    /**
     * constructor creates the list for the queue
     */
    public AnimationQueue() {
        animationQueue = new LinkedList<>();
    }

    /**
     * adds an <code>AnimationQueueItem</code> to the queue. If the queue is empty the Animations
     * of the <code>AnimationQueueItem</code> start immediately. Otherwise the <code>AnimationQueueItem</code>
     * gets added to the queue
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that gets added to the queue
     */
    public void addAnimation(AnimationQueueItem animationQueueItem) {
        if (animating) {
            animationQueue.add(animationQueueItem);
        } else {
            animating = true;
            animationQueueItem.startAnimation(this);
        }
    }

    /**
     * Method starts the next animation. The method checks if an unused <code>AnimationQueueItem</code>
     * is in the queue. If so, the method starts the animations of this <code>AnimationQueueItem</code>.
     * Otherwise the method sets the animating flag to false.
     */
    private void nextAnimation() {
        AnimationQueueItem animationQueueItem = animationQueue.poll();

        if (animationQueueItem != null) {
            animating = true;
            animationQueueItem.startAnimation(this);
        } else {
            animating = false;
        }
    }

    /**
     * Class representing one item of the queue which can consist out of multiple actions/animations
     * The <code>AnimationQueueItem</code> class only knows the one action it performs when it is started and
     * the onFinishedActions. More actions/animations that belong to the <code>AnimationQueueItem</code> are
     * just increments of the <code>animationsToFinish</code> field.
     * <p>
     * The <code>AnimationQueueItem</code> finishes if one of the following conditions are met:
     * <p>
     * The animationAction is null: This leads to the <code>AnimationQueueItem</code> finishing immediately
     * after it started
     * <p>
     * The <code>AnimationQueueItem</code> gets finished by calling the animationFinished method. This can be done at any time
     * and does not rely on any of the other conditions
     * <p>
     * If the field <code>finishedAnimations</code> equals the field <code>animationsToFinish</code> after a call of <code>addFinishedAnimation</code>
     */
    public static class AnimationQueueItem {

        /**
         * action performed when the <code>AnimationQueueItem</code> gets started
         */
        private Action animationAction;

        /**
         * reference to the <code>AnimationQueue</code> used to notify the queue when the <code>AnimationQueueItem</code>
         * is finished
         */
        private AnimationQueue animationQueue;

        /**
         * Actions performed when the <code>AnimationQueueItem</code> gets finished
         */
        private List<Action> onFinishedActions;

        /**
         * number of animations that need to finish (number of times <code>addFinishedAnimations</code> has to be called)
         * until the <code>AnimationQueueItem</code> ends this way
         */
        private int animationsToFinish;

        /**
         * number of animations that are already finished
         */
        private int finishedAnimations;

        /**
         * Constructor creates the <code>onFinishedActions</code> list
         */
        public AnimationQueueItem() {
            onFinishedActions = new ArrayList<>();
        }

        /**
         * sets the main animation action
         *
         * @param animationAction new main animation action
         */
        public void setAnimationAction(Action animationAction) {
            this.animationAction = animationAction;
        }

        /**
         * starts the animation in the passed queue. If the <code>animationAction</code> of the
         * <code>AnimationQueueItem</code> is null the <code>AnimationQueueItem</code> gets finished immediately
         *
         * @param animationQueue <code>AnimationQueue</code> the <code>AnimationQueueItem</code> gets added to
         */
        void startAnimation(AnimationQueue animationQueue) {
            this.animationQueue = animationQueue;
            if (animationAction != null) {
                animationAction.execute();
            } else {
                animationFinished();
            }
        }

        /**
         * finishes the <code>AnimationQueueItem</code> or gets called when the <code>AnimationQueueItem</code>
         * is finished. The method executes all <code>Actions</code> int the <code>onFinishedActions</code> list
         * and calls <code>nextAnimation</code> in <code>AnimationQueue</code>
         */
        public void animationFinished() {
            for (Action action : onFinishedActions) {
                action.execute();
            }
            animationQueue.nextAnimation();
        }

        /**
         * adds an <code>Action</code> to the <code>onFinishedAnimations</code> list so it gets
         * executed when the <code>AnimationQueueItem</code> finishes
         *
         * @param action <code>Action</code> to be added to the <code>onFinishedActions</code> list
         */
        public void addOnFinishedAction(Action action) {
            onFinishedActions.add(action);
        }

        /**
         * increases the <code>finishedAnimations</code> field and checks if it is equal to the
         * <code>animationsToFinish</code> field and if so the <code>AnimationQueueItem</code> ends
         */
        public void addFinishedAnimation() {
            finishedAnimations++;
            if (finishedAnimations == animationsToFinish) animationFinished();
        }

        /**
         * adds an unfinished action to the <code>AnimationQueueItem</code> by increasing
         * the field <code>animationsToFinish</code>
         */
        public void addUnfinishedAnimation() {
            animationsToFinish++;
        }
    }
}
