package programs.scoreBoard.control;

import assets.standardAssets.MyButton;
import programs.abstractProgram.ProgramView;

import java.awt.event.ActionListener;

class ScoreBoardControlView extends ProgramView {

    /**
     * creates a new view
     *
     * @param actionListener sets the actionListener of the view
     */
    ScoreBoardControlView(ActionListener actionListener) {
        super(actionListener);

        MyButton show = new MyButton("show");
        show.setActionCommand("show");
        show.addActionListener(actionListener);
        this.add(show);

        MyButton hide = new MyButton("hide");
        hide.setActionCommand("hide");
        hide.addActionListener(actionListener);
        this.add(hide);

        MyButton settings = new MyButton("settings");
        settings.setActionCommand("settings");
        settings.addActionListener(actionListener);
        this.add(settings);
    }
}
