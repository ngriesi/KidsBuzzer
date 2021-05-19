package programs.scoreBoard.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.rows.*;
import assets.standardAssets.MyPanel;
import programs.abstractProgram.ProgramSettingsView;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * view of the settings of the score board program
 */
public class ScoreBoardSettingsView extends ProgramSettingsView {

    /**
     * settings row used to layout the font of the output view
     */
    private FontChooserRow teamsFontChooserRow;

    /**
     * settings row to select the audio played on a buzzer press and to change its volume
     */
    private AudioSettingRow buzzerPressedSound;

    /**
     * settings rows to choose the icons of the teams
     */
    private FileChooserSettingsRow[] iconSettingRows;

    /**
     * settings rows to enter the team names
     */
    private TextFieldSettingsRow[] teamNames;

    /**
     * creates a new view
     *
     * @param actionListener         sets the actionListener of the view
     * @param settingsChangeListener change listener of the settings
     * @param panelNames             names of the panels
     */
    ScoreBoardSettingsView(ActionListener actionListener, SettingsChangeListener settingsChangeListener, String[] panelNames) {
        super(actionListener, settingsChangeListener, panelNames);
    }

    /**
     * Method creates the panel that a re the different pages of the settings
     *
     * @param actionListener         <code>ActionListener</code> of the view
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     * @return an array of panels representing the pages of the settings
     */
    @Override
    protected JPanel[] createPanels(ActionListener actionListener, SettingsChangeListener settingsChangeListener) {
        return new JPanel[]{createGeneralSettingsPanel(settingsChangeListener), createIconsSettingsPanel(settingsChangeListener)};
    }

    /**
     * creates the settings rows to select the icons
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     * @return a panel containing the <code>FileChooserSettingsRow</code>
     */
    private JPanel createIconsSettingsPanel(SettingsChangeListener settingsChangeListener) {
        MyPanel panel = new MyPanel(new GridBagLayout());

        iconSettingRows = new FileChooserSettingsRow[SaveDataHandler.MAX_BUZZER_COUNT];

        for (int i = 0; i < iconSettingRows.length; i++) {
            iconSettingRows[i] = new FileChooserSettingsRow(settingsChangeListener, "icon" + SaveDataHandler.BUZZER_NAMES[i], Text.SELECT_ICON_TEAM + " " + (i + 1), new File("default"), "Bilder", "png", "jpg");

            panel.addComponent(panel, iconSettingRows[i], 0, i, 1, 1);
        }

        for (int i = iconSettingRows.length; i < 5; i++) {
            panel.addComponent(panel, new EmptySettingsRow(), 0, i, 1, 1);
        }

        return panel;
    }

    /**
     * creates the settings rows for the general settings (audio, font and team names)
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     * @return a panel containing the settings rows
     */
    private JPanel createGeneralSettingsPanel(SettingsChangeListener settingsChangeListener) {
        MyPanel panel = new MyPanel(new GridBagLayout());

        teamsFontChooserRow = new FontChooserRow(settingsChangeListener, "font", Text.FONT, new FontData(new Font("arial", Font.PLAIN, 30), Color.WHITE));

        panel.addComponent(panel, teamsFontChooserRow, 0, 0, 1, 1);

        buzzerPressedSound = new AudioSettingRow(settingsChangeListener, "buzzerSound", Text.SOUND_WHEN_SCORED);

        panel.addComponent(panel, buzzerPressedSound, 0, 1, 1, 1);

        teamNames = new TextFieldSettingsRow[SaveDataHandler.MAX_BUZZER_COUNT];

        for (int i = 0; i < teamNames.length; i++) {
            teamNames[i] = new TextFieldSettingsRow(settingsChangeListener, "name" + SaveDataHandler.BUZZER_NAMES[i], Text.NAME_OF_TEAM + " " + (1 + i), "default");

            panel.addComponent(panel, teamNames[i], 0, 2 + i, 1, 1);
        }

        return panel;
    }

    /**
     * @return returns the settings row that selects the font
     */
    FontChooserRow getTeamsFontChooserRow() {
        return teamsFontChooserRow;
    }

    /**
     * @return returns the settings row that selects the sound
     */
    AudioSettingRow getBuzzerPressedSound() {
        return buzzerPressedSound;
    }

    /**
     * @return returns the settings rows that are used to select the icons of the teams
     */
    FileChooserSettingsRow[] getIconSettingRows() {
        return iconSettingRows;
    }

    /**
     * @return returns the settings rows that are used to select the names of the teams
     */
    TextFieldSettingsRow[] getTeamNames() {
        return teamNames;
    }
}
