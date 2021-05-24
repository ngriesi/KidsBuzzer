package programs.quiztime.control.control;

import programs.quizPrograms.control.control.SimpleVirtualBuzzer;

import java.awt.*;

/**
 * class used to draw a simple preview of the output screen of the quiz time program
 */
public class SimpleOutputView extends programs.quizPrograms.control.control.SimpleOutputView {

    /**
     * flag indicates that the view is currently at the title view
     */
    private boolean title;

    /**
     * the current question number
     */
    private int question;

    /**
     * create an object of the simple output view containing a buffered image which is the preview
     *
     * @param controlController controller of the view containing the preview, used for repainting
     */
    public SimpleOutputView(QuizTimeProgramControlController controlController) {
        super(controlController);
        question = 0;
        title = true;
    }

    /**
     * sets the question number
     *
     * @param question number of the question
     */
    public void setQuestion(int question) {
        this.question = question;
    }

    /**
     * hides the title of the preview
     */
    public void hideTitle() {
        title = false;
    }


    /**
     * draws the simple preview buzzers to the image
     *
     * @param g graphic context of the image
     */
    @Override
    protected void drawSimpleVirtualBuzzers(Graphics g) {
        if (!title) {
            for (SimpleVirtualBuzzer simpleVirtualBuzzer : simpleVirtualBuzzers) {
                simpleVirtualBuzzer.drawBuzzer(g);
            }
        }
    }

    /**
     * draws the text to the buffered image
     *
     * @param g graphic context of the image
     */
    @Override
    @SuppressWarnings("SpellCheckingInspection")
    protected void drawText(Graphics g) {
        if (right) {
            g.drawString("Richtig", SimpleOutputView.WIDTH / 3, SimpleOutputView.HEIGHT / 4);
        } else if (title) {
            g.drawString("Quiztime", (int) (SimpleOutputView.WIDTH / 3f), (int) (SimpleOutputView.HEIGHT / 1.8f));
        } else {
            g.drawString("Frage " + question, (int) (SimpleOutputView.WIDTH / 2.8f), SimpleOutputView.HEIGHT / 3);
        }
    }

    /**
     * changes the simple view to the state it has when the output is hidden
     */
    @Override
    public void changeToInvisibleState() {
        title = true;
        super.changeToInvisibleState();
    }
}
