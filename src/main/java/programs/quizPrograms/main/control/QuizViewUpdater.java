package programs.quizPrograms.main.control;

import presentationWindow.animations.AnimationQueue;
import programs.quizPrograms.control.control.QuizControlController;
import programs.quizPrograms.control.control.SimpleOutputView;
import programs.quizPrograms.main.view.QuizPresentationView;

/**
 * updates the views of the program
 */
public class QuizViewUpdater {

    /**
     * presentation view
     */
    protected QuizPresentationView presentationView;

    /**
     * simple output preview
     */
    protected SimpleOutputView simpleOutputView;

    /**
     * creates a new view updater with both views
     *
     * @param presentationView presentation view
     * @param quizProgram  reference to the program to access the simple output view
     */
    public QuizViewUpdater(QuizPresentationView presentationView, QuizProgram quizProgram) {
        this.presentationView = presentationView;
        this.simpleOutputView = ((QuizControlController)quizProgram.getProgramController()).getSimpleOutputView();
    }

    /**
     * update for a buzzer that gets pressed an is immediately on turn
     *
     * @param buzzerNumber       number of the buzzer
     * @param buzzerPosition     press position of the buzzer
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    void firstBuzzerPress(int buzzerNumber, int buzzerPosition, AnimationQueue.AnimationQueueItem animationQueueItem) {
        presentationView.firstBuzzerPress(buzzerNumber, buzzerPosition, animationQueueItem);
        simpleOutputView.firstPress(buzzerNumber, buzzerPosition);
    }

    /**
     * update for a buzzer that gets pressed an is not immediately on turn
     *
     * @param buzzerNumber       number of the buzzer
     * @param buzzerPosition     press position of the buzzer
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    void followBuzzerPress(int buzzerNumber, int buzzerPosition, AnimationQueue.AnimationQueueItem animationQueueItem) {
        presentationView.followBuzzerPress(buzzerNumber, buzzerPosition, animationQueueItem);
        simpleOutputView.followPress(buzzerNumber, buzzerPosition);
    }

    /**
     * updates the view if a buzzer gives a wrong answer
     *
     * @param buzzerNumber       number of the buzzer
     * @param animationQueueItem animation queue item
     */
    void wrongAnswerGiven(int buzzerNumber, AnimationQueue.AnimationQueueItem animationQueueItem) {
        presentationView.wrongAnswerGiven(buzzerNumber, animationQueueItem);
        simpleOutputView.wrong(buzzerNumber);
    }

    /**
     * updates the view if a new buzzer is on turn
     *
     * @param buzzerNumber       number of the buzzer
     * @param position           position of the buzzer
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    void newBuzzerOnTurn(int buzzerNumber, int position, AnimationQueue.AnimationQueueItem animationQueueItem) {
        presentationView.newBuzzerOnTurn(buzzerNumber, animationQueueItem);
        simpleOutputView.firstPress(buzzerNumber, position);
    }

    /**
     * updates the view if a buzzer gives a right answer
     *
     * @param buzzerNumber       number of the buzzer
     * @param animationQueueItem animation queue item
     */
    void rightAnswerGiven(int buzzerNumber, AnimationQueue.AnimationQueueItem animationQueueItem) {
        presentationView.rightAnswerGiven(buzzerNumber, animationQueueItem);
        simpleOutputView.right(buzzerNumber);
    }

    /**
     * updates the view if the next question should be displayed
     *
     * @param animationQueueItem animation queue item
     */
    public void nextQuestion(AnimationQueue.AnimationQueueItem animationQueueItem) {
        presentationView.resetToQuestionView(animationQueueItem);
        simpleOutputView.changeToDefaultState();
    }

    /**
     * plays the fade out animation
     *
     * @param animationQueueItem animation queue item
     */
    void fadeOutAnimation(AnimationQueue.AnimationQueueItem animationQueueItem) {
        presentationView.fadeOut(animationQueueItem);
    }
}
