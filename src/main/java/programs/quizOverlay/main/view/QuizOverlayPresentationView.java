package programs.quizOverlay.main.view;

import presentationWindow.animations.AnimationQueue;
import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.engine.Window;
import presentationWindow.renderItems.ImageItem;
import presentationWindow.renderItems.MainItem;
import presentationWindow.renderItems.TextItem;
import programs.abstractProgram.ProgramPresentationView;
import programs.quizOverlay.main.control.QuizOverlayProgram;
import programs.quizOverlay.main.view.virtualBuzzers.VirtualBuzzer;
import savedataHandler.SaveDataHandler;

import java.awt.*;

import static java.awt.Font.PLAIN;


/**
 * presentation view of the quiz time app
 */
public class QuizOverlayPresentationView extends ProgramPresentationView<QuizOverlayProgram> {

    /**
     * virtual buzzers
     */
    private VirtualBuzzer[] virtualBuzzers;

    /**
     * right text
     */
    private TextItem rightText;

    /**
     * title text
     */
    private TextItem title;

    /**
     * duration of the change animation
     */
    private int changeAnimationDuration = 60;



    /**
     * creates a new presentation view
     *
     * @param program sets the program
     */
    public QuizOverlayPresentationView(QuizOverlayProgram program) {
        super(program);
    }

    /**
     * sets up the view
     *
     * @param mainItem main item of the presentation scene
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void setupView(MainItem mainItem) {

        rightText = new TextItem("Richtig");
        rightText.changeFont(new Font(getProgram().getProgramModel().getSaveFile().getMainFont(), getProgram().getProgramModel().getSaveFile().isMainTextBold()?Font.BOLD:PLAIN, 200));
        rightText.setPosition(0.5f, 1f / 5f);
        rightText.setSize((rightText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
        rightText.setOpacity(0);
        rightText.setVisible(false);
        rightText.setColorScheme(new ColorScheme(new Color(getProgram().getProgramModel().getSaveFile().getMainTextColor())));
        mainItem.addItem(rightText);

        title = new TextItem("Quiz");
        title.changeFont(new Font(getProgram().getProgramModel().getSaveFile().getMainFont(), getProgram().getProgramModel().getSaveFile().isMainTextBold()?Font.BOLD:PLAIN, 200));
        title.setPosition(0.5f, 0.5f);
        title.setSize((rightText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
        title.setOpacity(0);
        title.setVisible(true);
        title.setColorScheme(new ColorScheme(new Color(getProgram().getProgramModel().getSaveFile().getMainTextColor())));
        mainItem.addItem(title);

        virtualBuzzers = new VirtualBuzzer[SaveDataHandler.MAX_BUZZER_COUNT];

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            virtualBuzzers[i] = new VirtualBuzzer(mainItem, i, getProgram(), SaveDataHandler.BUZZER_COUNT);
        }
    }


    public void rightAnswerGiven(int buzzerNumber, AnimationQueue.AnimationQueueItem animationQueueItem) {
        getProgram().getRenderer().addActionToOpenGlThread(() -> {
            for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
                if (i != buzzerNumber - 1) {

                    virtualBuzzers[i].hide(animationQueueItem);

                } else {

                    virtualBuzzers[i].moveToCenterAndScaleUp(animationQueueItem);

                    rightText.setPosition(0.5f, 1f / 5f);

                    rightText.setVisible(true);
                    getProgram().getRenderer().getLinearAnimator().fadeIn(rightText, changeAnimationDuration );
                }
            }
        });
    }

    /**
     * shows the first buzzer press for this question
     *
     * @param buzzerNumber number of the buzzer pressed
     * @param animationQueueItem
     */
    public void firstBuzzerPress(int buzzerNumber, int position, AnimationQueue.AnimationQueueItem animationQueueItem) {
        getProgram().getRenderer().addActionToOpenGlThread(() -> virtualBuzzers[buzzerNumber - 1].pressedSizeIncrease(position, animationQueueItem));
    }

    /**
     * shows all followup buzzer presses with their position
     *  @param buzzerNumber number of the buzzer
     * @param position position of the buzzer
     * @param animationQueueItem
     */
    public void followBuzzerPress(int buzzerNumber, int position, AnimationQueue.AnimationQueueItem animationQueueItem) {
        getProgram().getRenderer().addActionToOpenGlThread(() -> virtualBuzzers[buzzerNumber - 1].pressedColorAndIconChange(position, animationQueueItem));
    }

    /**
     * shows that a new buzzer is on turn
     *
     * @param newBuzzer number of the new buzzer
     * @param animationQueueItem
     */
    public void newBuzzerOnTurn(int newBuzzer, AnimationQueue.AnimationQueueItem animationQueueItem) {
        getProgram().getRenderer().addActionToOpenGlThread(() -> virtualBuzzers[newBuzzer - 1].scaleUpBuzzer(animationQueueItem));
    }

    /**
     * changes the look of the current buzzer to the disabled look
     *
     * @param oldBuzzer number of the buzzer who gave the wrong answer
     * @param animationQueueItem animation ques item
     */
    public void wrongAnswerGiven(int oldBuzzer, AnimationQueue.AnimationQueueItem animationQueueItem) {
        getProgram().getRenderer().addActionToOpenGlThread(() -> virtualBuzzers[oldBuzzer - 1].wrongAnswerGiven(animationQueueItem));
    }

    /**
     * resets the view for the next question
     */
    public void resetToQuestionView(AnimationQueue.AnimationQueueItem animationQueueItem) {
        if (title.getOpacity() > 0) {
            getProgram().getRenderer().addActionToOpenGlThread(() -> getProgram().getRenderer().getLinearAnimator().fadeOut(title, changeAnimationDuration).addOnFinishedAction(() -> resetToQuestionViewAfterTitleFade(animationQueueItem)));
        } else {
            getProgram().getRenderer().addActionToOpenGlThread(() -> resetToQuestionViewAfterTitleFade(animationQueueItem));
        }

    }

    private void resetToQuestionViewAfterTitleFade(AnimationQueue.AnimationQueueItem animationQueueItem) {
        int buzzerCount = SaveDataHandler.MAX_BUZZER_COUNT;

        getProgram().getRenderer().getLinearAnimator().fadeOut(rightText, changeAnimationDuration, animationQueueItem).addOnFinishedAction(() -> {
            rightText.setVisible(false);
        });
        getProgram().getRenderer().getLinearAnimator().moveYTo(0.1f, rightText, changeAnimationDuration, animationQueueItem);

        for (int i = 0; i < buzzerCount; i++) {
            virtualBuzzers[i].moveToStartPositionAndInitialScale(animationQueueItem);
        }
    }

    public void updateIcon(int index) {
        virtualBuzzers[index].getIcon().setTexture(getProgram().getProgramModel().getIcon(index + 1));
    }

    public void updateMainFont() {

        Font font = new Font(getProgram().getProgramModel().getSaveFile().getMainFont(), getProgram().getProgramModel().getSaveFile().isMainTextBold() ? Font.BOLD : PLAIN, 200);
        rightText.changeFont(font);
        title.changeFont(font);
        Color color = new Color(getProgram().getProgramModel().getSaveFile().getMainTextColor()[0],getProgram().getProgramModel().getSaveFile().getMainTextColor()[1],getProgram().getProgramModel().getSaveFile().getMainTextColor()[2],getProgram().getProgramModel().getSaveFile().getMainTextColor()[3]);
        rightText.setColorScheme(new ColorScheme(color));
        title.setColorScheme(new ColorScheme(color));

    }

    public void updateBuzzerFont() {
        Font font = new Font(getProgram().getProgramModel().getSaveFile().getBuzzerFont(), getProgram().getProgramModel().getSaveFile().isBuzzerTextBold() ? Font.BOLD : PLAIN, 200);
        Color color = new Color(getProgram().getProgramModel().getSaveFile().getBuzzerTextColor()[0],getProgram().getProgramModel().getSaveFile().getBuzzerTextColor()[1],getProgram().getProgramModel().getSaveFile().getBuzzerTextColor()[2],getProgram().getProgramModel().getSaveFile().getBuzzerTextColor()[3]);
        for (VirtualBuzzer virtualBuzzer : virtualBuzzers) {
            virtualBuzzer.updateFont(font, color);
        }
    }

    public void introAnimation(AnimationQueue.AnimationQueueItem animationQueueItem) {
        title.setVisible(true);
        getProgram().getRenderer().getLinearAnimator().fadeIn(title, changeAnimationDuration, animationQueueItem);
    }

    public void fadeOut(AnimationQueue.AnimationQueueItem animationQueueItem) {
        for(VirtualBuzzer virtualBuzzer : virtualBuzzers) {
            virtualBuzzer.hide(animationQueueItem);
        }
        getProgram().getRenderer().getLinearAnimator().fadeOut(title,changeAnimationDuration,animationQueueItem);
        getProgram().getRenderer().getLinearAnimator().fadeOut(rightText,changeAnimationDuration,animationQueueItem).addOnFinishedAction(this::resetView);
    }

    private void resetView() {
        title.setOpacity(0);
        title.setPosition(0.5f, 0.5f);
        title.setSize((rightText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
        rightText.setOpacity(0);
        rightText.setPosition(0.5f, 1f / 5f);
        rightText.setSize((rightText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
        for (VirtualBuzzer virtualBuzzer : virtualBuzzers) {
            virtualBuzzer.reset();
        }
    }

    public void updateBuzzerCount() {
        for (VirtualBuzzer virtualBuzzer : virtualBuzzers) {
            virtualBuzzer.setBuzzerCount(SaveDataHandler.BUZZER_COUNT);
        }
    }
}
