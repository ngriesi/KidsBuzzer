package programs.quizOverlay.main.view.virtualBuzzers;

import org.joml.Vector2f;
import presentationWindow.animations.Animation;
import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.engine.Window;
import presentationWindow.renderItems.*;
import presentationWindow.window.LinearAnimator;
import programs.quizOverlay.main.control.QuizOverlayProgram;
import presentationWindow.animations.AnimationQueue;
import savedataHandler.SaveDataHandler;

import java.awt.*;

import static java.awt.Font.PLAIN;

public class VirtualBuzzer {



    private QuadItem colorQuad;

    private ImageItem icon;

    private TextItem number;

    private int index;

    private float unpressedTransparency = 0.5f;

    private int buzzerCount;

    private LinearAnimator linearAnimator;

    private int changeAnimationDuration = 60;

    private VirtualBuzzerStateHandler virtualBuzzerStateHandler;

    public VirtualBuzzer(MainItem background, int index, QuizOverlayProgram program, int buzzerCount) {
        this.buzzerCount = buzzerCount;
        this.index = index;
        this.linearAnimator = program.getRenderer().getLinearAnimator();
        virtualBuzzerStateHandler = new VirtualBuzzerStateHandler(this);
        colorQuad = new QuadItem();
        background.addItem(colorQuad);

        number = new TextItem((index + 1) + "");
        number.changeFont(new Font(program.getProgramModel().getSaveFile().getBuzzerFont(), program.getProgramModel().getSaveFile().isBuzzerTextBold()?Font.BOLD:PLAIN, 200));
        number.setColorScheme(new ColorScheme(new Color(program.getProgramModel().getSaveFile().getBuzzerTextColor())));
        number.setOpacity(0);
        colorQuad.addItem(number);

        icon = new ImageItem(program.getProgramModel().getIcon(index + 1));
        colorQuad.addItem(icon);

        reset();
    }

    public int getChangeAnimationDuration() {
        return changeAnimationDuration;
    }

    int getIndex() {
        return index;
    }

    public Animation moveAndScale(float x, float y, float width, float height, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        linearAnimator.scaleSize(new Vector2f(width, height), colorQuad, duration, animationQueueItem);
        linearAnimator.moveTo(new Vector2f(x, y), number, duration, animationQueueItem);
        linearAnimator.scaleSize(new Vector2f((number.getAspectRatio() * height)/Window.WINDOW_ASPECT_RATIO,height), number ,duration, animationQueueItem);
        linearAnimator.moveTo(new Vector2f(x, y), icon, duration, animationQueueItem);
        linearAnimator.scaleSize(new Vector2f((icon.getAspectRatio() * height)/Window.WINDOW_ASPECT_RATIO,height), icon ,duration, animationQueueItem);
        return linearAnimator.moveTo(new Vector2f(x, y), colorQuad, duration, animationQueueItem);
    }

    public void setPositionAndSize(float x, float y, float width, float height) {
        colorQuad.setPosition(x, y);
        colorQuad.setSize(width, height);
        icon.setPosition(x, y);
        icon.setSize((icon.getAspectRatio() * height)/Window.WINDOW_ASPECT_RATIO,height);
        number.setPosition(x, y);
        number.setSize((number.getAspectRatio() * height)/Window.WINDOW_ASPECT_RATIO,height);
    }

    public Animation fadeInIcon(int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return linearAnimator.fadeIn(icon, duration, animationQueueItem);
    }

    public Animation fadeInNumber(int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return linearAnimator.fadeIn(number, duration, animationQueueItem);
    }

    public Animation fadeInQuad(int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return linearAnimator.fadeIn(colorQuad, duration, animationQueueItem);
    }

    public Animation fadeOutIcon(int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return linearAnimator.fadeOut(icon, duration, animationQueueItem);
    }

    public Animation fadeOutNumber(int duration, AnimationQueue.AnimationQueueItem animationQueueItem) { return linearAnimator.fadeOut(number, duration, animationQueueItem); }

    public Animation fadeOutQuad(int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return linearAnimator.fadeOut(colorQuad, duration, animationQueueItem);
    }

    public Animation fadeColor(Color newColor, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return linearAnimator.colorFade(newColor, colorQuad, duration, animationQueueItem);
    }

    public void hide(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.INVISIBLE_DEFAULT, animationQueueItem);
    }

    public void moveToCenterAndScaleUp(AnimationQueue.AnimationQueueItem animationQueueItem) {

        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.RIGHT, animationQueueItem);
    }

    public void moveToStartPositionAndInitialScale(AnimationQueue.AnimationQueueItem animationQueueItem) {

        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.VISIBLE_DEFAULT, animationQueueItem);
    }

    public void scaleUpBuzzer(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.ON_TURN, animationQueueItem);
    }

    public void pressedColorAndIconChange(int position, AnimationQueue.AnimationQueueItem animationQueueItem) {
        number.changeText(position + "");
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.PRESSED_NOT_ON_TURN, animationQueueItem);
    }

    public void pressedSizeIncrease(int position, AnimationQueue.AnimationQueueItem animationQueueItem) {
        number.changeText(position + "");
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.ON_TURN, animationQueueItem);
    }

    public void wrongAnswerGiven(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.WRONG, animationQueueItem);
    }

    public ImageItem getIcon() {
        return icon;
    }

    public void updateFont(Font font, Color color) {
        number.changeFont(font);
        number.setColorScheme(new ColorScheme(color));
    }

    public void reset() {

        colorQuad.setOpacity(0);
        Color disabled = new Color(SaveDataHandler.BUZZER_COLORS_PRESSED[index]);
        disabled.setAlpha(unpressedTransparency);
        colorQuad.setColorScheme(new ColorScheme(disabled));
        icon.setOpacity(0);

        setPositionAndSize((1f + index * 2f) / (buzzerCount * 2f),1.15f,1f / buzzerCount, 0.3f);
    }

    public QuadItem getColorQuad() {
        return colorQuad;
    }

    public TextItem getNumber() {
        return number;
    }

    public void setBuzzerCount(int buzzerCount) {
        this.buzzerCount = buzzerCount;
        virtualBuzzerStateHandler.setBuzzerCount(buzzerCount);
    }
}
