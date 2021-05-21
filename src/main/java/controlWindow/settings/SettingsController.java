package controlWindow.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.standardAssets.StandardAssetFields;
import controlWindow.MainController;
import controlWindow.settings.view.SettingsView;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
    private final String language = "language";
    private final String effectColor = "effectColor";

    /**
     * stores the old values for fields where the whole view needs to be updatet if they change
     */
    private int[] oldColor;
    private String oldLanguage;

    /**
     * creates a new settings controller
     *
     * @param mainController   reference to the main application model
     * @param settingsSaveFile save file that was loaded at the start of the application
     */
    public SettingsController(MainController mainController, SettingsSaveFile settingsSaveFile) {
        super(settingsSaveFile);
        setSettingsView(new SettingsView(this));
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

        if(changesMadeToView()) {
            mainController.updateView();
        }
        mainController.displayControlView();

        updateBuzzerCount();
    }

    /**
     * chacks if changes where made that require the whole view to update
     *
     * @return returns true if the view needs an update
     */
    private boolean changesMadeToView() {
        if (!oldLanguage.equals(saveFileHandler.getSaveFile().getLanguage())) {
            return true;
        }
        return !(oldColor[0] == saveFileHandler.getSaveFile().getEffectColor()[0]
                && oldColor[1] == saveFileHandler.getSaveFile().getEffectColor()[1]
                && oldColor[2] == saveFileHandler.getSaveFile().getEffectColor()[2]);

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
        Color color = new Color(saveFileHandler.getSaveFile().getEffectColor()[0], saveFileHandler.getSaveFile().getEffectColor()[1], saveFileHandler.getSaveFile().getEffectColor()[2]);
        StandardAssetFields.ROLLOVER_COLOR = color;
        StandardAssetFields.PRESSED_COLOR = color;
        new Text(saveFileHandler.getSaveFile().getLanguage());
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
     * <p>
     * SettingsName: "language":
     * updates the language of the application by reinstanciating the <code>Text</code> class
     * <p>
     * SettingsName: "effectColor":
     * updates the effect color of the application
     *
     * @param settingsEvent settings event containing the setting identification name and the new value of the setting
     */
    @Override
    public void settingChanged(SettingsEvent settingsEvent) {
        switch (settingsEvent.getName()) {
            case outputScreen:
                saveFileHandler.getSaveFile().setOutputScreen((int) settingsEvent.getValue());
                saveFileHandler.getSaveFile().setDesiredOutputScreen((int) settingsEvent.getValue());
                mainController.updateOutputScreen((int) settingsEvent.getValue());
                break;
            case buzzerCount:
                saveFileHandler.getSaveFile().setBuzzerNumber((int) settingsEvent.getValue());
                break;
            case nativeKey:
                saveFileHandler.getSaveFile().setUseNativeKeyListener((boolean) settingsEvent.getValue());
                mainController.getControlModel().setEnableNativeKeyListener((boolean) settingsEvent.getValue());
                break;
            case language:
                saveFileHandler.getSaveFile().setLanguage((String) settingsEvent.getValue());
                new Text((String) settingsEvent.getValue());
                this.setSettingsView(new SettingsView(this));
                mainController.getControlModel().getView().setView(getSettingsView());
                break;
            case effectColor:
                Color color = (Color) settingsEvent.getValue();
                saveFileHandler.getSaveFile().setEffectColor(new int[]{color.getRed(), color.getGreen(), color.getBlue()});
                StandardAssetFields.ROLLOVER_COLOR = (Color) settingsEvent.getValue();
                StandardAssetFields.PRESSED_COLOR = ((Color) settingsEvent.getValue()).brighter();
                this.setSettingsView(new SettingsView(this));
                mainController.getControlModel().getView().setView(getSettingsView());
                break;
        }
    }

    /**
     * @return returns an array of integers representing the connected screens
     */
    public Integer[] getPossibleScreens() {

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
        getSettingsView().getGeneralPage().getOutputScreen().setPossibleValues(screens);
    }


    /**
     * @return returns the identification name of the output screen setting
     */
    public String getOutputScreen() {
        return outputScreen;
    }

    /**
     * @return returns the identification name of the buzzer number setting
     */
    public String getBuzzerCount() {
        return buzzerCount;
    }

    /**
     * @return returns the identification name of the native key setting
     */
    public String getNativeKey() {
        return nativeKey;
    }

    /**
     * @return returns the identification name of the language setting
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @return returns the identification name of the effect color setting
     */
    public String getEffectColor() {
        return effectColor;
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
        getSettingsView().getGeneralPage().getNativeKeySettingsRow().setSetting(useNativeKeyListener);
    }

    /**
     * updates the view of the settings
     */
    public void updateSettingsView() {
        setSettingsView(new SettingsView(this));
    }

    /**
     * updates the old values of the settings
     */
    public void setOldValues() {
        oldColor = saveFileHandler.getSaveFile().getEffectColor();
        oldLanguage = saveFileHandler.getSaveFile().getLanguage();
    }
}
