package programs.quizOverlay.control.view;

import assets.standardAssets.MyButton;
import assets.standardAssets.StandardAssetFields;
import programs.abstractProgram.ProgramControllerView;
import programs.quizOverlay.control.control.QuizOverlayProgramController;
import programs.quizOverlay.control.control.SimpleOutputView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static java.awt.GridBagConstraints.*;

/**
 * control view of the quiz time program
 */
public class QuizOverlayControlView extends ProgramControllerView<QuizOverlayProgramController> {

    /**
     * size of the buttons
     */
    private Dimension buttonSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 8, Toolkit.getDefaultToolkit().getScreenSize().height / 20);

    /**
     * creates a new view
     *
     * @param programController sets the controller of the view
     */
    @SuppressWarnings("SpellCheckingInspection")
    public QuizOverlayControlView(QuizOverlayProgramController programController) {
        super(programController);

        this.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        setupKeyBindings(programController);


        GridBagConstraints gc = new GridBagConstraints(2, 0, 1, 2, 1, 1, CENTER, BOTH, new Insets(0, 0, 0, 0), 0, 0);

        SimpleOutputView simpleOutputView = programController.getSimpleOutputView();
        this.add(new SimpleViewPanel(simpleOutputView), gc);


        gc.gridx = 0;
        gc.weighty = 0.5f;
        gc.gridwidth = 2;
        gc.gridheight = 1;
        gc.fill = NONE;
        addButton("Einstellungen", "settings", programController, gc);

        gc.gridy = 1;
        addButton("Anzeigen", "show", programController, gc);

        gc.gridy = 2;
        addButton("Falsch", "wrong", programController, gc);

        gc.gridx = 2;
        addButton("Richtig", "right", programController, gc);

        gc.gridy = 3;
        addButton("Nächste Frage", "next", programController, gc);
    }

    /**
     * sets up the key bindings for this panel
     *
     * @param programController reference to the controller to call the action methods
     */
    private void setupKeyBindings(QuizOverlayProgramController programController) {

        addKeyAction(KeyStroke.getKeyStroke("R"), programController::rightButtonAction);
        addKeyAction(KeyStroke.getKeyStroke("F"), programController::wrongButtonAction);
        addKeyAction(KeyStroke.getKeyStroke("N"), programController::nextButtonAction);
        addKeyAction(KeyStroke.getKeyStroke("A"), programController::show);
        addKeyAction(KeyStroke.getKeyStroke("V"), programController::hide);
        addKeyAction(KeyStroke.getKeyStroke("ESCAPE"), programController::hide);

    }


    /**
     * adds a control button to the view
     *
     * @param buttonText     text of the button
     * @param actionCommand  action command of the button
     * @param actionListener action listener of the button
     * @param gbc            constraint of the button
     */
    private void addButton(String buttonText, String actionCommand, ActionListener actionListener, GridBagConstraints gbc) {
        MyButton button = new MyButton(buttonText);
        button.setActionCommand(actionCommand);
        button.addActionListener(actionListener);
        button.setPreferredSize(buttonSize);
        this.add(button, gbc);
    }
}
