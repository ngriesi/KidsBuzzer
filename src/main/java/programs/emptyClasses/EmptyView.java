package programs.emptyClasses;

import programs.abstractProgram.ProgramView;

import java.awt.event.ActionListener;

public class EmptyView extends ProgramView {
    /**
     * creates a new view
     *
     * @param actionListener sets the actionListener of the view
     */
    public EmptyView(ActionListener actionListener) {
        super(actionListener);
    }
}
