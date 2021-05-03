package assets.customWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * creates a panel that enables an undecorated window to be resizable again
 */
public class ResizeWindowPanel extends JPanel {

    /**
     * values for the possible x and y directions (prime numbers)
     */
    private final int X_LEFT = 5, X_RIGHT = 3, X_NOTING = 2, Y_UP = 7, Y_DOWN = 11, Y_NOTHING = 13;

    /**
     * stores the (old) bounds of the window
     */
    private int oldFrameX, oldFrameY, oldFrameWidth, oldFrameHeight;

    /**
     * stores the current resize direction
     */
    private Point currentDirection = new Point(0, 0);

    /**
     * the window this resize panel is inside
     */
    private JFrame frame;

    /**
     * if true the window is currently getting resized
     */
    private boolean dragging = false;

    /**
     * creates a new resize panel by creating the mouse and mouse motion listeners
     *
     * @param frame window this resize panel is inside
     */
    ResizeWindowPanel(JFrame frame) {
        super(new BorderLayout());
        this.frame = frame;

        this.addMouseListener(createMouseListener());
        this.addMouseMotionListener(createMouseMotionListener());
    }

    /**
     * @return returns the mouse motion listener for this component
     */
    private MouseMotionListener createMouseMotionListener() {
        return new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updateCursor(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseDraggedAction(e);
            }
        };
    }

    /**
     * @return returns the mouse listener for this component
     */
    private MouseListener createMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                updateCursor(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                frame.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
            }
        };
    }

    /**
     * calculates the new height of the window
     *
     * @param direction resize direction
     * @param yOnScreen y position of the mouse on screen
     * @return new window height
     */
    private int calculateNewHeight(int direction, int yOnScreen) {
        switch (direction) {
            case Y_UP:
                return oldFrameHeight + oldFrameY - yOnScreen;
            case Y_DOWN:
                return yOnScreen - oldFrameY;
            default:
                return oldFrameHeight;
        }
    }

    /**
     * calculates the new width of the window
     *
     * @param direction resize direction
     * @param xOnScreen x position of the mouse on screen
     * @return new window width
     */
    private int calculateNewWidth(int direction, int xOnScreen) {
        switch (direction) {
            case X_LEFT:
                return oldFrameWidth + oldFrameX - xOnScreen;
            case X_RIGHT:
                return xOnScreen - oldFrameX;
            default:
                return oldFrameWidth;
        }
    }

    /**
     * calculates the new bounds of the screen
     *
     * @param e the mouse event
     */
    private void mouseDraggedAction(MouseEvent e) {
        dragging = true;
        int newHeight = calculateNewHeight(currentDirection.y, e.getYOnScreen());
        int newWidth = calculateNewWidth(currentDirection.x, e.getXOnScreen());
        int newX = currentDirection.x == X_LEFT ? e.getXOnScreen() : oldFrameX;
        int newY = currentDirection.y == Y_UP ? e.getYOnScreen() : oldFrameY;

        setFrameBounds(newX, newY, newWidth, newHeight);
    }

    /**
     * sets new bounds to the screen if they are bigger than the minimal size
     *
     * @param x      new x position of the screen
     * @param y      new y position of the screen
     * @param width  new width of the screen
     * @param height new height of the screen
     */
    private void setFrameBounds(int x, int y, int width, int height) {
        frame.setSize(width, height);
        Point newPosition = frame.getLocationOnScreen();
        if (width > frame.getMinimumSize().width) newPosition.x = x;
        if (height > frame.getMinimumSize().height) newPosition.y = y;
        frame.setLocation(newPosition);
    }

    /**
     * stores the current window bounds in fields
     */
    private void setOldValues() {
        oldFrameX = frame.getX();
        oldFrameY = frame.getY();
        oldFrameWidth = frame.getWidth();
        oldFrameHeight = frame.getHeight();
    }

    /**
     * updates the mouse cursor according to its position
     *
     * @param e mouse event
     */
    private void updateCursor(MouseEvent e) {
        int xDiff = Toolkit.getDefaultToolkit().getScreenSize().width / 20;
        int yDiff = Toolkit.getDefaultToolkit().getScreenSize().height / 20;

        int mouseX = e.getXOnScreen();
        int mouseY = e.getYOnScreen();

        setOldValues();


        if (!dragging) {
            int horDir = mouseX < oldFrameX + xDiff ? X_LEFT : mouseX > oldFrameX + oldFrameWidth - xDiff ? X_RIGHT : X_NOTING;
            int verDir = mouseY < oldFrameY + yDiff ? Y_UP : mouseY > oldFrameY + oldFrameHeight - yDiff ? Y_DOWN : Y_NOTHING;

            currentDirection = new Point(horDir, verDir);

            updateCursor(horDir * verDir);
        }

    }

    /**
     * sets the new mouse cursor
     *
     * @param direction direction the resize arrow should be facing
     */
    private void updateCursor(int direction) {
        switch (direction) {
            case Y_DOWN * X_NOTING:
                frame.getContentPane().setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
                break;
            case Y_UP * X_NOTING:
                frame.getContentPane().setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
                break;
            case X_RIGHT * Y_NOTHING:
                frame.getContentPane().setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
                break;
            case X_RIGHT * Y_DOWN:
                frame.getContentPane().setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
                break;
            case X_RIGHT * Y_UP:
                frame.getContentPane().setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
                break;
            case X_LEFT * Y_NOTHING:
                frame.getContentPane().setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
                break;
            case X_LEFT * Y_DOWN:
                frame.getContentPane().setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
                break;
            case X_LEFT * Y_UP:
                frame.getContentPane().setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
                break;
        }
    }

    /**
     * @return returns the frame this resize panel belongs to
     */
    public JFrame getFrame() {
        return frame;
    }
}
