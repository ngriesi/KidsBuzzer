package controlWindow;

import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * a native key listener that is not dependent on the window being in focus. This is used to
 * control the application for example above a PowerPoint presentation
 */
public class NativeKeyListener implements org.jnativehook.keyboard.NativeKeyListener {

    /**
     * main controller of the application, used to pass the key actions to the different sub programs
     */
    private ControlModel controlModel;

    /**
     * flags storing the current state of the alt, control and shift keys
     */
    private boolean controlPressed, altPressed, shiftPressed;

    /**
     * creates a new native key listener with a reference to the <code>ControlModel</code> class
     *
     * @param controlModel reference to the main controller of the application
     */
    public NativeKeyListener(ControlModel controlModel) {
        this.controlModel = controlModel;
    }

    /**
     * Called every time a key gets typed (not used)
     *
     * @param nativeKeyEvent key event created by the key typing
     */
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

    /**
     * called every time a key gets pressed (called repeatedly when the key is held down)
     * used to update the values of the flags <code>controlPressed</code>, <code>altPressed</code>
     * and <code>shiftPressed</code>
     *
     * @param nativeKeyEvent key event created by the key press
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        updateControlAltShift(nativeKeyEvent.getKeyCode(), true);
    }

    /**
     * called every time a key is released
     * The method checks if the conditions for the deactivation/activation of the native key listener
     * are met (Alt + Control + Shift + K)
     * and updates the flags <code>controlPressed</code>, <code>altPressed</code>
     * and <code>shiftPressed</code>
     * and calls the method that passes the native key events to the currently used sub program in
     * <code>ControlModel</code>
     *
     * @param nativeKeyEvent key event created by the release of a key
     */
    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        if (controlPressed && shiftPressed && altPressed && nativeKeyEvent.getKeyCode() == 37) {
            controlModel.toggleNativeKeyListener();
        }

        updateControlAltShift(nativeKeyEvent.getKeyCode(), false);
        controlModel.nativeKeyAction(nativeKeyEvent.getKeyCode());
    }

    /**
     * method updates the flags <code>controlPressed</code>, <code>altPressed</code>
     * and <code>shiftPressed</code> with a keyCode and a pressed value. The key
     * code is used to check which key is processed and the pressed flag is the
     * new value the flags are assigned to
     *
     * @param keyCode code to identify the key
     * @param pressed new value for the flags
     */
    private void updateControlAltShift(int keyCode, boolean pressed) {
        switch (keyCode) {
            case 29:
                controlPressed = pressed;
                break;
            case 42:
            case 3638:
                shiftPressed = pressed;
                break;
            case 56:
                altPressed = pressed;
                break;
        }
    }
}
