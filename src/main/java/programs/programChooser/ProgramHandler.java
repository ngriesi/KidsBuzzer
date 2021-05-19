package programs.programChooser;

import programs.abstractProgram.Program;
import savedataHandler.languages.Text;

/**
 * handler contains the list of the programs
 */
public class ProgramHandler {

    private static final int PROGRAM_COUNT = 7;

    /**
     * list of the programs
     */
    private Program[] programs;

    /**
     * creates a new program list
     */
    public ProgramHandler() {
        programs = new Program[PROGRAM_COUNT];
    }

    /**
     * adds a program to the list
     *
     * @param program program to be added
     */
    public void addProgram(Program program) {
        programs[getProgramIndex(program.getName())] = program;
    }

    /**
     * returns the position a program has in the left panel list
     *
     * @param name name of the program
     * @return position in the list
     */
    private int getProgramIndex(String name) {
        if(name.equals(Text.QUIZTIME)) return 0;
        if(name.equals(Text.SCOREBOARD)) return 1;
        if(name.equals(Text.INSTANT_BUTTON)) return 2;
        if(name.equals(Text.QUIZ_OVERLAY)) return 3;
        if(name.equals(Text.MOUSE_CLICKER)) return 4;
        if(name.equals(Text.KEY_PRESSER)) return 5;
        if(name.equals("Test")) return 6;
        return -1;
    }

    /**
     * @return returns a list of the program names list
     */
    public String[] getProgramNamesList() {
        String[] result = new String[programs.length];

        for (int i = 0; i < programs.length; i++) {
            result[i] = programs[i].getName();
        }

        return result;
    }

    /**
     * used to get a program by name
     *
     * @param name name of the program
     * @return returns the program with that name
     */
    public Program getByName(String name) {
        for (Program program : programs) {
            if (program.getName().equals(name)) {
                return program;
            }
        }
        return null;
    }

    /**
     * resets the programs list
     */
    public void reset() {
        programs = new Program[PROGRAM_COUNT];
    }

    /**
     * calls the <code>updateBuzzerCount</code> method in all programs
     */
    public void updateBuzzerCount() {
        for (Program program : programs) {
            program.updateBuzzerCount();
        }
    }

    /**
     * gets a program by its position in the list
     *
     * @param i position of the program in the programs list starting with 0
     * @return the program at the given position
     */
    public Program getByNumber(int i) {
        return programs[i];
    }

    /**
     * updaes the control and settings views of a program
     */
    public void updateProgramViews() {
        for (Program program : programs) {
            program.updateView();
        }
    }
}
