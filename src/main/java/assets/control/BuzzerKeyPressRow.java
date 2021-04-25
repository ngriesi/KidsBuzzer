package assets.control;

import assets.standardAssets.MyCheckBox;
import assets.standardAssets.MyLabel;
import assets.standardAssets.MyTextField;
import assets.standardAssets.StandardAssetFields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class BuzzerKeyPressRow extends ControlViewRow {

    public static final String PRESS_KEY = "pressKey";
    public static final String KEY = "key";
    public static final String USE = "use";

    private MyTextField key;

    private MyCheckBox checkBox;


    public BuzzerKeyPressRow(String buzzerColor, ActionListener actionListener) {
        super(buzzerColor + " buzzer press key");

        JPanel interactionElementsContainer = new JPanel(new GridBagLayout());
        interactionElementsContainer.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        key = new MyTextField("0");
        key.setPreferredSize(new Dimension(100, 40));
        key.setActionCommand(PRESS_KEY + ":" + KEY +":"+ buzzerColor);
        key.addActionListener(actionListener);
        key.setMaximumLength(1);

        checkBox = new MyCheckBox();
        checkBox.setActionCommand(PRESS_KEY + ":" + USE + ":" + buzzerColor );
        checkBox.addActionListener(actionListener);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, spacing);
        c.gridx = 0;
        interactionElementsContainer.add(new MyLabel("Taste:"), c);
        c.gridx = 1;
        interactionElementsContainer.add(key, c);
        c.gridx = 2;
        interactionElementsContainer.add(new MyLabel(" Aktiv:"), c);
        c.gridx = 3;
        interactionElementsContainer.add(checkBox, c);

        this.add(interactionElementsContainer, BorderLayout.LINE_END);
    }

    public void setKey(int key) {
        this.key.setText(KeyEvent.getKeyText(key));
    }

    public void setActive(boolean active) {
        checkBox.setSelected(active);
    }

    public int getKey() {
        if (key.getText().toCharArray().length > 0) {
            System.out.println(KeyEvent.getExtendedKeyCodeForChar(key.getText().toCharArray()[0]));
            return KeyEvent.getExtendedKeyCodeForChar(key.getText().toCharArray()[0]);
        }
        return 0;
    }
}
