package programs.abstractProgram;

import presentationWindow.renderItems.MainItem;

/**
 * presentation view of the program
 *
 * @param <P> type of the program
 */
public abstract class ProgramPresentationView <P extends Program> {

    /**
     * the program this is the presentation view of
     */
    private P program;

    /**
     * creates a new presentation view
     *
     * @param program sets the program
     */
    public ProgramPresentationView(P program) {
        this.program = program;
    }

    /**
     * sets up the view
     *
     * @param mainItem main item of the presentation scene
     */
    public abstract void setupView(MainItem mainItem);

    /**
     * @return returns the program of the view
     */
    public P getProgram() {
        return program;
    }

}
