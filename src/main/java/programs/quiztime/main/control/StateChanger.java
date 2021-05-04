package programs.quiztime.main.control;

import controlWindow.ControlModel;
import controlWindow.MainController;
import presentationWindow.animations.AnimationQueue;
import presentationWindow.engine.Action;
import programs.quiztime.data.QuizTimeProgramModel;

/**
 * changes the state of the quiz time program
 */
class StateChanger {

    /**
     * model of the program to play
     */
    private QuizTimeProgramModel programModel;

    /**
     * control model to update the general buzzer view
     */
    private MainController mainController;

    /**
     * handles the states of the buzzers
     */
    private BuzzerStateHandler buzzerStateHandler;

    /**
     * updates the presentation and the simple output view
     */
    private ViewUpdater viewUpdater;

    /**
     * queues the animations of the program
     */
    private AnimationQueue animationQueue;

    /**
     * stores and updates the general state of the program
     */
    private GeneralState generalState;

    /**
     * creates a new state changer
     *
     * @param program program this is the state changer of
     * @param generalState general state of the program
     */
    StateChanger(QuizTimeProgram program, GeneralState generalState) {

        this.mainController = program.getMainController();
        this.generalState = generalState;

        this.programModel = program.getProgramModel();

        animationQueue = new AnimationQueue();

        viewUpdater = new ViewUpdater(program.getProgramPresentationView(), program);

        buzzerStateHandler = new BuzzerStateHandler(viewUpdater);
    }

    /**
     * sets the control model
     *
     * @param mainController control model
     */
    void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * method called when a buzzer press should invoke a state change
     *
     * @param buzzerNumber number of the buzzer that was pressed
     * @param animationQueueItem animation queue item
     */
    void buzzerPressed(int buzzerNumber, AnimationQueue.AnimationQueueItem animationQueueItem) {
        programModel.playBuzzerSound();
        buzzerStateHandler.press(buzzerNumber, animationQueueItem);
    }

    /**
     * method called when a buzzer has given a wrong answer and the programs state has to be changed accordingly
     */
    void wrongAnswerGiven() {
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        programModel.playWrongSound();
        animationQueueItem.setAnimationAction(() -> buzzerStateHandler.wrong(animationQueueItem));
        animationQueue.addAnimation(animationQueueItem);
    }

    /**
     * method called when a buzzer has given a right answer and the programs state has to be changed accordingly
     */
    void rightAnswerGiven() {
        programModel.fadeOutQuestionSound();
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        programModel.playRightSound();
        animationQueueItem.setAnimationAction(() -> buzzerStateHandler.right(animationQueueItem));
        animationQueue.addAnimation(animationQueueItem);
    }

    /**
     * method called and used when the intro screen should fade in
     */
    void changeToIntro() {
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        programModel.playIntroSound();
        animationQueueItem.setAnimationAction(() -> viewUpdater.introAnimation(animationQueueItem));
        animationQueueItem.addOnFinishedAction(() -> generalState.changeToIntroState());
        animationQueue.addAnimation(animationQueueItem);
    }

    /**
     * method called and used when the presentation view should fade out
     */
    void fadeToInvisible() {
        programModel.fadeOutIntroSound();
        programModel.fadeOutQuestionSound();
        programModel.fadeOutRightSound();
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        animationQueueItem.setAnimationAction(() -> viewUpdater.fadeOutAnimation(animationQueueItem));
        generalState.changeToInvisibleState(animationQueueItem, this);
        animationQueue.addAnimation(animationQueueItem);
    }

    /**
     * action performed, when a new question should be displayed
     *
     * @param animationQueueItem animation queue item that gets passed because it is needed for the
     *                           check whether this method should be called in the QuizTimeProgram class
     * @param action action containing the action of updating the control view of the program which is
     *               written inside the QuizTimeProgram class
     */
    void nextQuestion(AnimationQueue.AnimationQueueItem animationQueueItem, Action action) {
        programModel.fadeOutIntroSound();
        programModel.playQuestionSound();
        animationQueueItem.setAnimationAction(() -> {

            action.execute();

            viewUpdater.nextQuestion(generalState.getQuestionNumber(), animationQueueItem);

        });
        animationQueueItem.addOnFinishedAction(this::reset);
        animationQueue.addAnimation(animationQueueItem);
    }

    /**
     * resets the states of the program
     */
    void reset() {

        buzzerStateHandler.reset();
        mainController.getControlModel().getBuzzerControl().resetBuzzers();
    }
}

