package presentationWindow.animations;

import org.joml.Vector2f;

/**
 * Class used to animate a <code>Vector2f</code> field
 */
public class Vector2fAnimation extends Animation<Vector2f> {


    /**
     * creates a new <code>Vector2f</code> animation
     *
     * @param startValue      start position of the animation
     * @param endValue        end position of the animation
     * @param duration        duration of the animation
     * @param animationAction action of the animation performed every frame
     * @param animationCurve  curve of the animation
     */
    public Vector2fAnimation(Vector2f startValue, Vector2f endValue, int duration, AnimationAction<Vector2f> animationAction, AnimationCurve animationCurve) {
        super(startValue, endValue, duration, animationAction, animationCurve);
    }

    /**
     * checks if the animation should still be running by checking if the start value
     * of the current progress is equal to the end value of the animation
     *
     * @param progress progress of the animation
     * @return true if the animation should still be running
     */
    @Override
    protected boolean animationShouldStillBeRunning(float progress) {

        Vector2f mov = new Vector2f(endValue);
        mov.sub(startValue);

        Vector2f progressTemp = new Vector2f(startValue);
        progressTemp.add(mov.mul(progress));

        return !startValue.equals(endValue, 0) && !progressTemp.equals(endValue, 0);
    }

    /**
     * The method calculates the new value of the animated field with the
     * progress (float between 0 and 1) and applies it using <code>animationAction.stepAction</code>
     *
     * @param progress progress of the animation as a float between 0 and 1
     */
    @Override
    public void stepAction(float progress) {
        Vector2f mov = new Vector2f(endValue);
        mov.sub(startValue);

        Vector2f progressTemp = new Vector2f(startValue);
        progressTemp.add(mov.mul(progress));

        animationAction.stepAction(progressTemp);
    }
}
