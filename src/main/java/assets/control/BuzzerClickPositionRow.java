package assets.control;

import assets.standardAssets.MyCheckBox;
import assets.standardAssets.MyLabel;
import assets.standardAssets.MyTextField;
import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BuzzerClickPositionRow extends ControlViewRow {

    public static final String CLICK_POSITION = "clickPosition";
    public static final String X_CORD = "xCord";
    private static final String Y_CORD = "yCord";
    public static final String USE = "use";

    private MyTextField xCord,yCord;

    private MyCheckBox checkBox;


    public BuzzerClickPositionRow(String buzzerColor, ActionListener actionListener) {
        super(buzzerColor + " buzzer click cords");

        JPanel interactionElementsContainer = new JPanel(new GridBagLayout());
        interactionElementsContainer.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        xCord = new MyTextField("0");
        xCord.setPreferredSize(new Dimension(100, 40));
        xCord.setActionCommand(CLICK_POSITION + ":" + X_CORD + ":" + buzzerColor);
        xCord.addActionListener(actionListener);
        xCord.useOnlyInts();
        yCord = new MyTextField("0");
        yCord.setPreferredSize(new Dimension(100, 40));
        yCord.setActionCommand(CLICK_POSITION + ":" + Y_CORD + ":" + buzzerColor);
        yCord.addActionListener(actionListener);
        yCord.useOnlyInts();

        checkBox = new MyCheckBox();
        checkBox.setActionCommand(CLICK_POSITION + ":" + USE + ":" + buzzerColor);
        checkBox.addActionListener(actionListener);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, spacing);
        c.gridx = 0;
        interactionElementsContainer.add(new MyLabel("x:"), c);
        c.gridx = 1;
        interactionElementsContainer.add(xCord, c);
        c.gridx = 2;
        interactionElementsContainer.add(new MyLabel(" y:"), c);
        c.gridx = 3;
        interactionElementsContainer.add(yCord, c);
        c.gridx = 4;
        interactionElementsContainer.add(new MyLabel(" active:"), c);
        c.gridx = 5;
        interactionElementsContainer.add(checkBox, c);

        this.add(interactionElementsContainer, BorderLayout.LINE_END);
    }

    public int getXCord() {
        try {
            return Integer.parseInt(xCord.getText());
        } catch (NumberFormatException n) {
            return 0;
        }
    }

    public int getYCord() {
        try {
            return Integer.parseInt(yCord.getText());
        } catch (NumberFormatException n) {
            return 0;
        }
    }

    public void setXCord(int xCord) {
        this.xCord.setText(String.valueOf(xCord));
    }

    public void setYCord(int yCord) {
        this.yCord.setText(String.valueOf(yCord));
    }

    public void setActive(boolean active) {
        checkBox.setSelected(active);
    }
}
