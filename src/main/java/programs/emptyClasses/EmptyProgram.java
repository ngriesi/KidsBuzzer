package programs.emptyClasses;

import programs.abstractProgram.Program;
import programs.abstractProgram.ProgramController;
import programs.abstractProgram.ProgramModel;
import programs.abstractProgram.ProgramPresentationView;

public class EmptyProgram extends Program<ProgramController,ProgramController, ProgramModel, ProgramPresentationView> {
    /**
     * creates a new program
     *
     * @param blockBuzzers determines if the buzzer blocking should be used
     * @param name         name of the program
     */
    public EmptyProgram(boolean blockBuzzers, String name) {
        super(blockBuzzers, name);
    }

    @Override
    public ProgramModel createModel() {
        return new EmptyModel();
    }

    @Override
    public ProgramController createSettingsController() {
        return new EmptyController(this,getProgramModel());
    }

    @Override
    public ProgramController createControlController() {
        return new EmptyController(this, getProgramModel());
    }

    @Override
    public ProgramPresentationView createPresentationView() {
        return new EmptyPresentationView(this);
    }

    @Override
    protected void buzzerAction(int buzzerNumber) {

    }

    @Override
    public void updateBuzzerCount() {

    }
}
