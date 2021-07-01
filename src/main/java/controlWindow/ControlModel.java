package controlWindow;

import buzzerHandler.BuzzerModel;
import controlWindow.credits.CreditsController;
import controlWindow.settings.SettingsController;
import midi.MidiHandler;
import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.Program;
import programs.programChooser.ProgramChooserModel;
import programs.programChooser.ProgramHandler;
import savedataHandler.SaveDataHandler;

/**
 * Main class connecting the ControlWindow, the Presentation Window and the serialPortHandling.SerialPortReader
 */
public class ControlModel {

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

    /**
     * flag indicating if the input received from the native key listener should be used
     */
    private boolean useNativeKeyListener;

    /**
     * handler for the input coming form the serial port
     */
    private BuzzerPressHandler buzzerPressHandler;

    /**
     * Handler for midi messages
     */
    private MidiHandler midiController;

    /**
     * Constructor creates the ControlWindow, the Presentation Window and the serialPortHandling.SerialPortReader
     * Gets the SaveDataHandler form the loading model containing all the loaded resources
     *
     * @param saveDataHandler <code>SaveDataHandler</code> that contains general information the program uses
     * @param openGlRenderer  the renderer for the output window
     * @param programHandler  handler that contains references to all the programs the application uses
     */
    public ControlModel(SaveDataHandler saveDataHandler, OpenGlRenderer openGlRenderer, ProgramHandler programHandler) {
        applicationRunning = true;



        MainController mainController = new MainController(this);

        this.openGlRenderer = openGlRenderer;
        this.saveDataHandler = saveDataHandler;

        creditsController = new CreditsController(mainController);
        buzzerPressHandler = new BuzzerPressHandler(this);

        useNativeKeyListener = saveDataHandler.getSettings().getBoolean(SettingsController.NATIVE_KEY_LISTENER);

        this.settingsController = new SettingsController(mainController, saveDataHandler.getSettings());
        createControlView(programHandler, mainController);

        midiController = new MidiHandler(mainController);
        midiController.setupMidiHandler();

        mainController.setProgram(programHandler.getByName(programHandler.getProgramNamesList()[0]));

        new ScreenHandler(mainController);

    }

    /**
     * creates the view of the program
     *
     * @param programHandler handler for the programs
     * @param mainController main controller of the application
     */
    private void createControlView(ProgramHandler programHandler, MainController mainController) {
        this.buzzerModel = new BuzzerModel(mainController);
        this.programChooserModel = new ProgramChooserModel(mainController, programHandler);
        this.controlWindow = new ControlWindow(mainController);
    }

    /**
     * @return returns true if the application is running
     */
    public boolean isApplicationRunning() {
        return applicationRunning;
    }

    /**
     * sets the applicationRunning flag to false to stop all processes
     */
    void applicationIsClosing() {
        applicationRunning = false;
    }

    /**
     * sets the window bounds in the save file
     */
    void saveWindowBounds() {
        settingsController.getSettingsSaveFile().putInteger(SettingsController.WINDOW_HEIGHT, getView().getMyJFrame().getFrame().getHeight());
        settingsController.getSettingsSaveFile().putInteger(SettingsController.WINDOW_WIDTH, getView().getMyJFrame().getFrame().getWidth());
        settingsController.getSettingsSaveFile().putInteger(SettingsController.WINDOW_X, getView().getMyJFrame().getFrame().getX());
        settingsController.getSettingsSaveFile().putInteger(SettingsController.WINDOW_Y, getView().getMyJFrame().getFrame().getY());
    }

    /**
     * sets the flag that decides whether the native key listeners input should be used
     *
     * @param value new value of the flag
     */
    public void setEnableNativeKeyListener(boolean value) {
        useNativeKeyListener = value;
    }

    /**
     * method called by the native key listener if a key gets released.
     * The key code is the released key
     *
     * @param keyCode key that was released
     */
    void nativeKeyAction(int keyCode) {
        if (useNativeKeyListener) {
            currentProgram.nativeKeyAction(keyCode);
        }
    }

    /**
     * switches the state of the useNativeKeyListener flag and sets the new value in the <code>SettingsController</code>
     */
    void toggleNativeKeyListener() {
        useNativeKeyListener = !useNativeKeyListener;
        settingsController.setNativeKeyListenerSetting(useNativeKeyListener);
    }

    /**
     * @return returns true if the native key listener is currently getting used
     */
    public boolean isUseNativeKeyListener() {
        return useNativeKeyListener;
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
    SaveDataHandler getSaveDataHandler() {
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
     * @return returns the currently selected program
     */
    Program getCurrentProgram() {
        return currentProgram;
    }

    /**
     * sets the current program (no updates made, only used by setProgram in <code>MainController</code>)
     *
     * @param currentProgram new current program
     */
    void setCurrentProgram(Program currentProgram) {
        this.currentProgram = currentProgram;
    }

    /**
     * @return returns the renderer for the output window which is created using LWJGL
     */
    public OpenGlRenderer getOpenGlRenderer() {
        return openGlRenderer;
    }

    /**
     * @return returns the controller of the credits view to access its view
     */
    CreditsController getCreditsController() {
        return creditsController;
    }

    /**
     * sets an new <code>BuzzerModel</code>. This is used if the buzzer count was updated
     *
     * @param buzzerModel new <code>BuzzerModel</code>
     */
    void setBuzzerModel(BuzzerModel buzzerModel) {
        this.buzzerModel = buzzerModel;
    }

    /**
     * @return returns the buzzer press handler which handles the serial input from the receiver
     */
    public BuzzerPressHandler getBuzzerPressHandler() {
        return buzzerPressHandler;
    }

    /**
     * @return returns the midi contorller of the application
     */
    public MidiHandler getMidiHandler() {
        return midiController;
    }
}
