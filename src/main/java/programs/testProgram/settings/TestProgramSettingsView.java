package programs.testProgram.settings;

import assets.standardAssets.MyButton;
import programs.abstractProgram.ProgramView;

import java.awt.*;

public class TestProgramSettingsView extends ProgramView {



    public TestProgramSettingsView(TestProgramSettingsController testProgramSettingsController) {
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
