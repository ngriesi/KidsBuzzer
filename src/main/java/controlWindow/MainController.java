package controlWindow;

import buzzerHandler.BuzzerModel;
import programs.abstractProgram.Program;

import java.awt.*;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.LAST_LINE_END;

/**
 * main controller connecting all the parts of the application
 */
public class MainController {

    /**
     * main model of the application
     */
    private ControlModel controlModel;

    /**
     * constructor sets the reference to the main model of the application
     *
     * @param controlModel main model of the application
     */
    MainController(ControlModel controlModel) {
        this.controlModel = controlModel;


    }

    /**
     * recreates the <code>BuzzerModel</code> to update the view if the buzzer number has changed
     */
    public void recreateBuzzerModel() {
        controlModel.getView().getChooserCollapsed().remove(controlModel.getBuzzerControl().getView());
        controlModel.setBuzzerModel(new BuzzerModel(this));
        controlModel.getView().getChooserCollapsed().add(controlModel.getBuzzerControl().getView(), new GridBagConstraints(1, 1, 1, 1, 1, 0.3f, LAST_LINE_END, BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    /**
     * changes the output screen used by the <code>OpenGlRenderer</code>
     *
     * @param value index of the new output screen
     */
    public void updateOutputScreen(int value) {
        controlModel.getOpenGlRenderer().changeScreen(value);
    }

    /**
     * method calls the <code>updateBuzzerCount</code> method in <code>Program</code> to update the views
     * if the number of buzzers has changed
     */
    public void updateBuzzerCountOfPrograms() {
        controlModel.getProgramChooserModel().getProgramHandler().updateBuzzerCount();
    }

    /**
     * Method changes the currently used program. It blocks if the <code>OpenGlRenderer</code> is not ready yet
     *
     * @param program new program
     */
    public void setProgram(Program program) {
        program.setMainController(this);
        if (controlModel.getCurrentProgram() != null) {
            controlModel.getCurrentProgram().programClosed();
        }
        controlModel.setCurrentProgram(program);
        controlModel.getCurrentProgram().programSelected();
        controlModel.getView().setProgramPane(program.getMainView());
        while (controlModel.getOpenGlRenderer() == null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        controlModel.getOpenGlRenderer().addActionToOpenGlThread(() ->
                controlModel.getOpenGlRenderer().getScene().setMainItem(program.getMainPresentationViewItem()));
    }

    /**
     * performs the actions for the closing of the application
     */
    void applicationClosing() {
        getControlModel().getMidiHandler().closeMidi();
        controlModel.getCurrentProgram().programClosed();
        controlModel.applicationIsClosing();
        getControlModel().saveWindowBounds();
        getControlModel().getSettingsController().getSettingsSaveFile().saveFile();
    }

    /**
     * displays the main settings view in the control frame
     */
    public void displaySettings() {
        controlModel.getSettingsController().setOldValues();
        controlModel.getView().setView(controlModel.getSettingsController().getSettingsView());
    }


    /**
     * shows the presentation window
     */
    public void showPresentationWindow() {
        controlModel.getProgramChooserModel().permitProgramChange();
        controlModel.getOpenGlRenderer().getWindow().show();
    }

    /**
     * hides the presentation window
     */
    public void hidePresentationWindow() {
        controlModel.getOpenGlRenderer().getWindow().hide();
        controlModel.getProgramChooserModel().allowProgramChange();
    }

    /**
     * returns true if the presentation window is visible
     *
     * @return true if the output window is visible
     */
    public boolean isShowingPresentation() {
        return controlModel.getOpenGlRenderer().getWindow().isShown();
    }

    /**
     * displays the credits of the application
     */
    public void displayCredits() {
        controlModel.getView().setView(controlModel.getCreditsController().getView());
    }

    /**
     * @return returns the main model of the application with references to all the main parts of it
     */
    public ControlModel getControlModel() {
        return controlModel;
    }

    /**
     * displays the normal control view in the control frame
     */
    public void displayControlView() {
        controlModel.getView().setView(controlModel.getView().getMainLayout());
    }

    /**
     * Updates the view of the whole application
     */
    public void updateView() {
        controlModel.getView().getMyJFrame().recreate();
        controlModel.getCreditsController().updateView();
        controlModel.getSettingsController().updateSettingsView();
        controlModel.getProgramChooserModel().updateView();
        controlModel.getBuzzerControl().updateView();
        controlModel.getView().setupWindow();
        controlModel.getProgramChooserModel().getProgramHandler().updateProgramViews();


        setProgram(controlModel.getCurrentProgram());

    }

    public void midiDeviceFound() {
        getControlModel().getSettingsController().getSettingsView().getInformationPage().midiDeviceFound();
    }

    public void midiDeviceLost() {
        getControlModel().getSettingsController().getSettingsView().getInformationPage().midiDeviceLost();
    }
}
