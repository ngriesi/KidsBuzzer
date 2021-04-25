package programs.mouseClicker.main;

import presentationWindow.renderItems.MainItem;
import programs.abstractProgram.ProgramPresentationView;

public class MouseClickerProgramPresentationView extends ProgramPresentationView<MouseClickerProgram> {

    /**
     * creates a new presentation view
     *
     * @param program sets the program
     */
    MouseClickerProgramPresentationView(MouseClickerProgram program) {
        super(program);
    }

    @Override
    public void setupView(MainItem mainItem) {

    }
}
