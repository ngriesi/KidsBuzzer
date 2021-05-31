package controlWindow.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsEvent;
import assets.standardAssets.StandardAssetFields;
import controlWindow.MainController;
import controlWindow.settings.view.SettingsView;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;
import utils.save.SaveFile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * controller of the main settings of the application
 */
public class SettingsController extends assets.settings.general.SettingsController<SettingsView> implements ActionListener, SettingsChangeListener {

    /**
     * reference to the main application model
     */
    private MainController mainController;

    /**
     * Strings to identify the settings fields
     */
    public final static String LANGUAGE = "Language";
    public final static String EFFECT_COLOR = "Effect Color";
    public final static String BUZZER_COUNT="Buzzer Count";
    public final static String OUTPUT_SCREEN = "Output Screen";
    public final static String DESIRED_OUTPUT_SCREEN = "Desired Output Screen";
    public final static String NATIVE_KEY_LISTENER = "Native Key Listener";
    public static final String WINDOW_WIDTH = "Window Width", WINDOW_HEIGHT = "Window Height", WINDOW_X = "Window X", WINDOW_Y = "Window Y";

    /**
     * stores the old values for fields where the whole view needs to be updatet if they change
     */
    private Color oldColor;
    private String oldLanguage;

    /**
     * creates a new settings controller
     *
     * @param mainController   reference to the main application model
     * @param settingsSaveFile save file that was loaded at the start of the application
     */
    public SettingsController(MainController mainController, SaveFile settingsSaveFile) {
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
        if (!oldLanguage.equals(saveFileHandler.getSaveFile().getString(LANGUAGE))) {
            return true;
        }
        return !oldColor.equals(saveFileHandler.getSaveFile().getColor(EFFECT_COLOR));

    }

    /**
     * updates the number of buzzer used/displayed in the whole program
     */
    private void updateBuzzerCount() {
        SaveDataHandler.BUZZER_COUNT = saveFileHandler.getSaveFile().getInteger(BUZZER_COUNT);
        mainController.recreateBuzzerModel();
        mainController.updateBuzzerCountOfPrograms();
    }

    /**
     * action performed by the cancel button of the view
     */
    private void cancelButtonAction() {
        saveFileHandler.rollbackChanges();
        Color color = saveFileHandler.getSaveFile().getColor(EFFECT_COLOR);
        StandardAssetFields.ROLLOVER_COLOR = color;
        StandardAssetFields.PRESSED_COLOR = color;
        new Text(saveFileHandler.getSaveFile().getString(LANGUAGE));
        mainController.updateOutputScreen(saveFileHandler.getSaveFile().getInteger(OUTPUT_SCREEN));
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
            case OUTPUT_SCREEN:
                saveFileHandler.getSaveFile().putInteger(OUTPUT_SCREEN, (int) settingsEvent.getValue());
                saveFileHandler.getSaveFile().putInteger(DESIRED_OUTPUT_SCREEN, (int) settingsEvent.getValue());
                mainController.updateOutputScreen((int) settingsEvent.getValue());
                break;
            case BUZZER_COUNT:
                saveFileHandler.getSaveFile().putInteger(BUZZER_COUNT, (int) settingsEvent.getValue());
                break;
            case NATIVE_KEY_LISTENER:
                saveFileHandler.getSaveFile().putBoolean(NATIVE_KEY_LISTENER, (boolean) settingsEvent.getValue());
                mainController.getControlModel().setEnableNativeKeyListener((boolean) settingsEvent.getValue());
                break;
            case LANGUAGE:
                saveFileHandler.getSaveFile().putString(LANGUAGE, (String) settingsEvent.getValue());
                new Text((String) settingsEvent.getValue());
                this.setSettingsView(new SettingsView(this));
                mainController.getControlModel().getView().setView(getSettingsView());
                break;
            case EFFECT_COLOR:
                Color color = (Color) settingsEvent.getValue();
                saveFileHandler.getSaveFile().putColor(EFFECT_COLOR, color);
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
     * Changes the nativeKeyListener Settings. This method sets the useNativeKeyListener setting and
     * saves it to a file
     *
     * @param useNativeKeyListener new value for the use native key listener setting
     */
    public void setNativeKeyListenerSetting(boolean useNativeKeyListener) {
        saveFileHandler.getSaveFile().putBoolean(NATIVE_KEY_LISTENER, useNativeKeyListener);
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
        oldColor = saveFileHandler.getSaveFile().getColor(EFFECT_COLOR);
        oldLanguage = saveFileHandler.getSaveFile().getString(LANGUAGE);
    }
}
