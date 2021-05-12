package presentationWindow.window;

import org.joml.Vector2f;
import presentationWindow.animations.*;
import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.renderItems.ChildItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Animator used to animate renderItems with less code
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public abstract class Animator {

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

    /**
     * creates the animator by creating the lists it uses for animating and setting
     * the curve for the animations
     *
     * @param animationCurve <code>AnimationCurve</code> for the animations
     */
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

    /**
     * method has to be called in the update method of the game logic class to update the animations
     */
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
     * @return returns the started animation
     */
    @SuppressWarnings("unused")
    Animation startAnimation(Animation animation) {
        newAnimations.add(animation);
        return animation;
    }

    /**
     * starts a new animation as part of an <code>AnimationQueueItem</code>
     *
     * @param animation          <code>Animation</code> to start
     * @param animationQueueItem <code>AnimationQueueItem</code> it is part of
     * @return returns the animation
     */
    private Animation startAnimationInQueue(Animation animation, AnimationQueue.AnimationQueueItem animationQueueItem) {
        animationQueueItem.addUnfinishedAnimation();
        animation.addOnFinishedAction(animationQueueItem::addFinishedAnimation);
        newAnimations.add(animation);
        return animation;
    }


    /*
    *******************************************
            ANIMATIONS IN QUEUE
    *******************************************
     */

    /**
     * fades the color of <code>item</code> to <code>newColor</code> in <code>duration</code> update frames.
     * The animation is part of the <code>AnimationQueueItem animationsQueueItem</code>
     *
     * @param newColor           target Color of the animation
     * @param item               item that gets animated
     * @param duration           duration of the animation in update frames
     * @param animationQueueItem <code>AnimationQueueItem</code> of the animation
     * @return returns the animation
     */
    public Animation colorFade(Color newColor, ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new ColorAnimation(item.getColor(), newColor, duration, nextValue -> item.setColorScheme(new ColorScheme(nextValue)), animationCurve), animationQueueItem);
    }

    /**
     * fades the opacity of <code>item</code> to <code>0</code> in <code>duration</code> update frames.
     * The animation is part of the <code>AnimationQueueItem animationsQueueItem</code>
     *
     * @param item               item that gets animated
     * @param duration           duration of the animation in update frames
     * @param animationQueueItem <code>AnimationQueueItem</code> of the animation
     * @return returns the animation
     */
    public Animation fadeOut(ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new FloatAnimation(item.getOpacity(), 0f, duration, item::setOpacity, animationCurve), animationQueueItem);
    }

    /**
     * fades the opacity of <code>item</code> to <code>1</code> in <code>duration</code> update frames.
     * The animation is part of the <code>AnimationQueueItem animationsQueueItem</code>
     *
     * @param item               item that gets animated
     * @param duration           duration of the animation in update frames
     * @param animationQueueItem <code>AnimationQueueItem</code> of the animation
     * @return returns the animation
     */
    public Animation fadeIn(ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new FloatAnimation(item.getOpacity(), 1f, duration, item::setOpacity, animationCurve), animationQueueItem);
    }

    /**
     * fades the size of <code>item</code> to <code>newSize</code> in <code>duration</code> update frames.
     * The animation is part of the <code>AnimationQueueItem animationsQueueItem</code>
     *
     * @param newSize            target size of the animation
     * @param item               item that gets animated
     * @param duration           duration of the animation in update frames
     * @param animationQueueItem <code>AnimationQueueItem</code> of the animation
     * @return returns the animation
     */
    public Animation scaleSize(Vector2f newSize, ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new Vector2fAnimation(item.getSize(), newSize, duration, item::setSize, animationCurve), animationQueueItem);
    }

    /**
     * moves the position of <code>item</code> to <code>newPosition</code> in <code>duration</code> update frames.
     * The animation is part of the <code>AnimationQueueItem animationsQueueItem</code>
     *
     * @param newPosition        target position of the animation
     * @param item               item that gets animated
     * @param duration           duration of the animation in update frames
     * @param animationQueueItem <code>AnimationQueueItem</code> of the animation
     * @return returns the animation
     */
    public Animation moveTo(Vector2f newPosition, ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new Vector2fAnimation(item.getPosition(), newPosition, duration, item::setPosition, animationCurve), animationQueueItem);
    }

    /**
     * moves the x position of <code>item</code> to <code>newX</code> in <code>duration</code> update frames.
     * The animation is part of the <code>AnimationQueueItem animationsQueueItem</code>
     *
     * @param newX               target x position of the animation
     * @param item               item that gets animated
     * @param duration           duration of the animation in update frames
     * @param animationQueueItem <code>AnimationQueueItem</code> of the animation
     * @return returns the animation
     */
    public Animation moveXTo(float newX, ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new FloatAnimation(item.getXPosition(), newX, duration, item::setXPosition, animationCurve), animationQueueItem);
    }

    /**
     * moves the y position of <code>item</code> to <code>newY</code> in <code>duration</code> update frames.
     * The animation is part of the <code>AnimationQueueItem animationsQueueItem</code>
     *
     * @param newY               target y position of the animation
     * @param item               item that gets animated
     * @param duration           duration of the animation in update frames
     * @param animationQueueItem <code>AnimationQueueItem</code> of the animation
     * @return returns the animation
     */
    public Animation moveYTo(float newY, ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new FloatAnimation(item.getYPosition(), newY, duration, item::setYPosition, animationCurve), animationQueueItem);
    }

    /**
     * scales the height of <code>item</code> to <code>newHeight</code> in <code>duration</code> update frames.
     * The animation is part of the <code>AnimationQueueItem animationsQueueItem</code>
     *
     * @param newHeight          target height of the animation
     * @param item               item that gets animated
     * @param duration           duration of the animation in update frames
     * @param animationQueueItem <code>AnimationQueueItem</code> of the animation
     * @return returns the animation
     */
    public Animation scaleHeightTo(float newHeight, ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new FloatAnimation(item.getHeight(), newHeight, duration, item::setHeight, animationCurve), animationQueueItem);
    }

    /**
     * scales the width of <code>item</code> to <code>newWidth</code> in <code>duration</code> update frames.
     * The animation is part of the <code>AnimationQueueItem animationsQueueItem</code>
     *
     * @param newWidth           target width of the animation
     * @param item               item that gets animated
     * @param duration           duration of the animation in update frames
     * @param animationQueueItem <code>AnimationQueueItem</code> of the animation
     * @return returns the animation
     */
    public Animation scaleWidthTo(float newWidth, ChildItem item, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return startAnimationInQueue(new FloatAnimation(item.getWidth(), newWidth, duration, item::setWidth, animationCurve), animationQueueItem);
    }











     /*
    *******************************************
            ANIMATIONS WITHOUT QUEUE
    *******************************************
     */

    /**
     * fades the color of <code>item</code> to <code>newColor</code> in <code>duration</code> update frames.
     *
     * @param newColor target Color of the animation
     * @param item     item that gets animated
     * @param duration duration of the animation in update frames
     * @return returns the animation
     */
    public Animation colorFade(Color newColor, ChildItem item, int duration) {
        return startAnimation(new ColorAnimation(item.getColor(), newColor, duration, nextValue -> item.setColorScheme(new ColorScheme(nextValue)), animationCurve));
    }

    /**
     * fades the opacity of <code>item</code> to <code>0</code> in <code>duration</code> update frames.
     *
     * @param item     item that gets animated
     * @param duration duration of the animation in update frames
     * @return returns the animation
     */
    public Animation fadeOut(ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getOpacity(), 0f, duration, item::setOpacity, animationCurve));
    }

    /**
     * fades the opacity of <code>item</code> to <code>1</code> in <code>duration</code> update frames.
     *
     * @param item     item that gets animated
     * @param duration duration of the animation in update frames
     * @return returns the animation
     */
    public Animation fadeIn(ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getOpacity(), 1f, duration, item::setOpacity, animationCurve));
    }

    /**
     * scales the size of <code>item</code> to <code>newSize</code> in <code>duration</code> update frames.
     *
     * @param newSize  target size of the animation
     * @param item     item that gets animated
     * @param duration duration of the animation in update frames
     * @return returns the animation
     */
    public Animation scaleSize(Vector2f newSize, ChildItem item, int duration) {
        return startAnimation(new Vector2fAnimation(item.getSize(), newSize, duration, item::setSize, animationCurve));
    }

    /**
     * moves the position of <code>item</code> to <code>newPosition</code> in <code>duration</code> update frames.
     *
     * @param newPosition target position of the animation
     * @param item        item that gets animated
     * @param duration    duration of the animation in update frames
     * @return returns the animation
     */
    public Animation moveTo(Vector2f newPosition, ChildItem item, int duration) {
        return startAnimation(new Vector2fAnimation(item.getPosition(), newPosition, duration, item::setPosition, animationCurve));
    }

    /**
     * moves the x position of <code>item</code> to <code>newX</code> in <code>duration</code> update frames.
     *
     * @param newX     target x position of the animation
     * @param item     item that gets animated
     * @param duration duration of the animation in update frames
     * @return returns the animation
     */
    public Animation moveXTo(float newX, ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getXPosition(), newX, duration, item::setXPosition, animationCurve));
    }

    /**
     * moves the y position of <code>item</code> to <code>newY</code> in <code>duration</code> update frames.
     *
     * @param newY     target y position of the animation
     * @param item     item that gets animated
     * @param duration duration of the animation in update frames
     * @return returns the animation
     */
    public Animation moveYTo(float newY, ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getYPosition(), newY, duration, item::setYPosition, animationCurve));
    }

    /**
     * scales the height of <code>item</code> to <code>newHeight</code> in <code>duration</code> update frames.
     *
     * @param newHeight target height of the animation
     * @param item      item that gets animated
     * @param duration  duration of the animation in update frames
     * @return returns the animation
     */
    public Animation scaleHeightTo(float newHeight, ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getHeight(), newHeight, duration, item::setHeight, animationCurve));
    }

    /**
     * scales the width of <code>item</code> to <code>newWidth</code> in <code>duration</code> update frames.
     *
     * @param newWidth target width of the animation
     * @param item     item that gets animated
     * @param duration duration of the animation in update frames
     * @return returns the animation
     */
    public Animation scaleWidthTo(float newWidth, ChildItem item, int duration) {
        return startAnimation(new FloatAnimation(item.getWidth(), newWidth, duration, item::setWidth, animationCurve));
    }

    /**
     * fades the color scheme of <code>item</code> to <code>newColorScheme</code> in <code>duration</code> update frames.
     *
     * @param newColorScheme target color scheme of the animation
     * @param item           item that gets animated
     * @param duration       duration of the animation in update frames
     * @return returns the animation
     */
    public Animation fadeColorScheme(ColorScheme newColorScheme, ChildItem item, int duration) {
        return startAnimation(new ColorSchemeAnimation(item.getColorScheme(), newColorScheme, duration, item::setColorScheme, animationCurve));
    }
}
