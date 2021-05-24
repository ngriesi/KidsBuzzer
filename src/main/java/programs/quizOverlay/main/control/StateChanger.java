package programs.quizOverlay.main.control;

import presentationWindow.animations.AnimationQueue;
import presentationWindow.engine.Action;
import programs.quizPrograms.main.control.QuizGeneralState;
import programs.quizPrograms.main.control.QuizProgram;
import programs.quizPrograms.main.control.QuizStateChanger;

/**
 * changes the state of the quiz time program
 */
class StateChanger extends QuizStateChanger {


    /**
     * creates a new state changer
     *
     * @param program      program this is the state changer of
     * @param generalState general state of the program
     */
    public StateChanger(QuizProgram program, QuizGeneralState generalState) {
        super(program, generalState);
    }

    /**
     * action performed, when a new question should be displayed
     *
     * @param animationQueueItem animation queue item that gets passed because it is needed for the
     *                           check whether this method should be called in the QuizTimeProgram class
     */
    public void nextQuestion(AnimationQueue.AnimationQueueItem animationQueueItem, Action action) {
        programModel.playQuestionSound();
        animationQueueItem.setAnimationAction(() -> quizViewUpdater.nextQuestion(animationQueueItem));
        animationQueueItem.addOnFinishedAction(this::reset);
        animationQueue.addAnimation(animationQueueItem);
    }
}

