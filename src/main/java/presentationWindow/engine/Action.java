package presentationWindow.engine;

/**
 * simple interface for passing methods with no return type
 */
public interface Action {

    /**
     * contains the action (method call) that needs to be passed
     * to an engine class
     */
    void execute();
}
