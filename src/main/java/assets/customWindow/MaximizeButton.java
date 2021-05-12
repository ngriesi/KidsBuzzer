package assets.customWindow;

import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowStateListener;

/**
 * maximize button for the title bar
 */
public class MaximizeButton extends TitleBarButton {

    /**
     * state of the window defines the look of the button
     */
    private boolean maximized;

    /**
     * boolean used to draw the center of the normalize icon right depending on  the button state
     */
    private boolean colorChange = false;

    /**
     * creates a new maximize button
     *
     * @param frame window this button is inside
     */
    MaximizeButton(JFrame frame) {
        super(frame, StandardAssetFields.PRESSED_COLOR, StandardAssetFields.ROLLOVER_COLOR, StandardAssetFields.NORMAL_COLOR);

        frame.addWindowStateListener(createWindowStateListener());
        maximized = frame.getWidth() == Toolkit.getDefaultToolkit().getScreenSize().width && frame.getHeight() == Toolkit.getDefaultToolkit().getScreenSize().height;

        addChangeListener(evt -> colorChange = getModel().isRollover() || getModel().isPressed());
    }

    /**
     * action performed when this button gets clicked, changes the state of the window
     *
     * @param e action event
     */
    @Override
    void buttonAction(ActionEvent e) {
        if (maximized) {
            normalizeAction();
            maximized = false;
        } else {
            getFrame().setExtendedState(Frame.MAXIMIZED_BOTH);
            maximized = true;
        }
    }

    /**
     * action performed if the button gets clicked when the window was maximized
     */
    void normalizeAction() {
        getFrame().setExtendedState(Frame.NORMAL);
        if (getFrame().getHeight() == Toolkit.getDefaultToolkit().getScreenSize().height && getFrame().getWidth() == Toolkit.getDefaultToolkit().getScreenSize().width) {
            getFrame().setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
            getFrame().setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 4, Toolkit.getDefaultToolkit().getScreenSize().height / 4);
        }
    }

    /**
     * @return a change listener for the window to track if the window is currently maximized or not
     */
    private WindowStateListener createWindowStateListener() {
        return (e) -> {
            if (e.getNewState() == Frame.MAXIMIZED_BOTH) {
                maximized = true;
            } else if (e.getNewState() == JFrame.NORMAL) {
                maximized = false;
            }
        };
    }

    /**
     * draws the icons of the button
     *
     * @param g graphic context of the button
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = this.getWidth();
        int height = this.getHeight();

        if (!maximized) {
            drawMaximizeIcon(g, width, height);
        } else {
            drawNormalizeIcon(g, width, height);
        }
    }

    /**
     * draws the maximized button icon
     *
     * @param g      graphic context of this button
     * @param width  width of this button
     * @param height height of this button
     */
    private void drawMaximizeIcon(Graphics g, int width, int height) {
        g.setColor(StandardAssetFields.FOREGROUND_COLOR);
        g.drawRect(width / 8 * 3, height / 3, width / 4, height / 3);
    }

    /**
     * draws the minimized button icon
     *
     * @param g      graphic context of this button
     * @param width  width of this button
     * @param height height of this button
     */
    private void drawNormalizeIcon(Graphics g, int width, int height) {
        g.setColor(StandardAssetFields.FOREGROUND_COLOR);
        g.drawRect((int) (width / 100f * 43), (int) (height / 100f * 33), width / 12 * 2, (int) (height / 14f * 4));

        g.setColor(colorChange ? StandardAssetFields.PRESSED_COLOR : StandardAssetFields.NORMAL_COLOR);
        g.fillRect((int) (width / 100f * 40), (int) (height / 100f * 38), width / 12 * 2, (int) (height / 14f * 4));

        g.setColor(StandardAssetFields.FOREGROUND_COLOR);
        g.drawRect((int) (width / 100f * 40), (int) (height / 100f * 38), width / 12 * 2, (int) (height / 14f * 4));
    }
}
