package assets.virtualBuzzers;

import org.joml.Vector2f;
import presentationWindow.animations.Animation;
import presentationWindow.animations.AnimationQueue;
import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.engine.Window;
import presentationWindow.items.Texture;
import presentationWindow.renderItems.ImageItem;
import presentationWindow.renderItems.PresentationViewRenderItem;
import presentationWindow.renderItems.QuadItem;
import presentationWindow.renderItems.TextItem;
import presentationWindow.window.LinearAnimator;
import presentationWindow.window.OpenGlRenderer;
import savedataHandler.SaveDataHandler;

import java.awt.*;

/**
 * The <code>VirtualBuzzer</code> class represents a buzzer virtually in the
 * output view
 */
@SuppressWarnings("UnusedReturnValue")
public class VirtualBuzzer {

    /**
     * background quad of the buzzers with the color of the buzzer
     */
    private QuadItem colorQuad;

    /**
     * <code>ImageItem</code> displaying the icon of the team
     */
    private ImageItem icon;

    /**
     * <code>TextItem</code> displaying the number of the buzzer (at which position it was pressed)
     */
    private TextItem number;

    /**
     * Index of the buzzer
     */
    private int index;

    /**
     * stores the current buzzer count
     */
    private int buzzerCount;

    /**
     * linear animator used to animate the items (retrieved from <code>OpenGLRenderer</code>)
     */
    private LinearAnimator linearAnimator;

    /**
     * state handler for this virtual buzzer
     */
    private VirtualBuzzerStateHandler virtualBuzzerStateHandler;

    /**
     * creates the view elements of the virtual buzzer
     *
     * @param background  background the buzzer items get added to
     * @param index       index of the buzzer
     * @param buzzerFont  font of the buzzers
     * @param textColor   color of the number
     * @param texture     icon of the buzzer
     * @param buzzerCount number of buzzers currently used in the program
     * @param renderer    renderer of the buzzer
     */
    public VirtualBuzzer(PresentationViewRenderItem background, int index, Font buzzerFont, Color textColor, Texture texture, int buzzerCount, OpenGlRenderer renderer) {
        this.buzzerCount = buzzerCount;
        this.index = index;
        this.linearAnimator = renderer.getLinearAnimator();
        virtualBuzzerStateHandler = new VirtualBuzzerStateHandler(this);
        colorQuad = new QuadItem();
        background.addItem(colorQuad);

        number = new TextItem((index + 1) + "");
        number.changeFont(buzzerFont);
        number.setColorScheme(new ColorScheme(textColor));
        number.setOpacity(0);
        colorQuad.addItem(number);

        icon = new ImageItem(texture);
        colorQuad.addItem(icon);

        reset();
    }

    /**
     * @return returns the duration of all animations related to the buzzer
     */
    public int getChangeAnimationDuration() {
        return 60;
    }

    /**
     * @return returns the index of the buzzer
     */
    int getIndex() {
        return index;
    }

    /**
     * method animates the bound of the buzzer to the given coordinates an size
     *
     * @param x                  new x position of the buzzer
     * @param y                  new y position of the buzzer
     * @param width              new width of the buzzer
     * @param height             new height of the buzzer
     * @param duration           duration of the animation
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     * @return returns the move animation of the color quad
     */
    public Animation moveAndScale(float x, float y, float width, float height, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        linearAnimator.scaleSize(new Vector2f(width, height), colorQuad, duration, animationQueueItem);
        linearAnimator.moveTo(new Vector2f(x, y), number, duration, animationQueueItem);
        linearAnimator.scaleSize(new Vector2f((number.getAspectRatio() * height) / Window.WINDOW_ASPECT_RATIO, height), number, duration, animationQueueItem);
        linearAnimator.moveTo(new Vector2f(x, y), icon, duration, animationQueueItem);
        linearAnimator.scaleSize(new Vector2f((icon.getAspectRatio() * height) / Window.WINDOW_ASPECT_RATIO, height), icon, duration, animationQueueItem);
        return linearAnimator.moveTo(new Vector2f(x, y), colorQuad, duration, animationQueueItem);
    }

    /**
     * sets the bounds of the buzzer
     *
     * @param x      new x position of the buzzer
     * @param y      new y position of the buzzer
     * @param width  new width of the buzzer
     * @param height new height of the buzzer
     */
    public void setPositionAndSize(float x, float y, float width, float height) {
        colorQuad.setPosition(x, y);
        colorQuad.setSize(width, height);
        icon.setPosition(x, y);
        icon.setSize((icon.getAspectRatio() * height) / Window.WINDOW_ASPECT_RATIO, height);
        number.setPosition(x, y);
        number.setSize((number.getAspectRatio() * height) / Window.WINDOW_ASPECT_RATIO, height);
    }

    /**
     * fades in the icon of the buzzer
     *
     * @param duration           duration of the animation
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     * @return returns the fade in animation
     */
    public Animation fadeInIcon(int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return linearAnimator.fadeIn(icon, duration, animationQueueItem);
    }

    /**
     * fades in the color quad of the buzzer
     *
     * @param duration           duration of the animation
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     * @return returns the fade in animation
     */
    public Animation fadeInQuad(int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return linearAnimator.fadeIn(colorQuad, duration, animationQueueItem);
    }

    /**
     * fades out the icon of the buzzer
     *
     * @param duration           duration of the animation
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     * @return returns the fade out animation
     */
    public Animation fadeOutIcon(int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return linearAnimator.fadeOut(icon, duration, animationQueueItem);
    }

    /**
     * fades out the number of the buzzer
     *
     * @param duration           duration of the animation
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     * @return returns the fade out animation
     */
    public Animation fadeOutNumber(int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return linearAnimator.fadeOut(number, duration, animationQueueItem);
    }

    /**
     * fades out the color quad of the buzzer
     *
     * @param duration           duration of the animation
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     * @return returns the fade out animation
     */
    public Animation fadeOutQuad(int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return linearAnimator.fadeOut(colorQuad, duration, animationQueueItem);
    }

    /**
     * fades the color of the color quad to <code>newColor</code>
     *
     * @param newColor           new color of the color quad
     * @param duration           duration of the animation
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     * @return returns the color fade animation
     */
    public Animation fadeColor(Color newColor, int duration, AnimationQueue.AnimationQueueItem animationQueueItem) {
        return linearAnimator.colorFade(newColor, colorQuad, duration, animationQueueItem);
    }

    /**
     * changes the state of the buzzer to <code>INVISIBLE_DEFAULT</code>
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    public void hide(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.INVISIBLE_DEFAULT, animationQueueItem);
    }

    /**
     * changes the state of the buzzer to <code>RIGHT</code>
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    public void moveToCenterAndScaleUp(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.RIGHT, animationQueueItem);
    }

    /**
     * changes the state of the buzzer to <code>VISIBLE_DEFAULT</code>
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    public void moveToStartPositionAndInitialScale(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.VISIBLE_DEFAULT, animationQueueItem);
    }

    /**
     * changes the state of the buzzer to <code>ON_TURN</code>
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    public void scaleUpBuzzer(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.ON_TURN, animationQueueItem);
    }

    /**
     * changes the state of the buzzer to <code>PRESSED_NOT_ON_TURN</code> and sets the
     * press position
     *
     * @param position           press position of the buzzer
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    public void pressedColorAndIconChange(int position, AnimationQueue.AnimationQueueItem animationQueueItem) {
        number.changeText(position + "");
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.PRESSED_NOT_ON_TURN, animationQueueItem);
    }

    /**
     * changes the state of the buzzer to <code>ON_TURN</code> and sets the press position
     *
     * @param position           press position of the buzzer
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    public void pressedSizeIncrease(int position, AnimationQueue.AnimationQueueItem animationQueueItem) {
        number.changeText(position + "");
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.ON_TURN, animationQueueItem);
    }

    /**
     * changes the state of the buzzer to <code>WRONG</code>
     *
     * @param animationQueueItem <code>AnimationQueueItem</code> that is used to que this action
     */
    public void wrongAnswerGiven(AnimationQueue.AnimationQueueItem animationQueueItem) {
        virtualBuzzerStateHandler.changeState(VirtualBuzzerStateHandler.BuzzerState.WRONG, animationQueueItem);
    }

    /**
     * @return returns the icon of the buzzer
     */
    public ImageItem getIcon() {
        return icon;
    }

    /**
     * updates the font of the buzzer (of the number)
     *
     * @param font  new font
     * @param color new color of the text
     */
    public void updateFont(Font font, Color color) {
        number.changeFont(font);
        number.setColorScheme(new ColorScheme(color));
    }

    /**
     * reset the buzzer components to their initial state
     */
    public void reset() {

        colorQuad.setOpacity(0);
        Color disabled = new Color(SaveDataHandler.BUZZER_COLORS_PRESSED[index]);
        float unpressedTransparency = 0.5f;
        disabled.setAlpha(unpressedTransparency);
        colorQuad.setColorScheme(new ColorScheme(disabled));
        icon.setOpacity(0);

        setPositionAndSize((1f + index * 2f) / (buzzerCount * 2f), 1.15f, 1f / buzzerCount, 0.3f);
    }

    /**
     * @return returns the color quad of the buzzer
     */
    public QuadItem getColorQuad() {
        return colorQuad;
    }

    /**
     * @return returns the text item (number) of the buzzer
     */
    public TextItem getNumber() {
        return number;
    }

    /**
     * updates the buzzer count
     *
     * @param buzzerCount new buzzer count
     */
    public void setBuzzerCount(int buzzerCount) {
        this.buzzerCount = buzzerCount;
        virtualBuzzerStateHandler.setBuzzerCount(buzzerCount);
    }
}
