package controlWindow.credits;

import controlWindow.MainController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * the controller for the credits view
 */
public class CreditsController implements ActionListener {

    /**
     * the main controller of the application, used to return to the normal view
     */
    private MainController mainController;

    /**
     * the view of this controller
     */
    private CreditsView creditsView;

    /**
     * creates a new credits controller and a new credits view
     *
     * @param mainController main controller of the application
     */
    public CreditsController(MainController mainController) {

        this.mainController = mainController;

        creditsView = new CreditsView(this);
    }

    /**
     * returns the view of the credits to set it as the applications view
     *
     * @return view of the credits
     */
    public JPanel getView() {
        return creditsView;
    }

    /**
     * action performed method for the actions of the components of the
     * credits view
     * <p>
     * Actions:
     * ActionCommand: "back":
     * return to displaying the normal view of the application (close the credits)
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("back")) {
            mainController.displayControlView();
        }
    }

    /**
     * recreates the creadits view
     */
    public void updateView() {
        creditsView = new CreditsView(this);
    }
}
