package assets.customWindow;

import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * minimized button for the title bar
 */
public class MinimizeButton extends TitleBarButton {

    /**
     * creates a minimized button
     *
     * @param frame window the button is inside
     */
    MinimizeButton(JFrame frame) {
        super(frame, StandardAssetFields.PRESSED_COLOR, StandardAssetFields.ROLLOVER_COLOR, StandardAssetFields.NORMAL_COLOR);
    }

    /**
     * action of this button: iconify the window
     *
     * @param e action event
     */
    @Override
    void buttonAction(ActionEvent e) {
        getFrame().setState(Frame.ICONIFIED);
    }

    /**
     * draws the icon of the minimize button
     *
     * @param g graphic context of this button
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = this.getWidth();
        int height = this.getHeight();

        g.setColor(StandardAssetFields.FOREGROUND_COLOR);
        g.drawLine((int) (width / 5f * 2), height / 2, (int) (width / 5f * 3), height / 2);
    }
}
