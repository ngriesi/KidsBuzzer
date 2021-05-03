package controlWindow;

import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.HashMap;
import java.util.Map;

public class NativeKeyListener implements org.jnativehook.keyboard.NativeKeyListener {

    private ControlModel controlModel;

    private boolean controlPressed, altPressed, shiftPressed;

    /**
     * map saves the states of the keys
     */
    private Map<Integer,Boolean> keyStates;

    public NativeKeyListener(ControlModel controlModel) {
        this.controlModel = controlModel;
        keyStates = new HashMap<>();
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        switch (nativeKeyEvent.getKeyCode()) {
            case 29: controlPressed = true; break;
            case 42:
            case 3638:shiftPressed = true; break;
            case 56: altPressed = true; break;
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        if (controlPressed && shiftPressed && altPressed && nativeKeyEvent.getKeyCode() == 37) {

            controlModel.toggleNativeKeyListener();
        }

        switch (nativeKeyEvent.getKeyCode()) {
            case 29: controlPressed = false; break;
            case 42:
            case 3638:shiftPressed = false; break;
            case 56: altPressed = false; break;
        }
        controlModel.nativeKeyAction(nativeKeyEvent.getKeyCode());
    }
}
