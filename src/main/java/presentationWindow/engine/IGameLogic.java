package presentationWindow.engine;

/**
 * Interface implemented by the main class of the program
 */
public interface IGameLogic {

    /**
     * method to perform actions that have to happen before the first rendering cycle
     *
     * @param window passes the window instance to the game
     * @throws Exception if initialization fails
     */
    void init(Window window) throws Exception;

    /**
     * called every update frame to update the game
     *
     * @param interval time since last update cycle
     */
    void update(float interval);

    /**
     * called every rendering frame, used to render
     *
     * @param window to render in
     */
    void render(Window window);

    /**
     * should contain the cleanup for all the resource used
     */
    void cleanup();
}
