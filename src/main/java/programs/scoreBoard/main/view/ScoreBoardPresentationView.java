package programs.scoreBoard.main.view;

import presentationWindow.animations.AnimationQueue;
import presentationWindow.renderItems.PresentationViewRenderItem;
import programs.abstractProgram.ProgramPresentationView;
import programs.scoreBoard.main.ScoreBoardProgram;
import programs.scoreBoard.main.view.animations.BuzzerPressedAnimation;
import programs.scoreBoard.main.view.animations.EnteredAnimation;
import programs.scoreBoard.main.view.animations.ExitedAnimation;
import programs.scoreBoard.main.view.items.ViewItems;

/**
 * Presentation view of the program
 */
public class ScoreBoardPresentationView extends ProgramPresentationView<ScoreBoardProgram> {

    /**
     * Class containing the items  of the view
     */
    private ViewItems viewItems;

    /**
     * animation, played when the score board enters the screen
     */
    private EnteredAnimation enteredAnimation;

    /**
     * animation, played when the score board exits the screen
     */
    private ExitedAnimation exitedAnimation;

    /**
     * animation, played when a buzzer gets pressed
     */
    private BuzzerPressedAnimation buzzerPressedAnimation;

    /**
     * creates a new presentation view
     *
     * @param program sets the program
     */
    public ScoreBoardPresentationView(ScoreBoardProgram program) {
        super(program);

        viewItems = new ViewItems(getProgram().getProgramModel());

    }

    /**
     * sets up the view by creating the animations and calling setup view in <code>ViewItems</code>
     *
     * @param mainItem main item of the presentation scene
     */
    @Override
    public void setupView(PresentationViewRenderItem mainItem) {
        enteredAnimation = new EnteredAnimation(viewItems, getProgram().getRenderer().getExponentialAnimator());
        exitedAnimation = new ExitedAnimation(viewItems, getProgram().getRenderer().getExponentialAnimator());
        buzzerPressedAnimation = new BuzzerPressedAnimation(viewItems, getProgram().getRenderer().getLinearAnimator(), getProgram().getRenderer().getExponentialAnimator());

        viewItems.setupView(mainItem);
    }

    /**
     * Plays the entered animation
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> to queue the animation
     */
    public void enterAnimation(AnimationQueue.AnimationQueueItem animationQueueItem) {
        enteredAnimation.executeAnimation(animationQueueItem);
    }

    /**
     * Plays the exit animation
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> to queue the animation
     */
    public void exitAnimation(AnimationQueue.AnimationQueueItem animationQueueItem) {
        exitedAnimation.executeAnimation(animationQueueItem);
    }

    /**
     * Plays the buzzer press animation
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> to queue the animation
     * @param buzzer             buzzer of which the animation gets played
     */
    public void buzzerAnimation(AnimationQueue.AnimationQueueItem animationQueueItem, int buzzer) {
        buzzerPressedAnimation.executeAnimation(animationQueueItem, buzzer);
    }

    /**
     * sets the score of one of the buzzers
     *
     * @param buzzer buzzer which score gets set
     * @param score  new score of that buzzer / team
     */
    public void setBuzzerScore(int buzzer, int score) {
        viewItems.getLabels()[buzzer - 1].changeText(getProgram().getProgramModel().getSaveFile().getTeamNames()[buzzer - 1] + ": " + score);
    }

    /**
     * updates the font in the view
     */
    public void updateFont() {
        viewItems.updateFont();
    }

    /**
     * updates the text in the view
     */
    public void updateText() {
        viewItems.updateText();
    }

    /**
     * updates the icon of one of the buzzers
     *
     * @param buzzerNumber buzzer which icon gets updated
     */
    public void updateIcon(int buzzerNumber) {
        viewItems.updateIcon(buzzerNumber);
    }
}
