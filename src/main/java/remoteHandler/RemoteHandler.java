package remoteHandler;

import assets.combobox.MyComboBox;
import assets.standardAssets.MyTextField;
import programs.abstractProgram.ProgramSettingsView;
import programs.abstractProgram.ProgramView;
import remoteHandler.actions.KeyAction;
import remoteHandler.actions.RemoteAction;
import utils.saveFile.SaveFileLoader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler fot the remote. One is created for every individual program
 */
public class RemoteHandler implements ActionListener {


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
    private Map<String, RemoteAction> possibleActions;

    /**
     * save file storing the selected actions for the program
     */
    private RemoteSaveFile remoteSaveFile;

    /**
     * View of this remote handler
     */
    private RemoteSettingsView remoteSettingsView;

    /**
     * constructor creating the default actions every program has
     */
    public RemoteHandler() {
        possibleActions = new HashMap<>();
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
                topLeftButtonAction.performAction();
                break;
            case TOP_RIGHT:
                topRightButtonAction.performAction();
                break;
            case BOTTOM_LEFT:
                bottomLeftButtonAction.performAction();
                break;
            case BOTTOM_RIGHT:
                bottomRightButtonAction.performAction();
        }
    }

    /**
     * creates the default actions the remote can perform in every program
     */
    private void createDefaultActions() {
        addRemoteAction("Tastendruck", new KeyAction(KeyEvent.VK_A));
        addRemoteAction("Nichts", new RemoteAction(() -> {
        }));
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

            String[] possibleActions = this.possibleActions.keySet().toArray(new String[0]);

            String[] currentSelections = {remoteSaveFile.getTopLeftButtonActionName().split(":")[0], remoteSaveFile.getTopRightButtonActionName().split(":")[0],
                    remoteSaveFile.getBottomLeftButtonActionName().split(":")[0], remoteSaveFile.getBottomRightButtonActionName().split(":")[0]};

            String[] selectedKeys = {
                    remoteSaveFile.getTopLeftButtonActionName().split(":").length > 1 ? remoteSaveFile.getTopLeftButtonActionName().split(":")[1] : "",
                    remoteSaveFile.getTopRightButtonActionName().split(":").length > 1 ? remoteSaveFile.getTopRightButtonActionName().split(":")[1] : "",
                    remoteSaveFile.getBottomLeftButtonActionName().split(":").length > 1 ? remoteSaveFile.getBottomLeftButtonActionName().split(":")[1] : "",
                    remoteSaveFile.getBottomRightButtonActionName().split(":").length > 1 ? remoteSaveFile.getBottomRightButtonActionName().split(":")[1] : ""
            };

            remoteSettingsView = new RemoteSettingsView(this, possibleActions, currentSelections, selectedKeys);

            settingsView.createNewSettingsPage("Fernsteuerung", remoteSettingsView);
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
        topRightButtonAction = possibleActions.get(remoteSaveFile.getTopRightButtonActionName());
        topLeftButtonAction = possibleActions.get(remoteSaveFile.getTopLeftButtonActionName());
        bottomRightButtonAction = possibleActions.get(remoteSaveFile.getBottomRightButtonActionName());
        bottomLeftButtonAction = possibleActions.get(remoteSaveFile.getBottomLeftButtonActionName());
    }

    /**
     * loads the save file storing the actions of the remote
     *
     * @param name name of the program used to identify the save file
     */
    private void loadSaveFile(String name) {
        remoteSaveFile = SaveFileLoader.loadFile(name + "Remote", RemoteSaveFile.class);
        if (remoteSaveFile == null) {
            new RemoteSaveFile(name + "Remote");
        }
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
        String key = ((MyTextField) e.getSource()).getText();
        setSaveFileValue("Tastendruck:" + key, button);
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
            //noinspection ConstantConditions
            String newSelection = ((MyComboBox) e.getSource()).getSelectedItem().toString();
            if (newSelection.equals("Tastendruck")) {
                setSaveFileValue(newSelection + ":A", button);
                remoteSettingsView.changeToKeySelection(button);
            } else {
                setSaveFileValue(newSelection + ":", button);
                remoteSettingsView.changeFromKeySelection(button);
            }
        }
    }

    /**
     * saves a string as the identifying name of the action of a
     * button
     *
     * @param s      string to identify the action, this value gets saved
     * @param button button for which the value gets saved
     */
    private void setSaveFileValue(String s, RemoteButton button) {
        switch (button) {
            case TOP_LEFT:
                remoteSaveFile.setTopLeftButtonActionName(s);
                break;
            case TOP_RIGHT:
                remoteSaveFile.setTopRightButtonActionName(s);
                break;
            case BOTTOM_LEFT:
                remoteSaveFile.setBottomLeftButtonActionName(s);
                break;
            case BOTTOM_RIGHT:
                remoteSaveFile.setBottomRightButtonActionName(s);
        }
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
        possibleActions.put(name, remoteAction);
    }

    /**
     * when this method gets called the state of the action selection
     * is saved to a file
     */
    public void saveSettings() {
        remoteSaveFile.saveFile();
    }


}
