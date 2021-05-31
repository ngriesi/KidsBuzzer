package programs.quiztime.settings.pages;

import assets.settings.general.SettingsChangeListener;
import midi.MidiSettingsRow;
import org.joml.Vector2i;
import savedataHandler.languages.Text;

import static programs.quiztime.data.QuizTimeProgramModel.MIDI_INTRO;

/**
 * Settings page for the midi actions to be performed
 */
public class MidiSettingsPage extends programs.quizPrograms.settings.pages.MidiSettingsPage {

    /**
     * Settings rows for the general midi actions
     */
    private MidiSettingsRow intro;

    /**
     * creates the settings page
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    public MidiSettingsPage(SettingsChangeListener settingsChangeListener) {
        super(settingsChangeListener);
    }

    /**
     * creates the settings rows for the image settings
     *
     * @param settingsChangeListener <code>SettingsChangeListener</code> for the settings rows
     */
    @Override
    protected void createMidiSelectionView(SettingsChangeListener settingsChangeListener) {

        intro = new MidiSettingsRow(settingsChangeListener, MIDI_INTRO, Text.MIDI_INTRO_DESCRIPTION, new MidiSettingsRow.MidiSettingsRowData(new Vector2i(1, 1), false));
        super.addRow(intro);

        super.createMidiSelectionView(settingsChangeListener);
    }

    /**
     * @return returns the midi settings row for the intro
     */
    public MidiSettingsRow getIntro() {
        return intro;
    }
}
