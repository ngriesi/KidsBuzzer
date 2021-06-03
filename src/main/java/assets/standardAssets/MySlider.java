package assets.standardAssets;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

/**
 * custom slider with a different layout
 */
public class MySlider extends JSlider {

    /**
     * action listeners of the slider
     */
    private List<ActionListener> actionListeners;

    /**
     * action command of the slider
     */
    private String actionCommand = "";

    /**
     * maximum value of the slider
     */
    private int maxValue;

    /**
     * creates a new slider with a maximum value
     *
     * @param maxValue maximum value
     */
    public MySlider(int maxValue) {
        super(JSlider.HORIZONTAL, 0, maxValue, 0);
        this.maxValue = maxValue;
        actionListeners = new LinkedList<>();

        setLayoutValues();

        this.addChangeListener(e -> changeListenerAction());
    }

    /**
     * action of the change listener - creates an action event for the action listeners
     */
    private void changeListenerAction() {
        for (ActionListener actionListener : actionListeners) {
            actionListener.actionPerformed(new ActionEvent(MySlider.this, 0, MySlider.this.getActionCommand()));
        }
    }


    /**
     * sets the values for the layout
     */
    private void setLayoutValues() {
        this.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        this.setFocusable(false);
        this.setForeground(StandardAssetFields.FOREGROUND_COLOR);
    }

    /**
     * adds an action listener that gets an action event if the slider moves
     *
     * @param actionListener action listener ot be added
     */
    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    /**
     * gets the current position of the slider as a relative value
     * between 0 and 1
     *
     * @return returns the state of the slider as a value between 0 and 1
     */
    public float getRelativeValue() {
        return super.getValue() / (float) maxValue;
    }

    /**
     * sets the value of the slider (range 0 to 1)
     *
     * @param value new value of the slider
     */
    public void setRelativeValue(float value) {

        super.setValue((int) (Math.max(Math.min(value, 1), 0) * maxValue));
    }

    /**
     * getter to access the action command of this component
     *
     * @return returns the action command of this component
     */
    private String getActionCommand() {
        return actionCommand;
    }

    /**
     * sets the action command for this component
     *
     * @param actionCommand new action command of this component
     */
    public void setActionCommand(String actionCommand) {
        this.actionCommand = actionCommand;
    }
}
