package programs.quiztime.control.control;

import savedataHandler.SaveDataHandler;

import java.awt.*;

/**
 * class draws a simple preview buzzer to a graphics context
 */
public class SimpleVirtualBuzzer {

    /**
     * states the buzzer can be in
     */
    enum State {
        NORMAL, PRESSED_FIRST, PRESSED_FOLLOW, DEACTIVATED, RIGHT, INVISIBLE
    }

    /**
     * state the buzzer is in
     */
    private State state;

    /**
     * index of the buzzer
     */
    private int index;

    /**
     * position of the buzzer
     */
    private int position;

    /**
     * creates a new virtual buzzer with the passed index and in normal state
     *
     * @param index index of the buzzer
     */
    SimpleVirtualBuzzer(int index) {
        this.index = index;

        state = State.NORMAL;
    }

    /**
     * sets the press position of the buzzer
     *
     * @param position press position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * sets the state of the buzzer
     *
     * @param state new state of the buzzer
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * draws the buzzer according to its state
     *
     * @param g graphic context of the image
     */
    void drawBuzzer(Graphics g) {
        switch (state) {
            case NORMAL:
                drawNormal(g);
                break;
            case PRESSED_FIRST:
                drawPressedFirst(g);
                break;
            case PRESSED_FOLLOW:
                drawPressedFollow(g);
                break;
            case DEACTIVATED:
                drawDeactivated(g);
                break;
            case RIGHT:
                drawRight(g);
                break;

        }
    }

    /**
     * draws the buzzer in the right state
     *
     * @param g graphic context of the image
     */
    private void drawRight(Graphics g) {
        g.setColor(SaveDataHandler.BUZZER_COLORS_PRESSED[index]);
        g.fillRect(SimpleOutputView.WIDTH / 2 - SimpleOutputView.WIDTH / 4, SimpleOutputView.HEIGHT / 20 * 13 - SimpleOutputView.HEIGHT / 4, SimpleOutputView.WIDTH / 2, SimpleOutputView.HEIGHT / 2);
    }

    /**
     * draws the buzzer in its deactivated state
     *
     * @param g graphic context of the image
     */
    private void drawDeactivated(Graphics g) {
        g.setColor(SaveDataHandler.BUZZER_COLORS_DISABLED[index]);
        g.fillRect(SimpleOutputView.WIDTH / SaveDataHandler.BUZZER_COUNT * index, SimpleOutputView.HEIGHT / 3 * 2, SimpleOutputView.WIDTH / SaveDataHandler.BUZZER_COUNT, SimpleOutputView.HEIGHT / 3);
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 40));
        g.drawString("" + position, SimpleOutputView.WIDTH / (SaveDataHandler.BUZZER_COUNT * 2) * (1 + index * 2) - SimpleOutputView.WIDTH / (SaveDataHandler.BUZZER_COUNT * 10), (int) (SimpleOutputView.HEIGHT / 3 * 2.7f));
    }

    /**
     * draws the buzzer in its pressed follow state state
     *
     * @param g graphic context of the image
     */
    private void drawPressedFollow(Graphics g) {
        g.setColor(SaveDataHandler.BUZZER_COLORS_PRESSED[index]);
        g.fillRect(SimpleOutputView.WIDTH / SaveDataHandler.BUZZER_COUNT * index, SimpleOutputView.HEIGHT / 3 * 2, SimpleOutputView.WIDTH / SaveDataHandler.BUZZER_COUNT, SimpleOutputView.HEIGHT / 3);
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 40));
        g.drawString("" + position, SimpleOutputView.WIDTH / (SaveDataHandler.BUZZER_COUNT * 2) * (1 + index * 2) - SimpleOutputView.WIDTH / (SaveDataHandler.BUZZER_COUNT * 10), (int) (SimpleOutputView.HEIGHT / 3 * 2.7f));
    }

    /**
     * draws the buzzer in its first press state
     *
     * @param g graphic context of the image
     */
    private void drawPressedFirst(Graphics g) {
        g.setColor(SaveDataHandler.BUZZER_COLORS_PRESSED[index]);
        g.fillRect(SimpleOutputView.WIDTH / SaveDataHandler.BUZZER_COUNT * index, SimpleOutputView.HEIGHT / 10 * 6, SimpleOutputView.WIDTH / SaveDataHandler.BUZZER_COUNT, SimpleOutputView.HEIGHT / 10 * 4);
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 50));
        g.drawString("" + position, SimpleOutputView.WIDTH / (SaveDataHandler.BUZZER_COUNT * 2) * (1 + index * 2) - SimpleOutputView.WIDTH / (SaveDataHandler.BUZZER_COUNT * 8), (int) (SimpleOutputView.HEIGHT / 10 * 8.8f));
    }

    /**
     * draws the buzzer in its normal state
     *
     * @param g graphic context of the image
     */
    private void drawNormal(Graphics g) {
        Color color = new Color(SaveDataHandler.BUZZER_COLORS_PRESSED[index].getRed(), SaveDataHandler.BUZZER_COLORS_PRESSED[index].getGreen(), SaveDataHandler.BUZZER_COLORS_PRESSED[index].getBlue(), 125);
        g.setColor(color);
        g.fillRect(SimpleOutputView.WIDTH / SaveDataHandler.BUZZER_COUNT * index, SimpleOutputView.HEIGHT / 3 * 2, SimpleOutputView.WIDTH / SaveDataHandler.BUZZER_COUNT, SimpleOutputView.HEIGHT / 3);
    }
}
