package programs.quizPrograms.main.control;

import controlWindow.MainController;
import presentationWindow.animations.AnimationQueue;
import programs.abstractProgram.Program;
import programs.abstractProgram.ProgramController;
import programs.quizPrograms.control.control.QuizControlController;
import programs.quizPrograms.control.control.SimpleOutputView;
import programs.quizPrograms.data.QuizModel;
import programs.quizPrograms.main.view.QuizPresentationView;
import remoteHandler.RemoteHandler;
import remoteHandler.actions.RemoteAction;
import savedataHandler.languages.Text;

public abstract class QuizProgram<PC extends QuizControlController, SC extends ProgramController, QM extends QuizModel, QP extends QuizPresentationView> extends Program<PC,SC,QM,QP> {

    /**
     * handles the general state of the program
     */
    protected QuizGeneralState quizGeneralState;

    /**
     * handles the state changing
     */
    protected QuizStateChanger quizStateChanger;

    /**
     * creates a new program
     *
     * @param blockBuzzers determines if the buzzer blocking should be used
     * @param name         name of the program
     */
    public QuizProgram(boolean blockBuzzers, String name) {
        super(blockBuzzers, name);

    }

    /**
     * action of the buzzer
     *
     * @param buzzerNumber number of the buzzer pressed
     */
    @Override
    protected void buzzerAction(int buzzerNumber) {
        if (quizGeneralState.checkAndPerformAction(QuizGeneralState.QuizAction.BUZZER_PRESS)) {
            quizStateChanger.buzzerPressed(buzzerNumber, new AnimationQueue.AnimationQueueItem());
        }
    }

    /**
     * action of the wrong button
     */
    public void wrongAnswer() {
        if (quizGeneralState.checkAndPerformAction(QuizGeneralState.QuizAction.WRONG)) {
            quizStateChanger.wrongAnswerGiven();
        }
    }

    /**
     * action of the right button
     */
    public void rightAnswer() {
        if (quizGeneralState.checkAndPerformAction(QuizGeneralState.QuizAction.RIGHT)) {
            quizStateChanger.rightAnswerGiven();
        }
    }

    public void fadeIn() {
    }

    public abstract void nextQuestion();

    /**
     * fades out the presentation view
     *
     * @return returns the result of the check
     */
    public boolean fadeOut() {
        boolean result = quizGeneralState.checkAndPerformAction(QuizGeneralState.QuizAction.TO_INVISIBLE);
        if (result) {
            quizStateChanger.fadeToInvisible();
        }
        return result;
    }

    /**
     * updates the controlModel of the stateChanger
     *
     * @param mainController main controller of the control window
     */
    @Override
    public void setMainController(MainController mainController) {
        super.setMainController(mainController);
        quizStateChanger.setMainController(mainController);
        getProgramModel().getQuizMidiHandler().setMidiHandler(getMidiHandler());
        quizStateChanger.setMidiHandler(getProgramModel().getQuizMidiHandler());
    }

    /**
     * updates the number of buzzers
     */
    @Override
    public void updateBuzzerCount() {
        getProgramController().setSimpleOutputView(new SimpleOutputView(getProgramController()));
        getProgramController().getProgramView().repaint();
        getProgramPresentationView().updateBuzzerCount();
    }

    /**
     * action performed when the program gets closed
     */
    @Override
    public void programClosed() {
        super.programClosed();
    }

    /**
     * method called through the naive key listener when a key gets released
     *
     * @param keyCode code of the key that was released
     */
    @Override
    public void nativeKeyAction(int keyCode) {
        getProgramController().nativeKeyAction(keyCode);
    }

    /**
     * creates the actions that can be bound to the
     * buttons of the remote
     *
     * @param remoteHandler <code>RemoteHandler</code> of this program
     */
    @Override
    protected void createRemoteActions(RemoteHandler remoteHandler) {
        super.createRemoteActions(remoteHandler);

        remoteHandler.addRemoteAction(Text.NEXT_QUESTION, new RemoteAction(this::nextQuestion));
        remoteHandler.addRemoteAction(Text.RIGHT, new RemoteAction(this::rightAnswer));
        remoteHandler.addRemoteAction(Text.WRONG, new RemoteAction(this::wrongAnswer));
        remoteHandler.addRemoteAction(Text.SHOW_OR_HIDE, new RemoteAction(() -> getProgramController().showHideAction()));
    }
}
