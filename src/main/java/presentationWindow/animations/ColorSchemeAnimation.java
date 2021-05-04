package presentationWindow.animations;

import presentationWindow.assets.ColorScheme;

/**
 * Class used to animate the <code>ColorScheme</code> of an item
 */
public class ColorSchemeAnimation extends Animation<ColorScheme> {

    /**
     * <code>ColorAnimations</code> for the four sides of the <code>ColorScheme</code> that gets animated
     */
    private ColorAnimation[] colorAnimations;

    /**
     * progress of the animation as a <code>ColorScheme</code>
     */
    private ColorScheme progress;

    /**
     * creates a new animation
     *
     * @param startValue      start position of the animation
     * @param endValue        end position of the animation
     * @param duration        duration of the animation
     * @param animationAction action of the animation performed every frame
     * @param animationCurve  curve of the animation
     */
    public ColorSchemeAnimation(ColorScheme startValue, ColorScheme endValue, int duration, AnimationAction<ColorScheme> animationAction, AnimationCurve animationCurve) {
        super(startValue, endValue, duration, animationAction, animationCurve);

        progress = new ColorScheme(startValue);

        colorAnimations = new ColorAnimation[4];
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            colorAnimations[i] = new ColorAnimation(startValue.getColor(i), endValue.getColor(i), duration, nextValue -> progress.setColor(nextValue, finalI), animationCurve);
        }
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
        return !startValue.equals(endValue) && !endValue.equals(this.progress);
    }

    /**
     * The method calculates the new value of the animated field with the
     * progress (float between 0 and 1) by calling the <code>makeStep</code> methods in
     * the color animations that are part of this <code>ColorSchemeAnimation</code>
     * and applies it using <code>animationAction.stepAction</code>
     *
     * @param progress progress of the animation as a float between 0 and 1
     */
    @Override
    public void stepAction(float progress) {

        for (ColorAnimation colorAnimation : colorAnimations) {
            colorAnimation.makeStep();
        }

        animationAction.stepAction(this.progress);
    }
}
