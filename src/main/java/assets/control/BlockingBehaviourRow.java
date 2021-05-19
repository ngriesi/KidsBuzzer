package assets.control;

import assets.combobox.MyComboBox;
import assets.standardAssets.*;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static savedataHandler.SaveDataHandler.BUZZER_NAMES;

/**
 * class to create a row in the control view to set the blocking behaviour of the buzzers
 * <p>
 * the class needs an action listener to be used. The public static fields are used to identify the actions
 * in the action listeners action performed method by the action command of the action event
 * <p>
 * Possible Action commands:
 * <p>
 * BLOCKING_BEHAVIOUR:BUZZER_SELECTOR:{name of the buzzer, retrieved from SaveDataHandler.BUZZER_NAMES}
 *
 * @see SaveDataHandler
 * Example: "BLOCKING_BEHAVIOUR:BUZZER_SELECTOR:RED"
 * The passed buzzer (name) is used to unblock all buzzers
 * <p>
 * BLOCKING_BEHAVIOUR:BLOCK_TIME
 * The buzzers are individually blocked for a given time in milliseconds
 * <p>
 * BLOCKING_BEHAVIOUR:RESET
 * The buzzers are blocked until the reset button is pressed
 * <p>
 * BLOCKING_BEHAVIOUR:MAIN_SELECTOR
 * Actions with this action command are caused by the main selector. To use these the combo box has to be accessed
 * from the e.getSource() method of the action event.
 * With this the content of the newly selected field of the combo box can be retrieved and used to update the
 * behaviour of the program.
 * Possible values are: "Don't block", "Block until Released", "Block for Time", "Unblock with Buzzer"
 */
public class BlockingBehaviourRow extends ControlViewRow {

    /**
     * static fields to access the values of the action commands of the components of this view element
     * <p>
     * These fields are usd to identify the actions in the action performed method of the action listener
     * that gets passed to this view in the constructor
     */
    public static final String BLOCKING_BEHAVIOUR = "BLOCKING_BEHAVIOUR", RESET = "RESET", BLOCK_TIME = "BLOCK_TIME",
            BUZZER_SELECTOR = "BUZZER_SELECTOR", MAIN_SELECTOR = "MAIN_SELECTOR";

    /**
     * The main selector is the combo box that is always visible in the view element and is used to determine the
     * general blocking behaviour of the program
     * <p>
     * Values are: "Don't block", "Block until Released", "Block for Time", "Unblock with Buzzer"
     */
    private MyComboBox<String> mainSelector;

    /**
     * Text field that only allows numbers as input is used to set the time the buzzers should get blocked if "Block for Time"
     * is selected in the main selector
     * <p>
     * The text field is only visible if this option in the main selector is selected
     */
    private MyTextField blockTime;

    /**
     * CheckBoxes that are used to set the buzzer used for unblocking if "Unblock with Buzzer"
     * is selected in the main selector
     * <p>
     * The CheckBoxes are only visible if this option in the main selector is selected
     */
    private MyCheckBox[] buzzer;

    /**
     * Button used to unblock the buzzers if "Block until Released"
     * is selected in the main selector
     * <p>
     * The Button field is only visible if this option in the main selector is selected
     */
    private MyButton releaseButton;

    /**
     * Background panel of the Check boxes used to select the buzzer which unblocks all buzzers if the option
     * "Unblock with Buzzer" is selected in the main selector
     * <p>
     * The panel is only visible if this option is selected
     */
    private JPanel buzzerChooserBack;

    /**
     * constructor creates the layout
     *
     * @param actionListener action listener used by all the components in this view element
     */
    public BlockingBehaviourRow(ActionListener actionListener) {
        super(Text.BLOCKING_BEHAVIOUR);

        createLayoutElements(actionListener);

        this.add(createLeftSidePanel(), BorderLayout.LINE_START);
        this.add(createInteractionElementContainer(), BorderLayout.LINE_END);
    }

    /**
     * method calls the methods to create the main layout elements of the right side section of this view element,
     * containing all the interactive parts
     *
     * @param actionListener action listener used by all the components in this view element
     */
    private void createLayoutElements(ActionListener actionListener) {
        createMainSelector(actionListener);
        createReleaseButton(actionListener);
        createBlockTime(actionListener);
        createBuzzerChooserBack();
        createBuzzerButtons(actionListener);
    }

    /**
     * Method creates the panel that sits at the left side of this view element and contains the description label
     *
     * @return returns the completely build panel
     */
    private JPanel createLeftSidePanel() {
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, spacing, 0, spacing * 3);
        c.gridx = 0;
        center.add(new MyLabel(Text.BLOCKING_BEHAVIOUR), c);
        c.gridx = 1;
        center.add(mainSelector, c);

        return center;
    }

    /**
     * creates the panel that contains all the interaction elements of this view element and sits
     * on the right side
     *
     * @return returns the completely build panel
     */
    private JPanel createInteractionElementContainer() {
        JPanel interactionElementsContainer = new JPanel(new GridBagLayout());
        interactionElementsContainer.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, spacing);
        c.gridx = 0;
        interactionElementsContainer.add(releaseButton, c);
        interactionElementsContainer.add(blockTime, c);
        interactionElementsContainer.add(buzzerChooserBack, c);

        return interactionElementsContainer;
    }

    /**
     * creates the background panel for the check boxes used to select a buzzer if the
     * main selector is at "Unblock with Buzzer"
     */
    private void createBuzzerChooserBack() {
        buzzerChooserBack = new JPanel();
        buzzerChooserBack.setBackground(StandardAssetFields.NORMAL_COLOR);
        buzzerChooserBack.setVisible(false);
    }

    /**
     * creates check boxes resembling the buzzers. These are used to select a buzzer to unblock
     * all buzzers if the main selector is at the option "Unblock with Buzzer"
     *
     * @param actionListener action listener used by all the components in the view element
     */
    private void createBuzzerButtons(ActionListener actionListener) {
        buzzer = new MyCheckBox[3];
        for (int i = 0; i < 3; i++) {
            buzzer[i] = createBuzzerButton(i, actionListener);
            buzzerChooserBack.add(buzzer[i]);
        }
    }

    /**
     * method that creates one of the buttons resembling the buzzers. These buttons are used to
     * select a buzzer to unblock all buzzers if the main selector is at the option "Unblock with Buzzer"
     * <p>
     * Action Command: BLOCKING_BEHAVIOUR:BUZZER_SELECTOR:{name of the buzzer, retrieved from SaveDataHandler.BUZZER_NAMES}
     *
     * @param i              number of the buzzer (starting with 0)
     * @param actionListener action listener used by all the components in the view element
     * @return returns a Check Box resembling a buzzer
     * @see SaveDataHandler
     */
    private MyCheckBox createBuzzerButton(int i, ActionListener actionListener) {
        MyCheckBox buzzer = new MyCheckBox();
        buzzer.setBorder(BorderFactory.createEmptyBorder());
        buzzer.setSelectedColor(SaveDataHandler.BUZZER_COLORS_PRESSED[i]);
        buzzer.setPressedColor(SaveDataHandler.BUZZER_COLORS_UNPRESSED[i]);
        buzzer.setActionCommand(BLOCKING_BEHAVIOUR + ":" + BUZZER_SELECTOR + ":" + BUZZER_NAMES[i]);
        buzzer.addActionListener(actionListener);
        buzzer.addActionListener(e -> buzzerAction(BUZZER_NAMES[i]));
        return buzzer;
    }

    /**
     * creates a TextField used to set the time (in milliseconds) the buzzers get blocked
     * It is only visible if the main selector is at the option "Block for Time".
     * The TextField only allows integers
     * <p>
     * Action Command: BLOCKING_BEHAVIOUR:BLOCK_TIME
     *
     * @param actionListener action listener used by all the components in this view element
     */
    private void createBlockTime(ActionListener actionListener) {
        blockTime = new MyTextField("1");
        blockTime.setActionCommand(BLOCKING_BEHAVIOUR + ":" + BLOCK_TIME);
        blockTime.addActionListener(actionListener);
        blockTime.setVisible(false);
    }

    /**
     * creates the button used to unblock all the buzzers:
     * It is only visible if the main selector is at the option "Block until Released"
     * <p>
     * Action Command: BLOCKING_BEHAVIOUR:RESET
     *
     * @param actionListener action listener used by all the components in this view element
     */
    private void createReleaseButton(ActionListener actionListener) {
        releaseButton = new MyButton(Text.RESET_BUZZER);
        releaseButton.setActionCommand(BLOCKING_BEHAVIOUR + ":" + RESET);
        releaseButton.addActionListener(actionListener);
        releaseButton.setVisible(false);
    }

    /**
     * creates the main selector (Combo Box) to select the blocking behaviour of the Buzzers in the program that uses this view
     * it has the options: "Don't block", "Block until Released", "Block for Time", "Unblock with Buzzer"
     * <p>
     * Action Command: BLOCKING_BEHAVIOUR:MAIN_SELECTOR
     *
     * @param actionListener action listener used by all the components in this view element
     */
    private void createMainSelector(ActionListener actionListener) {
        mainSelector = new MyComboBox<>(new String[]{Text.DONT_BLOCK, Text.BLOCK_UNTIL_RELEASED, Text.BLOCK_FOR_TIME, Text.UNBLOCK_WITH_BUZZER});
        mainSelector.setFont(new Font("arial", Font.PLAIN, 20));
        mainSelector.setPreferredSize(new Dimension(250, 40));
        mainSelector.setActionCommand(BLOCKING_BEHAVIOUR + ":" + MAIN_SELECTOR);
        mainSelector.addActionListener(actionListener);
        mainSelector.addActionListener(e -> {
            Object item = ((MyComboBox) e.getSource()).getSelectedItem();
            if (item != null) changeBlockingBehaviour(item.toString());
        });
    }

    /**
     * action of the check box buttons used for buzzer selection when the main selector stands
     * at "Unblock with Buzzer"
     * The method selects the buzzer that was Pressed, deselects the Buzzers that were not pressed
     * and disables the buzzer that was pressed and enables the buzzers that are not selected
     *
     * @param buzzerName name of the buzzer that was pressed
     */
    private void buzzerAction(String buzzerName) {
        for (int i = 0; i < 3; i++) {
            buzzer[i].setSelected(buzzerName.equals(BUZZER_NAMES[i]));
            buzzer[i].setEnabled(!buzzerName.equals(BUZZER_NAMES[i]));
        }
    }

    /**
     * sets the selected item of the main selector
     * Possible values: "Don't block", "Block until Released", "Block for Time", "Unblock with Buzzer"
     *
     * @param item item that is now the selected item
     */
    public void setMainSelectorItem(String item) {
        mainSelector.setSelectedItem(item);
    }

    /**
     * sets the selected item of the main selector from an index
     * Possible values: 0, 1, 2, 3
     *
     * @param item item that is now the selected item
     */
    public void setMainSelectorItem(int item) {
        mainSelector.setSelectedIndex(item);
    }

    /**
     * Method returns the block time (in milliseconds) for the buzzers. This time is used to block the buzzer
     * if the option "Block for Time" is selected in the main selector.
     * It returns the content of the text field blockTime as an int. If the conversion to an int fails it returns 0
     *
     * @return returns the time the buzzers are blocked
     */
    public int getBlockTime() {
        try {
            return Integer.parseInt(blockTime.getText());
        } catch (NumberFormatException n) {
            return 0;
        }
    }

    /**
     * sets the content of the text Field blockTime
     *
     * @param blockTime new content of the text field
     */
    public void setBlockTime(int blockTime) {
        this.blockTime.setText(String.valueOf(blockTime));
    }

    /**
     * sets the state of one of the check boxes which are used to select an unblock buzzer
     * to true
     *
     * @param buzzerNumber the check box that gets selected
     */
    public void setUnblockBuzzer(int buzzerNumber) {
        buzzer[buzzerNumber - 1].setSelected(true);
    }

    /**
     * method used to update the visibility of the interaction elements if the selection in
     * the main selector changes
     *
     * @param behaviour the behaviour used to determine which elements should be visible
     *                  possible values: "Don't block", "Block until Released", "Block for Time", "Unblock with Buzzer"
     */
    private void changeBlockingBehaviour(String behaviour) {
        releaseButton.setVisible(behaviour.equals(Text.BLOCK_UNTIL_RELEASED));
        blockTime.setVisible(behaviour.equals(Text.BLOCK_FOR_TIME));
        buzzerChooserBack.setVisible(behaviour.equals(Text.UNBLOCK_WITH_BUZZER));
    }
}
