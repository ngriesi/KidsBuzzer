package remoteHandler.actions;

import java.awt.*;

/**
 * action performs a key press
 */
public class KeyAction extends RemoteAction {

    /**
     * creates a new key action for the given key code
     *
     * @param keyCode code of the key that gets pressed
     */
    public KeyAction(int keyCode) {
        super(() -> new Thread(() -> {
            Robot bot;
            try {
                bot = new Robot();
                bot.keyPress(keyCode);
                Thread.sleep(100);
                bot.keyRelease(keyCode);
            } catch (AWTException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
    }
}
