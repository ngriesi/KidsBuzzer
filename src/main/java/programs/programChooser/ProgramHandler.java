package programs.programChooser;

import programs.abstractProgram.Program;

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
        switch (name) {
            case "Quiztime": return 0;
            case "Score Board": return 1;
            case "Instant Button": return 2;
            case "Quizoverlay": return 3;
            case "Mouse Clicker": return 4;
            case "Key Presser": return 5;
            case "Test": return 6;
            default: return -1;
        }
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
}
