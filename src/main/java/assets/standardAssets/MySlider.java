package assets.standardAssets;

import presentationWindow.engine.Action;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class MySlider extends JSlider {

    private List<ActionListener> actionListeners;

    private String actionCommand = "";

    private int maxValue;

    public MySlider(int maxValue) {
        super(JSlider.HORIZONTAL, 0, maxValue, 0);
        this.maxValue = maxValue;
        //this.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width/10,30));
        actionListeners = new LinkedList<>();

        this.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
        this.setFocusable(false);
        this.setForeground(StandardAssetFields.FOREGROUND_COLOR);

        this.addChangeListener(e -> {
            for (ActionListener actionListener : actionListeners) {
                actionListener.actionPerformed(new ActionEvent(MySlider.this, 0, MySlider.this.getActionCommand()));
            }
        });
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    public float getRelativeValue() {
        return super.getValue()/(float)maxValue;
    }

    public void setRelativeValue(float value) {
        super.setValue((int) (value * maxValue));
    }

    private String getActionCommand() {
        return actionCommand;
    }

    public void setActionCommand(String actionCommand) {
        this.actionCommand = actionCommand;
    }
}
