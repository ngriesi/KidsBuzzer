package programs.quiztime.main.control;

/**
 * stores the state of a buzzer
 */
public class BuzzerState {

    /**
     * number of the buzzer
     */
    private int buzzerNumber;

    /**
     * flag indicating if the buzzer is pressed
     */
    private boolean pressed = false;

    /**
     * flag indicating if the buzzer is in the wrong state
     */
    private boolean wrong = false;

    /**
     * press position of the buzzer
     */
    private int position = 0;

    /**
     * creates a new buzzer with a specific number
     *
     * @param buzzerNumber number of the buzzer
     */
    BuzzerState(int buzzerNumber) {
        this.buzzerNumber = buzzerNumber;
    }

    /**
     * resets the buzzer state to the default values
     */
    public void reset() {
        wrong = false;
        pressed = false;
        position = 0;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getBuzzerNumber() {
        return buzzerNumber;
    }

    public void setBuzzerNumber(int buzzerNumber) {
        this.buzzerNumber = buzzerNumber;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public boolean isWrong() {
        return wrong;
    }

    public void setWrong(boolean wrong) {
        this.wrong = wrong;
    }
}
