package presentationWindow.window;

import org.joml.Vector2f;
import presentationWindow.animations.Animation;
import presentationWindow.animations.ColorAnimation;
import presentationWindow.animations.FloatAnimation;
import presentationWindow.animations.Vector2fAnimation;
import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.renderItems.ChildItem;

import java.util.ArrayList;
import java.util.List;

public class ExponentialAnimator {


    /**
     * open gl renderer to create animations
     */
    private OpenGlRenderer openGlRenderer;

    /**
     * new Animations about to be added to the currentAnimations list
     */
    private List<Animation> newAnimations;

    /**
     * animations currently playing
     */
    private List<Animation> currentAnimations;

    /**
     * finished Animations, to be removed from the current animations list
     */
    private List<Animation> finishedAnimations;

    ExponentialAnimator(OpenGlRenderer openGlRenderer) {
        this.openGlRenderer = openGlRenderer;
        createAnimationLists();
    }

    /**
     * creates the lists for the animation handling
     */
    private void createAnimationLists() {
        newAnimations = new ArrayList<>();
        currentAnimations = new ArrayList<>();
        finishedAnimations = new ArrayList<>();
    }

    void update() {
        handleAnimations();
    }

    /**
     * handles the animations
     */
    private void handleAnimations() {
        currentAnimations.addAll(newAnimations);
        newAnimations.clear();
        for (Animation animation : currentAnimations) {
            if (animation.isFinished()) {
                finishedAnimations.add(animation);
            } else {
                animation.makeStep();
            }
        }
        currentAnimations.removeAll(finishedAnimations);
        finishedAnimations.clear();
    }

    /**
     * starts a new animation
     *
     * @param animation animation to start
     */
    @SuppressWarnings("unused")
    public Animation startAnimation(Animation animation) {
        newAnimations.add(animation);
        return animation;
    }

    public Animation colorFade(Color newColor, ChildItem item, int duration) {
        return startAnimation(new ColorAnimation(item.getColor(), newColor, duration, nextValue -> item.setColorScheme(new ColorScheme(nextValue)), Animation.AnimationCurve.EXPONENTIAL));
    }

    public Animation fadeOut(ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getOpacity(), 0f, duration, item::setOpacity, Animation.AnimationCurve.EXPONENTIAL));
    }

    public Animation fadeIn(ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getOpacity(), 1f, duration, item::setOpacity, Animation.AnimationCurve.EXPONENTIAL));
    }

    public Animation scaleSize(Vector2f newSize, ChildItem item, int duration) {
        return startAnimation(new Vector2fAnimation(item.getSize(),newSize,duration,item::setSize, Animation.AnimationCurve.EXPONENTIAL));
    }

    public Animation moveTo(Vector2f newPosition, ChildItem item, int duration) {
        return startAnimation(new Vector2fAnimation(item.getPosition(),newPosition,duration,item::setPosition, Animation.AnimationCurve.EXPONENTIAL));
    }

    public Animation moveXTo(float newX, ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getXPosition(), newX, duration, item::setXPosition, Animation.AnimationCurve.EXPONENTIAL));
    }

    public Animation moveYTo(float newY, ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getYPosition(), newY, duration, item::setYPosition, Animation.AnimationCurve.EXPONENTIAL));
    }

    public Animation scaleHeightTo(float newHeight, ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getHeight(),newHeight,duration,item::setHeight, Animation.AnimationCurve.EXPONENTIAL));
    }

    public Animation scaleWidthTo(float newWidth, ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getWidth(),newWidth,duration,item::setWidth, Animation.AnimationCurve.EXPONENTIAL));
    }

}
