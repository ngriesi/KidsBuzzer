package programs.quiztime.main.control;

import midi.MidiSettingsRow;
import utils.save.SaveFile;

import static programs.quiztime.data.QuizTimeProgramModel.MIDI_INTRO;

/**
 * Midi handler for the quiztime program
 */
public class MidiHandler extends programs.quizPrograms.main.control.MidiHandler {

    /**
     * creates a new midi handler
     *
     * @param saveFile save file of the program
     */
    public MidiHandler(SaveFile saveFile) {
        super(saveFile);
    }

    /**
     * performs the midi action of the intro action
     */
    void performIntroMidiAction() {



        MidiSettingsRow.MidiSettingsRowData midiData = quizSaveFile.getMidiSettingsRowData(MIDI_INTRO);
        if (midiData.isActive()) {
            System.out.println("test");
            midiHandler.sendMessageToPressExecutor(midiData.getButton().x, midiData.getButton().y);
        }
    }
}
