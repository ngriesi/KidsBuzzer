package presentationWindow.animations;

/**
 * Animation used to animate a float value
 */
@SuppressWarnings("unused")
public class FloatAnimation extends Animation<Float> {

    /**
     * creates a new animation
     *
     * @param startValue      start position of the animation
     * @param endValue        end position of the animation
     * @param duration        duration of the animation
     * @param animationAction action of the animation performed every frame
     * @param animationCurve  curve of the animation
     */
    public FloatAnimation(Float startValue, Float endValue, int duration, AnimationAction<Float> animationAction, AnimationCurve animationCurve) {
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
        return !startValue.equals(endValue) && startValue + (endValue - startValue) * progress != endValue;
    }

    /**
     * updates the value of the animation
     *
     * @param progress progress of the animation
     */
    @Override
    public void stepAction(float progress) {
        animationAction.stepAction(startValue + (endValue - startValue) * progress);
    }
}
