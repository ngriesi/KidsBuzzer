package programs.quizOverlay.main.control;

import programs.quizOverlay.control.control.SimpleOutputView;
import presentationWindow.animations.AnimationQueue;
import programs.quizOverlay.main.view.QuizOverlayPresentationView;

/**
 * updates the views of the program
 */
class ViewUpdater {

    /**
     * presentation view
     */
    private QuizOverlayPresentationView presentationView;

    /**
     * simple output preview
     */
    private SimpleOutputView simpleOutputView;

    /**
     * creates a new view updater with both views
     *
     * @param presentationView presentation view
     * @param quizTimeProgram  reference to the program to access the simple output view
     */
    ViewUpdater(QuizOverlayPresentationView presentationView, QuizOverlayProgram quizTimeProgram) {
        this.presentationView = presentationView;
        this.simpleOutputView = quizTimeProgram.getProgramController().getSimpleOutputView();
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
    void nextQuestion(AnimationQueue.AnimationQueueItem animationQueueItem) {
        presentationView.resetToQuestionView(animationQueueItem);
        simpleOutputView.resetToQuestionView();
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
