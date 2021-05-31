package programs.quizOverlay.main.view;

import assets.virtualBuzzers.VirtualBuzzer;
import presentationWindow.animations.AnimationQueue;
import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.engine.Window;
import programs.quizOverlay.main.control.QuizOverlayProgram;
import programs.quizPrograms.main.view.QuizPresentationView;
import savedataHandler.SaveDataHandler;

import java.awt.*;

import static programs.quizPrograms.data.QuizModel.MAIN_FONT;


/**
 * presentation view of the quiz time app
 */
public class QuizOverlayPresentationView extends QuizPresentationView<QuizOverlayProgram> {

    /**
     * creates a new presentation view
     *
     * @param program sets the program
     */
    public QuizOverlayPresentationView(QuizOverlayProgram program) {
        super(program);
    }

    /**
     * Method performs the animation if a right answer is given.
     *
     * @param buzzerNumber       number of the buzzer that was right
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    public void rightAnswerGiven(int buzzerNumber, AnimationQueue.AnimationQueueItem animationQueueItem) {
        getProgram().getRenderer().addActionToOpenGlThread(() -> {
            for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
                if (i != buzzerNumber - 1) {

                    virtualBuzzers[i].hide(animationQueueItem);

                } else {
                    virtualBuzzers[i].moveToCenterAndScaleUp(animationQueueItem);
                    rightText.setPosition(0.5f, 1f / 5f);
                    rightText.setVisible(true);
                    getProgram().getRenderer().getLinearAnimator().fadeIn(rightText, changeAnimationDuration);
                }
            }
        });
    }


    /**
     * resets the view for the next question
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> used to queue the animation
     */
    public void resetToQuestionView(AnimationQueue.AnimationQueueItem animationQueueItem) {

        getProgram().getRenderer().addActionToOpenGlThread(() -> resetToQuestionViewAfterTitleFade(animationQueueItem));
    }

    /**
     * Methods resets the view to the state where all buzzers are unpressed
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    private void resetToQuestionViewAfterTitleFade(AnimationQueue.AnimationQueueItem animationQueueItem) {
        int buzzerCount = SaveDataHandler.MAX_BUZZER_COUNT;

        getProgram().getRenderer().getLinearAnimator().fadeOut(rightText, changeAnimationDuration, animationQueueItem).addOnFinishedAction(() -> rightText.setVisible(false));
        getProgram().getRenderer().getLinearAnimator().moveYTo(0.1f, rightText, changeAnimationDuration, animationQueueItem);

        for (int i = 0; i < buzzerCount; i++) {
            virtualBuzzers[i].moveToStartPositionAndInitialScale(animationQueueItem);
            virtualBuzzers[i].fadeInIcon(changeAnimationDuration, animationQueueItem);
            virtualBuzzers[i].fadeInQuad(changeAnimationDuration, animationQueueItem);
        }
    }

    /**
     * method needs to be called to be called when the main font has to be updated
     */
    public void updateMainFont() {
        Font font = getProgram().getProgramModel().getSaveFile().getFontData(MAIN_FONT).getFont();
        rightText.changeFont(font);
        Color color = new Color(getProgram().getProgramModel().getSaveFile().getFontData(MAIN_FONT).getColor());
        rightText.setColorScheme(new ColorScheme(color));
    }

    /**
     * method fades out the view
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    public void fadeOut(AnimationQueue.AnimationQueueItem animationQueueItem) {
        for (VirtualBuzzer virtualBuzzer : virtualBuzzers) {
            virtualBuzzer.hide(animationQueueItem);
        }
        getProgram().getRenderer().getLinearAnimator().fadeOut(rightText, changeAnimationDuration, animationQueueItem);
        animationQueueItem.addOnFinishedAction(this::resetView);
    }

    /**
     * resets the view to its default state
     */
    private void resetView() {
        rightText.setOpacity(0);
        rightText.setPosition(0.5f, 1f / 5f);
        rightText.setSize((rightText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
        for (VirtualBuzzer virtualBuzzer : virtualBuzzers) {
            virtualBuzzer.reset();
        }
    }
}
