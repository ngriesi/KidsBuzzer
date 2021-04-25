package programs.scoreBoard.settings;

import assets.settings.general.SettingsChangeListener;
import assets.settings.rows.*;
import assets.standardAssets.MyPanel;
import programs.abstractProgram.ProgramSettingsView;
import savedataHandler.SaveDataHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class ScoreBoardSettingsView extends ProgramSettingsView {

    private FontChooserRow teamsFontChooserRow;

    private AudioSettingRow buzzerPressedSound;

    private FileChooserSettingsRow[] iconSettingRows;

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

    @Override
    protected JPanel[] createPanels(ActionListener actionListener, SettingsChangeListener settingsChangeListener) {
        return new JPanel[] {createGeneralSettingsPanel(settingsChangeListener), createIconsSettingsPanel(settingsChangeListener)};
    }

    private JPanel createIconsSettingsPanel(SettingsChangeListener settingsChangeListener) {
        MyPanel panel = new MyPanel(new GridBagLayout());

        iconSettingRows = new FileChooserSettingsRow[SaveDataHandler.MAX_BUZZER_COUNT];

        for (int i = 0; i < iconSettingRows.length; i++) {
            iconSettingRows[i] = new FileChooserSettingsRow(settingsChangeListener, "icon" + SaveDataHandler.BUZZER_NAMES[i], SaveDataHandler.BUZZER_NAMES[i] + " Buzzer Icon", new File("default"), "Bilder", "png", "jpg");

            panel.addComponent(panel,iconSettingRows[i],0,i,1,1);
        }

        for (int i = iconSettingRows.length; i < 5; i++) {
            panel.addComponent(panel, new EmptySettingsRow(), 0, i,1,1);
        }

        return panel;
    }

    private JPanel createGeneralSettingsPanel(SettingsChangeListener settingsChangeListener) {
        MyPanel panel = new MyPanel(new GridBagLayout());

        teamsFontChooserRow = new FontChooserRow(settingsChangeListener, "font", "Schriftart", new FontData(new Font("arial", Font.PLAIN, 30), Color.WHITE));

        panel.addComponent(panel, teamsFontChooserRow, 0, 0,1,1);

        buzzerPressedSound = new AudioSettingRow(settingsChangeListener, "buzzerSound", "Sound bei Punkt", new AudioSettingRow.AudioData(new File("default"), 1));

        panel.addComponent(panel, buzzerPressedSound, 0, 1,1,1);

        teamNames = new TextFieldSettingsRow[SaveDataHandler.MAX_BUZZER_COUNT];

        for (int i = 0; i < teamNames.length; i++) {
            teamNames[i] = new TextFieldSettingsRow(settingsChangeListener, "name" + SaveDataHandler.BUZZER_NAMES[i], "Name von Team " + (1 + i), "default");

            panel.addComponent(panel, teamNames[i], 0, 2 + i,1,1);
        }

        return panel;
    }

    FontChooserRow getTeamsFontChooserRow() {
        return teamsFontChooserRow;
    }

    AudioSettingRow getBuzzerPressedSound() {
        return buzzerPressedSound;
    }

    FileChooserSettingsRow[] getIconSettingRows() {
        return iconSettingRows;
    }

    TextFieldSettingsRow[] getTeamNames() {
        return teamNames;
    }
}
