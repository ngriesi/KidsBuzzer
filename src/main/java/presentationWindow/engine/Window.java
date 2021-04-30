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


    public static float WINDOW_ASPECT_RATIO = 16f/9f;


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
    private long windowHandle;

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
     * Action when window gets minimized
     */
    private Action minimizedAction;

    /**
     * Action when window gets reopened
     */
    private Action reopenAction;

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
    private boolean fullScreen;

    private boolean transparent;

    private int screen;


    /**
     * Constructor sets all parameters except windowHandle and update mode
     *
     * @param title  window title
     * @param width  window width in pixels
     * @param height window height in pixels
     * @param vSync  should use vSync
     */

    public Window(String title, int width, int height, boolean vSync,Engine engine, boolean transparent, boolean fullScreen, int screen) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
        this.engine = engine;
        this.resized = false;
        this.closeable = true;
        this.transparent = transparent;
        this.fullScreen = fullScreen;
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
        glfwWindowHint(GLFW_SCALE_TO_MONITOR, GL_TRUE);



        if(transparent) {
            glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, GL_TRUE);
        }
        glfwWindowHint(GLFW_DECORATED, GL_FALSE);

        glfwWindowHint(GLFW_FLOATING, GL_TRUE);



        PointerBuffer monitors = glfwGetMonitors();
        long monitor = 0;
        if (monitors != null && monitors.sizeof() > screen - 1) {
            monitor = monitors.get(screen - 1);
        } else {
            monitor = glfwGetPrimaryMonitor();
        }

        //get Resolution of the primary monitor
        monitorData = glfwGetVideoMode(monitor);

        int[] xPos = new int[1];

        int[] yPos = new int[1];


        glfwGetMonitorPos(monitor,xPos,yPos);



        if (fullScreen) {

            width = monitorData.width();
            height = monitorData.height() + 1;

            windowHandle = glfwCreateWindow(width,height, title, NULL, NULL);

            glfwSetWindowPos(windowHandle, xPos[0] + width/2, yPos[0] + height/2);

            glfwSetWindowMonitor(windowHandle,NULL,xPos[0], yPos[0], width, height,GLFW_DONT_CARE);
        } else {
            windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);

            glfwSetWindowMonitor(windowHandle,NULL,xPos[0],yPos[0], width, height,GLFW_DONT_CARE);
        }





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



        glfwSetWindowIconifyCallback(windowHandle, (window, direction) -> {
            if (direction) {
                if (minimizedAction != null) {
                    minimizedAction.execute();
                }
            } else {
                if (reopenAction != null) {
                    reopenAction.execute();
                }
            }
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

        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);


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
     * Setter for Actions
     */

    public void setMinimizedAction(Action minimizedAction) {
        this.minimizedAction = minimizedAction;
    }

    public void setReopenAction(Action reopenAction) {
        this.reopenAction = reopenAction;
    }

    public void setClosedAction(Action closedAction) {
        this.closedAction = closedAction;
    }

    public void setOnFocusAction(Action onFocusAction) {
        this.onFocusAction = onFocusAction;
    }

    public void setScreen(int outputScreen) {
        screen = outputScreen;
        PointerBuffer monitors = glfwGetMonitors();
        long monitor = 0;
        if (monitors != null) {
            monitor = monitors.get(outputScreen - 1);
        }

        //get Resolution of the primary monitor
        monitorData = glfwGetVideoMode(monitor);

        int[] xPos = new int[1];
        int[] yPos = new int[1];
        glfwGetMonitorPos(monitor,xPos,yPos);

        if (monitor != glfwGetPrimaryMonitor()) {

            width = monitorData.width();
            height = monitorData.height() + 1;

            glfwSetWindowPos(windowHandle, xPos[0] + width/2, yPos[0] + height/2);

            glfwSetWindowMonitor(windowHandle,NULL,xPos[0], yPos[0], width, height,GLFW_DONT_CARE);
        } else {
            windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);

            glfwSetWindowMonitor(windowHandle,NULL,xPos[0],yPos[0], width, height,GLFW_DONT_CARE);
        }


    }

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
        return glfwGetVideoMode(glfwGetPrimaryMonitor()).height();
    }

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
        return glfwGetVideoMode(glfwGetPrimaryMonitor()).width();
    }

    public float getAspectRatio() {
        return width/(float) height;
    }
}
