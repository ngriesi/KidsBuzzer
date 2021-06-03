package remoteHandler;

import assets.combobox.MyComboBox;
import assets.standardAssets.KeySelector;
import programs.abstractProgram.ProgramSettingsView;
import programs.abstractProgram.ProgramView;
import remoteHandler.actions.KeyAction;
import remoteHandler.actions.RemoteAction;
import savedataHandler.languages.Text;
import utils.save.SaveFile;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler fot the remote. One is created for every individual program
 */
public class RemoteHandler implements ActionListener {

    /**
     * Strings used to identify the settings
     */
    private final String TOP_LEFT_ACTION = "Top Left Action", TOP_RIGHT_ACTION = "Top Right Action", BOTTOM_LEFT_ACTION = "Bottom Left Action", BOTTOM_RIGHT_ACTION = "Bottom Right Action";
    private final String KEYS = "Keys";

    /**
     * enum to identify the different buttons
     */
    public enum RemoteButton {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    /**
     * Action of the top left button
     */
    private RemoteAction topLeftButtonAction;

    /**
     * Action of the top right button
     */
    private RemoteAction topRightButtonAction;

    /**
     * Action of the bottom left button
     */
    private RemoteAction bottomLeftButtonAction;

    /**
     * Action of the bottom right button
     */
    private RemoteAction bottomRightButtonAction;

    /**
     * All possible Actions of the remote
     */
    private Map<Integer, RemoteAction> possibleActions;

    /**
     * save file storing the selected actions for the program
     */
    private SaveFile remoteSaveFile;

    /**
     * View of this remote handler
     */
    private RemoteSettingsView remoteSettingsView;

    /**
     * index storing the number of actions already added to this handler
     */
    private int actionIndex = 0;

    /**
     * array containing the names of the actions
     */
    private String[] actions;

    /**
     * constructor creating the default actions every program has
     */
    public RemoteHandler() {
        possibleActions = new HashMap<>();
        actions = new String[0];
        createDefaultActions();
    }

    /**
     * Method gets called when a button of the remote gets pressed
     *
     * @param pressedButton button of the remote that was pressed
     */
    public void pressedAction(RemoteButton pressedButton) {
        switch (pressedButton) {
            case TOP_LEFT:
                topLeftButtonAction.performAction(remoteSaveFile.getInteger(KEYS + 0, 0));
                break;
            case TOP_RIGHT:
                topRightButtonAction.performAction(remoteSaveFile.getInteger(KEYS + 1, 0));
                break;
            case BOTTOM_LEFT:
                bottomLeftButtonAction.performAction(remoteSaveFile.getInteger(KEYS + 2, 0));
                break;
            case BOTTOM_RIGHT:
                bottomRightButtonAction.performAction(remoteSaveFile.getInteger(KEYS + 3, 0));
        }
    }

    /**
     * creates the default actions the remote can perform in every program
     */
    private void createDefaultActions() {
        addRemoteAction(Text.NOTHING, new RemoteAction(() -> {
        }));
        addRemoteAction(Text.KEY_PRESS, new KeyAction());
    }

    /**
     * creates the settings view of this <code>RemoteHandler</code> and adds it to the
     * settings view of a program
     *
     * @param programView settings view of the program this handler belongs to
     */
    public void createAndAddRemoteSettingsView(ProgramView programView) {

        if (programView instanceof ProgramSettingsView) {
            ProgramSettingsView settingsView = (ProgramSettingsView) programView;

            int[] currentSelections = {remoteSaveFile.getInteger(TOP_LEFT_ACTION, 0),
                    remoteSaveFile.getInteger(TOP_RIGHT_ACTION, 0),
                    remoteSaveFile.getInteger(BOTTOM_LEFT_ACTION, 0),
                    remoteSaveFile.getInteger(BOTTOM_RIGHT_ACTION, 0)};

            int[] selectedKeys = new int[4];

            for (int i = 0; i < selectedKeys.length; i++) {
                selectedKeys[i] = remoteSaveFile.getInteger(KEYS + i, 65);
            }

            remoteSettingsView = new RemoteSettingsView(this, actions, currentSelections, selectedKeys);

            settingsView.createNewSettingsPage(Text.REMOTE, remoteSettingsView);
        }
    }

    /**
     * sets the actions for the individual buttons of the remote
     *
     * @param programName name of the program this remote handler belongs to,
     *                    used to identify the save file
     */
    public void setActions(String programName) {
        loadSaveFile(programName);
        updateActionValues();
    }

    /**
     * updates the values of the action fields form the save file
     */
    private void updateActionValues() {
        topRightButtonAction = possibleActions.get(remoteSaveFile.getInteger(TOP_RIGHT_ACTION));
        topLeftButtonAction = possibleActions.get(remoteSaveFile.getInteger(TOP_LEFT_ACTION));
        bottomRightButtonAction = possibleActions.get(remoteSaveFile.getInteger(BOTTOM_RIGHT_ACTION));
        bottomLeftButtonAction = possibleActions.get(remoteSaveFile.getInteger(BOTTOM_LEFT_ACTION));
    }

    /**
     * loads the save file storing the actions of the remote
     *
     * @param name name of the program used to identify the save file
     */
    private void loadSaveFile(String name) {
        remoteSaveFile = new SaveFile(name + "Remote");
    }

    /**
     * Action performed method for the combo boxes and
     * the text fields of the view
     *
     * @param e <code>ActionEvent</code> created when a action occurs
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        RemoteButton button = getButtonFromString(e.getActionCommand().split(":")[1]);
        if (e.getActionCommand().split(":")[0].equals("comboBox")) {
            comboBoxSelectionAction(e, button);
        } else {
            textFieldInputAction(e, button);
        }
    }

    /**
     * method performs the action for the text field
     *
     * @param e      <code>ActionEvent</code> created when a action occurs
     * @param button enum to identify the pressed button
     */
    private void textFieldInputAction(ActionEvent e, RemoteButton button) {
        int key = ((KeySelector) e.getSource()).getKey();
        saveKeyValue(key, button);
    }

    /**
     * method performs the action for a changed selection
     * in one of the combo boxes for the actions of the buttons
     *
     * @param e      <code>ActionEvent</code> created when a action occurs
     * @param button enum to identify the pressed button
     */
    private void comboBoxSelectionAction(ActionEvent e, RemoteButton button) {
        if (((MyComboBox) e.getSource()).getSelectedItem() != null) {
            int newSelection = ((MyComboBox) e.getSource()).getSelectedIndex();
            if (newSelection == 1) {
                setSaveFileValue(newSelection, button);
                saveKeyValue(65, button);
                remoteSettingsView.changeToKeySelection(button);
            } else {
                setSaveFileValue(newSelection, button);
                remoteSettingsView.changeFromKeySelection(button);
            }
        }
    }

    /**
     * Method saves a key for teh key press action
     *
     * @param key    key that gets saved
     * @param button button for which the key gets saved
     */
    private void saveKeyValue(int key, RemoteButton button) {
        switch (button) {
            case TOP_LEFT:
                remoteSaveFile.putInteger(KEYS + 0, key);
                break;
            case TOP_RIGHT:
                remoteSaveFile.putInteger(KEYS + 1, key);
                break;
            case BOTTOM_LEFT:
                remoteSaveFile.putInteger(KEYS + 2, key);
                break;
            case BOTTOM_RIGHT:
                remoteSaveFile.putInteger(KEYS + 3, key);
        }
    }

    /**
     * saves a string as the identifying name of the action of a
     * button
     *
     * @param index  index to identify the action, this value gets saved
     * @param button button for which the value gets saved
     */
    private void setSaveFileValue(int index, RemoteButton button) {
        switch (button) {
            case TOP_LEFT:
                remoteSaveFile.putInteger(TOP_LEFT_ACTION, index);
                break;
            case TOP_RIGHT:
                remoteSaveFile.putInteger(TOP_RIGHT_ACTION, index);
                break;
            case BOTTOM_LEFT:
                remoteSaveFile.putInteger(BOTTOM_LEFT_ACTION, index);
                break;
            case BOTTOM_RIGHT:
                remoteSaveFile.putInteger(BOTTOM_RIGHT_ACTION, index);
        }
        updateActionValues();
    }

    /**
     * returns the enum identifying the remote button from the string of the enum
     *
     * @param s string created form the enum
     * @return enum of a button
     */
    private RemoteButton getButtonFromString(String s) {
        switch (s) {
            case "TOP_LEFT":
                return RemoteButton.TOP_LEFT;
            case "TOP_RIGHT":
                return RemoteButton.TOP_RIGHT;
            case "BOTTOM_LEFT":
                return RemoteButton.BOTTOM_LEFT;
            case "BOTTOM_RIGHT":
                return RemoteButton.BOTTOM_RIGHT;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * adds an action to the possible actions list
     *
     * @param name         name of the action displayed in the view
     * @param remoteAction action performed when the button on the remote gets pressed
     */
    public void addRemoteAction(String name, RemoteAction remoteAction) {
        int length = actions.length;
        String[] newActions = new String[length + 1];
        System.arraycopy(actions, 0, newActions, 0, length);
        newActions[length] = name;
        actions = newActions;
        possibleActions.put(actionIndex, remoteAction);
        actionIndex++;
    }

    /**
     * when this method gets called the state of the action selection
     * is saved to a file
     */
    public void saveSettings() {
        remoteSaveFile.saveFile();
    }


}
