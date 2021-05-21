package controlWindow.settings.view;

import assets.settings.general.SettingsPage;
import assets.settings.rows.CheckBoxSettingsRow;
import assets.settings.rows.ColorSelectionRow;
import assets.settings.rows.ComboBoxSettingsRow;
import controlWindow.settings.SettingsController;
import controlWindow.settings.SettingsSaveFile;
import savedataHandler.languages.Text;

import java.awt.*;

public class GeneralPage extends SettingsPage {


    /**
     * settings rows
     */
    private ComboBoxSettingsRow<Integer> outputScreen;
    private ComboBoxSettingsRow<Integer> buzzerNumber;
    private CheckBoxSettingsRow useNativeKeyListener;
    private ComboBoxSettingsRow<String> language;
    private ColorSelectionRow effectColorSeector;

    /**
     * creates Panel with Layout
     */
    GeneralPage(SettingsController settingsController) {
        super(Text.GENERAL, "general");
        createSettingsRows(settingsController);
    }

    /**
     * updates the settings views
     *
     * @param settingsSaveFile save file with the values
     */
    void updateSettings(SettingsSaveFile settingsSaveFile) {
        outputScreen.setSetting(settingsSaveFile.getOutputScreen());
        buzzerNumber.setSetting(settingsSaveFile.getBuzzerNumber());
        language.setSetting(settingsSaveFile.getLanguage());
        effectColorSeector.setSetting(new Color(settingsSaveFile.getEffectColor()[0], settingsSaveFile.getEffectColor()[1], settingsSaveFile.getEffectColor()[2]));
    }

    /**
     * creates the settings rows and adds them to the <code>JPanel</code> passed to the method
     */
    private void createSettingsRows(SettingsController settingsController) {

        outputScreen = new ComboBoxSettingsRow<>(settingsController, settingsController.getOutputScreen(), Text.CHOOSE_OUTPUT_SCREEN, settingsController.getSettingsSaveFile().getOutputScreen(), settingsController.getPossibleScreens());
        super.addRow(outputScreen);
        buzzerNumber = new ComboBoxSettingsRow<>(settingsController, settingsController.getBuzzerCount(), Text.SELECT_BUZZER_NUMBER, settingsController.getSettingsSaveFile().getBuzzerNumber(), new Integer[]{1, 2, 3});
        super.addRow(buzzerNumber);
        useNativeKeyListener = new CheckBoxSettingsRow(settingsController, settingsController.getNativeKey(), Text.USE_NATIVE_KEYS, settingsController.getSettingsSaveFile().isUseNativeKeyListener());
        super.addRow(useNativeKeyListener);
        language = new ComboBoxSettingsRow<>(settingsController, settingsController.getLanguage(), Text.LANGUAGE_SELECTION, settingsController.getSettingsSaveFile().getLanguage(), Text.LANGUAGES);
        super.addRow(language);
        effectColorSeector = new ColorSelectionRow(settingsController, settingsController.getEffectColor(), Text.EFFECT_COLOR_SELECTION, new Color(settingsController.getSettingsSaveFile().getEffectColor()[0], settingsController.getSettingsSaveFile().getEffectColor()[1], settingsController.getSettingsSaveFile().getEffectColor()[2]));
        super.addRow(effectColorSeector);
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
