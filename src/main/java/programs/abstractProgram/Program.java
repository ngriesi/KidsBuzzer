package programs.abstractProgram;

import controlWindow.ControlModel;
import presentationWindow.renderItems.MainItem;
import presentationWindow.window.OpenGlRenderer;
import startupApp.LoadingHandler;
import startupApp.LoadingMonitor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;

/**
 * abstract super class of all programs
 *
 * @param <C>  program control view controller
 * @param <SC> program settings view controller
 * @param <M>  program model
 * @param <P>  program presentation view
 */
@SuppressWarnings("WeakerAccess")
public abstract class Program<C extends ProgramController, SC extends ProgramController, M extends ProgramModel, P extends ProgramPresentationView> {

    /**
     * program control view controller
     */
    private C programController;

    /**
     * program settings view controller
     */
    private SC settingsController;

    /**
     * program model
     */
    private M programModel;

    /**
     * program presentation view
     */
    private P programPresentationView;

    /**
     * open gl renderer
     */
    private OpenGlRenderer renderer;

    /**
     * flag decides if the buzzer blocking should be used in this program
     */
    private boolean blockBuzzers;

    /**
     * name of the program
     */
    private String name;

    /**
     * main view in the control window
     */
    private JPanel mainView;

    /**
     * main controller of the control window
     */
    private ControlModel controlModel;

    /**
     * main item in the presentation window
     */
    private MainItem mainItem;

    /**
     * List of actions performed when the program gets closed or changed away
     */
    private List<presentationWindow.engine.Action> programClosedActions;

    /**
     * creates a new program
     *
     * @param blockBuzzers determines if the buzzer blocking should be used
     * @param name         name of the program
     */
    public Program(boolean blockBuzzers, String name) {
        this.blockBuzzers = blockBuzzers;
        this.name = name;
        programClosedActions = new ArrayList<>();
        mainView = new JPanel(new GridBagLayout());
        programModel = createModel();
        settingsController = createSettingsController();
        programController = createControlController();
        programPresentationView = createPresentationView();
        setView(getProgramController().getProgramView());
        addClosedAction(() -> getProgramModel().getSaveFile().saveFile());
    }

    /**
     * abstract creation methods
     */
    public abstract M createModel();

    public abstract SC createSettingsController();

    public abstract C createControlController();

    public abstract P createPresentationView();

    /**
     * loads the program
     *
     * @param openGlRenderer open gl context to load image resources
     * @param loadingModel   loading model to track the loading
     */
    public void loadProgram(OpenGlRenderer openGlRenderer, LoadingHandler loadingModel) {

        new Thread(() -> {

            this.renderer = openGlRenderer;
            programModel.loadModel(loadingModel, openGlRenderer);

            this.renderer.addActionToOpenGlThread(() -> {

                LoadingMonitor monitor = new LoadingMonitor(getName() + " openGl view");
                loadingModel.addLoadingProcess(monitor);
                recreateView();
                monitor.finishedProcess(loadingModel);
                getProgramModel().updateChangeListener();
            });


        }).start();
    }

    /**
     * recreates the presentation view
     */
    public void recreateView() {
        mainItem = new MainItem(renderer.getWindow());
        programPresentationView.setupView(mainItem);
    }

    /**
     * handles the input of the buzzers
     *
     * @param buzzerNumber number of the buzzer pressed
     * @param blocked      true if the buzzer is currently blocked
     */
    public void handleBuzzerInput(int buzzerNumber, boolean blocked) {
        if (!blockBuzzers) {
            buzzerAction(buzzerNumber);
        } else {
            if (!blocked) {
                buzzerAction(buzzerNumber);
            }
        }
    }

    /**
     * sets the content of the control view of the program
     *
     * @param panel new control view content
     */
    public void setView(JPanel panel) {
        mainView.removeAll();
        mainView.add(panel, new GridBagConstraints(0, 0, 1, 1, 1, 1, CENTER, BOTH, new Insets(0, 0, 0, 0), 0, 0));
        mainView.validate();
        mainView.repaint();
    }

    /**
     * method gets called when the program gets closed
     */
    public void programClosed() {
        for (presentationWindow.engine.Action action : programClosedActions) {
            action.execute();
        }
    }

    /**
     * action performed after a buzzer press by the individual program
     *
     * @param buzzerNumber number of the buzzer pressed
     */
    protected abstract void buzzerAction(int buzzerNumber);

    /**
     * @return returns the name of the program
     */
    public String getName() {
        return name;
    }

    /**
     * @return returns the main control view of the program
     */
    public JPanel getMainView() {
        return mainView;
    }

    /**
     * @return returns the controller of the control view of the program
     */
    public C getProgramController() {
        return programController;
    }

    /**
     * @return returns the controller of the settings view of the program
     */
    public SC getSettingsController() {
        return settingsController;
    }

    /**
     * @return returns the model of the program
     */
    public M getProgramModel() {
        return programModel;
    }

    /**
     * @return returns the main controller of the control window
     */
    public ControlModel getControlModel() {
        return controlModel;
    }

    /**
     * @return returns the renderer for the presentation window
     */
    public OpenGlRenderer getRenderer() {
        return renderer;
    }

    /**
     * sets the main controller of the control window
     *
     * @param controlModel main controller of the control window
     */
    public void setControlModel(ControlModel controlModel) {
        this.controlModel = controlModel;
    }

    /**
     * @return returns the presentation view of the program
     */
    public P getProgramPresentationView() {
        return programPresentationView;
    }


    /**
     * @return returns the main item of the presentation view of the program
     */
    public MainItem getMainPresentationViewItem() {
        return mainItem;
    }

    /**
     * adds a closed action
     *
     * @param closedAction new closed action
     */
    public void addClosedAction(presentationWindow.engine.Action closedAction) {
        this.programClosedActions.add(closedAction);
    }

    public void programSelected() {}

    public abstract void updateBuzzerCount();
}
