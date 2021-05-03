package assets.control;

import assets.standardAssets.MyCheckBox;
import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Row used to enable or disable a small window that tracks the mouse coordinates
 * <p>
 * The public static field is used to identify the action event passed to the action listener by
 * using the action command of the event
 * <p>
 * Action Command:
 * MOUSE_TRACKER
 * action used to disable/enable a mouse tracker window
 */
public class DisplayMouseTrackerRow extends ControlViewRow {

    /**
     * static field to access the value of the action command of the component of this view element
     * <p>
     * This field is used to identify the action in the action performed method of the action listener
     * that gets passed to this view in the constructor
     */
    public static final String MOUSE_TRACKER = "MOUSE_TRACKER";

    /**
     * check box used to disable/enable a mouse tracker window
     */
    private MyCheckBox checkBox;

    /**
     * constructor creates the layout of the row
     *
     * @param actionListener action listener for all components in this view element
     */
    public DisplayMouseTrackerRow(ActionListener actionListener) {
        super("Display Mouse Tracker");

        JPanel interactionElementsContainer = new JPanel(new GridBagLayout());
        interactionElementsContainer.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        createCheckBox(actionListener);
        addComponentsToPanel(interactionElementsContainer);

        this.add(interactionElementsContainer, BorderLayout.LINE_END);
    }

    /**
     * Adds all the components to a panel
     *
     * @param interactionElementsContainer panel the components get added to
     */
    private void addComponentsToPanel(JPanel interactionElementsContainer) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, ControlViewRow.spacing);
        interactionElementsContainer.add(checkBox, c);
    }

    /**
     * Method used to create the check box to enable or disable the action this view element controls
     *
     * @param actionListener action listener for all components in this view element
     */
    private void createCheckBox(ActionListener actionListener) {
        checkBox = new MyCheckBox();
        checkBox.setActionCommand(MOUSE_TRACKER);
        checkBox.addActionListener(actionListener);
    }

    /**
     * sets the value of the check box
     *
     * @param value value set to the check box
     */
    public void setValue(boolean value) {
        checkBox.setSelected(value);
    }
}
