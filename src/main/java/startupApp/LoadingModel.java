package startupApp;

import controlWindow.ControlModel;
import controlWindow.NativeKeyListener;
import controlWindow.settings.SettingsSaveFile;
import org.jnativehook.GlobalScreen;
import presentationWindow.engine.Engine;
import presentationWindow.window.OpenGlRenderer;
import programs.instantButton.main.InstantButtonProgram;
import programs.keyPresser.main.KeyPressProgram;
import programs.mouseClicker.main.MouseClickerProgram;
import programs.programChooser.ProgramHandler;
import programs.quizOverlay.main.control.QuizOverlayProgram;
import programs.quiztime.main.control.QuizTimeProgram;
import programs.scoreBoard.main.ScoreBoardProgram;
import programs.testProgram.main.TestProgram;
import savedataHandler.SaveDataHandler;
import utils.saveFile.SaveFileLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
     *
     * @throws IOException if the loading screen image cant be loaded
     */
    public LoadingModel() throws IOException {


        //noinspection SpellCheckingInspection
        BufferedImage backgroundImage = ImageIO.read(new File("anktarktis.jpg"));

        loadingHandler = new LoadingHandler();

        initializeLoading = new LoadingMonitor("initialize");
        loadingHandler.addLoadingProcess(initializeLoading);

        loadingView = new LoadingView(backgroundImage, this);


        new Thread(() -> {


            programHandler = new ProgramHandler();

            SettingsSaveFile settingsSaveFile = SaveFileLoader.loadFile("settings", SettingsSaveFile.class);
            if (settingsSaveFile == null) {
                settingsSaveFile = new SettingsSaveFile();
            }
            saveDataHandler = new SaveDataHandler(settingsSaveFile);

            GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] devices = g.getScreenDevices();
            if (settingsSaveFile.getOutputScreen() > devices.length) {
                settingsSaveFile.setOutputScreen(1);
            }

            createPrograms();


            startOpenGlThread();
        }).start();


    }

    /**
     * creates the sub programs of the application
     */
    private void createPrograms() {
        programHandler.addProgram(new QuizTimeProgram());
        programHandler.addProgram(new ScoreBoardProgram());
        programHandler.addProgram(new InstantButtonProgram());
        programHandler.addProgram(new TestProgram("test"));
        programHandler.addProgram(new MouseClickerProgram());
        programHandler.addProgram(new KeyPressProgram());
        programHandler.addProgram(new QuizOverlayProgram());
    }

    /**
     * creates and starts the open gl engine and loads the resources of the programs
     */
    private void startOpenGlThread() {
        new Thread(() -> {

            LoadingMonitor loadingMonitor = new LoadingMonitor("mainOpenGL");
            loadingHandler.addLoadingProcess(loadingMonitor);

            openGlRenderer = new OpenGlRenderer();

            programHandler.loadPrograms(openGlRenderer, loadingHandler);


            Engine gameEngine = null;
            try {
                gameEngine = new Engine("Buzzer", 800, 450, false, openGlRenderer, loadingHandler, true, saveDataHandler.getSettings().getOutputScreen() != 1, saveDataHandler.getSettings().getOutputScreen());
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
        StringBuilder sb = new StringBuilder();
        for (String s : loadingHandler.getCurrentBufferContent()) {
            sb.append(s);
        }
        loadingView.updateProgressBar(loadingHandler.getProgress(), sb.toString());
        if (loadingHandler.getProgress() == 1) {
            loadingFinished();
        }
    }
}
