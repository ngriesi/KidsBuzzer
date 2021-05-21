package programs.scoreBoard.settings.pages;

import assets.settings.general.SettingsChangeListener;
import assets.settings.general.SettingsPage;
import midi.MidiSettingsRow;
import org.joml.Vector2i;
import savedataHandler.languages.Text;

public class MidiSettingsPage extends SettingsPage {

    /**
     * Midi settings row if a point is scored
     */
    private MidiSettingsRow scored;

    /**
     * creates Panel with Layout
     *
     * @param settingsChangeListener change listenrer of these settings
     */
    public MidiSettingsPage(SettingsChangeListener settingsChangeListener) {
        super(Text.LIGHT_CONTROL, "midi");
        createMidiSettingsPage(settingsChangeListener);
    }

    /**
     * creates the midi settings page
     *
     * @param settingsChangeListener changes listener for this settings page
     */
    private void createMidiSettingsPage(SettingsChangeListener settingsChangeListener) {

        scored = new MidiSettingsRow(settingsChangeListener, "score", Text.MIDI_POINT_SCORED, new MidiSettingsRow.MidiSettingsRowData(new Vector2i(1, 1), false));

        super.addRow(scored);

        addEmpty();
        addEmpty();
        addEmpty();
        addEmpty();
    }

    /**
     * @return returns the midi settings row of this page
     */
    public MidiSettingsRow getScored() {
        return scored;
    }
}
