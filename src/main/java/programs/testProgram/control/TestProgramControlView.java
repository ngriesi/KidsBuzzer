package programs.testProgram.control;

import assets.standardAssets.MyButton;
import programs.abstractProgram.ProgramView;

import java.awt.*;

/**
 * Control view of the test program
 */
class TestProgramControlView extends ProgramView {

    /**
     * creates the view with its 3 buttons
     *
     * @param testProgramController controller of this view
     */
    TestProgramControlView(TestProgramController testProgramController) {
        super(testProgramController);

        MyButton settings = new MyButton("Settings");
        settings.addActionListener(testProgramController);
        settings.setActionCommand("settings");
        this.add(settings);

        MyButton show = new MyButton("show");
        show.setActionCommand("show");
        show.addActionListener(testProgramController);
        this.add(show);

        MyButton move = new MyButton("move");
        move.setActionCommand("move");
        move.addActionListener(testProgramController);
        this.add(move);
        this.setBackground(Color.YELLOW);
    }
}
