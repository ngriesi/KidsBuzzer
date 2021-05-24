package programs.quiztime.main.control;

import programs.quiztime.data.QuizTimeProgramSaveFile;

/**
 * Midi handler for the quiztime program
 */
public class MidiHandler extends programs.quizPrograms.main.control.MidiHandler {

    /**
     * creates a new midi handler
     *
     * @param quizTimeProgramSaveFile save file of the program
     */
    public MidiHandler(QuizTimeProgramSaveFile quizTimeProgramSaveFile) {
        super(quizTimeProgramSaveFile);
    }

    /**
     * performs the midi aciton of the intro aciton
     */
    void performIntroMidiAction() {
        QuizTimeProgramSaveFile quizTimeProgramSaveFile = (QuizTimeProgramSaveFile) quizSaveFile;
        if (quizTimeProgramSaveFile.isIntroMidiActivate()) {
            midiHandler.sendMessageToPressExecuter(quizTimeProgramSaveFile.getIntroMidiX(), quizTimeProgramSaveFile.getIntroMidiY());
        }
    }
}
