package programs.quiztime.control.control;

import assets.standardAssets.MyTextField;
import programs.abstractProgram.ProgramController;
import programs.quiztime.control.view.QuizTimeProgramControlView;
import programs.quiztime.data.QuizTimeProgramModel;
import programs.quiztime.main.control.QuizTimeProgram;

import java.awt.event.ActionEvent;

/**
 * controller of the control panel of the quiz time program
 */
public class QuizTimeProgramControlController extends ProgramController<QuizTimeProgram, QuizTimeProgramControlView, QuizTimeProgramModel> {

    /**
     * simple preview of the output screen
     */
    private SimpleOutputView simpleOutputView;

    private boolean ignoreNextTextUpdate = false;

    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public QuizTimeProgramControlController(QuizTimeProgram program, QuizTimeProgramModel programModel) {
        super(program, programModel);
        simpleOutputView.resetToQuestionView();

    }

    /**
     * @return returns the newly created view
     */
    @Override
    protected QuizTimeProgramControlView createView() {
        simpleOutputView = new SimpleOutputView(this);
        return new QuizTimeProgramControlView(this);
    }

    /**
     * method used to update the view when it gets launched
     */
    @Override
    protected void updateView() {
    }

    /**
     * @return returns the simple output preview
     */
    public SimpleOutputView getSimpleOutputView() {
        return simpleOutputView;
    }

    /**
     * actions of the buttons of the view
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "show":
                if (getProgram().getMainController().isShowingPresentation()) {
                    hide();
                } else {
                    show();
                }
                break;
            case "wrong":
                wrongButtonAction();
                break;
            case "right":
                rightButtonAction();
                break;
            case "next":
                nextButtonAction();
                break;
            case "number":
                if (ignoreNextTextUpdate) {
                    ignoreNextTextUpdate = false;
                } else {
                    getProgram().setQuestionNumber(Integer.parseInt(((MyTextField) e.getSource()).getText()));
                }
                break;
            case "settings":
                getProgram().setView(getProgram().getSettingsController().getProgramView());
                break;
        }
    }

    /**
     * sets the value of the TextField
     *
     * @param number int displayed in the text field
     */
    public void setQuestionNumber(int number) {
        ignoreNextTextUpdate = true;
        getProgramView().getTextField().setText(String.valueOf(number));
    }

    /**
     * shoes the presentation window and starts the intro animation
     */
    public void show() {
        getProgram().getMainController().showPresentationWindow();
        getProgram().introAnimation();
    }

    /**
     * starts the fade out animation and hides the presentation window when it is finished
     */
    public void hide() {
        if (getProgram().fadeOut()) {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            getProgram().getMainController().hidePresentationWindow();
                            getSimpleOutputView().changeToDefaultState();
                        }
                    },
                    2000
            );
        }
    }

    /**
     * action of the wrong button - tells the program that the current buzzer gave a wrong answer
     */
    public void wrongButtonAction() {
        if (!getSimpleOutputView().isRight()) {
            getProgram().wrongAnswer();
        }
    }

    /**
     * action of the right button - tells the program that the current buzzer gave a right answer
     */
    public void rightButtonAction() {
        getProgram().rightAnswer();
    }

    /**
     * action of the next button - tells the program to display the next answer
     */
    public void nextButtonAction() {
        getProgram().nextQuestion();
    }

    /**
     * sets the simple output view
     *
     * @param simpleOutputView new simple output view
     */
    public void setSimpleOutputView(SimpleOutputView simpleOutputView) {
        this.simpleOutputView = simpleOutputView;
    }
}
