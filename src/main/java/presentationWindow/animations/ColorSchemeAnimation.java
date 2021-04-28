package presentationWindow.animations;

import presentationWindow.assets.ColorScheme;

public class ColorSchemeAnimation extends Animation<ColorScheme> {

    private ColorAnimation[] colorAnimations;

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
            colorAnimations[i] = new ColorAnimation(startValue.getColor(i), endValue.getColor(i), duration, nextValue -> {
                progress.setColor(nextValue, finalI);
            }, animationCurve);
        }
    }



    @Override
    protected boolean animationShouldStillBeRunning(float progress) {
        return !startValue.equals(endValue) && !endValue.equals(this.progress);
    }

    @Override
    public void stepAction(float progress) {

        for (ColorAnimation colorAnimation : colorAnimations) {
            colorAnimation.makeStep();
        }

        animationAction.stepAction(this.progress);
    }
}
