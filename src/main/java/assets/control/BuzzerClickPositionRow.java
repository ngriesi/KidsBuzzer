package assets.control;

import assets.standardAssets.MyCheckBox;
import assets.standardAssets.MyLabel;
import assets.standardAssets.MyTextField;
import assets.standardAssets.StandardAssetFields;
import savedataHandler.languages.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * A row in a view to set x and y coordinates. The row also contains a button to disable or enable
 * the function that uses the coordinates
 * <p>
 * The public static fields are used to identify the action events passed to the action listener by
 * using the action command of the event
 * <p>
 * possible action commands:
 * <p>
 * CLICK_POSITION:X_CORD:{name of the buzzer, retrieved from SaveDataHandler.BUZZER_NAMES}
 * this action is used to set the x cord of an action (e.g. click) performed by a buzzer
 * <p>
 * CLICK_POSITION:Y_CORD:{name of the buzzer, retrieved from SaveDataHandler.BUZZER_NAMES}
 * this action is used to set the y cord of an action (e.g. click) performed by a buzzer
 * <p>
 * CLICK_POSITION:USE:{name of the buzzer, retrieved from SaveDataHandler.BUZZER_NAMES}
 * this action is used to enable or disable the function controlled by this row
 */
public class BuzzerClickPositionRow extends ControlViewRow {

    /**
     * static fields to access the values of the action commands of the components of this view element
     * <p>
     * These fields are usd to identify the actions in the action performed method of the action listener
     * that gets passed to this view in the constructor
     */
    @SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
    public static final String CLICK_POSITION = "CLICK_POSITION", X_CORD = "X_CORD", Y_CORD = "Y_CORD", USE = "USE";

    /**
     * Text fields that only allow ints to set the x and y coordinates
     */
    private MyTextField xCord, yCord;

    /**
     * check box to disable or enable the function this view element controls
     */
    private MyCheckBox checkBox;


    /**
     * constructor that creates the view
     *
     * @param buzzerColor    color of the buzzer, the action of this view element belongs to
     * @param actionListener action listener for all components in this view element
     */
    public BuzzerClickPositionRow(String buzzerColor, ActionListener actionListener) {
        super(buzzerColor + " " + Text.BUZZER_CLICK_CORDS);

        JPanel interactionElementsContainer = new JPanel(new GridBagLayout());
        interactionElementsContainer.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        createInteractionElements(buzzerColor, actionListener);

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
        interactionElementsContainer.add(new MyLabel("x:"), c);
        c.gridx = 1;
        interactionElementsContainer.add(xCord, c);
        c.gridx = 2;
        interactionElementsContainer.add(new MyLabel(" y:"), c);
        c.gridx = 3;
        interactionElementsContainer.add(yCord, c);
        c.gridx = 4;
        interactionElementsContainer.add(new MyLabel(" "+Text.ACTIVE+":"), c);
        c.gridx = 5;
        interactionElementsContainer.add(checkBox, c);
    }

    /**
     * Method used to create the interaction elements of the view
     *
     * @param buzzerColor    color of the buzzer this view element belongs to
     * @param actionListener action listener for all components in this view element
     */
    private void createInteractionElements(String buzzerColor, ActionListener actionListener) {
        xCord = createTextField(buzzerColor, actionListener, true);
        yCord = createTextField(buzzerColor, actionListener, false);

        checkBox = createCheckBox(buzzerColor, actionListener);
    }

    /**
     * Method used to create the check box to enable or disable the action this view element controls
     *
     * @param buzzerColor    color of the buzzer this view element belongs to
     * @param actionListener action listener for all components in this view element
     * @return completely build check box
     */
    private MyCheckBox createCheckBox(String buzzerColor, ActionListener actionListener) {
        MyCheckBox checkBox = new MyCheckBox();
        checkBox.setActionCommand(CLICK_POSITION + ":" + USE + ":" + buzzerColor);
        checkBox.addActionListener(actionListener);
        return checkBox;
    }

    /**
     * Method used to create the two text fields that are used to input the x and y coordinates
     *
     * @param buzzerColor    color of the buzzer this view element belongs to
     * @param actionListener action listener for all components in this view element
     * @param xCord          flag used to determine whether the field is the one used for the x coordinates
     *                       or the one used for the y coordinates
     * @return completely build text field
     */
    private MyTextField createTextField(String buzzerColor, ActionListener actionListener, boolean xCord) {
        MyTextField textField = new MyTextField("0");
        textField.setPreferredSize(new Dimension(100, 40));
        textField.setActionCommand(CLICK_POSITION + ":" + (xCord ? X_CORD : Y_CORD) + ":" + buzzerColor);
        textField.addActionListener(actionListener);
        textField.useOnlyInts();
        return textField;
    }

    /**
     * Method returns the x coordinate. T
     * It returns the content of the text field xCord as an int. If the conversion to an int fails it returns 0
     *
     * @return returns the x coordinate
     */
    public int getXCord() {
        try {
            return Integer.parseInt(xCord.getText());
        } catch (NumberFormatException n) {
            return 0;
        }
    }

    /**
     * Method returns the y coordinate. T
     * It returns the content of the text field yCord as an int. If the conversion to an int fails it returns 0
     *
     * @return returns the y coordinate
     */
    public int getYCord() {
        try {
            return Integer.parseInt(yCord.getText());
        } catch (NumberFormatException n) {
            return 0;
        }
    }

    /**
     * sets the content of the text field xCord to the given value
     *
     * @param xCord value set to the text field xCord
     */
    public void setXCord(int xCord) {
        this.xCord.setText(String.valueOf(xCord));
    }

    /**
     * sets the content of the text field yCord to the given value
     *
     * @param yCord value set to the text field xCord
     */
    public void setYCord(int yCord) {
        this.yCord.setText(String.valueOf(yCord));
    }

    /**
     * sets the value of the check box
     *
     * @param active value set to the check box
     */
    public void setActive(boolean active) {
        checkBox.setSelected(active);
    }
}
