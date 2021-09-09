package controlWindow.settings.view;

import assets.settings.general.SettingsPage;
import assets.settings.rows.ColorSelectionRow;
import assets.settings.rows.ComboBoxSettingsRow;
import controlWindow.settings.SettingsController;
import savedataHandler.languages.Text;
import utils.save.SaveFile;

/**
 * Settings Page for the Settings for the Buzzers
 */
class BuzzerPage extends SettingsPage {

    private ColorSelectionRow colorBuzzer1;
    private ColorSelectionRow colorBuzzer2;
    private ColorSelectionRow colorBuzzer3;

    /**
     * Settings rows
     */
    private ComboBoxSettingsRow<Integer> buzzerNumber;

    /**
     * creates Panel with Layout
     *
     * @param settingsController settings controller of the page
     */
    BuzzerPage(SettingsController settingsController) {
        super(Text.BUZZER, "buzzer");

        createSettingsRows(settingsController);
    }

    /**
     * updates the settings views
     *
     * @param settingsSaveFile save file with the values
     */
    void updateSettings(SaveFile settingsSaveFile) {
        buzzerNumber.setSetting(settingsSaveFile.getInteger(SettingsController.BUZZER_COUNT, 3));
        colorBuzzer1.setSetting(settingsSaveFile.getColor(SettingsController.BUZZER_COLOR_1));
        colorBuzzer2.setSetting(settingsSaveFile.getColor(SettingsController.BUZZER_COLOR_2));
        colorBuzzer3.setSetting(settingsSaveFile.getColor(SettingsController.BUZZER_COLOR_3));
    }

    /**
     * creates the settings rows and adds them to the <code>JPanel</code> passed to the method
     */
    private void createSettingsRows(SettingsController settingsController) {

        buzzerNumber = new ComboBoxSettingsRow<>(settingsController, SettingsController.BUZZER_COUNT, Text.SELECT_BUZZER_NUMBER, settingsController.getSettingsSaveFile().getInteger(SettingsController.BUZZER_COUNT, 3), new Integer[]{1, 2, 3});
        super.addRow(buzzerNumber);

        colorBuzzer1 = new ColorSelectionRow(settingsController, SettingsController.BUZZER_COLOR_1, Text.BUZZER_COLOR_1, settingsController.getSettingsSaveFile().getColor(SettingsController.BUZZER_COLOR_1));
        super.addRow(colorBuzzer1);

        colorBuzzer2 = new ColorSelectionRow(settingsController, SettingsController.BUZZER_COLOR_2, Text.BUZZER_COLOR_2, settingsController.getSettingsSaveFile().getColor(SettingsController.BUZZER_COLOR_2));
        super.addRow(colorBuzzer2);

        colorBuzzer3 = new ColorSelectionRow(settingsController, SettingsController.BUZZER_COLOR_3, Text.BUZZER_COLOR_3, settingsController.getSettingsSaveFile().getColor(SettingsController.BUZZER_COLOR_3));
        super.addRow(colorBuzzer3);
    }
}
