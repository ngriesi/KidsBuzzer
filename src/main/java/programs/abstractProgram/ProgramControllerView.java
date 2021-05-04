package programs.abstractProgram;

import presentationWindow.engine.Action;

import javax.swing.*;
import java.awt.event.ActionEvent;

public abstract class ProgramControllerView <T extends ProgramController> extends ProgramView {

    /**
     * controller of the view
     */
    private T controller;

    /**
     * number of the key bindings this view has
     */
    private int keyBindingID = 0;

    /**
     * creates a new view
     *
     * @param controller sets the actionListener of the view
     */
    public ProgramControllerView(T controller) {
        super(controller);
        this.controller = controller;
    }

    /**
     * adds a new key action
     */
    protected void addKeyAction(KeyStroke keyStroke, Action action) {
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(keyStroke,"" + keyBindingID);
        this.getActionMap().put("" + keyBindingID, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!controller.getProgram().getMainController().getControlModel().isUseNativeKeyListener()) {
                    action.execute();
                }
            }
        });
        keyBindingID++;
    }
}
