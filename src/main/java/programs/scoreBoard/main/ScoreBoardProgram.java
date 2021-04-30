package programs.scoreBoard.main;

import programs.abstractProgram.Program;
import programs.scoreBoard.control.ScoreBoardControlController;
import programs.scoreBoard.data.ScoreBoardModel;
import programs.scoreBoard.main.view.ScoreBoardPresentationView;
import programs.scoreBoard.settings.ScoreBoardSettingsController;

public class ScoreBoardProgram extends Program<ScoreBoardControlController, ScoreBoardSettingsController, ScoreBoardModel, ScoreBoardPresentationView> {


    private MainScoreBoardController mainController;

    /**
     * creates a new program
     */
    public ScoreBoardProgram() {
        super(false, "Score Board");
        mainController = new MainScoreBoardController(this);
    }

    @Override
    public ScoreBoardModel createModel() {
        return new ScoreBoardModel();
    }

    @Override
    public ScoreBoardSettingsController createSettingsController() {
        return new ScoreBoardSettingsController(this, getProgramModel());
    }

    @Override
    public ScoreBoardControlController createControlController() {
        return new ScoreBoardControlController(this, getProgramModel());
    }

    @Override
    public ScoreBoardPresentationView createPresentationView() {
        return new ScoreBoardPresentationView(this);
    }

    public MainScoreBoardController getMainController() {
        return mainController;
    }

    @Override
    protected void buzzerAction(int buzzerNumber) {
        getControlModel().getBuzzerControl().setBlockAllBuzzer(true);
        mainController.buzzerPressed(buzzerNumber);
    }

    @Override
    public void updateBuzzerCount() {

    }

    @Override
    public void nativeKeyAction(int keyCode) {
        getProgramController().nativeKeyAction(keyCode);
    }
}
