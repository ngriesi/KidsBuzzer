package presentationWindow.engine;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;
import static org.lwjgl.system.MemoryUtil.NULL;

@SuppressWarnings("unused")
public class Window {


    public static float WINDOW_ASPECT_RATIO = 16f / 9f;


    /**
     * window title
     */
    private final String title;

    /**
     * width and height of window in pixels
     */
    private int width, height;

    /**
     * id code to identify window in glfw context
     */
    private long windowHandle = -1;

    /**
     * variable used to perform action in game loop thread after resized callback was called
     */
    private boolean resized;

    /**
     * determines if window should use vSync
     */
    private boolean vSync;

    /**
     * info about the primary monitor
     */
    private GLFWVidMode monitorData;

    /**
     * the engine for this window
     */
    private Engine engine;

    /**
     * Action when window gets closed
     */
    private Action closedAction;

    /**
     * Action when window gets focus
     */
    @SuppressWarnings("FieldCanBeLocal")
    private Action onFocusAction;

    /**
     * determines if the window can be closed
     */
    private boolean closeable;

    /**
     * values for window creation
     */
    private boolean transparent;

    private int screen;


    /**
     * Constructor sets all parameters except windowHandle and update mode
     *
     * @param title       window title
     * @param width       window width in pixels
     * @param height      window height in pixels
     * @param vSync       should use vSync
     * @param engine      engine that uses this window
     * @param transparent transparency of the window
     * @param fullScreen  flag determines if the window should be full screen
     * @param screen      screen the window should be inside
     */

    public Window(String title, int width, int height, boolean vSync, Engine engine, boolean transparent, boolean fullScreen, int screen) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        this.engine = engine;
        this.resized = false;
        this.closeable = true;
        this.transparent = transparent;
        this.screen = screen;
    }

    /**
     * Method initializes window by creating an openGl window and setting the
     * parameters depending on the preset set in the constructor
     */

    void init() {


        //ErrorCallback
        try {
            GLFWErrorCallback.createPrint(System.err).set();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Initializing GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }


        glfwDefaultWindowHints(); //optional
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); //window will stay hidden
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3); //major glfw version used (libraries)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2); //minor glfw version used (libraries)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_FOCUSED, GL_FALSE);
        glfwWindowHint(GLFW_FOCUS_ON_SHOW, GL_FALSE);
        glfwWindowHint(GLFW_DECORATED, GL_FALSE);
        glfwWindowHint(GLFW_FLOATING, GL_TRUE);


        if (transparent) {
            glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, GL_TRUE);
        }

        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);

        setScreen(screen);

        // Create window

        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create window");
        }

        //make openGl context current
        glfwMakeContextCurrent(windowHandle);

        glfwSetWindowSizeCallback(windowHandle, (window, width, height) -> {
            Window.this.width = width;
            Window.this.height = height;
            Window.this.setResized(true);
        });


        glfwSetWindowCloseCallback(windowHandle, window -> {
            if (closedAction != null) {
                closedAction.execute();
            }
            if (!closeable) {
                glfwSetWindowShouldClose(windowHandle, false);
            }
        });

        if (isvSync()) {
            glfwSwapInterval(1);
        }


        GL.createCapabilities();

        //resize Callback
        glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> engine.frameAction());

        //support for transparent textures
        glEnable(GL_BLEND);
        glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_SRC_ALPHA, GL_DST_ALPHA);

        glEnable(GL_DEPTH_TEST);
    }

    /**
     * marks the end of a frame:
     * the buffer that the scene was rendered on the last frame gets displayed
     * and the one that was displayed for the last frame is the one on which
     * the rendering of the next frame happens
     */
    void swapBuffers() {

        glfwSwapBuffers(windowHandle);

    }

    /**
     * closes the window
     */
    public void closeWindow() {
        glfwSetWindowShouldClose(windowHandle, true);
    }

    /**
     * forces a new event
     */
    public void forceEvents() {
        glfwPostEmptyEvent();
    }

    /**
     * method handles the way the events are used
     */
    void events() {

        glfwPollEvents();
    }

    /**
     * returns true if the window should be close (user tried to close it)
     *
     * @return if window should close
     */
    @SuppressWarnings("WeakerAccess")
    public boolean windowShouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }

    /**
     * Sets the resized field
     * The field is set to true by the callback and to false by the
     * main game thread after updating the framebuffer and window content
     *
     * @param resized value set to resized
     */

    void setResized(boolean resized) {
        this.resized = resized;
    }

    /**
     * returns the title of the window
     *
     * @return title of the window
     */

    @SuppressWarnings("unused")
    public String getTitle() {
        return title;
    }

    /**
     * returns the window width in pixels
     *
     * @return window width in pixels
     */

    public int getWidth() {
        return width;
    }

    /**
     * returns the window height in pixels
     *
     * @return window height in pixels
     */


    public int getHeight() {
        return height;
    }

    /**
     * returns the windowHandle, the unique identifier of the window
     *
     * @return window handle
     */

    public long getWindowHandle() {
        return windowHandle;
    }

    /**
     * check if the window was resized
     *
     * @return content of field resized
     */

    boolean isResized() {
        return resized;
    }

    /**
     * check if window uses vSync
     *
     * @return content of field vSync
     */

    boolean isvSync() {
        return vSync;
    }

    /**
     * @param keyCode code of the key that is checked
     * @return returns true if the key is pressed
     */
    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    /**
     * returns the visibility of the window
     *
     * @return window visibility
     */
    public boolean isShown() {
        return 1 == glfwGetWindowAttrib(windowHandle, GLFW_VISIBLE);
    }

    /**
     * shows window
     */
    public void show() {
        glfwShowWindow(windowHandle);
    }

    /**
     * hides window
     */
    public void hide() {
        glfwHideWindow(windowHandle);
    }

    /**
     * @return the primary monitors data
     */
    @SuppressWarnings("unused")
    public GLFWVidMode getMonitorData() {
        return monitorData;
    }

    /**
     * makes program not finish on window close
     */
    public void disableClosing() {
        closeable = false;
    }

    /**
     * Setter for the Close Action
     *
     * @param closedAction new close action of the window
     */
    public void setClosedAction(Action closedAction) {
        this.closedAction = closedAction;
    }

    /**
     * Setter for the Focus Action
     *
     * @param onFocusAction new focus action of the window
     */
    public void setOnFocusAction(Action onFocusAction) {
        this.onFocusAction = onFocusAction;
    }

    /**
     * method sets the screen for this window if the passed screen index corresponds to an existing
     * monitor
     *
     * @param outputScreen index of the screen this window should be placed in
     */
    public void setScreen(int outputScreen) {
        screen = outputScreen;
        PointerBuffer monitors = glfwGetMonitors();
        long monitor = 0;
        if (monitors != null) {
            if (monitors.capacity() >= screen) {
                monitor = monitors.get(screen - 1);
            } else {
                monitor = glfwGetPrimaryMonitor();
            }
        }

        //get Resolution of the monitor
        monitorData = glfwGetVideoMode(monitor);

        if (monitorData != null) {
            WINDOW_ASPECT_RATIO = monitorData.width() / (float) monitorData.height();
        }

        int[] xPos = new int[1];
        int[] yPos = new int[1];
        glfwGetMonitorPos(monitor, xPos, yPos);

        if (monitor != glfwGetPrimaryMonitor()) {
            width = monitorData.width();
            height = monitorData.height() + 1;
        } else {
            width = monitorData.width() / 4;
            height = monitorData.height() / 4 + 1;
        }

        glfwSetWindowSize(windowHandle, width, height);
        glfwSetWindowPos(windowHandle, xPos[0], yPos[0]);
    }

    /**
     * Tries to return the height of the screen this window is currently inside.
     * If this fails it returns the height of the primary screen.
     * If this fails it returns 1080
     *
     * @return returns the height of the screen this window is placed inside
     */
    public int getScreenHeight() {
        PointerBuffer monitors = glfwGetMonitors();
        long monitor = 0;
        if (monitors != null) {
            monitor = monitors.get(screen - 1);
        }
        monitorData = glfwGetVideoMode(monitor);
        if (monitorData != null) {
            return monitorData.height();
        }
        monitorData = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (monitorData != null) {
            return monitorData.height();
        } else {
            return 1080;
        }
    }

    /**
     * Tries to return the width of the screen this window is currently inside.
     * If this fails it returns the width of the primary screen.
     * If this fails it returns 1920
     *
     * @return returns the width of the screen this window is placed inside
     */
    public int getScreenWidth() {
        PointerBuffer monitors = glfwGetMonitors();
        long monitor = 0;
        if (monitors != null) {
            monitor = monitors.get(screen - 1);
        }
        monitorData = glfwGetVideoMode(monitor);
        if (monitorData != null) {
            return monitorData.width();
        }
        monitorData = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (monitorData != null) {
            return monitorData.width();
        } else {
            return 1920;
        }
    }

    /**
     * @return calculates and returns the aspect ratio of this window
     */
    public float getAspectRatio() {
        return width / (float) height;
    }
}
