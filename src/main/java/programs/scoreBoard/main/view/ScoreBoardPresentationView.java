package programs.scoreBoard.main.view;

import presentationWindow.animations.AnimationQueue;
import presentationWindow.renderItems.MainItem;
import programs.abstractProgram.ProgramPresentationView;
import programs.scoreBoard.main.ScoreBoardProgram;
import programs.scoreBoard.main.view.animations.BuzzerPressedAnimation;
import programs.scoreBoard.main.view.animations.EnteredAnimation;
import programs.scoreBoard.main.view.animations.ExitedAnimation;
import programs.scoreBoard.main.view.items.ViewItems;

public class ScoreBoardPresentationView extends ProgramPresentationView<ScoreBoardProgram> {

    private ViewItems viewItems;

    private EnteredAnimation enteredAnimation;

    private ExitedAnimation exitedAnimation;

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

    @Override
    public void setupView(MainItem mainItem) {
        enteredAnimation = new EnteredAnimation(viewItems, getProgram().getRenderer().getLinearAnimator(),getProgram().getRenderer().getExponentialAnimator());
        exitedAnimation = new ExitedAnimation(viewItems, getProgram().getRenderer().getLinearAnimator(), getProgram().getRenderer().getExponentialAnimator());
        buzzerPressedAnimation = new BuzzerPressedAnimation(viewItems, getProgram().getRenderer().getLinearAnimator(), getProgram().getRenderer().getExponentialAnimator());

        viewItems.setupView(mainItem);
    }

    public void enterAnimation(AnimationQueue.AnimationQueueItem animationQueueItem) {
        enteredAnimation.executeAnimation(animationQueueItem);
    }

    public void exitAnimation(AnimationQueue.AnimationQueueItem animationQueueItem) {
        exitedAnimation.executeAnimation(animationQueueItem);
    }

    public void buzzerAnimation(AnimationQueue.AnimationQueueItem animationQueueItem, int buzzer) {
        buzzerPressedAnimation.executeAnimation(animationQueueItem, buzzer);
    }

    public void setBuzzerScore(int buzzer, int score) {
        viewItems.getLabels()[buzzer - 1].changeText(getProgram().getProgramModel().getSaveFile().getTeamNames()[buzzer - 1] + ": " + score);
    }

    public void updateFont() {
        viewItems.updateFont();
    }

    public void updateText() {
        viewItems.updateText();
    }

    public void updateIcon(int buzzerNumber) {
        viewItems.updateIcon(buzzerNumber);
    }
}
