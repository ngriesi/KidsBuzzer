package programs.quizPrograms.settings.pages;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsPage;
import midi.MidiSettingsRow;
import org.joml.Vector2i;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;

import static programs.quizPrograms.data.QuizModel.*;

/**
 * Settings page for the midi actions to be performed
 */
public class MidiSettingsPage extends SettingsPage {

    /**
     * Settings rows for the general midi actions
     */
    private MidiSettingsRow right, wrong, next;

    /**
     * Settings rows for the buzzer midi actions
     */
    private MidiSettingsRow[] buzzer;

    /**
     * creates the settings page
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    public MidiSettingsPage(SettingsChangeListener settingsChangeListener) {
        super(Text.LIGHT_CONTROL, "midi");
        createMidiSelectionView(settingsChangeListener);
    }

    /**
     * creates the settings rows for the image settings
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    protected void createMidiSelectionView(SettingsChangeListener settingsChangeListener) {

        right = new MidiSettingsRow(settingsChangeListener, RIGHT_MIDI, Text.MIDI_RIGHT_DESCRIPTION, new MidiSettingsRow.MidiSettingsRowData(new Vector2i(1, 1), false));
        super.addRow(right);

        wrong = new MidiSettingsRow(settingsChangeListener, WRONG_MIDI, Text.MIDI_WRONG_DESCRIPTION, new MidiSettingsRow.MidiSettingsRowData(new Vector2i(1, 1), false));
        super.addRow(wrong);

        next = new MidiSettingsRow(settingsChangeListener, NEXT_MIDI, Text.MIDI_NEXT_DESCRIPTION, new MidiSettingsRow.MidiSettingsRowData(new Vector2i(1, 1), false));
        super.addRow(next);

        buzzer = new MidiSettingsRow[SaveDataHandler.MAX_BUZZER_COUNT];
        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            buzzer[i] = new MidiSettingsRow(settingsChangeListener, BUZZER_MIDI + i, SaveDataHandler.BUZZER_NAMES[i] + " " + Text.MIDI_BUZZER_DESCRIPTION, new MidiSettingsRow.MidiSettingsRowData(new Vector2i(1, 1), false));
            super.addRow(buzzer[i]);
        }
    }

    /**
     * @return returns the midi settings row for the right action
     */
    public MidiSettingsRow getRight() {
        return right;
    }

    /**
     * @return returns the midi settings row for the wrong action
     */
    public MidiSettingsRow getWrong() {
        return wrong;
    }

    /**
     * @return returns the midi settings row for the next action
     */
    public MidiSettingsRow getNext() {
        return next;
    }

    /**
     * @return returns the midi settings row for the buzzer press actions
     */
    public MidiSettingsRow[] getBuzzer() {
        return buzzer;
    }
}
