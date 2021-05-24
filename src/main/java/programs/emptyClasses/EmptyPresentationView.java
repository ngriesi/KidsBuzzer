package programs.emptyClasses;

import presentationWindow.renderItems.PresentationViewRenderItem;
import programs.abstractProgram.Program;
import programs.abstractProgram.ProgramPresentationView;

/**
 * empty presentation view class for unused generics
 */
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
    public void setupView(PresentationViewRenderItem mainItem) {

    }
}
