package programs.quiztime.main.control;

import programs.quiztime.data.QuizTimeProgramSaveFile;

/**
 * Midi handler for the quiztime program
 */
public class MidiHandler {

    /**
     * Main midi handler of the applicaiton
     */
    private midi.MidiHandler midiHandler;

    /**
     * save file of the quiz time application
     */
    private QuizTimeProgramSaveFile quizTimeProgramSaveFile;

    /**
     * creates a new midi handler
     *
     * @param quizTimeProgramSaveFile save file of the program
     */
    public MidiHandler(QuizTimeProgramSaveFile quizTimeProgramSaveFile) {
        this.quizTimeProgramSaveFile = quizTimeProgramSaveFile;
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
     * performs the midi aciton of the intro aciton
     */
    void performIntroMidiAction() {
        if (quizTimeProgramSaveFile.isIntroMidiActivate()) {
            midiHandler.sendMessageToPressExecuter(quizTimeProgramSaveFile.getIntroMidiX(), quizTimeProgramSaveFile.getIntroMidiY());
        }
    }

    /**
     * performs the midi aciton of the Right aciton
     */
    void performRightMidiAction() {
        if (quizTimeProgramSaveFile.isRightMidiActivate()) {
            midiHandler.sendMessageToPressExecuter(quizTimeProgramSaveFile.getRightMidiX(), quizTimeProgramSaveFile.getRightMidiY());
        }
    }

    /**
     * performs the midi aciton of the Wrong aciton
     */
    void performWrongMidiAction() {
        if (quizTimeProgramSaveFile.isWrongMidiActivate()) {
            midiHandler.sendMessageToPressExecuter(quizTimeProgramSaveFile.getWrongMidiX(), quizTimeProgramSaveFile.getWrongMidiY());
        }
    }

    /**
     * performs the midi aciton of the Next aciton
     */
    void performNextMidiAction() {
        if (quizTimeProgramSaveFile.isNextMidiActivate()) {
            midiHandler.sendMessageToPressExecuter(quizTimeProgramSaveFile.getNextMidiX(), quizTimeProgramSaveFile.getNextMidiY());
        }
    }

    /**
     * performs the midi aciton of the Buzzer aciton
     *
     * @param buzzerIndex index of the buzzer starting with 0
     */
    void performBuzzerMidiAction(int buzzerIndex) {
        if (quizTimeProgramSaveFile.getBuzzerMidiActivate()[buzzerIndex]) {
            midiHandler.sendMessageToPressExecuter(quizTimeProgramSaveFile.getBuzzerMidiX()[buzzerIndex], quizTimeProgramSaveFile.getBuzzerMidiY()[buzzerIndex]);
        }
    }


}
