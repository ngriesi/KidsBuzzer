package remoteHandler.actions;

import java.awt.*;

/**
 * action performs a key press
 */
public class KeyAction extends RemoteAction {

    /**
     * creates a new key action for the given key code
     *
     */
    public KeyAction() {
        super();
    }

    /**
     * Methods Performs a Key Press
     *
     * @param keyCode key that gets pressed
     */
    @Override
    public void performAction(int keyCode) {
        new Thread(() -> {
            Robot bot;
            try {
                bot = new Robot();
                bot.keyPress(keyCode);
                Thread.sleep(100);
                bot.keyRelease(keyCode);
            } catch (AWTException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
