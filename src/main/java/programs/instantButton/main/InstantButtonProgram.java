package programs.instantButton.main;

import programs.abstractProgram.Program;
import programs.emptyClasses.EmptyController;
import programs.emptyClasses.EmptyPresentationView;
import programs.instantButton.control.InstantButtonController;
import programs.instantButton.data.InstantButtonModel;
import savedataHandler.languages.Text;

/**
 * main class of the instant button program
 */
public class InstantButtonProgram extends Program<InstantButtonController, EmptyController, InstantButtonModel, EmptyPresentationView> {

    /**
     * creates a new program
     */
    public InstantButtonProgram() {
        super(true, Text.INSTANT_BUTTON);
    }

    /**
     * @return the create <code>InstantButtonModel</code>
     */
    @Override
    public InstantButtonModel createModel() {
        return new InstantButtonModel();
    }

    /**
     * @return the create <code>EmptyController</code>
     */
    @Override
    public EmptyController createSettingsController() {
        return new EmptyController(this, getProgramModel());
    }

    /**
     * @return the create <code>InstantButtonController</code>
     */
    @Override
    public InstantButtonController createControlController() {
        return new InstantButtonController(this, getProgramModel());
    }

    /**
     * @return the create <code>EmptyPresentationView</code>
     */
    @Override
    public EmptyPresentationView createPresentationView() {
        return new EmptyPresentationView(this);
    }

    /**
     * action performed when a buzzer gets pressed (plays the corresponding audio)
     *
     * @param buzzerNumber number of the buzzer pressed
     */
    @Override
    protected void buzzerAction(int buzzerNumber) {
        getProgramModel().getAudioClips()[buzzerNumber - 1].setOnFinishedAction(() -> getMainController().getControlModel().getBuzzerControl().unblockBuzzer(buzzerNumber));
        if (!getProgramModel().playSound(buzzerNumber - 1)) {
            getMainController().getControlModel().getBuzzerControl().unblockBuzzer(buzzerNumber);
        }
    }

    @Override
    public void updateBuzzerCount() {

    }
}
