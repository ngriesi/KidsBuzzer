package programs.quizPrograms.main.control;

import midi.MidiSettingsRow;
import programs.quizPrograms.data.QuizModel;
import utils.save.SaveFile;

/**
 * Midi handler for the quiztime program
 */
public class MidiHandler {

    /**
     * Main midi handler of the applicaiton
     */
    protected midi.MidiHandler midiHandler;

    /**
     * save file of the quiz time application
     */
    protected SaveFile quizSaveFile;

    /**
     * creates a new midi handler
     *
     * @param quizSaveFile save file of the program
     */
    public MidiHandler(SaveFile quizSaveFile) {
        this.quizSaveFile = quizSaveFile;
    }

    /**
     * sets the midi handler
     *
     * @param midiHandler main midi handler of the application
     */
    void setMidiHandler(midi.MidiHandler midiHandler) {
        this.midiHandler = midiHandler;
    }

    /**
     * performs the midi aciton of the Right aciton
     */
    void performRightMidiAction() {
        MidiSettingsRow.MidiSettingsRowData midiData = quizSaveFile.getMidiSettingsRowData(QuizModel.RIGHT_MIDI);
        if (midiData.isActive()) {
            midiHandler.sendMessageToPressExecuter(midiData.getButton().x, midiData.getButton().y);
        }
    }

    /**
     * performs the midi aciton of the Wrong aciton
     */
    void performWrongMidiAction() {
        MidiSettingsRow.MidiSettingsRowData midiData = quizSaveFile.getMidiSettingsRowData(QuizModel.WRONG_MIDI);
        if (midiData.isActive()) {
            midiHandler.sendMessageToPressExecuter(midiData.getButton().x, midiData.getButton().y);
        }
    }

    /**
     * performs the midi aciton of the Next aciton
     */
    public void performNextMidiAction() {
        MidiSettingsRow.MidiSettingsRowData midiData = quizSaveFile.getMidiSettingsRowData(QuizModel.NEXT_MIDI);
        if (midiData.isActive()) {
            midiHandler.sendMessageToPressExecuter(midiData.getButton().x, midiData.getButton().y);
        }
    }

    /**
     * performs the midi aciton of the Buzzer aciton
     *
     * @param buzzerIndex index of the buzzer starting with 0
     */
    void performBuzzerMidiAction(int buzzerIndex) {
        MidiSettingsRow.MidiSettingsRowData midiData = quizSaveFile.getMidiSettingsRowData(QuizModel.BUZZER_MIDI + buzzerIndex);
        if (midiData.isActive()) {
            midiHandler.sendMessageToPressExecuter(midiData.getButton().x, midiData.getButton().y);
        }
    }


}
