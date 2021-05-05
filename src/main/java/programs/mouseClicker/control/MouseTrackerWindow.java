package programs.mouseClicker.control;

import assets.standardAssets.MyLabel;
import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a small window ot the top left corner of the screen
 * to display the current mouse positions
 */
public class MouseTrackerWindow extends JPanel {

    /**
     * window containing the labels showing the coordinates of the mouse
     */
    private JFrame window;

    /**
     * label displaying the x coordinate of the mouse
     */
    private MyLabel xCord;

    /**
     * label displaying the y coordinate of the mouse
     */
    private MyLabel yCord;

    /**
     * Constructor creates the <code>MouseTrackerWindow</code> by settings the settings for
     * the JFrame and creating the layout
     */
    public MouseTrackerWindow() {
        super(new BorderLayout());
        window = new JFrame();
        window.setUndecorated(true);
        window.setBackground(StandardAssetFields.NORMAL_COLOR);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setSize(screenSize.width / 10, screenSize.height / 20);

        this.setBackground(StandardAssetFields.NORMAL_COLOR);

        xCord = new MyLabel("x: 0");
        yCord = new MyLabel("y: 0");

        this.add(xCord, BorderLayout.LINE_START);
        this.add(yCord, BorderLayout.LINE_END);

        window.setContentPane(this);
    }

    /**
     * sets the visibility of the window and starts listening for the mouse position
     * if the visibility is set to true
     *
     * @param visibility visibility of the window
     */
    public void setWindowVisibility(boolean visibility) {
        window.setVisible(visibility);
        if (visibility) {
            listenForMousePosition();
        }
    }

    /**
     * starts a thread that retrieves the mouse position and sets it
     * as the content of the labels
     */
    private void listenForMousePosition() {
        new Thread(() -> {
            while (window.isVisible()) {
                PointerInfo inf = MouseInfo.getPointerInfo();
                Point p = inf.getLocation();
                SwingUtilities.invokeLater(() -> {
                    xCord.setText("x: " + p.x);
                    yCord.setText("y: " + p.y);
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
