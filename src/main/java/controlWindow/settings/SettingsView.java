package controlWindow.settings;

import assets.settings.rows.CheckBoxSettingsRow;
import assets.settings.rows.ComboBoxSettingsRow;
import assets.settings.rows.EmptySettingsRow;
import assets.standardAssets.MyButton;

import javax.swing.*;

/**
 * creates  the main settings view for the whole application
 */
public class SettingsView extends assets.settings.general.SettingsView<SettingsSaveFile> {

    /**
     * width of the buttons at the bottom
     */
    private final float bottom_button_width = 0.15f;

    /**
     * height of the bottom bar
     */
    private final float bottom_height = 0.05f;

    /**
     * settings rows
     */
    private ComboBoxSettingsRow<Integer> outputScreen;
    private ComboBoxSettingsRow<Integer> buzzerNumber;
    private CheckBoxSettingsRow useNativeKeyListener;

    /**
     * updates the settings views
     *
     * @param settingsSaveFile save file with the values
     */
    public void updateSettings(SettingsSaveFile settingsSaveFile) {
        outputScreen.setSetting(settingsSaveFile.getOutputScreen());
        buzzerNumber.setSetting(settingsSaveFile.getBuzzerNumber());
    }

    /**
     * creates the settings rows and adds them to the <code>JPanel</code> passed to the method
     *
     * @param mainPanel main panel, back of the rows
     */
    @Override
    protected void createSettingsRows(JPanel mainPanel) {

        SettingsController settingsController = (SettingsController) this.settingsController;

        createBottomBar(settingsController);

        outputScreen = new ComboBoxSettingsRow<>(settingsController, settingsController.getOutputScreen(), "Choose the output screen", settingsController.getSettingsSaveFile().getOutputScreen(), settingsController.getPossibleScreens());
        super.addComponent(mainPanel, outputScreen, 0, 1, 1, 1);
        buzzerNumber = new ComboBoxSettingsRow<>(settingsController, settingsController.getBuzzerCount(), "Select the number of buzzers", settingsController.getSettingsSaveFile().getBuzzerNumber(), new Integer[]{1, 2, 3});
        super.addComponent(mainPanel, buzzerNumber, 0, 2, 1, 1);
        useNativeKeyListener = new CheckBoxSettingsRow(settingsController, settingsController.getNativeKey(), "Use the Native Key Listener", settingsController.getSettingsSaveFile().isUseNativeKeyListener());
        super.addComponent(mainPanel, useNativeKeyListener, 0, 3, 1, 1);
        super.addComponent(mainPanel, new EmptySettingsRow(), 0, 4, 1, 1);


    }

    /**
     * creates the bottom bar of the view with the back and save button
     *
     * @param settingsController controller handling changes to the settings
     */
    private void createBottomBar(SettingsController settingsController) {
        createBackButton(settingsController);
        createBottomSpacing();
        createSaveButton(settingsController);
    }

    /**
     * creates the save button
     *
     * @param settingsController action listener of the button
     */
    private void createSaveButton(SettingsController settingsController) {
        MyButton save = new MyButton("Save");
        save.setActionCommand("save");
        save.addActionListener(settingsController);
        super.addComponent(this, save, 2, 1, bottom_button_width, bottom_height);
    }

    /**
     * creates an empty panel as spacing at the bottom of the screen
     */
    private void createBottomSpacing() {
        JPanel bottomSpacing = new JPanel();
        bottomSpacing.setOpaque(false);
        super.addComponent(this, bottomSpacing, 1, 1, 1f, bottom_height);
    }

    /**
     * creates the back button for the settings view
     *
     * @param settingsController action listener of the button
     */
    private void createBackButton(SettingsController settingsController) {
        MyButton back = new MyButton("Cancel");
        back.setActionCommand("cancel");
        back.addActionListener(settingsController);
        super.addComponent(this, back, 0, 1, bottom_button_width, bottom_height);
    }

    /**
     * used to update the nativeKeySettingsRow from elsewhere for example if the
     * setting was changed with the key combination assigned to it in the <code>NativeKeyListener</code>
     *
     * @return returns the settings row used for the useNativeKeyListener settings
     * @see controlWindow.NativeKeyListener for the update with keys
     */
    CheckBoxSettingsRow getNativeKeySettingsRow() {
        return useNativeKeyListener;
    }

    /**
     * used to update the number of output screens that can be selected
     *
     * @return returns the combo box settings row used to select the output screen
     */
    ComboBoxSettingsRow<Integer> getOutputScreen() {
        return outputScreen;
    }
}
