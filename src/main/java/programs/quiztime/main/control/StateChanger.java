package programs.quiztime.main.control;

import presentationWindow.animations.AnimationQueue;
import presentationWindow.engine.Action;
import programs.quizPrograms.main.control.QuizGeneralState;
import programs.quizPrograms.main.control.QuizProgram;
import programs.quizPrograms.main.control.QuizStateChanger;
import programs.quizPrograms.main.control.QuizViewUpdater;
import programs.quizPrograms.main.view.QuizPresentationView;
import programs.quiztime.data.QuizTimeProgramModel;

/**
 * changes the state of the quiz time program
 */
class StateChanger extends QuizStateChanger {

    private QuizTimeProgramModel quizTimeProgramModel;


    /**
     * creates a new state changer
     *
     * @param program      program this is the state changer of
     * @param generalState general state of the program
     */
    public StateChanger(QuizProgram program, QuizGeneralState generalState) {
        super(program, generalState);
        quizTimeProgramModel = (QuizTimeProgramModel) program.getProgramModel();
    }

    @Override
    protected QuizViewUpdater createViewUpdater(QuizProgram program) {
        return new ViewUpdater((QuizPresentationView) program.getProgramPresentationView(), program);
    }

    /**
     * method called and used when the intro screen should fade in
     */
    void changeToIntro() {
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        quizTimeProgramModel.playIntroSound();
        ((MidiHandler)midiHandler).performIntroMidiAction();
        animationQueueItem.setAnimationAction(() -> ((ViewUpdater)quizViewUpdater).introAnimation(animationQueueItem));
        animationQueueItem.addOnFinishedAction(() -> ((GeneralState)generalState).changeToIntroState());
        animationQueue.addAnimation(animationQueueItem);
    }

    /**
     * method called and used when the presentation view should fade out
     */
    @Override
    public void fadeToInvisible() {
        quizTimeProgramModel.fadeOutIntroSound();
        super.fadeToInvisible();
    }

    /**
     * action performed, when a new question should be displayed
     *
     * @param animationQueueItem animation queue item that gets passed because it is needed for the
     *                           check whether this method should be called in the QuizTimeProgram class
     * @param action             action containing the action of updating the control view of the program which is
     *                           written inside the QuizTimeProgram class
     */
    public void nextQuestion(AnimationQueue.AnimationQueueItem animationQueueItem, Action action) {
        quizTimeProgramModel.fadeOutIntroSound();
        programModel.playQuestionSound();
        midiHandler.performNextMidiAction();
        animationQueueItem.setAnimationAction(() -> {

            action.execute();

            ((ViewUpdater)quizViewUpdater).nextQuestion(((GeneralState)generalState).getQuestionNumber(), animationQueueItem);

        });
        animationQueueItem.addOnFinishedAction(this::reset);
        animationQueue.addAnimation(animationQueueItem);
    }
}

