package presentationWindow.animations;

import presentationWindow.engine.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * animation class used to animate an object from one state to another in a set amount of steps
 *
 * @param <T> type of the animated object
 */
@SuppressWarnings("unused")
public abstract class Animation<T> {

    public enum AnimationCurve {
        LINEAR, EXPONENTIAL
    }

    /**
     * start value of the object
     */
    T startValue;

    /**
     * end value of the object
     */
    T endValue;

    /**
     * duration of the animation in frames
     */
    private int duration;

    /**
     * current position int the animation
     */
    private int time = 0;

    /**
     * action, performed when the animation is finished
     */
    private List<Action> onFinishedActions;

    /**
     * action of the animation, performed every frame
     */
    AnimationAction<T> animationAction;

    /**
     * flag stating whether the animation is finished or not
     */
    private boolean finished;

    /**
     * curve of the animation
     */
    private AnimationCurve animationCurve;

    /**
     * creates a new animation
     *
     * @param startValue start position of the animation
     * @param endValue end position of the animation
     * @param duration duration of the animation
     * @param animationAction action of the animation performed every frame
     */
    Animation(T startValue, T endValue, int duration, AnimationAction<T> animationAction, AnimationCurve animationCurve) {
        onFinishedActions = new ArrayList<>();
        this.startValue = startValue;
        this.endValue = endValue;
        this.duration = duration;
        this.animationAction = animationAction;
        this.animationCurve = animationCurve;
        finished = false;
    }

    /**
     * returns the progress of a exponential animation
     *
     * @param time current time
     * @param duration duration of the animation
     * @return progress of the animation in the range of 0 to 1
     */
    private float getExponentialProgress(float time, float duration) {
        int iterations = 3;
        time /= duration / 2;
        if (time < 1)  {
            return (float) (0.5f * Math.pow(time,iterations));
        }

        return (float) (0.5f * Math.pow(time - 2, iterations) + 1);
    }

    /**
     * returns the progress of a linear animation
     *
     * @param time current time
     * @param duration duration of the animation
     * @return progress of the animation in the range of 0 to 1
     */
    private float getLinearProgress(float time, float duration) {
        return time/ duration;
    }

    /**
     * calculates the progress of the animation
     *
     * @return progress in the range from 1 to 0
     */
    private float getProgress() {
        switch (animationCurve) {
            case LINEAR: return getLinearProgress(time,duration);
            case EXPONENTIAL: return getExponentialProgress(time, duration);
            default: return 0;
        }
    }

    /**
     * method is called every frame to make one animation step
     */
    public void makeStep() {
        if(time < duration && animationShouldStillBeRunning(getProgress())) {
            stepAction(getProgress());
            time++;
        } else {
            animationAction.stepAction(endValue);
            animationFinished();
        }
    }

    protected abstract boolean animationShouldStillBeRunning(float progress);

    /**
     * method is called to update animation value
     */
    public abstract void stepAction(float progress);

    /**
     * method is called when the animation is finished
     * executes the finished action if one exists
     */
    private void animationFinished() {
        finished = true;
        for (Action action : onFinishedActions) {
            action.execute();
        }
    }

    /**
     * adds an onFinished action for this animation
     *
     * @param onFinishedAction finished action of this animation
     */
    public void addOnFinishedAction(Action onFinishedAction) {
        onFinishedActions.add(onFinishedAction);
    }

    /**
     * @return returns true if the animation is finished
     */
    public boolean isFinished() {
        return finished;
    }
}
