package presentationWindow.animations;

import org.joml.Vector2f;

public class Vector2fAnimation extends Animation<Vector2f> {


    /**
     * creates a new animation
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

    @Override
    protected boolean animationShouldStillBeRunning(float progress) {

        Vector2f mov = new Vector2f(endValue);
        mov.sub(startValue);

        Vector2f progressTemp = new Vector2f(startValue);
        progressTemp.add(mov.mul(progress));

        return !startValue.equals(endValue,0) && !progressTemp.equals(endValue,0);
    }

    @Override
    public void stepAction(float progress) {
        Vector2f mov = new Vector2f(endValue);
        mov.sub(startValue);

        Vector2f progressTemp = new Vector2f(startValue);
        progressTemp.add(mov.mul(progress));

        animationAction.stepAction(progressTemp);
    }
}
