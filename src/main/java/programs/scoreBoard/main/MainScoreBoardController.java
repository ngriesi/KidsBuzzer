package programs.scoreBoard.main;


import midi.MidiSettingsRow;
import presentationWindow.animations.AnimationQueue;

import static programs.scoreBoard.data.ScoreBoardModel.*;

/**
 * main controller of the score board program
 */
public class MainScoreBoardController {

    /**
     * reference to the program class
     */
    private ScoreBoardProgram program;

    /**
     * animation queue this program uses
     */
    private AnimationQueue animationQueue;

    /**
     * flag showing that the output score board si visible
     */
    private boolean outputVisible;

    /**
     * creates the controller by setting the reference to the program
     * and creating the <code>AnimationQueue</code>
     *
     * @param program reference to the programs main class
     */
    MainScoreBoardController(ScoreBoardProgram program) {
        this.program = program;
        animationQueue = new AnimationQueue();
    }


    /**
     * Method hides plays the disappearing animation and hides the output view
     */
    public void hide() {
        outputVisible = false;
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        animationQueueItem.setAnimationAction(() -> {
            program.getProgramPresentationView().exitAnimation(animationQueueItem);
            performMidiAction(MIDI_HIDE);
            program.getProgramModel().fadeOutBuzzerSound();
        });
        animationQueueItem.addOnFinishedAction(() -> program.getMainController().hidePresentationWindow());
        animationQueue.addAnimation(animationQueueItem);
    }

    /**
     * Method shows the output view and plays the intro animation
     */
    public void show() {
        AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
        animationQueueItem.setAnimationAction(() -> {
            program.getMainController().showPresentationWindow();

            program.getProgramPresentationView().enterAnimation(animationQueueItem);
            performMidiAction(MIDI_SHOW);
        });
        animationQueueItem.addOnFinishedAction(() -> {
            program.getMainController().getControlModel().getBuzzerControl().resetBuzzers();
            outputVisible = true;
        });
        animationQueue.addAnimation(animationQueueItem);
    }

    /**
     * Method plays the animation for  buzzer press and increases the score by one
     *
     * @param buzzer number of the buzzer pressed (team scored)
     */
    void buzzerPressed(int buzzer) {
        if(outputVisible) {
            program.getProgramModel().getScores()[buzzer - 1]++;
            program.getProgramController().updateScores();
            program.getProgramModel().playBuzzerSound();
            performMidiAction(MIDI_POINT);
            AnimationQueue.AnimationQueueItem animationQueueItem = new AnimationQueue.AnimationQueueItem();
            animationQueueItem.setAnimationAction(() -> program.getProgramPresentationView().buzzerAnimation(animationQueueItem, buzzer));
            animationQueueItem.addOnFinishedAction(this::buzzerAnimationFinished);
            animationQueue.addAnimation(animationQueueItem);
        }

    }

    /**
     * performs the midi action for the scored action
     */
    private void performMidiAction(String action) {
        MidiSettingsRow.MidiSettingsRowData midiData = program.getProgramModel().getSaveFile().getMidiSettingsRowData(action);
        if (midiData.isActive()) {
            program.getMainController().getControlModel().getMidiHandler().sendMessageToPressExecutor(midiData.getButton().x, midiData.getButton().y);
        }
    }

    /**
     * Method is called at the end of the score increase animation and sets the
     * <code>blockAllBuzzers</code> in <code>BuzzerModel</code> to false
     */
    private void buzzerAnimationFinished() {
        program.getMainController().getControlModel().getBuzzerControl().setBlockAllBuzzer(false);
        program.getMainController().getControlModel().getBuzzerControl().resetBuzzers();
    }

    /**
     * sets the score of a team without playing the animation
     *
     * @param buzzer buzzer / team which score gets changed
     * @param score  new value of the score
     */
    public void setBuzzerScore(int buzzer, int score) {
        program.getProgramModel().getScores()[buzzer - 1] = score;
        program.getRenderer().addActionToOpenGlThread(() -> program.getProgramPresentationView().setBuzzerScore(buzzer, score));
    }

    /**
     * Updates the view if the number of buzzers has changed
     */
    public void updateBuzzerCount() {
        program.getProgramPresentationView().updateBuzzerCount();
        program.getProgramModel().fadeOutBuzzerSound();
    }
}
