package programs.quizOverlay.control.control;

import org.jnativehook.keyboard.NativeKeyEvent;
import programs.abstractProgram.ProgramController;
import programs.quizOverlay.control.view.QuizOverlayControlView;
import programs.quizOverlay.data.QuizOverlayModel;
import programs.quizOverlay.main.control.QuizOverlayProgram;

import java.awt.event.ActionEvent;

/**
 * controller of the control panel of the quiz time program
 */
public class QuizOverlayProgramController extends ProgramController<QuizOverlayProgram, QuizOverlayControlView, QuizOverlayModel> {

    /**
     * simple preview of the output screen
     */
    private SimpleOutputView simpleOutputView;


    /**
     * creates a new controller
     *
     * @param program      program this controller belongs to
     * @param programModel model of this program
     */
    public QuizOverlayProgramController(QuizOverlayProgram program, QuizOverlayModel programModel) {
        super(program, programModel);
        simpleOutputView.resetToQuestionView();

    }

    /**
     * @return returns the newly created view
     */
    @Override
    protected QuizOverlayControlView createView() {
        if (simpleOutputView == null) {
            simpleOutputView = new SimpleOutputView(this);
        }
        return new QuizOverlayControlView(this);
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
                showHideAction();
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
            case "settings":
                getProgram().setView(getProgram().getSettingsController().getProgramView());
                break;
        }
    }

    /**
     * hides the output when it is visible and shows it
     * when it is invisible
     */
    public void showHideAction() {
        if (getProgram().getMainController().isShowingPresentation()) {
            hide();
        } else {
            show();
        }
    }

    /**
     * shoes the presentation window and starts the intro animation
     */
    public void show() {
        getProgram().getMainController().showPresentationWindow();
        getProgram().fadeIn();
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
                            getSimpleOutputView().resetToQuestionView();
                        }
                    },
                    1000
            );
        }
    }

    /**
     * action of the wrong button - tells the program that the current buzzer gave a wrong answer
     */
    public void wrongButtonAction() {
        getProgram().wrongAnswer();
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

    /**
     * Method gets called when a native key release event occurs. Performs the same
     * actions as the normal key bindings in <code>QuizOverlayControlView</code>
     *
     * @param keyCode code of the key released
     */
    public void nativeKeyAction(int keyCode) {
        switch (keyCode) {
            case NativeKeyEvent.VC_V:
                if (getProgram().getMainController().isShowingPresentation()) {
                    hide();
                }
                break;
            case NativeKeyEvent.VC_A:
                if (!getProgram().getMainController().isShowingPresentation()) {
                    show();
                }
                break;
            case NativeKeyEvent.VC_F:
                wrongButtonAction();
                break;
            case NativeKeyEvent.VC_R:
                rightButtonAction();
                break;
            case NativeKeyEvent.VC_N:
                nextButtonAction();
                break;
        }
    }
}
