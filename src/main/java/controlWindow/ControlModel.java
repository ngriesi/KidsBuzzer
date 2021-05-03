package controlWindow;

import buzzerHandler.BuzzerModel;
import controlWindow.credits.CreditsController;
import controlWindow.settings.SettingsController;
import presentationWindow.window.OpenGlRenderer;
import programs.programChooser.ProgramHandler;
import programs.abstractProgram.Program;
import programs.programChooser.ProgramChooserModel;
import savedataHandler.SaveDataHandler;
import serialPortHandling.SerialPortReader;
import serialPortHandling.SerialPortReaderInterface;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.LAST_LINE_END;

/**
 * Main class connecting the ControlWindow, the Presentation Window and the serialPortHandling.SerialPortReader
 */
public class ControlModel implements SerialPortReaderInterface {

    /**
     * shows that the Application is running for separate Threads
     */
    private boolean applicationRunning;

    /**
     * Window to control the Presentation and to Monitor the Buzzers
     */
    private ControlWindow controlWindow;

    /**
     * The logic controlling the buzzer view on the control window
     */
    private BuzzerModel buzzerModel;

    /**
     * object handles all loaded resources and save files
     */
    private SaveDataHandler saveDataHandler;

    /**
     * stores the program that is currently in use
     */
    private Program currentProgram;

    /**
     * logic controlling the view to choose the program to use
     */
    private ProgramChooserModel programChooserModel;

    /**
     * renderer for the presentation screen
     */
    private OpenGlRenderer openGlRenderer;

    /**
     * controller for the settings screen
     */
    private SettingsController settingsController;

    /**
     * controller for the credits screen
     */
    private CreditsController creditsController;

    private boolean useNativeKeyListener;

    /**
     * Constructor creates the ControlWindow, the Presentation Window and the serialPortHandling.SerialPortReader
     * Gets the SaveDataHandler form the loading model containing all the loaded resources
     */
    public ControlModel(SaveDataHandler saveDataHandler, OpenGlRenderer openGlRenderer, ProgramHandler programHandler) {
        applicationRunning = true;

        this.openGlRenderer = openGlRenderer;
        this.saveDataHandler = saveDataHandler;
        this.settingsController = new SettingsController(this, saveDataHandler.getSettings());
        creditsController = new CreditsController(this);

        new SerialPortReader(this);

        useNativeKeyListener = saveDataHandler.getSettings().isUseNativeKeyListener();

        createControlView(programHandler);
        setProgram(programHandler.getByName(programHandler.getProgramNamesList()[0]));
    }

    /**
     * creates the view of the program
     *
     * @param programHandler handler for the programs
     */
    private void createControlView(ProgramHandler programHandler) {
        this.buzzerModel = new BuzzerModel(this);
        this.programChooserModel = new ProgramChooserModel(this, programHandler);
        this.controlWindow = new ControlWindow(this);
    }

    /**
     * Method changes the currently used program
     *
     * @param program new program
     */
    public void setProgram(Program program) {
        program.setControlModel(this);
        if (currentProgram != null) {
            currentProgram.programClosed();
        }
        this.currentProgram = program;
        currentProgram.programSelected();
        getView().setProgramPane(program.getMainView());
        while (openGlRenderer == null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(openGlRenderer);
        openGlRenderer.addActionToOpenGlThread(() -> openGlRenderer.getScene().setMainItem(program.getMainPresentationViewItem()));
    }

    /**
     * shows the presentation window
     */
    public void showPresentationWindow() {
        openGlRenderer.getWindow().show();
    }

    /**
     * hides the presentation window
     */
    public void hidePresentationWindow() {
        openGlRenderer.getWindow().hide();
    }

    /**
     * returns true if the presentation window is visible
     */
    public boolean isShowingPresentation() {
        return openGlRenderer.getWindow().isShown();
    }


    /**
     * Handles the Button number input coming from the SerialBuffer (Part of the Serial Port Reader). The
     * Method is called with SwingUtilities.invokeLater() for it to be able to do things on the UI
     *
     * @param buzzerID ID of the pressed buzzer
     */
    public void handleBuzzerInput(int buzzerID) {
        if(!buzzerModel.isBlockAllBuzzer()) {
            int buzzerNum = buzzerID / 2;

            boolean blockedSave = buzzerModel.isBuzzerBlocked(buzzerNum);
            buzzerModel.pressBuzzer(buzzerNum);
            currentProgram.handleBuzzerInput(buzzerNum, blockedSave);
        }
    }

    /**
     * performs the actions for the closing of the application
     */
    void applicationClosing() {
        currentProgram.programClosed();
        applicationRunning = false;
        saveWindowBounds();
        settingsController.getSettingsSaveFile().saveFile();
    }

    /**
     * sets the window bounds in the save file
     */
    private void saveWindowBounds() {
        settingsController.getSettingsSaveFile().setWindowHeight(getView().getMyJFrame().getFrame().getHeight());
        settingsController.getSettingsSaveFile().setWindowWidth(getView().getMyJFrame().getFrame().getWidth());
        settingsController.getSettingsSaveFile().setWindowPositionX(getView().getMyJFrame().getFrame().getX());
        settingsController.getSettingsSaveFile().setWindowPositionY(getView().getMyJFrame().getFrame().getY());
    }

    /**
     * displays the normal control view in the control frame
     */
    public void displayControlView() {
        getView().setView(getView().getMainLayout());
    }

    /**
     * displays the main settings view in the control frame
     */
    public void displaySettings() {
        getView().setView(settingsController.getSettingsView());
    }

    /**
     * @return returns true if the application is running
     */
    public boolean isApplicationRunning() {
        return applicationRunning;
    }

    /**
     * @return returns the controller for the virtual buzzers at the bottom of the control window
     */
    public BuzzerModel getBuzzerControl() {
        return buzzerModel;
    }

    /**
     * @return returns the save data handler of the application
     */
    public SaveDataHandler getSaveDataHandler() {
        return saveDataHandler;
    }

    /**
     * @return returns the controller of the program choosing view
     */
    ProgramChooserModel getProgramChooserModel() {
        return programChooserModel;
    }

    /**
     * @return returns the main view of the control frame
     */
    public ControlWindow getView() {
        return controlWindow;
    }

    /**
     * @return returns the controller for the main settings view
     */
    public SettingsController getSettingsController() {
        return settingsController;
    }

    /**
     * @param buzzerModel replaces the virtual buzzer view with a new one when updates were made to it
     */
    public void setBuzzerControl(BuzzerModel buzzerModel) {
        getView().getChooserCollapsed().remove(this.buzzerModel.getView());
        this.buzzerModel = buzzerModel;
        getView().getChooserCollapsed().add(getBuzzerControl().getView(),new GridBagConstraints(1, 1, 1, 1, 1, 0.3f, LAST_LINE_END, BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    /**
     * shows on the gui that the program is searching the receiver
     */
    @Override
    public void searchingForReceiver() {

        SwingUtilities.invokeLater(() -> buzzerModel.changeButtonText("searching"));
    }

    @Override
    public void handleData(String data) {
        SwingUtilities.invokeLater(() -> {
            try {
                handleBuzzerInput(Integer.parseInt(data.trim()));
            } catch (NumberFormatException ignored) {
            }
        });
    }

    @Override
    public boolean isRunning() {
        return isApplicationRunning();
    }

    /**
     * shows on the gui that the receiver was found
     *
     * @param receiverName name of the receiver
     */
    @Override
    public void receiverFound(String receiverName) {
        SwingUtilities.invokeLater(() -> buzzerModel.changeButtonText(receiverName));
    }

    public void updateOutputScreen() {
        openGlRenderer.changeScreen(saveDataHandler.getSettings().getOutputScreen());
    }

    public void recreatePrograms() {

        getProgramChooserModel().getProgramHandler().updateBuzzerCount();
    }

    public void setEnableNativeKeyListener(boolean value) {
        useNativeKeyListener = value;
    }

    void nativeKeyAction(int keyCode) {
        if (useNativeKeyListener) {

            currentProgram.nativeKeyAction(keyCode);
        }
    }

    void toggleNativeKeyListener() {
        useNativeKeyListener = !useNativeKeyListener;
        settingsController.setNativeKeyListenerSetting(useNativeKeyListener);
    }

    public void displayCredits() {
        getView().setView(creditsController.getView());
    }

    public boolean isUseNativeKeyListener() {
        return useNativeKeyListener;
    }
}
