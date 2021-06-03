package remoteHandler;

import assets.combobox.MyComboBox;
import assets.standardAssets.KeySelector;
import assets.standardAssets.MyPanel;
import controlWindow.ControlWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static java.awt.GridBagConstraints.NONE;

/**
 * Settings block used to set the action of one button of the remote
 */
class RemoteSettingsViewBlock extends MyPanel {

    /**
     * main action selector for one button of the remote
     */
    private MyComboBox<String> comboBox;

    /**
     * text input filed for the key selection
     */
    private KeySelector keySelector;

    /**
     * Button this settings view block belongs to
     */
    private RemoteHandler.RemoteButton button;

    /**
     * creates Panel with Layout
     *
     * @param actionListener <code>ActionListener</code> of all the elements in this view
     * @param comboBoxItems  all items that can be selected in the combo box
     * @param selectedIndex  start index of the combo box
     * @param key            start value of the text field
     * @param button         button for which this settings block is created
     */
    RemoteSettingsViewBlock(ActionListener actionListener, String[] comboBoxItems, int selectedIndex, int key, RemoteHandler.RemoteButton button) {
        super(new GridBagLayout());

        this.button = button;

        createComboBox(actionListener, comboBoxItems, selectedIndex);

        this.addComponent(this, comboBox, 1, 2, 0, 0, 1f, 1f, NONE);

        this.addComponent(this, createKeySelector(actionListener, selectedIndex, key), 1, 1, 0, 1, 1f, 1f, NONE, GridBagConstraints.PAGE_START);

        this.addComponent(this, new MyPanel(new FlowLayout()), 1, 1, 0, 2, 1f, 1f, NONE);

    }

    /**
     * creates the combo box which is the main selection item for the
     * action of the action of the remote
     *
     * @param actionListener <code>ActionListener</code> of all the elements in this view
     * @param comboBoxItems  all items that can be selected in the combo box
     * @param selectedIndex  start index of the combo box
     */
    private void createComboBox(ActionListener actionListener, String[] comboBoxItems, int selectedIndex) {
        comboBox = new MyComboBox<>(comboBoxItems);
        comboBox.setSelectedIndex(selectedIndex);
        comboBox.setActionCommand("comboBox:" + button);
        comboBox.addActionListener(actionListener);
        comboBox.setPreferredSize(new Dimension(300, 40));
    }

    /**
     * creates the text field to enter the key selection
     *
     * @param actionListener <code>ActionListener</code> of all the elements in this view
     * @param selectedIndex  selected index of the combo box
     * @param key            start value of the text field
     * @return returns the panel containing the text field that was created
     */
    private JPanel createKeySelector(ActionListener actionListener, int selectedIndex, int key) {
        keySelector = new KeySelector();
        keySelector.setActionCommand("keySelector:" + button);
        keySelector.addActionListener(actionListener);
        keySelector.setPreferredSize(new Dimension(200, 40));
        keySelector.setKey(key);

        if (selectedIndex == 1) {
            keySelector.setVisible(true);
        } else {
            keySelector.setVisible(false);
        }

        MyPanel placeholder = new MyPanel(new GridBagLayout());
        placeholder.add(keySelector);
        placeholder.setPreferredSize(new Dimension(200, 40));
        return placeholder;
    }

    /**
     * sets the visibility of the text field
     *
     * @param value new visibility of the text field
     */
    void setTextFieldVisibility(boolean value) {
        if (!keySelector.isVisible() && value) {
            keySelector.setKey(65);
        }
        keySelector.setVisible(value);
        ControlWindow.myJFrame.getFrame().setVisible(true);
    }
}
