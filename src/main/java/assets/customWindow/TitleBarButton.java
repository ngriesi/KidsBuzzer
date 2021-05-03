package assets.customWindow;

import assets.standardAssets.CleanButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * abstract super class for all the buttons in the title bar, storing the frame the buttons are in and
 * sets the size of the buttons
 */
abstract class TitleBarButton extends CleanButton {

    /**
     * Frame this Button is inside
     */
    private JFrame frame;

    /**
     * constructor creates a new TitleBarButton, calls the constructor of CleanButton and sets the colors for the different states
     *
     * @param frame         window the button is inside
     * @param pressedColor  color of the button when it is pressed
     * @param rolloverColor color of the button when the mouse rolls over it
     * @param normalColor   color of the button when it is pressed
     */
    TitleBarButton(JFrame frame, Color pressedColor, Color rolloverColor, Color normalColor) {
        super(pressedColor, rolloverColor, normalColor);
        this.frame = frame;
        this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 35, Toolkit.getDefaultToolkit().getScreenSize().height / 35));
        this.addActionListener(this::buttonAction);
    }

    /**
     * @return the window the button is in
     */
    JFrame getFrame() {
        return frame;
    }

    /**
     * action, performed when the button is clicked
     *
     * @param e action event
     */
    abstract void buttonAction(ActionEvent e);
}
