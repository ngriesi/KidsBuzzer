package assets.customWindow;

import assets.standardAssets.MyLabel;
import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.awt.GridBagConstraints.LINE_START;

/**
 * creates the title bar of the window, should be added as top component in the border layout of the frame
 */
class TitleBar extends JPanel {

    /**
     * stores the position difference between the mouse when it was pressed in this component and the top left corner of the window
     */
    private int framePositionXDiff = 0, framePositionYDiff = 0;

    /**
     * the window of which this is the title bar of
     */
    private JFrame frame;

    /**
     * creates a new title bar with a title and close, minimize and maximize buttons
     *
     * @param frame window this is the title bar of
     */
    TitleBar(JFrame frame) {
        super(new BorderLayout());

        this.frame = frame;

        setupLayout(createTitle(),createButtonBack());
    }

    /**
     * creates the layout for this title bar
     *
     * @param title title of the window
     * @param buttonPanel panel of the buttons in the top right corner
     */
    private void setupLayout(JLabel title, JPanel buttonPanel) {
        this.setBackground(StandardAssetFields.NORMAL_COLOR);
        this.setPreferredSize(new Dimension(frame.getWidth(),Toolkit.getDefaultToolkit().getScreenSize().height / 35));

        this.addMouseListener(createMouseListener());
        this.addMouseMotionListener(createMouseMotionListener());

        this.add(title,BorderLayout.LINE_START);
        this.add(buttonPanel, BorderLayout.LINE_END);
    }

    /**
     * @return returns the mouse listener for this component. it consumes the mouse entered event and sets the position difference
     * when the mouse gets pressed
     */
    private MouseListener createMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                framePositionXDiff = e.getXOnScreen() - frame.getX();
                framePositionYDiff = e.getYOnScreen() - frame.getY();
            }
        };
    }

    /**
     * @return returns the mouse motion listener for this component for dragging the window
     */
    private MouseMotionListener createMouseMotionListener() {
        return new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                frame.setLocation(e.getXOnScreen() - framePositionXDiff,e.getYOnScreen() - framePositionYDiff);
            }
        };
    }

    /**
     * @return returns the title for this title bar
     */
    private JLabel createTitle() {
        MyLabel title = new MyLabel(" Buzzer");
        title.setForeground(StandardAssetFields.FOREGROUND_COLOR);
        title.setOpaque(false);
        title.setFont(new Font("arial",Font.PLAIN,20));
        title.setAlignmentX(LINE_START);
        title.setHorizontalAlignment(SwingConstants.LEFT);
        return title;
    }

    /**
     * @return returns the layout for the button panel int the top corner
     */
    private FlowLayout createButtonBackLayout() {
        FlowLayout buttonBackLayout = new FlowLayout();
        buttonBackLayout.setVgap(0);
        buttonBackLayout.setHgap(0);
        return buttonBackLayout;
    }

    /**
     * @return returns the button panel for the buttons in the top right corner
     */
    private JPanel createButtonBack() {
        JPanel buttonBack = new JPanel(createButtonBackLayout());

        buttonBack.add(new MinimizeButton(frame));
        buttonBack.add(new MaximizeButton(frame));
        buttonBack.add(new CloseButton(frame));

        return buttonBack;
    }
}
