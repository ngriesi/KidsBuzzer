package programs.quizPrograms.settings.pages;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsPage;
import assets.settings.rows.FontChooserRow;
import assets.settings.rows.FontData;
import savedataHandler.languages.Text;

import java.awt.*;

import static programs.quizPrograms.data.QuizModel.BUZZER_FONT;
import static programs.quizPrograms.data.QuizModel.MAIN_FONT;

/**
 * Settings page for the font selection
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
     * creates Panel with Layout
     *
     * @param settingsChangeListener change listener of this settings view page
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

        mainFontChooserRow = new FontChooserRow(settingsChangeListener, MAIN_FONT, Text.MAIN_FONT, new FontData(new Font("arial", Font.PLAIN, 100), Color.WHITE));

        super.addRow(mainFontChooserRow);

        buzzerFontChooserRow = new FontChooserRow(settingsChangeListener, BUZZER_FONT, Text.BUZZER_FONT, new FontData(new Font("arial", Font.PLAIN, 100), Color.WHITE));

        super.addRow(buzzerFontChooserRow);

        for (int i = 2; i < 5; i++) {
            addEmpty();
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
