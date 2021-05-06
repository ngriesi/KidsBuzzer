package programs.testProgram.settings;

import assets.standardAssets.MyButton;
import programs.abstractProgram.ProgramView;

import java.awt.*;

/**
 * test settings view with a button to return to the control view
 */
class TestProgramSettingsView extends ProgramView {

    /**
     * creates the settings view
     *
     * @param testProgramSettingsController controller of the view
     */
    TestProgramSettingsView(TestProgramSettingsController testProgramSettingsController) {
        super(testProgramSettingsController);

        MyButton back = new MyButton("back");
        back.setActionCommand("back");
        back.addActionListener(testProgramSettingsController);
        this.add(back);

        this.add(new MyButton("set rect"));

        this.add(new MyButton("set image"));
        this.setBackground(Color.GREEN);
    }
}
