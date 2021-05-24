package programs.quizPrograms.main.control;

import programs.quizPrograms.data.QuizSaveFile;

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
    protected QuizSaveFile quizSaveFile;

    /**
     * creates a new midi handler
     *
     * @param quizSaveFile save file of the program
     */
    public MidiHandler(QuizSaveFile quizSaveFile) {
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
        if (quizSaveFile.isRightMidiActivate()) {
            midiHandler.sendMessageToPressExecuter(quizSaveFile.getRightMidiX(), quizSaveFile.getRightMidiY());
        }
    }

    /**
     * performs the midi aciton of the Wrong aciton
     */
    void performWrongMidiAction() {
        if (quizSaveFile.isWrongMidiActivate()) {
            midiHandler.sendMessageToPressExecuter(quizSaveFile.getWrongMidiX(), quizSaveFile.getWrongMidiY());
        }
    }

    /**
     * performs the midi aciton of the Next aciton
     */
    public void performNextMidiAction() {
        if (quizSaveFile.isNextMidiActivate()) {
            midiHandler.sendMessageToPressExecuter(quizSaveFile.getNextMidiX(), quizSaveFile.getNextMidiY());
        }
    }

    /**
     * performs the midi aciton of the Buzzer aciton
     *
     * @param buzzerIndex index of the buzzer starting with 0
     */
    void performBuzzerMidiAction(int buzzerIndex) {
        if (quizSaveFile.getBuzzerMidiActivate()[buzzerIndex]) {
            midiHandler.sendMessageToPressExecuter(quizSaveFile.getBuzzerMidiX()[buzzerIndex], quizSaveFile.getBuzzerMidiY()[buzzerIndex]);
        }
    }


}
