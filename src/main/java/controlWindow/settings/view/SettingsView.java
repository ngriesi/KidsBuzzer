package controlWindow.settings.view;

import assets.settings.general.SettingsChangeListener;
import assets.standardAssets.MyButton;
import assets.standardAssets.MyPanel;
import controlWindow.settings.SettingsController;
import savedataHandler.languages.Text;
import utils.save.SaveFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * creates  the main settings view for the whole application
 */
public class SettingsView extends assets.settings.general.SettingsView {

    /**
     * width of the buttons at the bottom
     */
    private final float bottom_button_width = 0.15f;

    /**
     * height of the bottom bar
     */
    private final float bottom_height = 0.05f;

    /**
     * General settings page
     */
    private GeneralPage generalPage;

    /**
     * information settings page
     */
    private InformationPage informationPage;

    /**
     * buzzer settings page
     */
    private BuzzerPage buzzerPage;

    /**
     * @return returns the general Settings page of the main Settings
     */
    public GeneralPage getGeneralPage() {
        return generalPage;
    }

    /**
     * @return returns the information settings page of the main Settings
     */
    public InformationPage getInformationPage() {
        return informationPage;
    }

    /**
     * @return returns the settings page of the buzzers
     */
    public BuzzerPage getBuzzerPage() {
        return buzzerPage;
    }

    /**
     * creates a new view
     *
     * @param settingsController settings controller of this view
     */
    public SettingsView(SettingsController settingsController) {
        super(settingsController);
        createLayout(settingsController);
    }

    /**
     * Creates the layout of this view
     *
     * @param settingsController controller of this Settings view
     */
    private void createLayout(SettingsController settingsController) {

        informationPage = new InformationPage();
        generalPage = new GeneralPage(settingsController);
        buzzerPage = new BuzzerPage(settingsController);
        createNewPage(Text.GENERAL, generalPage);
        createNewPage(Text.INFORMATION, informationPage);
        createNewPage(Text.BUZZER, buzzerPage);
    }

    /**
     * creates the bottom bar of the view with the back and save button
     *
     * @param actionListener controller handling changes to the settings
     */
    @Override
    protected JPanel createBottomBar(ActionListener actionListener) {
        MyPanel bottomBar = new MyPanel(new GridBagLayout());
        super.addComponent(bottomBar, createBackButton(actionListener), 0, 1, bottom_button_width, bottom_height);
        super.addComponent(bottomBar, createBottomSpacing(), 1, 1, 1f, bottom_height);
        super.addComponent(bottomBar, createSaveButton(actionListener), 2, 1, bottom_button_width, bottom_height);
        return bottomBar;
    }


    /**
     * creates the save button
     *
     * @param actionListener action listener of the button
     */
    private MyButton createSaveButton(ActionListener actionListener) {
        MyButton save = new MyButton(Text.SAVE);
        save.setActionCommand("save");
        save.addActionListener(actionListener);
        return save;
    }

    /**
     * creates an empty panel as spacing at the bottom of the screen
     */
    private JPanel createBottomSpacing() {
        JPanel bottomSpacing = new JPanel();
        bottomSpacing.setOpaque(false);
        return bottomSpacing;
    }

    /**
     * creates the back button for the settings view
     *
     * @param actionListener action listener of the button
     */
    private MyButton createBackButton(ActionListener actionListener) {
        MyButton back = new MyButton(Text.CANCEL);
        back.setActionCommand("cancel");
        back.addActionListener(actionListener);
        return back;
    }


    @Override
    protected JPanel[] createPanels(ActionListener actionListener, SettingsChangeListener settingsChangeListener) {
        return new JPanel[0];
    }

    @Override
    public void updateSettings(SaveFile saveFile) {
        generalPage.updateSettings(saveFile);
        buzzerPage.updateSettings(saveFile);
    }
}
