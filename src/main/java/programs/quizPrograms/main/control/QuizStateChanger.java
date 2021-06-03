package programs.quizPrograms.main.control;

import controlWindow.MainController;
import presentationWindow.animations.AnimationQueue;
import presentationWindow.engine.Action;
import programs.quizPrograms.data.QuizModel;
import programs.quizPrograms.main.view.QuizPresentationView;

public abstract class QuizStateChanger {

    /**
     * model of the program to play
     */
    protected QuizModel programModel;

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
    protected QuizViewUpdater quizViewUpdater;

    /**
     * queues the animations of the program
     */
    protected AnimationQueue animationQueue;

    /**
     * stores and updates the general state of the program
     */
    protected QuizGeneralState generalState;

    /**
     * Midi handler of the quiz time program
     */
    protected MidiHandler midiHandler;

    /**
     * Program of this state changer
     */
    protected QuizProgram program;

    /**
     * creates a new state changer
     *
     * @param program      program this is the state changer of
     * @param generalState general state of the program
     */
    public QuizStateChanger(QuizProgram program, QuizGeneralState generalState) {

        this.mainController = program.getMainController();
        this.generalState = generalState;

        this.program = program;

        this.programModel = (QuizModel) program.getProgramModel();

        animationQueue = new AnimationQueue();

        quizViewUpdater = createViewUpdater(program);

        buzzerStateHandler = new BuzzerStateHandler(quizViewUpdater);
    }

    /**
     * creates a new view updater
     *
     * @param program program of the updater
     * @return returns the newly created view updater
     */
    protected QuizViewUpdater createViewUpdater(QuizProgram program) {
        return new QuizViewUpdater((QuizPresentationView) program.getProgramPresentationView(), program);
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
     * @param buzzerNumber       number of the buzzer that was pressed
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
        animationQueueItem.setAnimationAction(() -> {
            programModel.playWrongSound();
            midiHandler.performWrongMidiAction();
            buzzerStateHandler.wrong(animationQueueItem);
        });
        animationQueue.addAnimation(animationQueueItem);
    }

    /**
     * method called when a buzzer has given a right answer and the programs state has to be changed accordingly
     */
    void rightAnswerGiven() {

        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        animationQueueItem.setAnimationAction(() -> {
            programModel.fadeOutQuestionSound();
            programModel.playRightSound();
            midiHandler.performRightMidiAction();
            buzzerStateHandler.right(animationQueueItem);
        });
        animationQueue.addAnimation(animationQueueItem);
    }

    public abstract void nextQuestion(AnimationQueue.AnimationQueueItem animationQueueItem, Action action);

    /**
     * method called and used when the presentation view should fade out
     */
    public void fadeToInvisible() {

        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        animationQueueItem.setAnimationAction(() -> {
            programModel.fadeOutQuestionSound();
            programModel.fadeOutRightSound();
            quizViewUpdater.fadeOutAnimation(animationQueueItem);
        });
        animationQueueItem.addOnFinishedAction(() -> program.getMainController().hidePresentationWindow());
        generalState.changeToInvisibleState(animationQueueItem, this);
        animationQueue.addAnimation(animationQueueItem);
    }

    /**
     * resets the states of the program
     */
    public void reset() {

        buzzerStateHandler.reset();
        mainController.getControlModel().getBuzzerControl().resetBuzzers();
    }

    /**
     * Setter for the midi handler
     *
     * @param midiHandler new midi handler
     */
    void setMidiHandler(MidiHandler midiHandler) {
        this.midiHandler = midiHandler;
        buzzerStateHandler.setMidiHandler(midiHandler);
    }
}
