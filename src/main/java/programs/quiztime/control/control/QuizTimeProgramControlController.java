package programs.quiztime.control.control;

import assets.standardAssets.MyTextField;
import programs.quizPrograms.control.control.QuizControlController;
import programs.quiztime.control.view.QuizTimeProgramControlView;
import programs.quiztime.data.QuizTimeProgramModel;
import programs.quiztime.main.control.QuizTimeProgram;

import java.awt.event.ActionEvent;

/**
 * controller of the control panel of the quiz time program
 */
public class QuizTimeProgramControlController extends QuizControlController<QuizTimeProgram, QuizTimeProgramControlView, QuizTimeProgramModel> {

    /**
     * flag indicationg whether the next update to the text field should be ignored
     */
    private boolean ignoreNextTextUpdate = false;

    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public QuizTimeProgramControlController(QuizTimeProgram program, QuizTimeProgramModel programModel) {
        super(program, programModel);
        fadeOutTime = 2000;
    }

    /**
     * @return returns the newly craeted view
     */
    @Override
    protected QuizTimeProgramControlView createQuizView() {
        if (simpleOutputView == null) {
            simpleOutputView = new SimpleOutputView(this);
        }
        return new QuizTimeProgramControlView(this);
    }

    /**
     * actions of the buttons of the view
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("number")) {
            if (ignoreNextTextUpdate) {
                ignoreNextTextUpdate = false;
            } else {
                getProgram().setQuestionNumber(Integer.parseInt(((MyTextField) e.getSource()).getText()));
            }
        } else {
            super.actionPerformed(e);
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
}
