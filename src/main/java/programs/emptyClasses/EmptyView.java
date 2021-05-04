package programs.emptyClasses;

import programs.abstractProgram.ProgramView;

import java.awt.event.ActionListener;

/**
 * empty view class for unused generics
 */
class EmptyView extends ProgramView {
    /**
     * creates a new view
     *
     * @param actionListener sets the actionListener of the view
     */
    EmptyView(ActionListener actionListener) {
        super(actionListener);
    }
}
