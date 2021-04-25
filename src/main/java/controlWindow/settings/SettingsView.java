package controlWindow.settings;

import assets.settings.rows.CheckBoxSettingsRow;
import assets.settings.rows.ComboBoxSettingsRow;
import assets.settings.rows.MessageSettingsRow;
import assets.standardAssets.MyButton;

import javax.swing.*;
import java.awt.*;

/**
 * creates  the main settings view for the whole application
 */
public class SettingsView extends assets.settings.general.SettingsView<SettingsSaveFile> {

    /**
     * Message row, displays an alert message at the bottom of the page
     */
    private MessageSettingsRow messageSettingsRow;

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
     * @return returns the message row from the bottom of the view
     */
    MessageSettingsRow getMessageSettingsRow() {
        return messageSettingsRow;
    }

    /**
     * creates the settings rows
     *
     * @param mainPanel main panel, back of the rows
     */
    @Override
    protected void createSettingsRows(JPanel mainPanel) {

        SettingsController settingsController = (SettingsController) this.settingsController;

        createBottomBar(settingsController);

        outputScreen = new ComboBoxSettingsRow<>(settingsController, settingsController.getOutputScreen(), "Choose the output screen", settingsController.getSettingsSaveFile().getOutputScreen(), settingsController.getPossibleScreens());
        super.addComponent(mainPanel, outputScreen,0,1,1,1);
        buzzerNumber = new ComboBoxSettingsRow<>(settingsController, settingsController.getBuzzerCount(), "Select the number of buzzers", settingsController.getSettingsSaveFile().getBuzzerNumber(), new Integer[]{1, 2, 3});
        super.addComponent(mainPanel,buzzerNumber,0,2,1,1);
        createMessageRow(mainPanel);
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
     * creates the row that displays the alert message
     *
     * @param mainPanel main back panel of the rows
     */
    private void createMessageRow(JPanel mainPanel) {
        messageSettingsRow = new MessageSettingsRow("<html><body>The Application has to be restarted to apply all changes<br>Press save and restart the App to apply</body></html>");
        messageSettingsRow.getLabel().setForeground(Color.RED);
        super.addComponent(mainPanel,messageSettingsRow,0,3,1,1);
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
        super.addComponent(this,save,2,1,bottom_button_width,bottom_height);
    }

    /**
     * creates an empty panel as spacing at the bottom of the screen
     */
    private void createBottomSpacing() {
        JPanel bottomSpacing = new JPanel();
        bottomSpacing.setOpaque(false);
        super.addComponent(this,bottomSpacing,1,1,1f,bottom_height);
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
        super.addComponent(this,back,0,1,bottom_button_width,bottom_height);
    }
}
