package controlWindow.settings.view;

import assets.settings.general.SettingsPage;
import assets.settings.rows.CheckBoxSettingsRow;
import assets.settings.rows.ColorSelectionRow;
import assets.settings.rows.ComboBoxSettingsRow;
import controlWindow.settings.SettingsController;
import savedataHandler.languages.Text;
import utils.save.SaveFile;

/**
 * Main settings page of the main settings
 */
public class GeneralPage extends SettingsPage {


    /**
     * settings rows
     */
    private ComboBoxSettingsRow<Integer> outputScreen;
    private ComboBoxSettingsRow<Integer> buzzerNumber;
    private CheckBoxSettingsRow useNativeKeyListener;
    private ComboBoxSettingsRow<String> language;
    private ColorSelectionRow effectColorSelector;

    /**
     * main controller of the settings
     */
    private  SettingsController settingsController;



    /**
     * creates Panel with Layout
     */
    GeneralPage(SettingsController settingsController) {
        super(Text.GENERAL, "general");
        createSettingsRows(settingsController);
        this.settingsController = settingsController;
    }

    /**
     * updates the settings views
     *
     * @param settingsSaveFile save file with the values
     */
    void updateSettings(SaveFile settingsSaveFile) {
        outputScreen.setSetting(settingsSaveFile.getInteger(settingsController.OUTPUT_SCREEN));
        buzzerNumber.setSetting(settingsSaveFile.getInteger(settingsController.BUZZER_COUNT));
        language.setSetting(settingsSaveFile.getString(settingsController.LANGUAGE));
        effectColorSelector.setSetting(settingsSaveFile.getColor(settingsController.EFFECT_COLOR));
    }

    /**
     * creates the settings rows and adds them to the <code>JPanel</code> passed to the method
     */
    private void createSettingsRows(SettingsController settingsController) {

        outputScreen = new ComboBoxSettingsRow<>(settingsController, settingsController.OUTPUT_SCREEN, Text.CHOOSE_OUTPUT_SCREEN, settingsController.getSettingsSaveFile().getInteger(settingsController.OUTPUT_SCREEN), settingsController.getPossibleScreens());
        super.addRow(outputScreen);
        buzzerNumber = new ComboBoxSettingsRow<>(settingsController, settingsController.BUZZER_COUNT, Text.SELECT_BUZZER_NUMBER, settingsController.getSettingsSaveFile().getInteger(settingsController.BUZZER_COUNT), new Integer[]{1, 2, 3});
        super.addRow(buzzerNumber);
        useNativeKeyListener = new CheckBoxSettingsRow(settingsController, settingsController.NATIVE_KEY_LISTENER, Text.USE_NATIVE_KEYS, settingsController.getSettingsSaveFile().getBoolean(settingsController.NATIVE_KEY_LISTENER));
        super.addRow(useNativeKeyListener);
        language = new ComboBoxSettingsRow<>(settingsController, settingsController.LANGUAGE, Text.LANGUAGE_SELECTION, settingsController.getSettingsSaveFile().getString(settingsController.LANGUAGE), Text.LANGUAGES);
        super.addRow(language);
        effectColorSelector = new ColorSelectionRow(settingsController, settingsController.EFFECT_COLOR, Text.EFFECT_COLOR_SELECTION, settingsController.getSettingsSaveFile().getColor(settingsController.EFFECT_COLOR));
        super.addRow(effectColorSelector);
    }

    /**
     * used to update the nativeKeySettingsRow from elsewhere for example if the
     * setting was changed with the key combination assigned to it in the <code>NativeKeyListener</code>
     *
     * @return returns the settings row used for the useNativeKeyListener settings
     * @see controlWindow.NativeKeyListener for the update with keys
     */
    public CheckBoxSettingsRow getNativeKeySettingsRow() {
        return useNativeKeyListener;
    }

    /**
     * used to update the number of output screens that can be selected
     *
     * @return returns the combo box settings row used to select the output screen
     */
    public ComboBoxSettingsRow<Integer> getOutputScreen() {
        return outputScreen;
    }
}
