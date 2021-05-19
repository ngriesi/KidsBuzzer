package programs.scoreBoard.main;

import programs.abstractProgram.Program;
import programs.scoreBoard.control.ScoreBoardControlController;
import programs.scoreBoard.data.ScoreBoardModel;
import programs.scoreBoard.main.view.ScoreBoardPresentationView;
import programs.scoreBoard.settings.ScoreBoardSettingsController;
import savedataHandler.languages.Text;

public class ScoreBoardProgram extends Program<ScoreBoardControlController, ScoreBoardSettingsController, ScoreBoardModel, ScoreBoardPresentationView> {


    private MainScoreBoardController mainController;

    /**
     * creates a new program
     */
    public ScoreBoardProgram() {
        super(false, Text.SCOREBOARD);
        mainController = new MainScoreBoardController(this);
    }

    /**
     * @return the created <code>ScoreBoardModel</code>
     */
    @Override
    public ScoreBoardModel createModel() {
        return new ScoreBoardModel();
    }

    /**
     * @return the created <code>ScoreBoardSettingsController</code>
     */
    @Override
    public ScoreBoardSettingsController createSettingsController() {
        return new ScoreBoardSettingsController(this, getProgramModel());
    }

    /**
     * @return the created <code>ScoreBoardControlController</code>
     */
    @Override
    public ScoreBoardControlController createControlController() {
        return new ScoreBoardControlController(this, getProgramModel());
    }

    /**
     * @return the created <code>ScoreBoardPresentationView</code>
     */
    @Override
    public ScoreBoardPresentationView createPresentationView() {
        return new ScoreBoardPresentationView(this);
    }

    /**
     * @return returns the main controller of the program
     */
    public MainScoreBoardController getMainScoreBoardController() {
        return mainController;
    }

    /**
     * method called when a buzzer was pressed
     *
     * @param buzzerNumber number of the buzzer pressed (starting with 1)
     */
    @Override
    protected void buzzerAction(int buzzerNumber) {
        this.getMainController().getControlModel().getBuzzerControl().setBlockAllBuzzer(true);
        mainController.buzzerPressed(buzzerNumber);
    }

    @Override
    public void updateBuzzerCount() {

    }

    /**
     * method called when a native key event occurs
     *
     * @param keyCode code of the key that was released
     */
    @Override
    public void nativeKeyAction(int keyCode) {
        getProgramController().nativeKeyAction(keyCode);
    }
}
