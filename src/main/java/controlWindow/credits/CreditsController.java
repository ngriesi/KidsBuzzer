package controlWindow.credits;

import controlWindow.ControlModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreditsController implements ActionListener {

    private ControlModel controlModel;

    private CreditsView creditsView;

    public CreditsController(ControlModel controlModel) {

        this.controlModel = controlModel;

        creditsView = new CreditsView(this);
    }

    public JPanel getView() {
        return creditsView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("back")) {
            controlModel.displayControlView();
        }
    }
}
