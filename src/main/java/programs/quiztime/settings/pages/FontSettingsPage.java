package programs.quiztime.settings.pages;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsPage;
import assets.settings.rows.FontChooserRow;
import assets.settings.rows.FontData;
import savedataHandler.languages.Text;

import java.awt.*;


/**
 * Settings Page of the font selection of the quiztime program
 */
public class FontSettingsPage extends SettingsPage {

    /**
     * Settings row to layout teh main font
     */
    private FontChooserRow mainFontChooserRow;

    /**
     * settings row to layout the buzzer font
     */
    private FontChooserRow buzzerFontChooserRow;

    /**
     * creates the settings page
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    public FontSettingsPage(SettingsChangeListener settingsChangeListener) {
        super(Text.FONT, "font");
        createFontSelectionView(settingsChangeListener);
    }

    /**
     * creates the <code>FontChooserRows</code>
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    private void createFontSelectionView(SettingsChangeListener settingsChangeListener) {


        mainFontChooserRow = new FontChooserRow(settingsChangeListener, "main", Text.MAIN_FONT, new FontData(new Font("arial", Font.PLAIN, 100), Color.WHITE));

        super.addRow(mainFontChooserRow);

        buzzerFontChooserRow = new FontChooserRow(settingsChangeListener, "buzzer", Text.BUZZER_FONT, new FontData(new Font("arial", Font.PLAIN, 100), Color.WHITE));

        super.addRow(buzzerFontChooserRow);

        for (int i = 2; i < 5; i++) {
            super.addEmpty();
        }

    }

    /**
     * @return returns the settings row that sets up the main font
     */
    public FontChooserRow getMainFontChooserRow() {
        return mainFontChooserRow;
    }

    /**
     * @return returns the settings row that sets up the buzzer font
     */
    public FontChooserRow getBuzzerFontChooserRow() {
        return buzzerFontChooserRow;
    }
}
