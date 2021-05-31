package programs.quizPrograms.main.view;

import assets.virtualBuzzers.VirtualBuzzer;
import presentationWindow.animations.AnimationQueue;
import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.engine.Window;
import presentationWindow.items.Texture;
import presentationWindow.renderItems.PresentationViewRenderItem;
import presentationWindow.renderItems.TextItem;
import programs.abstractProgram.ProgramPresentationView;
import programs.quizPrograms.data.QuizModel;
import programs.quizPrograms.main.control.QuizProgram;
import savedataHandler.SaveDataHandler;
import utils.save.SaveFile;

import java.awt.*;

import static programs.quizPrograms.data.QuizModel.BUZZER_FONT;
import static programs.quizPrograms.data.QuizModel.MAIN_FONT;

public abstract class QuizPresentationView<Q extends QuizProgram> extends ProgramPresentationView<Q> {

    /**
     * virtual buzzers
     */
    protected VirtualBuzzer[] virtualBuzzers;

    /**
     * right text
     */
    protected TextItem rightText;

    /**
     * duration of the change animation
     */
    protected int changeAnimationDuration = 60;

    /**
     * creates a new presentation view
     *
     * @param program sets the program
     */
    public QuizPresentationView(Q program) {
        super(program);
    }

    /**
     * sets up the view
     *
     * @param mainItem main item of the presentation scene
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void setupView(PresentationViewRenderItem mainItem) {

        SaveFile saveFile = getProgram().getProgramModel().getSaveFile();

        rightText = new TextItem("Richtig");
        rightText.changeFont(saveFile.getFontData(MAIN_FONT).getFont());
        rightText.setPosition(0.5f, 1f / 5f);
        rightText.setSize((rightText.getAspectRatio() * 0.3f) / Window.WINDOW_ASPECT_RATIO, 0.3f);
        rightText.setOpacity(0);
        rightText.setVisible(false);
        rightText.setColorScheme(new ColorScheme(new Color(saveFile.getFontData(MAIN_FONT).getColor())));
        mainItem.addItem(rightText);

        virtualBuzzers = new VirtualBuzzer[SaveDataHandler.MAX_BUZZER_COUNT];

        Font font = saveFile.getFontData(BUZZER_FONT).getFont();
        Color textColor = new Color(saveFile.getFontData(BUZZER_FONT).getColor());

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            Texture icon = ((QuizModel) getProgram().getProgramModel()).getIcon(i + 1);
            virtualBuzzers[i] = new VirtualBuzzer(mainItem, i, font, textColor, icon, SaveDataHandler.BUZZER_COUNT, getProgram().getRenderer());
        }
    }


    /**
     * shows the first buzzer press for this question
     *
     * @param buzzerNumber       number of the buzzer pressed
     * @param position           position of the buzzer
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    public void firstBuzzerPress(int buzzerNumber, int position, AnimationQueue.AnimationQueueItem animationQueueItem) {
        getProgram().getRenderer().addActionToOpenGlThread(() -> virtualBuzzers[buzzerNumber - 1].pressedSizeIncrease(position, animationQueueItem));
    }

    /**
     * shows all followup buzzer presses with their position
     *
     * @param buzzerNumber       number of the buzzer
     * @param position           position of the buzzer
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    public void followBuzzerPress(int buzzerNumber, int position, AnimationQueue.AnimationQueueItem animationQueueItem) {
        getProgram().getRenderer().addActionToOpenGlThread(() -> virtualBuzzers[buzzerNumber - 1].pressedColorAndIconChange(position, animationQueueItem));
    }

    /**
     * shows that a new buzzer is on turn
     *
     * @param newBuzzer          number of the new buzzer
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    public void newBuzzerOnTurn(int newBuzzer, AnimationQueue.AnimationQueueItem animationQueueItem) {
        getProgram().getRenderer().addActionToOpenGlThread(() -> virtualBuzzers[newBuzzer - 1].scaleUpBuzzer(animationQueueItem));
    }

    /**
     * changes the look of the current buzzer to the disabled look
     *
     * @param oldBuzzer          number of the buzzer who gave the wrong answer
     * @param animationQueueItem animation ques item
     */
    public void wrongAnswerGiven(int oldBuzzer, AnimationQueue.AnimationQueueItem animationQueueItem) {
        getProgram().getRenderer().addActionToOpenGlThread(() -> virtualBuzzers[oldBuzzer - 1].wrongAnswerGiven(animationQueueItem));
    }

    /**
     * makes an item update its texture with the texture from the model
     *
     * @param index index of the icon that needs to update
     */
    public void updateIcon(int index) {
        virtualBuzzers[index].getIcon().setTexture(((QuizModel) getProgram().getProgramModel()).getIcon(index + 1));
    }

    /**
     * method needs to be called to be called when the buzzer font has to be updated
     */
    public void updateBuzzerFont() {

        SaveFile saveFile = getProgram().getProgramModel().getSaveFile();

        Font font = saveFile.getFontData(BUZZER_FONT).getFont();
        presentationWindow.assets.Color color = new Color(saveFile.getFontData(BUZZER_FONT).getColor());
        for (VirtualBuzzer virtualBuzzer : virtualBuzzers) {
            virtualBuzzer.updateFont(font, color);
        }
    }

    /**
     * updates the buzzers if the buzzer count changes
     */
    public void updateBuzzerCount() {
        for (VirtualBuzzer virtualBuzzer : virtualBuzzers) {
            virtualBuzzer.setBuzzerCount(SaveDataHandler.BUZZER_COUNT);
        }
    }

    public abstract void rightAnswerGiven(int buzzerNumber, AnimationQueue.AnimationQueueItem animationQueueItem);

    public abstract void resetToQuestionView(AnimationQueue.AnimationQueueItem animationQueueItem);

    public abstract void fadeOut(AnimationQueue.AnimationQueueItem animationQueueItem);

    public abstract void updateMainFont();
}
