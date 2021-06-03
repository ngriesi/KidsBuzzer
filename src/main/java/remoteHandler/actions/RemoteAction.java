package remoteHandler.actions;

import presentationWindow.engine.Action;

/**
 * a possible action of for the remote control of the program
 */
public class RemoteAction {


    /**
     * The action performed
     */
    private Action action;

    public RemoteAction(Action action) {
        this.action = action;
    }

    RemoteAction() {}

    /**
     * Performs the remote action
     */
    public void performAction(int keyCode) {
        action.execute();
    }
}
