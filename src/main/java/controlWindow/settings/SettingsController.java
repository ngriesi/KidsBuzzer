package controlWindow.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import controlWindow.MainController;
import savedataHandler.SaveDataHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * controller of the main settings of the application
 */
public class SettingsController extends assets.settings.general.SettingsController<SettingsSaveFile, SettingsView> implements ActionListener, SettingsChangeListener {

    /**
     * reference to the main application model
     */
    private MainController mainController;

    /**
     * names to identify the possible <code>SettingsChangedEvents</code> that can occur
     */
    private final String outputScreen = "outputScreen";
    private final String buzzerCount = "buzzerCount";
    private final String nativeKey = "nativeKey";


    /**
     * creates a new settings controller
     *
     * @param mainController   reference to the main application model
     * @param settingsSaveFile save file that was loaded at the start of the application
     */
    public SettingsController(MainController mainController, SettingsSaveFile settingsSaveFile) {
        super(settingsSaveFile, new SettingsView());
        this.mainController = mainController;
    }


    /**
     * actions performed by the buttons of the view
     * <p>
     * Actions:
     * ActionCommand: "cancel":
     * closes the settings and deletes all changes made
     * <p>
     * ActionCommand: "save":
     * saves the settings and updates the values respectively
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "cancel":
                cancelButtonAction();
                break;
            case "save":
                saveButtonAction();
                break;

        }
    }

    /**
     * The Method saves and applies the changes made in the settings.
     * The method applies the changes in the <code>SaveFileHandler</code> and saves them to the file.
     * It also applies the changes to the view by updating the output window of the application and the
     * buzzer count in the application.
     */
    private void saveButtonAction() {
        saveFileHandler.applyChanges();
        saveFileHandler.saveToFile();
        mainController.displayControlView();
        updateBuzzerCount();
    }

    /**
     * updates the number of buzzer used/displayed in the whole program
     */
    private void updateBuzzerCount() {
        SaveDataHandler.BUZZER_COUNT = saveFileHandler.getSaveFile().getBuzzerNumber();
        mainController.recreateBuzzerModel();
        mainController.updateBuzzerCountOfPrograms();
    }

    /**
     * action performed by the cancel button of the view
     */
    private void cancelButtonAction() {
        saveFileHandler.rollbackChanges();
        mainController.updateOutputScreen(saveFileHandler.getSaveFile().getOutputScreen());
        mainController.displayControlView();
    }

    /**
     * updates the values in the save file class when settings were changed
     * <p>
     * SettingsEvents:
     * SettingsName: "outputScreen":
     * updates the output screen value in the <code>SaveFileHandler</code> and updates the
     * output screen by calling <code>updateOutputScreen</code> in <code>MainController</code>
     * <p>
     * SettingsName: "buzzerCount":
     * updates the buzzer count value in the <code>SaveFileHandler</code>
     * <p>
     * SettingsName: "nativeKey":
     * updates the <code>useNativeKeyListener</code> flag in the <code>SaveFileHandler</code> and
     * updates the behaviour of the program by settings the flag in <code>ControlModel</code>
     *
     * @param settingsEvent settings event containing the setting identification name and the new value of the setting
     */
    @Override
    public void settingChanged(SettingsEvent settingsEvent) {
        switch (settingsEvent.getName()) {
            case outputScreen:
                saveFileHandler.getSaveFile().setOutputScreen((int) settingsEvent.getValue());
                saveFileHandler.getSaveFile().setDesiredOutputScreen((int) settingsEvent.getValue());
                System.out.println("test");
                mainController.updateOutputScreen((int) settingsEvent.getValue());
                break;
            case buzzerCount:
                saveFileHandler.getSaveFile().setBuzzerNumber((int) settingsEvent.getValue());
                break;
            case nativeKey:
                saveFileHandler.getSaveFile().setUseNativeKeyListener((boolean) settingsEvent.getValue());
                mainController.getControlModel().setEnableNativeKeyListener((boolean) settingsEvent.getValue());
        }
    }

    /**
     * @return returns an array of integers representing the connected screens
     */
    Integer[] getPossibleScreens() {

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = env.getScreenDevices();
        Integer[] result = new Integer[devices.length];
        for (int i = 0; i < devices.length; i++) {
            result[i] = i + 1;
        }
        return result;
    }

    /**
     * changes the possible values of the combo box to select
     * the output screen
     *
     * @param screens possible output screens
     */
    public void setPossibleScreens(Integer[] screens) {
        getSettingsView().getOutputScreen().setPossibleValues(screens);
    }


    /**
     * @return returns the identification name of the output screen setting
     */
    String getOutputScreen() {
        return outputScreen;
    }

    /**
     * @return returns the identification name of the buzzer number setting
     */
    String getBuzzerCount() {
        return buzzerCount;
    }

    /**
     * @return returns the identification name of the native key setting
     */
    String getNativeKey() {
        return nativeKey;
    }

    /**
     * Changes the nativeKeyListener Settings. This method sets the useNativeKeyListener setting and
     * saves it to a file
     *
     * @param useNativeKeyListener new value for the use native key listener setting
     */
    public void setNativeKeyListenerSetting(boolean useNativeKeyListener) {
        saveFileHandler.getSaveFile().setUseNativeKeyListener(useNativeKeyListener);
        saveFileHandler.applyChanges();
        saveFileHandler.saveToFile();
        getSettingsView().getNativeKeySettingsRow().setSetting(useNativeKeyListener);
    }
}
