package startupApp;

import assets.standardAssets.StandardAssetFields;
import controlWindow.ControlModel;
import controlWindow.NativeKeyListener;
import controlWindow.settings.SettingsController;
import org.jnativehook.GlobalScreen;
import presentationWindow.engine.Engine;
import presentationWindow.window.OpenGlRenderer;
import programs.abstractProgram.Program;
import programs.instantButton.main.InstantButtonProgram;
import programs.keyPresser.main.KeyPressProgram;
import programs.mouseClicker.main.MouseClickerProgram;
import programs.programChooser.ProgramHandler;
import programs.quizOverlay.main.control.QuizOverlayProgram;
import programs.quiztime.main.control.QuizTimeProgram;
import programs.scoreBoard.main.ScoreBoardProgram;
import programs.testProgram.main.TestProgram;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;
import utils.save.SaveFile;

import java.awt.*;

/**
 * controller of the loading window at the startup of the application
 */
public class LoadingModel {

    /**
     * loading handler that handles the loading processes
     */
    private LoadingHandler loadingHandler;

    /**
     * loading view that shows the progress
     */
    private LoadingView loadingView;

    /**
     * save data handler that gets passed to the control model
     */
    private SaveDataHandler saveDataHandler;

    /**
     * open gl renderer for the presentation window
     */
    private OpenGlRenderer openGlRenderer;

    /**
     * program handler that handles the programs, gets passed to the control model
     */
    private ProgramHandler programHandler;

    /**
     * initialize loading loading process
     */
    private LoadingMonitor initializeLoading;

    /**
     * creates a new loading model
     * loads all the resources for the programs
     */
    public LoadingModel() {

        loadingHandler = new LoadingHandler();

        initializeLoading = new LoadingMonitor("initialize");
        loadingHandler.addLoadingProcess(initializeLoading);

        loadingView = new LoadingView(this);


        new Thread(() -> {




            programHandler = new ProgramHandler();

            SaveFile settingsSaveFile = new SaveFile("settings");

            System.out.println(settingsSaveFile.getColor(SettingsController.EFFECT_COLOR));

            StandardAssetFields.ROLLOVER_COLOR = settingsSaveFile.getColor(SettingsController.EFFECT_COLOR);

            StandardAssetFields.PRESSED_COLOR = settingsSaveFile.getColor(SettingsController.EFFECT_COLOR).brighter();

            saveDataHandler = new SaveDataHandler(settingsSaveFile);

            new Text(settingsSaveFile.getString(SettingsController.LANGUAGE));

            GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] devices = g.getScreenDevices();
            if (settingsSaveFile.getInteger(SettingsController.OUTPUT_SCREEN) > devices.length) {
                settingsSaveFile.putInteger(SettingsController.OUTPUT_SCREEN, 1);
            }

            openGlRenderer = new OpenGlRenderer();

            createPrograms();

            startOpenGlThread();
        }).start();


    }

    /**
     * creates the sub programs of the application
     */
    private void createPrograms() {
        createAndLoadProgram("QuizOverlayProgram");
        createAndLoadProgram("QuizTimeProgram");
        createAndLoadProgram("MouseClickerProgram");
        createAndLoadProgram("KeyPressProgram");
        createAndLoadProgram("TestProgram");
        createAndLoadProgram("ScoreBoardProgram");
        createAndLoadProgram("InstantButtonProgram");

    }

    /**
     * creates and loads a program
     *
     * @param name of the program that gets created
     */
    private void createAndLoadProgram(String name) {
        new Thread(() -> {
            LoadingMonitor loadingMonitor = new LoadingMonitor(name);
            loadingHandler.addLoadingProcess(loadingMonitor);
            Program program = createProgram(name);
            programHandler.addProgram(program);
            program.loadProgram(openGlRenderer, loadingHandler);
            loadingMonitor.finishedProcess(loadingHandler);
        }).start();
    }

    /**
     * creates the right program according to the given name
     *
     * @param name name of the program to create the right object
     * @return created program
     */
    private synchronized Program createProgram(String name) {
        switch (name) {
            case "QuizOverlayProgram":
                return new QuizOverlayProgram();
            case "QuizTimeProgram":
                return new QuizTimeProgram();
            case "MouseClickerProgram":
                return new MouseClickerProgram();
            case "KeyPressProgram":
                return new KeyPressProgram();
            case "TestProgram":
                return new TestProgram("Test");
            case "ScoreBoardProgram":
                return new ScoreBoardProgram();
            case "InstantButtonProgram":
                return new InstantButtonProgram();
            default:
                return new TestProgram("Failure");
        }
    }

    /**
     * creates and starts the open gl engine and loads the resources of the programs
     */
    private void startOpenGlThread() {
        new Thread(() -> {

            LoadingMonitor loadingMonitor = new LoadingMonitor("mainOpenGL");
            loadingHandler.addLoadingProcess(loadingMonitor);

            Engine gameEngine = null;
            try {
                gameEngine = new Engine("Buzzer", 800, 450, false, openGlRenderer, loadingHandler, true, saveDataHandler.getSettings().getInteger(SettingsController.OUTPUT_SCREEN) != 1, saveDataHandler.getSettings().getInteger(SettingsController.OUTPUT_SCREEN));
                Thread.sleep(10);
                loadingMonitor.finishedProcess(loadingHandler);
                initializeLoading.finishedProcess(loadingHandler);
            } catch (Exception e) {
                e.printStackTrace();
            }

            new Thread(gameEngine).start();
        }).start();
    }

    /**
     * method starts the control model end ends the loading window
     */
    private void loadingFinished() {

        ControlModel controlModel = new ControlModel(saveDataHandler, openGlRenderer, programHandler);
        NativeKeyListener nativeKeyListener = new NativeKeyListener(controlModel);
        GlobalScreen.addNativeKeyListener(nativeKeyListener);
        loadingView.closeWindow();
    }

    /**
     * method updates the process bar and finishes the loading when it is full
     */
    void updateProgressBar() {
        if (loadingView != null) {
            StringBuilder sb = new StringBuilder();
            for (String s : loadingHandler.getCurrentBufferContent()) {
                sb.append(s).append("  -  ");
            }
            loadingView.updateProgressBar(loadingHandler.getProgress(), sb.toString());
            if (loadingHandler.getProgress() == 1) {
                loadingFinished();
            }
        }
    }

    /**
     * @return returns the loading handler
     */
    public LoadingHandler getLoadingHandler() {
        return loadingHandler;
    }
}
