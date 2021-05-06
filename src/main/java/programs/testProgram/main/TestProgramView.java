package programs.testProgram.main;

import presentationWindow.assets.Color;
import presentationWindow.assets.ColorScheme;
import presentationWindow.engine.TimedAction;
import presentationWindow.renderItems.MainItem;
import presentationWindow.renderItems.QuadItem;
import presentationWindow.renderItems.TextItem;
import programs.abstractProgram.ProgramPresentationView;

/**
 * Class representing the presentation view of the test program
 */
public class TestProgramView extends ProgramPresentationView<TestProgram> {

    /**
     * quads showing the colors of the buzzers when they are pressed
     */
    private QuadItem red, green, black;

    /**
     * text items showing the numbers of the buzzers
     */
    private TextItem redText, greenText, blackText;

    /**
     * Constructor sets the reference to the main class of the program
     *
     * @param program main class of the program
     */
    TestProgramView(TestProgram program) {
        super(program);
    }


    /**
     * creates the output view of the test program
     *
     * @param mainItem main item of the presentation scene
     */
    @Override
    public void setupView(MainItem mainItem) {
        red = new QuadItem();
        red.setSize(1 / 3f, 1);
        red.setPosition(1 / 6f, 0.5f);
        red.setColorScheme(new ColorScheme(Color.WHITE));
        red.setEdgeSize(0.01f);
        mainItem.addItem(red);

        redText = new TextItem("1");
        redText.setSize(0.2f, 0.5f);
        redText.setPosition(0.166f, 0.5f);
        red.addItem(redText);

        green = new QuadItem();
        green.setSize(1f / 3f, 1);
        green.setPosition(0.5f, 0.5f);
        green.setColorScheme(new ColorScheme(Color.WHITE));
        green.setEdgeSize(0.01f);
        mainItem.addItem(green);

        greenText = new TextItem("2");
        greenText.setSize(0.2f, 0.5f);
        greenText.setPosition(0.5f, 0.5f);
        green.addItem(greenText);

        black = new QuadItem();
        black.setSize(1f / 3f, 1);
        black.setPosition(5f / 6f, 0.5f);
        black.setColorScheme(new ColorScheme(Color.WHITE));
        black.setEdgeSize(0.01f);
        mainItem.addItem(black);

        blackText = new TextItem("3");
        blackText.setSize(0.2f, 0.5f);
        blackText.setPosition(0.833f, 0.5f);
        black.addItem(blackText);


    }

    /**
     * shows a buzzer press on the output screen for one second
     *
     * @param buzzer pressed buzzer
     */
    void press(int buzzer) {
        switch (buzzer) {
            case 1:
                red.setColorScheme(new ColorScheme(Color.RED));
                redText.setColorScheme(new ColorScheme(Color.WHITE));
                getProgram().getRenderer().addTimedAction(new TimedAction(1, () -> {
                    red.setColorScheme(new ColorScheme(Color.WHITE));
                    redText.setColorScheme(new ColorScheme(Color.BLACK));
                    getProgram().getMainController().getControlModel().getBuzzerControl().unblockBuzzer(1);
                }));
                break;
            case 2:
                green.setColorScheme(new ColorScheme(Color.GREEN));
                greenText.setColorScheme(new ColorScheme(Color.WHITE));
                getProgram().getRenderer().addTimedAction(new TimedAction(1, () -> {
                    green.setColorScheme(new ColorScheme(Color.WHITE));
                    greenText.setColorScheme(new ColorScheme(Color.BLACK));
                    getProgram().getMainController().getControlModel().getBuzzerControl().unblockBuzzer(2);
                }));
                break;
            case 3:
                black.setColorScheme(new ColorScheme(Color.BLACK));
                blackText.setColorScheme(new ColorScheme(Color.WHITE));
                getProgram().getRenderer().addTimedAction(new TimedAction(1, () -> {
                    black.setColorScheme(new ColorScheme(Color.WHITE));
                    blackText.setColorScheme(new ColorScheme(Color.BLACK));
                    getProgram().getMainController().getControlModel().getBuzzerControl().unblockBuzzer(3);
                }));
                break;
        }
    }
}
