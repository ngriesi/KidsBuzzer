package programs.quiztime.main.view;

import presentationWindow.animations.AnimationQueue;
import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.engine.Window;
import presentationWindow.renderItems.ImageItem;
import presentationWindow.renderItems.MainItem;
import presentationWindow.renderItems.TextItem;
import programs.abstractProgram.ProgramPresentationView;
import programs.quiztime.main.control.QuizTimeProgram;
import programs.quiztime.main.view.virtualBuzzers.VirtualBuzzer;
import savedataHandler.SaveDataHandler;

import java.awt.*;

import static java.awt.Font.PLAIN;


/**
 * presentation view of the quiz time app
 */
public class QuizTimeProgramPresentationView extends ProgramPresentationView<QuizTimeProgram> {

    /**
     * virtual buzzers
     */
    private VirtualBuzzer[] virtualBuzzers;

    /**
     * question text
     */
    private TextItem questionText;

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
     * background of the presentation window
     */
    private ImageItem background;


    /**
     * creates a new presentation view
     *
     * @param program sets the program
     */
    public QuizTimeProgramPresentationView(QuizTimeProgram program) {
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

        background = new ImageItem(getProgram().getProgramModel().getBackgroundTexture());
        background.setColorScheme(new ColorScheme(Color.WHITE));
        background.setPosition(0.5f, 0.5f);
        background.setSize(1, 1);
        background.setOpacity(0);
        mainItem.addItem(background);

        questionText = new TextItem("Frage 1");
        questionText.changeFont(new Font(getProgram().getProgramModel().getSaveFile().getMainFont(), getProgram().getProgramModel().getSaveFile().isMainTextBold()?Font.BOLD:PLAIN, 200));
        questionText.setPosition(0.5f, 1f / 3f);
        questionText.setSize((questionText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
        questionText.setVisible(false);
        questionText.setOpacity(0);
        questionText.setColorScheme(new ColorScheme(new Color(getProgram().getProgramModel().getSaveFile().getMainTextColor())));
        background.addItem(questionText);

        rightText = new TextItem("Richtig");
        rightText.changeFont(new Font(getProgram().getProgramModel().getSaveFile().getMainFont(), getProgram().getProgramModel().getSaveFile().isMainTextBold()?Font.BOLD:PLAIN, 200));
        rightText.setPosition(0.5f, 1f / 5f);
        rightText.setSize((rightText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
        rightText.setOpacity(0);
        rightText.setVisible(false);
        rightText.setColorScheme(new ColorScheme(new Color(getProgram().getProgramModel().getSaveFile().getMainTextColor())));
        background.addItem(rightText);

        title = new TextItem("Quiztime");
        title.changeFont(new Font(getProgram().getProgramModel().getSaveFile().getMainFont(), getProgram().getProgramModel().getSaveFile().isMainTextBold()?Font.BOLD:PLAIN, 200));
        title.setPosition(0.5f, 0.5f);
        title.setSize((rightText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
        title.setOpacity(0);
        title.setVisible(true);
        title.setColorScheme(new ColorScheme(new Color(getProgram().getProgramModel().getSaveFile().getMainTextColor())));
        background.addItem(title);

        virtualBuzzers = new VirtualBuzzer[SaveDataHandler.MAX_BUZZER_COUNT];

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            virtualBuzzers[i] = new VirtualBuzzer(background, i, getProgram(), SaveDataHandler.BUZZER_COUNT);
        }
    }

    public void changeQuestionNumber(int number) {

        getProgram().getRenderer().addActionToOpenGlThread(() -> {
            //noinspection SpellCheckingInspection
            questionText.changeText("Frage " + number);
            questionText.setPosition(0.5f, 1f / 3f);
            questionText.setSize((questionText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
        });
    }

    public void rightAnswerGiven(int buzzerNumber, AnimationQueue.AnimationQueueItem animationQueueItem) {
        getProgram().getRenderer().addActionToOpenGlThread(() -> {
            for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
                if (i != buzzerNumber - 1) {

                    virtualBuzzers[i].hide(animationQueueItem);

                } else {

                    virtualBuzzers[i].moveToCenterAndScaleUp(animationQueueItem);

                    rightText.setPosition(0.5f, 1f / 5f);
                    getProgram().getRenderer().getLinearAnimator().fadeOut(questionText, changeAnimationDuration / 2).addOnFinishedAction(() -> {
                        questionText.setVisible(false);
                        rightText.setVisible(true);
                        getProgram().getRenderer().getLinearAnimator().fadeIn(rightText, changeAnimationDuration / 2);
                    });
                    getProgram().getRenderer().getLinearAnimator().moveYTo(0.2f, questionText, changeAnimationDuration / 2);
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
            questionText.setPosition(0.5f, 1f / 3f);
            questionText.setVisible(true);
            getProgram().getRenderer().getLinearAnimator().fadeIn(questionText, changeAnimationDuration,animationQueueItem);
        });
        getProgram().getRenderer().getLinearAnimator().moveYTo(0.1f, rightText, changeAnimationDuration, animationQueueItem);

        for (int i = 0; i < buzzerCount; i++) {
            virtualBuzzers[i].moveToStartPositionAndInitialScale(animationQueueItem);
        }
    }

    public void updateBackground() {
        background.setTexture(getProgram().getProgramModel().getBackgroundTexture());
    }

    public void updateIcon(int index) {
        virtualBuzzers[index].getIcon().setTexture(getProgram().getProgramModel().getIcon(index + 1));
    }

    public void updateMainFont() {

        Font font = new Font(getProgram().getProgramModel().getSaveFile().getMainFont(), getProgram().getProgramModel().getSaveFile().isMainTextBold() ? Font.BOLD : PLAIN, 200);
        rightText.changeFont(font);
        questionText.changeFont(font);
        title.changeFont(font);
        Color color = new Color(getProgram().getProgramModel().getSaveFile().getMainTextColor()[0],getProgram().getProgramModel().getSaveFile().getMainTextColor()[1],getProgram().getProgramModel().getSaveFile().getMainTextColor()[2],getProgram().getProgramModel().getSaveFile().getMainTextColor()[3]);
        rightText.setColorScheme(new ColorScheme(color));
        questionText.setColorScheme(new ColorScheme(color));
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
        getProgram().getRenderer().addActionToOpenGlThread(() -> getProgram().getRenderer().getLinearAnimator().fadeIn(background, changeAnimationDuration, animationQueueItem).addOnFinishedAction(() -> {
            title.setVisible(true);
            getProgram().getRenderer().getLinearAnimator().fadeIn(title, changeAnimationDuration, animationQueueItem);
        }));
    }

    public void fadeOut(AnimationQueue.AnimationQueueItem animationQueueItem) {
        for(VirtualBuzzer virtualBuzzer : virtualBuzzers) {
            virtualBuzzer.hide(animationQueueItem);
        }
        getProgram().getRenderer().getLinearAnimator().fadeOut(title,changeAnimationDuration,animationQueueItem);
        getProgram().getRenderer().getLinearAnimator().fadeOut(rightText,changeAnimationDuration,animationQueueItem);
        getProgram().getRenderer().getLinearAnimator().fadeOut(questionText,changeAnimationDuration,animationQueueItem).addOnFinishedAction(() -> getProgram().getRenderer().getLinearAnimator().fadeOut(background, changeAnimationDuration,animationQueueItem).addOnFinishedAction(this::resetView));


    }

    private void resetView() {
        title.setOpacity(0);
        title.setPosition(0.5f, 0.5f);
        title.setSize((rightText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
        rightText.setOpacity(0);
        rightText.setPosition(0.5f, 1f / 5f);
        rightText.setSize((rightText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
        questionText.setOpacity(0);
        questionText.setPosition(0.5f, 1f / 3f);
        questionText.setSize((questionText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
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
