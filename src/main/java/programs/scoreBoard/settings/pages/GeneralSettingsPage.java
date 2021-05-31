package programs.scoreBoard.settings.pages;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsPage;
import assets.settings.rows.AudioSettingRow;
import assets.settings.rows.FontChooserRow;
import assets.settings.rows.FontData;
import assets.settings.rows.TextFieldSettingsRow;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;

import java.awt.*;

/**
 * settings page for the general settings of the score board program
 */
public class GeneralSettingsPage extends SettingsPage {

    /**
     * settings row used to layout the font of the output view
     */
    private FontChooserRow teamsFontChooserRow;

    /**
     * settings row to select the audio played on a buzzer press and to change its volume
     */
    private AudioSettingRow buzzerPressedSound;

    /**
     * settings rows to enter the team names
     */
    private TextFieldSettingsRow[] teamNames;

    /**
     * creates the image settings page
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    public GeneralSettingsPage(SettingsChangeListener settingsChangeListener) {
        super(Text.GENERAL, "general");
        createGeneralSettingsPanel(settingsChangeListener);
    }

    /**
     * creates the settings rows for the general settings (audio, font and team names)
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    private void createGeneralSettingsPanel(SettingsChangeListener settingsChangeListener) {

        teamsFontChooserRow = new FontChooserRow(settingsChangeListener, "font", Text.FONT, new FontData(new Font("arial", Font.PLAIN, 30), Color.WHITE));

        super.addRow(teamsFontChooserRow);

        buzzerPressedSound = new AudioSettingRow(settingsChangeListener, "buzzerSound", Text.SOUND_WHEN_SCORED);

        super.addRow(buzzerPressedSound);

        teamNames = new TextFieldSettingsRow[SaveDataHandler.MAX_BUZZER_COUNT];

        for (int i = 0; i < teamNames.length; i++) {
            teamNames[i] = new TextFieldSettingsRow(settingsChangeListener, "name" + SaveDataHandler.BUZZER_NAMES[i], Text.NAME_OF_TEAM + " " + (1 + i), "default");

            super.addRow(teamNames[i]);
        }
    }

    /**
     * @return returns the settings row that selects the font
     */
    public FontChooserRow getTeamsFontChooserRow() {
        return teamsFontChooserRow;
    }

    /**
     * @return returns the settings row that selects the sound
     */
    public AudioSettingRow getBuzzerPressedSound() {
        return buzzerPressedSound;
    }

    /**
     * @return returns the settings rows that are used to select the names of the teams
     */
    public TextFieldSettingsRow[] getTeamNames() {
        return teamNames;
    }
}
