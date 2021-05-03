package programs.quizOverlay.control.control;

import savedataHandler.SaveDataHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * class used to draw a simple preview of the output screen of the quiz time program
 */
public class SimpleOutputView {

    /**
     * defines the resolution of the simple preview
     */
    static int HEIGHT = 180, WIDTH = 320;

    /**
     * simple virtual buzzer
     */
    private SimpleVirtualBuzzer[] simpleVirtualBuzzers;

    /**
     * simple view of the output screen
     */
    private BufferedImage simpleView;

    /**
     * flag indicates if the currently displayed text says right
     */
    private boolean right;

    /**
     * reference for repainting
     */
    private QuizOverlayProgramController controller;

    /**
     * create an object of the simple output view containing a buffered image which is the preview
     *
     * @param controlController controller of the view containing the preview, used for repainting
     */
    public SimpleOutputView(QuizOverlayProgramController controlController) {
        this.controller = controlController;
        simpleView = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        right = false;
        simpleVirtualBuzzers = new SimpleVirtualBuzzer[SaveDataHandler.MAX_BUZZER_COUNT];

        for (int i = 0; i < SaveDataHandler.MAX_BUZZER_COUNT; i++) {
            simpleVirtualBuzzers[i] = new SimpleVirtualBuzzer(i);
        }
    }

    /**
     * performs the action of the first buzzer press in the simple preview
     *
     * @param buzzer buzzer that was pressed
     * @param position position of the buzzer that was pressed
     */
    public void firstPress(int buzzer, int position) {
        simpleVirtualBuzzers[buzzer - 1].setPosition(position);
        simpleVirtualBuzzers[buzzer - 1].setState(SimpleVirtualBuzzer.State.PRESSED_FIRST);
        drawOutputView();
    }

    /**
     * performs the action of a buzzer pressed after one is already pressed
     *
     * @param buzzer buzzer that was pressed
     * @param position position of the buzzer pressed
     */
    public void followPress(int buzzer, int position) {
        simpleVirtualBuzzers[buzzer - 1].setPosition(position);
        simpleVirtualBuzzers[buzzer - 1].setState(SimpleVirtualBuzzer.State.PRESSED_FOLLOW);
        drawOutputView();
    }

    /**
     * changes the look of a simple preview buzzer to its wrong view
     *
     * @param buzzer number of the buzzer
     */
    public void wrong(int buzzer) {
        simpleVirtualBuzzers[buzzer - 1].setState(SimpleVirtualBuzzer.State.DEACTIVATED);
        drawOutputView();
    }

    /**
     * hides the simple preview of a buzzer
     *
     * @param buzzer number of the buzzer
     */
    private void hide(int buzzer) {
        simpleVirtualBuzzers[buzzer - 1].setState(SimpleVirtualBuzzer.State.INVISIBLE);
        drawOutputView();
    }

    /**
     * draws one buzzer centered in the middle and hides the others and sets the right to true
     *
     * @param buzzer number of the buzzer that gets drawn in the center
     */
    public void right(int buzzer) {
        simpleVirtualBuzzers[buzzer - 1].setState(SimpleVirtualBuzzer.State.RIGHT);
        for (int i = 0; i < SaveDataHandler.BUZZER_COUNT; i++) {
            if (i != buzzer - 1) {
                hide(i+1);
            }
        }
        right = true;
        drawOutputView();
    }

    /**
     * rests the simple buzzer view to its question view state
     */
    public void resetToQuestionView() {
        for (SimpleVirtualBuzzer simpleVirtualBuzzer : simpleVirtualBuzzers) {
            simpleVirtualBuzzer.setState(SimpleVirtualBuzzer.State.NORMAL);
            simpleVirtualBuzzer.setPosition(1);
        }
        right = false;
        drawOutputView();
    }

    /**
     * draws the current state to the buffered image
     */
    private void drawOutputView() {

        Graphics g = simpleView.createGraphics();

        drawBackground(g);

        handleTextDrawing(g);

        drawSimpleVirtualBuzzers(g);

        controller.getProgramView().repaint();
    }

    /**
     * draws the simple preview buzzers to the image
     *
     * @param g graphic context of the image
     */
    private void drawSimpleVirtualBuzzers(Graphics g) {

        for (SimpleVirtualBuzzer simpleVirtualBuzzer : simpleVirtualBuzzers) {
            simpleVirtualBuzzer.drawBuzzer(g);
        }

    }

    /**
     * draws the text to the buffered image
     *
     * @param g graphic context of the image
     */
    private void drawText(Graphics g) {
        if (right) {
            g.drawString("Richtig", SimpleOutputView.WIDTH/3, SimpleOutputView.HEIGHT/4);
        }
    }

    /**
     * prepares and performs the text drawing
     *
     * @param g graphic context of the image
     */
    private void handleTextDrawing(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial",Font.PLAIN,30));
        drawText(g);
    }

    /**
     * draws the background to the image
     *
     * @param g graphic context of the image
     */
    private void drawBackground(Graphics g) {
        g.setColor(new Color(125, 125, 125));
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    /**
     * draws the image to a graphic context from a swing component
     *
     * @param graphics graphics context of the component
     * @param width width of the component
     * @param height height of the component
     */
    public void drawImage(Graphics graphics, int width, int height) {
        graphics.drawImage(simpleView, 0,0,width,height, null);
    }

    /**
     * @return returns true if the right flag is true
     */
    public boolean isRight() {
        return right;
    }
}
