package programs.programChooser;

import controlWindow.MainController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * controller for the program choosing side panel
 */
public class ProgramChooserModel implements ActionListener {

    /**
     * view of this controller
     */
    private ProgramChooserView programChooserView;

    /**
     * main controller of the controlling window
     */
    private MainController mainController;

    /**
     * handler for the programs
     */
    private ProgramHandler programHandler;

    /**
     * creates a new program chooser
     *
     * @param mainController main controller of the controlling window
     * @param programHandler handler for the programs
     */
    public ProgramChooserModel(MainController mainController, ProgramHandler programHandler) {
        this.mainController = mainController;

        this.programHandler = programHandler;

        programChooserView = new ProgramChooserView(this);
    }

    /**
     * @return returns the view of this controller
     */
    public ProgramChooserView getProgramChooserView() {
        return programChooserView;
    }

    /**
     * action of the high, slim button when the program choosing side panel is collapsed
     */
    void expandButtonAction() {
        mainController.getControlModel().getView().getMyJFrame().getFrame().repaint();
        programChooserView.getExpandedView().setVisible(true);
    }

    /**
     * action of the high, slim button when the program choosing side panel is expanded
     */
    void collapseButtonAction() {
        mainController.getControlModel().getView().getMyJFrame().getFrame().repaint();
        programChooserView.getExpandedView().setVisible(false);

    }

    /**
     * @return returns the handler of the programs
     */
    public ProgramHandler getProgramHandler() {
        return programHandler;
    }

    /**
     * action of the buttons for the programs
     *
     * @param name name of the selected program
     */
    void programButtonAction(String name) {
        mainController.setProgram(programHandler.getByName(name));
        collapseButtonAction();
    }

    /**
     * action of the settings button
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("settings")) {
            mainController.displaySettings();
        } else if (e.getActionCommand().equals("credits")) {
            mainController.displayCredits();
        }
    }

    /**
     * recreates the program chooser view
     */
    public void updateView() {
        programChooserView = new ProgramChooserView(this);
    }
}
