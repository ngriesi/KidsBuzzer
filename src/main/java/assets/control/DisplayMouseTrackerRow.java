package assets.control;

import assets.standardAssets.MyCheckBox;
import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DisplayMouseTrackerRow extends ControlViewRow {

    public static final String MOUSE_TRACKER = "mouseTracker";

    private MyCheckBox checkBox;

    public DisplayMouseTrackerRow(ActionListener actionListener) {
        super("Display Mouse Tracker");

        JPanel interactionElementsContainer = new JPanel(new GridBagLayout());
        interactionElementsContainer.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        checkBox = new MyCheckBox();
        checkBox.setActionCommand(MOUSE_TRACKER);
        checkBox.addActionListener(actionListener);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, ControlViewRow.spacing);
        interactionElementsContainer.add(checkBox, c);

        this.add(interactionElementsContainer, BorderLayout.LINE_END);
    }

    public void setValue(boolean value) {
        checkBox.setSelected(value);
    }
}
