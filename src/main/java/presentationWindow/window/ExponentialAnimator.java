package presentationWindow.window;

import presentationWindow.animations.Animation;

public class ExponentialAnimator extends Animator {

    ExponentialAnimator() {
        super(Animation.AnimationCurve.EXPONENTIAL);
    }
}