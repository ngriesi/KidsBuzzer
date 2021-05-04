package presentationWindow.window;

import presentationWindow.animations.Animation;

/**
 * class used to animate render items with an exponential <code>AnimationCurve</code>
 */
public class ExponentialAnimator extends Animator {

    ExponentialAnimator() {
        super(Animation.AnimationCurve.EXPONENTIAL);
    }
}