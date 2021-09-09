package programs.quiztime.main.control;

import presentationWindow.animations.AnimationQueue;
import programs.quizPrograms.main.control.QuizGeneralState;
import programs.quizPrograms.main.control.QuizProgram;
import programs.quiztime.control.control.QuizTimeProgramControlController;
import programs.quiztime.data.QuizTimeProgramModel;
import programs.quiztime.main.view.QuizTimeProgramPresentationView;
import programs.quiztime.settings.QuizTimeProgramSettingsController;
import savedataHandler.languages.Text;

/**
 * main class of the quiz time program
 */
public class QuizTimeProgram extends QuizProgram<QuizTimeProgramControlController, QuizTimeProgramSettingsController, QuizTimeProgramModel, QuizTimeProgramPresentationView> {

    /**
     * creates a new program
     */
    public QuizTimeProgram() {
        super(true, Text.QUIZTIME);
        quizGeneralState = new GeneralState();
        quizStateChanger = new StateChanger(this, quizGeneralState);
    }

    /**
     * @return returns a newly created quiz time model
     */
    @Override
    public QuizTimeProgramModel createModel() {
        return new QuizTimeProgramModel();
    }

    /**
     * @return returns the newly created settings controller for the quiz time program
     */
    @Override
    public QuizTimeProgramSettingsController createSettingsController() {
        return new QuizTimeProgramSettingsController(this, getProgramModel());
    }

    /**
     * @return returns the newly created control controller for the quiz time program
     */
    @Override
    public QuizTimeProgramControlController createControlController() {
        return new QuizTimeProgramControlController(this, getProgramModel());
    }

    /**
     * @return returns the newly created presentation view of the quiz time app
     */
    @Override
    public QuizTimeProgramPresentationView createPresentationView() {
        return new QuizTimeProgramPresentationView(this);
    }

    /**
     * starts the intro animation
     */
    public void fadeIn() {
        if (quizGeneralState.checkAndPerformAction(GeneralState.QuizAction.SHOW_TITLE)) {
            ((StateChanger)quizStateChanger).changeToIntro();
        }
    }

    /**
     * action performed when the program gets closed
     */
    @Override
    public void programClosed() {

        super.programClosed();
    }

    /**
     * sets the question number
     *
     * @param number new question number
     */
    public void setQuestionNumber(int number) {
        ((GeneralState)quizGeneralState).setQuestionNumber(number);
    }

    /**
     * next button action
     */
    @Override
    public void nextQuestion() {
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        if (quizGeneralState.checkAndPerformAction(GeneralState.QuizAction.NEXT_QUESTION, animationQueueItem)) {
            quizStateChanger.nextQuestion(animationQueueItem, () -> getProgramController().setQuestionNumber(((GeneralState)quizGeneralState).getQuestionNumber() + 1));
        }
    }
}
