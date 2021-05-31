package programs.quiztime.main.view;

import assets.virtualBuzzers.VirtualBuzzer;
import presentationWindow.animations.AnimationQueue;
import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.engine.Window;
import presentationWindow.renderItems.ImageItem;
import presentationWindow.renderItems.PresentationViewRenderItem;
import presentationWindow.renderItems.TextItem;
import programs.quizPrograms.main.view.QuizPresentationView;
import programs.quiztime.main.control.QuizTimeProgram;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;

import java.awt.*;

import static programs.quizPrograms.data.QuizModel.MAIN_FONT;


/**
 * presentation view of the quiz time app
 */
public class QuizTimeProgramPresentationView extends QuizPresentationView<QuizTimeProgram> {

    /**
     * question text
     */
    private TextItem questionText;

    /**
     * title text
     */
    private TextItem title;

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
    @Override
    public void setupView(PresentationViewRenderItem mainItem) {

        background = new ImageItem(getProgram().getProgramModel().getBackgroundTexture());
        background.setColorScheme(new ColorScheme(Color.WHITE));
        background.setPosition(0.5f, 0.5f);
        background.setSize(1, 1);
        background.setOpacity(0);
        mainItem.addItem(background);

        super.setupView(background);

        questionText = new TextItem(Text.QUESTION + " 1");
        questionText.changeFont(getProgram().getProgramModel().getSaveFile().getFontData(MAIN_FONT).getFont());
        questionText.setPosition(0.5f, 1f / 3f);
        questionText.setSize((questionText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
        questionText.setVisible(false);
        questionText.setOpacity(0);
        questionText.setColorScheme(new ColorScheme(new Color(getProgram().getProgramModel().getSaveFile().getFontData(MAIN_FONT).getColor())));
        background.addItem(questionText);

        title = new TextItem(Text.QUIZTIME);
        title.changeFont(getProgram().getProgramModel().getSaveFile().getFontData(MAIN_FONT).getFont());
        title.setPosition(0.5f, 0.5f);
        title.setSize((rightText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
        title.setOpacity(0);
        title.setVisible(true);
        title.setColorScheme(new ColorScheme(new Color(getProgram().getProgramModel().getSaveFile().getFontData(MAIN_FONT).getColor())));
        background.addItem(title);
    }

    /**
     * updates the number of the question text item and updates its position and size
     *
     * @param number new question number
     */
    public void changeQuestionNumber(int number) {

        getProgram().getRenderer().addActionToOpenGlThread(() -> {
            //noinspection SpellCheckingInspection
            questionText.changeText("Frage " + number);
            questionText.setPosition(0.5f, 1f / 3f);
            questionText.setSize((questionText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
        });
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
     * Method fads out the title and resets the view to the next question
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> used to queue the animation
     */
    public void resetToQuestionView(AnimationQueue.AnimationQueueItem animationQueueItem) {
        if (title.getOpacity() > 0) {
            getProgram().getRenderer().addActionToOpenGlThread(() -> getProgram().getRenderer().getLinearAnimator().fadeOut(title, changeAnimationDuration).addOnFinishedAction(() -> resetToQuestionViewAfterTitleFade(animationQueueItem)));
        } else {
            getProgram().getRenderer().addActionToOpenGlThread(() -> resetToQuestionViewAfterTitleFade(animationQueueItem));
        }

    }

    /**
     * Methods resets the view to the state where all buzzers are unpressed
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    private void resetToQuestionViewAfterTitleFade(AnimationQueue.AnimationQueueItem animationQueueItem) {
        int buzzerCount = SaveDataHandler.MAX_BUZZER_COUNT;

        getProgram().getRenderer().getLinearAnimator().fadeOut(rightText, changeAnimationDuration, animationQueueItem).addOnFinishedAction(() -> {
            rightText.setVisible(false);
            questionText.setPosition(0.5f, 1f / 3f);
            questionText.setVisible(true);
            getProgram().getRenderer().getLinearAnimator().fadeIn(questionText, changeAnimationDuration, animationQueueItem);
        });
        getProgram().getRenderer().getLinearAnimator().moveYTo(0.1f, rightText, changeAnimationDuration, animationQueueItem);

        for (int i = 0; i < buzzerCount; i++) {
            virtualBuzzers[i].moveToStartPositionAndInitialScale(animationQueueItem);
        }
    }

    /**
     * makes the background update its texture form the program model
     */
    public void updateBackground() {
        background.setTexture(getProgram().getProgramModel().getBackgroundTexture());
    }

    /**
     * method needs to be called to be called when the main font has to be updated
     */
    public void updateMainFont() {

        Font font = getProgram().getProgramModel().getSaveFile().getFontData(MAIN_FONT).getFont();
        rightText.changeFont(font);
        questionText.changeFont(font);
        title.changeFont(font);
        Color color = new Color(getProgram().getProgramModel().getSaveFile().getFontData(MAIN_FONT).getColor());
        rightText.setColorScheme(new ColorScheme(color));
        questionText.setColorScheme(new ColorScheme(color));
        title.setColorScheme(new ColorScheme(color));

    }

    /**
     * Method performs the intro animation when the output view gets shown
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    public void introAnimation(AnimationQueue.AnimationQueueItem animationQueueItem) {
        getProgram().getRenderer().addActionToOpenGlThread(() -> getProgram().getRenderer().getLinearAnimator().fadeIn(background, changeAnimationDuration, animationQueueItem).addOnFinishedAction(() -> {
            title.setVisible(true);
            getProgram().getRenderer().getLinearAnimator().fadeIn(title, changeAnimationDuration, animationQueueItem);
        }));
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
        getProgram().getRenderer().getLinearAnimator().fadeOut(title, changeAnimationDuration, animationQueueItem);
        getProgram().getRenderer().getLinearAnimator().fadeOut(rightText, changeAnimationDuration, animationQueueItem);
        getProgram().getRenderer().getLinearAnimator().fadeOut(questionText, changeAnimationDuration, animationQueueItem).addOnFinishedAction(() -> getProgram().getRenderer().getLinearAnimator().fadeOut(background, changeAnimationDuration, animationQueueItem).addOnFinishedAction(this::resetView));


    }

    /**
     * resets the view to its default state
     */
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
}
