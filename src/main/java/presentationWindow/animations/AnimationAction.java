package presentationWindow.animations;

/**
 * action performed every step of an animation
 *
 * @param <T> type of the object that gets animated
 */
public interface AnimationAction<T> {

    /**
     * action performed every step of an animation
     *
     * @param nextValue next value of the animated object
     */
    void stepAction(T nextValue);
}
