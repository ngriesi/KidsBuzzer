package programs.quizOverlay.control.view;

import programs.quizOverlay.control.control.SimpleOutputView;

import javax.swing.*;
import java.awt.*;

/**
 * panel containing the simple preview of the output screen
 */
public class SimpleViewPanel extends JPanel {

    /**
     * simple preview object
     */
    private SimpleOutputView simpleOutputView;

    /**
     * sets the simple output view of the panel
     *
     * @param simpleOutputView simple output view
     */
    SimpleViewPanel(SimpleOutputView simpleOutputView) {
        this.simpleOutputView = simpleOutputView;
    }

    /**
     * paints the simple output view to this panel
     *
     * @param g graphical context of this panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        simpleOutputView.drawImage(g, this.getWidth(), this.getHeight());
    }
}
