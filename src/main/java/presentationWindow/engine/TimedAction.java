package presentationWindow.engine;

/**
 * class used to perform an action after a specified amount of time
 */
public class TimedAction {

    /**
     * Action performed after the defined time
     */
    private Action action;

    /**
     * time in seconds until the action is performed
     */
    private float time;

    /**
     * creates a new timed action
     *
     * @param time time until the action gets performed
     * @param action action performed after the specified time
     */
    public TimedAction(float time, Action action) {
        this.action = action;
        this.time = time;
    }

    /**
     * @return returns the action
     */
    public Action getAction() {
        return action;
    }

    /**
     * @return returns the time
     */
    public float getTime() {
        return time;
    }

    /**
     * sets the delay time for the action
     *
     * @param time till the action gets performed
     */
    public void setTime(float time) {
        this.time = time;
    }
}
