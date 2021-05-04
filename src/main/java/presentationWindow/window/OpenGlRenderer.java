package presentationWindow.window;

import presentationWindow.animations.Animation;
import presentationWindow.engine.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * main class for rendering things on the presentation window
 */
public class OpenGlRenderer implements IGameLogic {

    /**
     * renderer of the window
     */
    private Renderer renderer;

    /**
     * scene containing the items that get rendered
     */
    private Scene scene;

    /**
     * window of the renderer
     */
    private Window window;

    /**
     * actions scheduled for this thread
     */
    private Queue<Action> executeOnOpenGlThread;

    /**
     * new timed actions about to be added to the currentTimedActions list
     */
    private List<TimedAction> newTimedActions;

    /**
     * current timed actions
     */
    private List<TimedAction> currentTimedActions;

    /**
     * finished timed actions, to be removed from the currentTimedActions list
     */
    private List<TimedAction> finishedTimedActions;

    /**
     * animates items of the scene linear
     */
    private LinearAnimator linearAnimator;

    /**
     * animates items of the scene exponential
     */
    private ExponentialAnimator exponentialAnimator;

    /**
     * creates a new open gl renderer
     */
    public OpenGlRenderer() {
        executeOnOpenGlThread = new LinkedList<>();
        linearAnimator = new LinearAnimator();
        exponentialAnimator = new ExponentialAnimator();
        createTimedActionLists();
    }

    /**
     * creates the lists for the timed action handling
     */
    private void createTimedActionLists() {
        newTimedActions = new ArrayList<>();
        currentTimedActions = new ArrayList<>();
        finishedTimedActions = new ArrayList<>();
    }

    /**
     * initialises the renderer
     *
     * @param window passes the window instance to the game
     * @throws Exception when initialization fails
     */
    @Override
    public void init(Window window) throws Exception {
        renderer = new Renderer();
        scene = new Scene(window);
        this.window = window;
        renderer.init(window);
    }

    /**
     * update method to update the program every frame
     *
     * @param interval time since last update cycle
     */
    @Override
    public void update(float interval) {


        handlerOnOpenGLThreadActions();
        handleTimedActions(interval);
        linearAnimator.update();
        exponentialAnimator.update();

    }

    /**
     * executes the actions from the open gl thread actions list
     */
    private synchronized void handlerOnOpenGLThreadActions() {
        while (!executeOnOpenGlThread.isEmpty()) {
            executeOnOpenGlThread.remove().execute();
        }
    }

    /**
     * handles the timed actions
     *
     * @param interval time since last update cycle
     */
    private void handleTimedActions(float interval) {
        currentTimedActions.addAll(newTimedActions);
        newTimedActions.clear();
        for (TimedAction timedAction : currentTimedActions) {
            if (timedAction.getTime() <= 0) {
                timedAction.getAction().execute();
                finishedTimedActions.add(timedAction);
            } else {
                timedAction.setTime(timedAction.getTime() - interval);
            }
        }
        currentTimedActions.removeAll(finishedTimedActions);
        finishedTimedActions.clear();
    }

    /**
     * renders the scene
     *
     * @param window to render in
     */
    @Override
    public void render(Window window) {

        renderer.render(window, scene);
    }

    /**
     * cleans up the renderer and the items
     */
    @Override
    public void cleanup() {
        renderer.cleanup();
        scene.getMainItem().cleanup();
    }

    /**
     * @return returns the window of the scene
     */
    public Window getWindow() {
        return window;
    }

    /**
     * @return returns the scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * adds an action to be performed on this open gl thread
     *
     * @param action action to be added
     */
    public synchronized void addActionToOpenGlThread(Action action) {
        executeOnOpenGlThread.add(action);
    }

    /**
     * starts a new animation
     *
     * @param animation animation to start
     */
    @SuppressWarnings("unused")
    public void startAnimation(Animation animation) {
        linearAnimator.startAnimation(animation);
    }

    /**
     * adds a new timed action
     *
     * @param timedAction action to be added
     */
    public void addTimedAction(TimedAction timedAction) {
        newTimedActions.add(timedAction);
    }

    /**
     * @return returns the linear animator of the renderer
     */
    public LinearAnimator getLinearAnimator() {
        return linearAnimator;
    }

    /**
     * changes the screen the open gl window of this renderer is inside
     *
     * @param outputScreen index of the new screen
     */
    public void changeScreen(int outputScreen) {
        window.setScreen(outputScreen);
    }

    /**
     * @return returns the exponential animator of the renderer
     */
    public ExponentialAnimator getExponentialAnimator() {
        return exponentialAnimator;
    }
}
