package assets.customWindow;

import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

/**
 * creates a custom resizable frame with the default windows title bar with a different color from an undecorated window
 */
public class MyJFrame extends ResizeWindowPanel {

    /**
     * Panel that consumes the mouse entered event so the resizing can only be done on the edge of the window
     */
    private JPanel mouseEventConsumer;

    /**
     * stores the default shadow border of the window
     */
    private Border shadowBorder;

    /**
     * creates a new window with the passed bounds
     *
     * @param x      window x position
     * @param y      window y position
     * @param width  window width
     * @param height window height
     */
    public MyJFrame(int x, int y, int width, int height) {
        super(new JFrame());

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        setFrameSettings(screenWidth, screenHeight, x, y, width, height);

        createShadowBorder();
        if (getFrame().getHeight() == screenHeight && getFrame().getWidth() == screenWidth) {
            getFrame().setExtendedState(Frame.MAXIMIZED_BOTH);
            this.setBorder(BorderFactory.createLineBorder(StandardAssetFields.NORMAL_COLOR, 1));
        } else {
            chooseWindowBorder(screenWidth, screenHeight, getFrame().getState());
        }

        this.setBackground(new Color(0, 0, 0, 0));
        this.add(new TitleBar(super.getFrame()), BorderLayout.PAGE_START);

        createMouseEventConsumer();
    }

    /**
     * chooses the border of the window according to its state and size
     *
     * @param screenWidth  width of the screen
     * @param screenHeight height of the screen
     * @param state        state used to determine which border should be chosen
     */
    private void chooseWindowBorder(int screenWidth, int screenHeight, int state) {

        if (state != Frame.MAXIMIZED_BOTH || (super.getFrame().getSize().width != screenWidth || super.getFrame().getSize().height != screenHeight)) {
            this.setBorder(shadowBorder);
        } else {
            this.setBorder(BorderFactory.createLineBorder(StandardAssetFields.NORMAL_COLOR, 1));
        }
    }

    /**
     * creates the shadow border
     */
    private void createShadowBorder() {
        Border border1 = BorderFactory.createLineBorder(new Color(50, 50, 50, 60), 1);
        Border border2 = BorderFactory.createLineBorder(new Color(50, 50, 50, 30), 1);
        Border border3 = BorderFactory.createLineBorder(new Color(50, 50, 50, 10), 1);
        shadowBorder = BorderFactory.createCompoundBorder(border3, BorderFactory.createCompoundBorder(border2, border1));
    }

    /**
     * sets the settings for the frame
     *
     * @param screenWidth  width of the screen
     * @param screenHeight height of the screen
     * @param x            x position of the window
     * @param y            y position of the window
     * @param width        width of the window
     * @param height       height of the window
     */
    private void setFrameSettings(int screenWidth, int screenHeight, int x, int y, int width, int height) {
        setWindowBounds(screenWidth, screenHeight, x, y, width, height);
        setWindowAttributes();
        super.getFrame().addWindowStateListener(createWindowStateListener());
    }

    /**
     * sets the attributes of the window besides its bounds
     */
    private void setWindowAttributes() {
        super.getFrame().setUndecorated(true);
        super.getFrame().setContentPane(this);
        super.getFrame().setBackground(new Color(0, 0, 0, 0));
        super.getFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    /**
     * sets the bounds for the frame
     *
     * @param screenWidth  width of the screen
     * @param screenHeight height of the screen
     * @param x            x position of the window
     * @param y            y position of the window
     * @param width        width of the window
     * @param height       height of the window
     */
    private void setWindowBounds(int screenWidth, int screenHeight, int x, int y, int width, int height) {
        super.getFrame().setLocation(x, y);
        super.getFrame().setMinimumSize(new Dimension(screenWidth / 2, screenHeight / 2));
        super.getFrame().setMaximumSize(new Dimension(screenWidth, screenHeight));
        super.getFrame().setSize(width, height);
    }

    /**
     * @return returns a state listener for the window to set the border of the window
     */
    private WindowStateListener createWindowStateListener() {
        return e -> chooseWindowBorder(getFrame().getWidth(), getFrame().getHeight(), e.getNewState());
    }

    /**
     * creates a JPanel that lies under the content of the window and consumes the mouse entered event
     */
    private void createMouseEventConsumer() {
        mouseEventConsumer = new JPanel(new BorderLayout());
        mouseEventConsumer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            }
        });
        this.add(mouseEventConsumer, BorderLayout.CENTER);
    }

    /**
     * sets a new JPanel as content of the window and updates the visuals
     *
     * @param panel new content panel of the window
     */
    public void setView(JPanel panel) {
        if (mouseEventConsumer.getComponents().length > 0) {
            mouseEventConsumer.remove(0);
        }
        mouseEventConsumer.add(panel, BorderLayout.CENTER);
        super.getFrame().repaint();
        super.getFrame().setVisible(true);
    }

    /**
     * @return returns the window
     */
    public JFrame getFrame() {
        return super.getFrame();
    }
}
