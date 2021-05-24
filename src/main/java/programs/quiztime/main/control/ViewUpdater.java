package programs.quiztime.main.control;

import presentationWindow.animations.AnimationQueue;
import programs.quizOverlay.main.view.QuizOverlayPresentationView;
import programs.quizPrograms.main.control.QuizProgram;
import programs.quizPrograms.main.control.QuizViewUpdater;
import programs.quizPrograms.main.view.QuizPresentationView;
import programs.quiztime.control.control.SimpleOutputView;
import programs.quiztime.main.view.QuizTimeProgramPresentationView;

/**
 * updates the views of the program
 */
class ViewUpdater extends QuizViewUpdater {

    /**
     * creates a new view updater with both views
     *
     * @param presentationView presentation view
     * @param quizProgram  reference to the program to access the simple output view
     */
    public ViewUpdater(QuizPresentationView presentationView, QuizProgram quizProgram) {
        super(presentationView, quizProgram);
    }

    /**
     * updates the view if the next question should be displayed
     *
     * @param questionNumber     number of the question displayed
     * @param animationQueueItem animation queue item
     */
    void nextQuestion(int questionNumber, AnimationQueue.AnimationQueueItem animationQueueItem) {
        ((SimpleOutputView)simpleOutputView).setQuestion(questionNumber);
        ((SimpleOutputView)simpleOutputView).hideTitle();
        ((QuizTimeProgramPresentationView)presentationView).changeQuestionNumber(questionNumber);
        super.nextQuestion(animationQueueItem);
    }

    /**
     * plays the intro animation
     *
     * @param animationQueueItem animation queue item
     */
    void introAnimation(AnimationQueue.AnimationQueueItem animationQueueItem) {
        ((QuizTimeProgramPresentationView)presentationView).introAnimation(animationQueueItem);
    }
}
