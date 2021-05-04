package presentationWindow.animations;

import org.joml.Vector4f;
import presentationWindow.assets.Color;

/**
 * Class used to animate a color from one rgba value to another
 */
public class ColorAnimation extends Animation<Color> {


    /**
     * creates a new color animation
     *
     * @param startValue      start position of the animation
     * @param endValue        end position of the animation
     * @param duration        duration of the animation
     * @param animationAction action of the animation performed every frame
     * @param animationCurve  animation curve
     */
    public ColorAnimation(Color startValue, Color endValue, int duration, AnimationAction<Color> animationAction, AnimationCurve animationCurve) {
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
        Vector4f mov = new Vector4f(endValue.getVector4f());
        mov.sub(startValue.getVector4f());

        Vector4f progressTemp = new Vector4f(startValue.getVector4f());
        progressTemp.add(mov.mul(progress));

        return !startValue.getVector4f().equals(endValue.getVector4f(), 0) && !progressTemp.equals(endValue.getVector4f(), 0);
    }

    /**
     * The method calculates the new value of the animated field with the
     * progress (float between 0 and 1) and applies it using <code>animationAction.stepAction</code>
     *
     * @param progress progress of the animation as a float between 0 and 1
     */
    @Override
    public void stepAction(float progress) {
        Vector4f mov = new Vector4f(endValue.getVector4f());
        mov.sub(startValue.getVector4f());

        Vector4f progressTemp = new Vector4f(startValue.getVector4f());
        progressTemp.add(mov.mul(progress));

        animationAction.stepAction(new Color(progressTemp));
    }
}
