package assets.control;

import assets.standardAssets.KeySelector;
import assets.standardAssets.MyCheckBox;
import assets.standardAssets.MyLabel;
import assets.standardAssets.StandardAssetFields;
import savedataHandler.languages.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * row in a control layout used to bind a key to a buzzer. The row contains a text field to set the key
 * and a check box to disable/enable the key binding
 * <p>
 * The public static fields are used to identify the action events passed to the action listener by
 * using the action command of the event
 * <p>
 * possible action commands:
 * <p>
 * PRESS_KEY:KEY:{name of the buzzer, retrieved from SaveDataHandler.BUZZER_NAMES}
 *
 * @see savedataHandler.SaveDataHandler for BUZZER_NAMES
 * this action is used to set the key that is binded to a buzzer
 * use event.getSource() to access the content of the text field
 * <p>
 * PRESS_KEY:KEY:{name of the buzzer, retrieved from SaveDataHandler.BUZZER_NAMES}
 * @see savedataHandler.SaveDataHandler for BUZZER_NAMES
 * this action is used to enable/disable the key binding
 * use event.getSource() to access the state of the check box
 */
public class BuzzerKeyPressRow extends ControlViewRow {

    /**
     * static fields to access the values of the action commands of the components of this view element
     * <p>
     * These fields are usd to identify the actions in the action performed method of the action listener
     * that gets passed to this view in the constructor
     */
    @SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
    public static final String PRESS_KEY = "PRESS_KEY", KEY = "KEY", USE = "USE";

    /**
     * Text field used to set the key that gets bound to the buzzer
     * <p>
     * The <code>KeySelector</code> to select the Keys
     */
    private KeySelector key;

    /**
     * check box used to disable the key binding of a buzzer
     */
    private MyCheckBox checkBox;

    /**
     * Constructor creates this view element
     *
     * @param buzzerColor    color of the buzzer this row belongs to
     * @param actionListener action listener for all components in this view element
     * @see savedataHandler.SaveDataHandler for the buzzer names
     */
    public BuzzerKeyPressRow(String buzzerColor, ActionListener actionListener) {
        super(buzzerColor + " " + Text.BUZZER_PRESS_KEY);

        JPanel interactionElementsContainer = new JPanel(new GridBagLayout());
        interactionElementsContainer.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        createKeySelector(buzzerColor, actionListener);
        createCheckBox(buzzerColor, actionListener);

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
        c.insets = new Insets(0, 0, 0, spacing);
        c.gridx = 0;
        interactionElementsContainer.add(new MyLabel(Text.KEY + ":"), c);
        c.gridx = 1;
        interactionElementsContainer.add(key, c);
        c.gridx = 2;
        interactionElementsContainer.add(new MyLabel(" " + Text.ACTIVE + ":"), c);
        c.gridx = 3;
        interactionElementsContainer.add(checkBox, c);
    }

    /**
     * Method used to create the check box to enable or disable the action this view element controls
     *
     * @param buzzerColor    color of the buzzer this view element belongs to
     * @param actionListener action listener for all components in this view element
     */
    private void createCheckBox(String buzzerColor, ActionListener actionListener) {
        checkBox = new MyCheckBox();
        checkBox.setActionCommand(PRESS_KEY + ":" + USE + ":" + buzzerColor);
        checkBox.addActionListener(actionListener);
    }

    /**
     * Method used to create the text field key
     *
     * @param buzzerColor    color of the buzzer this view element belongs to
     * @param actionListener action listener for all components in this view element
     */
    private void createKeySelector(String buzzerColor, ActionListener actionListener) {
        key = new KeySelector();
        key.setPreferredSize(new Dimension(200, 40));
        key.setActionCommand(PRESS_KEY + ":" + KEY + ":" + buzzerColor);
        key.addActionListener(actionListener);
    }

    /**
     * sets the value of the text field key with a given key code
     *
     * @param key key code of the char that gets set as text of the text field key
     */
    public void setKey(int key) {
        this.key.setKey(key);
    }

    /**
     * sets the value of the check box
     *
     * @param active value set to the check box
     */
    public void setActive(boolean active) {
        checkBox.setSelected(active);
    }

    /**
     * returns the key code of the char entered in the text field
     * if no text was entered the method returns 0
     *
     * @return returns the key code of the key entered to the text field key
     */
    public int getKey() {
        return key.getKey();
    }
}
