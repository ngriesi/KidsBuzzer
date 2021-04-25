package programs.quiztime.main.view.virtualBuzzers;

import org.joml.Vector2f;
import presentationWindow.animations.Animation;
import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.engine.Window;
import presentationWindow.renderItems.ChildItem;
import presentationWindow.renderItems.ImageItem;
import presentationWindow.renderItems.QuadItem;
import presentationWindow.renderItems.TextItem;
import presentationWindow.window.LinearAnimator;
import programs.quiztime.main.control.QuizTimeProgram;
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

    public VirtualBuzzer(ChildItem background, int index, QuizTimeProgram program, int buzzerCount) {
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

    public Animation moveAndScale(float x, float y, float width, float height, int duration) {
        linearAnimator.scaleSize(new Vector2f(width, height), colorQuad, duration);
        linearAnimator.moveTo(new Vector2f(x, y), number, duration);
        linearAnimator.scaleSize(new Vector2f((number.getAspectRatio() * height)/Window.WINDOW_ASPECT_RATIO,height), number ,duration);
        linearAnimator.moveTo(new Vector2f(x, y), icon, duration);
        linearAnimator.scaleSize(new Vector2f((icon.getAspectRatio() * height)/Window.WINDOW_ASPECT_RATIO,height), icon ,duration);
        return linearAnimator.moveTo(new Vector2f(x, y), colorQuad, duration);
    }

    public void setPositionAndSize(float x, float y, float width, float height) {
        colorQuad.setPosition(x, y);
        colorQuad.setSize(width, height);
        icon.setPosition(x, y);
        icon.setSize((icon.getAspectRatio() * height)/Window.WINDOW_ASPECT_RATIO,height);
        number.setPosition(x, y);
        number.setSize((number.getAspectRatio() * height)/Window.WINDOW_ASPECT_RATIO,height);
    }

    public Animation fadeInIcon(int duration) {
        return linearAnimator.fadeIn(icon, duration);
    }

    public Animation fadeInNumber(int duration) {
        return linearAnimator.fadeIn(number, duration);
    }

    public Animation fadeInQuad(int duration) {
        return linearAnimator.fadeIn(colorQuad, duration);
    }

    public Animation fadeOutIcon(int duration) {
        return linearAnimator.fadeOut(icon, duration);
    }

    public Animation fadeOutNumber(int duration) { return linearAnimator.fadeOut(number, duration); }

    public Animation fadeOutQuad(int duration) {
        return linearAnimator.fadeOut(colorQuad, duration);
    }

    public Animation fadeColor(Color newColor, int duration) {
        return linearAnimator.colorFade(newColor, colorQuad, duration);
    }

    public void hide() {
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.INVISIBLE_DEFAULT);
    }

    public void moveToCenterAndScaleUp() {

        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.RIGHT);
    }

    public void moveToStartPositionAndInitialScale() {

        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.VISIBLE_DEFAULT);
    }

    public void scaleUpBuzzer() {
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.ON_TURN);
    }

    public void pressedColorAndIconChange(int position) {
        number.changeText(position + "");
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.PRESSED_NOT_ON_TURN);
    }

    public void pressedSizeIncrease(int position) {
        number.changeText(position + "");
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.ON_TURN);
    }

    public void wrongAnswerGiven() {
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.WRONG);
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
