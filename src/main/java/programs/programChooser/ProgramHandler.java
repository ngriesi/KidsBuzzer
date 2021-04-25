package programs.programChooser;

import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.Program;
import startupApp.LoadingHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * handler contains the list of the programs
 */
public class ProgramHandler {

    /**
     * list of the programs
     */
    private List<Program> programs;

    /**
     * creates a new program list
     */
    public ProgramHandler() {
        programs = new ArrayList<>();
    }

    /**
     * adds a program to the list
     *
     * @param program program to be added
     */
    public void addProgram(Program program) {
        programs.add(program);
    }

    /**
     * loads the programs, calls the loading methods form the programs in the programs list
     *
     * @param openGlRenderer for loading of open gl resources
     * @param loadingHandler for handling the loading
     */
    public void loadPrograms(OpenGlRenderer openGlRenderer, LoadingHandler loadingHandler) {
        for (Program program : programs) {
            program.loadProgram(openGlRenderer, loadingHandler);
        }
    }

    /**
     * @return returns a list of the program names list
     */
    public String[] getProgramNamesList() {
        String[] result = new String[programs.size()];

        for(int i = 0; i < programs.size();i++) {
            result[i] = programs.get(i).getName();
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
        for(Program program : programs) {
            if (program.getName().equals(name)) {
                return program;
            }
        }
        return null;
    }

    public void reset() {
        programs.clear();
    }

    public void updateBuzzerCount() {
        for (Program program : programs) {
            program.updateBuzzerCount();
        }
    }
}
