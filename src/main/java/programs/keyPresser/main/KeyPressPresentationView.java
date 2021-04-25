package programs.keyPresser.main;

import presentationWindow.renderItems.MainItem;
import programs.abstractProgram.ProgramPresentationView;

public class KeyPressPresentationView extends ProgramPresentationView<KeyPressProgram> {
    /**
     * creates a new presentation view
     *
     * @param program sets the program
     */
    public KeyPressPresentationView(KeyPressProgram program) {
        super(program);
    }

    @Override
    public void setupView(MainItem mainItem) {

    }
}
