package controlWindow.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.standardAssets.StandardAssetFields;
import buzzerHandler.BuzzerModel;
import controlWindow.ControlModel;
import savedataHandler.SaveDataHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * controller of the main settings of the application
 */
public class SettingsController extends assets.settings.general.SettingsController<SettingsSaveFile,SettingsView> implements ActionListener, SettingsChangeListener {

    /**
     * reference to the main application model
     */
    private ControlModel controlModel;

    /**
     * old settings values to check the requirement of a restart
     */
    private int oldScreenValue;
    private int oldBuzzerNumber;

    /**
     * identification names of the settings
     */
    private final String presentationTransparency = "presentationTransparency";
    private final String outputScreen = "outputScreen";
    private final String buzzerCount = "buzzerCount";
    private final String nativeKey = "nativeKey";



    /**
     * creates a new settings controller
     *
     * @param controlModel reference to the main application model
     * @param settingsSaveFile save file that was loaded at the start of the application
     */
    public SettingsController(ControlModel controlModel, SettingsSaveFile settingsSaveFile) {
        super(settingsSaveFile, new SettingsView());
        this.controlModel = controlModel;

        oldScreenValue = settingsSaveFile.getOutputScreen();
        oldBuzzerNumber = settingsSaveFile.getBuzzerNumber();

        updateMessage();
    }



    /**
     * actions performed by the buttons of the view
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "cancel":
                saveFileHandler.rollbackChanges();
                controlModel.displayControlView();
                break;
            case "save":
                saveFileHandler.applyChanges();
                saveFileHandler.saveToFile();
                controlModel.displayControlView();
                SaveDataHandler.BUZZER_COUNT = saveFileHandler.getSaveFile().getBuzzerNumber();
                controlModel.setBuzzerControl(new BuzzerModel(controlModel));
                controlModel.recreatePrograms();
                break;

        }
    }

    /**
     * updates the values in the save file class when settings were changed
     *
     * @param settingsEvent settings event containing the setting identification name and the new value of the setting
     */
    @Override
    public void settingChanged(SettingsEvent settingsEvent) {
        switch (settingsEvent.getName()) {
            case outputScreen:
                saveFileHandler.getSaveFile().setOutputScreen((int)settingsEvent.getValue());
                controlModel.updateOutputScreen();
                break;
            case buzzerCount:
                saveFileHandler.getSaveFile().setBuzzerNumber((int) settingsEvent.getValue());
                break;
            case nativeKey:
                saveFileHandler.getSaveFile().setUseNativeKeyListener((boolean)settingsEvent.getValue());
                controlModel.setEnableNativeKeyListener(false);
        }
        updateMessage();
    }

    /**
     * @return returns an array of integers representing the connected screens
     */
    Integer[] getPossibleScreens() {

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = env.getScreenDevices();
        Integer[] result = new Integer[devices.length];
        for (int i = 0; i < devices.length; i++) {
            result[i] = i+1;
        }
        return result;
    }

    /**
     * updates the visibility of the message int the settings view
     */
    private void updateMessage() {

        getViewWithoutUpdate().getMessageSettingsRow().getLabel().setForeground(
                (saveFileHandler.getSaveFile().getOutputScreen() != oldScreenValue
                || saveFileHandler.getSaveFile().getBuzzerNumber() != oldBuzzerNumber) ?
                Color.RED:
                StandardAssetFields.PANEL_BACKGROUND_COLOR);
    }

    /**
     * @return returns the identification name of the presentation window transparency setting
     */
    String getPresentationTransparency() {
        return presentationTransparency;
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

    String getNativeKey() {
        return nativeKey;
    }

    public void setNativeKeyListenerSetting(boolean useNativeKeyListener) {
        saveFileHandler.getSaveFile().setUseNativeKeyListener(useNativeKeyListener);
        saveFileHandler.applyChanges();
        saveFileHandler.saveToFile();
        getSettingsView().getNativeKeySettingsRow().setSetting(useNativeKeyListener);
    }
}
