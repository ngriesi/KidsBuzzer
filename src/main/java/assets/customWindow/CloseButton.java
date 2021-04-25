package assets.customWindow;

import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

/**
 * class represents the close (X) button on the title bar, it creates its default look and gives it its action
 */
public class CloseButton extends TitleBarButton {

    /**
     * creates the close button. Sets the colors for the different states
     *
     * @param frame window the button is inside
     */
    CloseButton(JFrame frame) {
        super(frame, new Color(250,150,150),new Color(250,0,0), StandardAssetFields.NORMAL_COLOR);
    }

    /**
     * actions of the button. Dispatches a WINDOW_CLOSING event to close the JFrame this button is inside.
     *
     * @param event unused
     */
    @Override
    void buttonAction(ActionEvent event) {
        getFrame().dispatchEvent(new WindowEvent(getFrame(), WindowEvent.WINDOW_CLOSING));
    }

    /**
     * draws the cross on the button
     *
     * @param g graphic context of this button
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = this.getWidth();
        int height = this.getHeight();

        g.setColor(Color.WHITE);

        g.drawLine(width/5 * 2,height/3, width/5 * 3,height/3 * 2);
        g.drawLine(width/5 * 2,height/3 * 2, width/5 * 3,height/3);
    }
}
