package presentationWindow.engine;


import startupApp.LoadingHandler;
import startupApp.LoadingMonitor;

/**
 * main engine class: contains the main loop and calls all
 * the necessary methods every frame for updating, rendering and
 * input handling.
 */
public class Engine implements Runnable {

    @SuppressWarnings({"unused"})
    private static long lastTime;

    /**
     * frames per second (rendering and input)
     */
    @SuppressWarnings("WeakerAccess")
    public static final int TARGET_FPS = 60;

    /**
     * updates per second
     */
    @SuppressWarnings("WeakerAccess")
    public static final int TARGET_UPS = 60;

    /**
     * window used
     */
    private final Window window;

    /**
     * timer of loop times
     */
    private final Timer timer;

    /**
     * interface implemented by the game class
     */
    private final IGameLogic gameLogic;

    /**
     * monitors the loading of the game engine
     */
    private final LoadingMonitor loadingMonitor;
    private final LoadingHandler loadingHandler;

    /**
     * values needed for time calculations
     */
    private float accumulator;
    @SuppressWarnings("FieldCanBeLocal")
    private float interval = 1f / TARGET_UPS;
    @SuppressWarnings("FieldCanBeLocal")
    private float elapsedTime;

    /**
     * constructor uses parameters to create the window
     * it also gets the gameLogic passed
     *
     * @param windowTitle    for window
     * @param width          for window
     * @param height         for window
     * @param vSync          for window
     * @param gameLogic      the game
     * @param loadingHandler loading handler
     * @param transparent    transparency of the window
     * @param fullScreen     flag determines if the window should be full screen
     * @param screen         screen the window should be inside
     */
    public Engine(String windowTitle, int width, int height, boolean vSync, IGameLogic gameLogic, LoadingHandler loadingHandler, boolean transparent, boolean fullScreen, int screen) {
        window = new Window(windowTitle, width, height, vSync, this, transparent, fullScreen, screen);
        this.loadingMonitor = new LoadingMonitor("openGlRenderer");
        this.loadingHandler = loadingHandler;
        loadingHandler.addLoadingProcess(loadingMonitor);
        this.gameLogic = gameLogic;
        timer = new Timer();
    }

    /**
     * run method of the main game loop thread
     * <p>
     * initialises everything and runs the loop
     * <p>
     * cleans up at the end
     */
    @Override
    public void run() {
        try {

            init();

            loadingMonitor.finishedProcess(loadingHandler);

            gameLoop();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    /**
     * initialises all the used objects
     *
     * @throws Exception if game logic initialization fails
     */
    private void init() throws Exception {

        window.init();

        timer.init();

        gameLogic.init(window);

    }

    /**
     * main game loop
     */
    private void gameLoop() {

        accumulator = 0f;


        boolean running = true;
        //noinspection ConstantConditions
        while (running && !window.windowShouldClose()) {


            frameAction();

            if (!window.isvSync()) {
                sync();
            }
        }
    }

    /**
     * action performed every frame: updates the view and renders it
     */
    void frameAction() {

        elapsedTime = timer.getElapsedTime();
        accumulator += elapsedTime;

        while (accumulator >= interval) {
            update(interval);
            accumulator -= interval;
        }

        render();
    }

    /**
     * used to calculate the waiting time if vSync isn't used
     */
    private void sync() {

        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }
    }

    /**
     * updates the program
     *
     * @param interval used to pass the time it took since the last update cycle
     */
    private void update(float interval) {

        gameLogic.update(interval);

    }

    /**
     * renders the program
     */
    private void render() {


        gameLogic.render(window);
        window.swapBuffers();

        window.events();


    }

    /**
     * calls the cleanup method of the program
     */
    private void cleanup() {
        gameLogic.cleanup();
    }
}
