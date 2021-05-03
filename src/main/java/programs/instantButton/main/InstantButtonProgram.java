package programs.instantButton.main;

import programs.abstractProgram.Program;
import programs.emptyClasses.EmptyController;
import programs.emptyClasses.EmptyPresentationView;
import programs.instantButton.control.InstantButtonController;
import programs.instantButton.data.InstantButtonModel;
import programs.testProgram.main.TestProgramView;

public class InstantButtonProgram extends Program<InstantButtonController, EmptyController, InstantButtonModel, EmptyPresentationView> {
    /**
     * creates a new program
     */
    public InstantButtonProgram() {
        super(true, "Instant Button");
    }

    @Override
    public InstantButtonModel createModel() {
        return new InstantButtonModel();
    }

    @Override
    public EmptyController createSettingsController() {
        return new EmptyController(this, getProgramModel());
    }

    @Override
    public InstantButtonController createControlController() {
        return new InstantButtonController(this,getProgramModel());
    }

    @Override
    public EmptyPresentationView createPresentationView() {
        return new EmptyPresentationView(this);
    }

    @Override
    protected void buzzerAction(int buzzerNumber) {
        getProgramModel().getAudioClips()[buzzerNumber - 1].setOnFinishedAction(() -> getControlModel().getBuzzerControl().unblockBuzzer(buzzerNumber));
        if(!getProgramModel().playSound(buzzerNumber - 1)) {
            getControlModel().getBuzzerControl().unblockBuzzer(buzzerNumber);
        }
    }

    @Override
    public void updateBuzzerCount() {

    }
}
