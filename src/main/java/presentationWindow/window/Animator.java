package presentationWindow.window;

import org.joml.Vector2f;
import presentationWindow.animations.*;
import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.renderItems.ChildItem;
import presentationWindow.renderItems.QuadItem;
import programs.quiztime.main.view.AnimationQueue;

import java.util.ArrayList;
import java.util.List;

public abstract class Animator  {

    /**
     * curve of the animations
     */
    private Animation.AnimationCurve animationCurve;

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

    Animator(Animation.AnimationCurve animationCurve) {
        this.animationCurve = animationCurve;
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
    Animation startAnimation(Animation animation) {
        newAnimations.add(animation);
        return animation;
    }

    private Animation startAnimationInQueue(Animation animation, AnimationQueue.AnimationQueueItem animationQueueItem) {
        animationQueueItem.addUnfinishedAnimation();
        animation.addOnFinishedAction(animationQueueItem::addFinishedAnimation);
        newAnimations.add(animation);
        return animation;
    }









    public Animation colorFade(Color newColor, ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new ColorAnimation(item.getColor(), newColor, duration, nextValue -> item.setColorScheme(new ColorScheme(nextValue)), animationCurve),animationQueueItem);
    }

    public Animation fadeOut(ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new FloatAnimation(item.getOpacity(), 0f, duration, item::setOpacity, animationCurve),animationQueueItem);
    }

    public Animation fadeIn(ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new FloatAnimation(item.getOpacity(), 1f, duration, item::setOpacity, animationCurve),animationQueueItem);
    }

    public Animation scaleSize(Vector2f newSize, ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new Vector2fAnimation(item.getSize(),newSize,duration,item::setSize, animationCurve),animationQueueItem);
    }

    public Animation moveTo(Vector2f newPosition, ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new Vector2fAnimation(item.getPosition(),newPosition,duration,item::setPosition, animationCurve),animationQueueItem);
    }

    public Animation moveXTo(float newX, ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new FloatAnimation(item.getXPosition(), newX, duration, item::setXPosition, animationCurve),animationQueueItem);
    }

    public Animation moveYTo(float newY, ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new FloatAnimation(item.getYPosition(), newY, duration, item::setYPosition, animationCurve),animationQueueItem);
    }

    public Animation scaleHeightTo(float newHeight, ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new FloatAnimation(item.getHeight(),newHeight,duration,item::setHeight, animationCurve),animationQueueItem);
    }

    public Animation scaleWidthTo(float newWidth, ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new FloatAnimation(item.getWidth(),newWidth,duration,item::setWidth, animationCurve),animationQueueItem);
    }














    public Animation colorFade(Color newColor, ChildItem item, int duration) {
        return startAnimation(new ColorAnimation(item.getColor(), newColor, duration, nextValue -> item.setColorScheme(new ColorScheme(nextValue)), animationCurve));
    }

    public Animation fadeOut(ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getOpacity(), 0f, duration, item::setOpacity, animationCurve));
    }

    public Animation fadeIn(ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getOpacity(), 1f, duration, item::setOpacity, animationCurve));
    }

    public Animation scaleSize(Vector2f newSize, ChildItem item, int duration) {
        return startAnimation(new Vector2fAnimation(item.getSize(),newSize,duration,item::setSize, animationCurve));
    }

    public Animation moveTo(Vector2f newPosition, ChildItem item, int duration) {
        return startAnimation(new Vector2fAnimation(item.getPosition(),newPosition,duration,item::setPosition, animationCurve));
    }

    public Animation moveXTo(float newX, ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getXPosition(), newX, duration, item::setXPosition, animationCurve));
    }

    public Animation moveYTo(float newY, ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getYPosition(), newY, duration, item::setYPosition, animationCurve));
    }

    public Animation scaleHeightTo(float newHeight, ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getHeight(),newHeight,duration,item::setHeight, animationCurve));
    }

    public Animation scaleWidthTo(float newWidth, ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getWidth(),newWidth,duration,item::setWidth, animationCurve));
    }

    public Animation fadeColorScheme(ColorScheme newColorScheme, ChildItem item, int duration) {
        return startAnimation(new ColorSchemeAnimation(item.getColorScheme(), newColorScheme, duration, item::setColorScheme, animationCurve));
    }
}
