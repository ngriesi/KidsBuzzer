package programs.quiztime.main.control;

import presentationWindow.animations.AnimationQueue;
import programs.quiztime.control.control.SimpleOutputView;
import programs.quiztime.main.view.QuizTimeProgramPresentationView;

/**
 * updates the views of the program
 */
class ViewUpdater {

    /**
     * presentation view
     */
    private QuizTimeProgramPresentationView presentationView;

    /**
     * simple output preview
     */
    private SimpleOutputView simpleOutputView;

    /**
     * creates a new view updater with both views
     *
     * @param presentationView presentation view
     * @param quizTimeProgram reference to the program to access the simple output view
     */
    ViewUpdater(QuizTimeProgramPresentationView presentationView, QuizTimeProgram quizTimeProgram) {
        this.presentationView = presentationView;
        this.simpleOutputView = quizTimeProgram.getProgramController().getSimpleOutputView();
    }

    /**
     * update for a buzzer that gets pressed an is immediately on turn
     *  @param buzzerNumber number of the buzzer
     * @param buzzerPosition press position of the buzzer
     * @param animationQueueItem
     */
    void firstBuzzerPress(int buzzerNumber, int buzzerPosition, AnimationQueue.AnimationQueueItem animationQueueItem) {
        presentationView.firstBuzzerPress(buzzerNumber, buzzerPosition, animationQueueItem);
        simpleOutputView.firstPress(buzzerNumber, buzzerPosition);
    }

    /**
     * update for a buzzer that gets pressed an is not immediately on turn
     *  @param buzzerNumber number of the buzzer
     * @param buzzerPosition press position of the buzzer
     * @param animationQueueItem
     */
    void followBuzzerPress(int buzzerNumber, int buzzerPosition, AnimationQueue.AnimationQueueItem animationQueueItem) {
        presentationView.followBuzzerPress(buzzerNumber, buzzerPosition, animationQueueItem);
        simpleOutputView.followPress(buzzerNumber, buzzerPosition);
    }

    /**
     * updates the view if a buzzer gives a wrong answer
     *
     * @param buzzerNumber number of the buzzer
     * @param animationQueueItem animation queue item
     */
    void wrongAnswerGiven(int buzzerNumber, AnimationQueue.AnimationQueueItem animationQueueItem) {
        presentationView.wrongAnswerGiven(buzzerNumber,animationQueueItem);
        simpleOutputView.wrong(buzzerNumber);
    }

    /**
     * updates the view if a new buzzer is on turn
     *  @param buzzerNumber number of the buzzer
     * @param position position of the buzzer
     * @param animationQueueItem
     */
    void newBuzzerOnTurn(int buzzerNumber, int position, AnimationQueue.AnimationQueueItem animationQueueItem) {
        presentationView.newBuzzerOnTurn(buzzerNumber, animationQueueItem);
        simpleOutputView.firstPress(buzzerNumber, position);
    }

    /**
     * updates the view if a buzzer gives a right answer
     *
     * @param buzzerNumber number of the buzzer
     * @param animationQueueItem animation queue item
     */
    void rightAnswerGiven(int buzzerNumber, AnimationQueue.AnimationQueueItem animationQueueItem) {
        presentationView.rightAnswerGiven(buzzerNumber, animationQueueItem);
        simpleOutputView.right(buzzerNumber);
    }

    /**
     * updates the view if the next question should be displayed
     *
     * @param questionNumber number of the question displayed
     * @param animationQueueItem animation queue item
     */
    void nextQuestion(int questionNumber, AnimationQueue.AnimationQueueItem animationQueueItem) {
        simpleOutputView.setQuestion(questionNumber);
        simpleOutputView.hideTitle();
        presentationView.changeQuestionNumber(questionNumber);
        presentationView.resetToQuestionView(animationQueueItem);
        simpleOutputView.resetToQuestionView();
    }

    /**
     * plays the intro animation
     *
     * @param animationQueueItem animation queue item
     */
    void introAnimation(AnimationQueue.AnimationQueueItem animationQueueItem) {
        presentationView.introAnimation(animationQueueItem);
    }

    /**
     * plays the fade out animation
     *
     * @param animationQueueItem animation queue item
     */
    void fadeOutAnimation(AnimationQueue.AnimationQueueItem animationQueueItem) {
        simpleOutputView.changeToDefaultState();
        presentationView.fadeOut(animationQueueItem);
    }
}
