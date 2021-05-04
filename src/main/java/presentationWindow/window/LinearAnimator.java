package presentationWindow.window;

import presentationWindow.animations.Animation;

/**
 * class used to animate render items with a linear <code>AnimationCurve</code>
 */
public class LinearAnimator extends Animator {

    LinearAnimator() {
        super(Animation.AnimationCurve.LINEAR);
    }
}
