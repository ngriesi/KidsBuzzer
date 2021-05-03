package programs.emptyClasses;

import presentationWindow.renderItems.MainItem;
import programs.abstractProgram.Program;
import programs.abstractProgram.ProgramPresentationView;

public class EmptyPresentationView extends ProgramPresentationView<Program> {

    /**
     * creates a new presentation view
     *
     * @param program sets the program
     */
    public EmptyPresentationView(Program program) {
        super(program);
    }

    @Override
    public void setupView(MainItem mainItem) {

    }
}
