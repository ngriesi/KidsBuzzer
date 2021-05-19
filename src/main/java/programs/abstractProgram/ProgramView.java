package programs.abstractProgram;

import assets.standardAssets.MyPanel;
import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * view of the program in the control window
 */
@SuppressWarnings("unused")
public abstract class ProgramView extends MyPanel {

    /**
     * controller of this view
     */
    private ActionListener actionListener;

    /**
     * creates a new view
     *
     * @param actionListener sets the actionListener of the view
     */
    public ProgramView(ActionListener actionListener) {
        super(new GridBagLayout());
        this.actionListener = actionListener;
        this.setBorder(createShadowBorder());
        this.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);
    }

    /**
     * creates a shadow border for the panel
     *
     * @return border for the panel
     */
    Border createShadowBorder() {
        Border border1 = BorderFactory.createLineBorder(new Color(50, 50, 50, 20), 3);
        Border border2 = BorderFactory.createLineBorder(new Color(50, 50, 50, 50), 3);
        Border border3 = BorderFactory.createLineBorder(new Color(50, 50, 50, 90), 3);
        return BorderFactory.createCompoundBorder(border3, BorderFactory.createCompoundBorder(border2, border1));
    }

    /**
     * sets a new action listener
     *
     * @param actionListener new action listener
     */
    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }
}
