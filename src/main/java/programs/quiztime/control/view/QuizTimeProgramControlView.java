package programs.quiztime.control.view;

import assets.standardAssets.MyButton;
import assets.standardAssets.MyLabel;
import assets.standardAssets.MyTextField;
import assets.standardAssets.StandardAssetFields;
import programs.abstractProgram.ProgramView;
import programs.quiztime.control.control.QuizTimeProgramControlController;
import programs.quiztime.control.control.SimpleOutputView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.GridBagConstraints.*;

/**
 * control view of the quiz time program
 */
public class QuizTimeProgramControlView extends ProgramView {

    /**
     * text field containing the number of the next question
     */
    private MyTextField textField;

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
    public QuizTimeProgramControlView(QuizTimeProgramControlController programController) {
        super(programController);

        this.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);

        setupInputMap();

        setupActionMap(programController);


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

        gc.gridx = 0;
        gc.weightx = 0.5f;
        gc.gridwidth = 1;
        MyLabel nextQuestion = new MyLabel("Nächste Frage:");
        this.add(nextQuestion, gc);

        gc.gridx = 1;

        createTextField(gc, programController);
    }

    /**
     * creates the text field containing the number of the next question
     *
     * @param gc             constraint of the text field
     * @param actionListener action listener of the text field
     */
    private void createTextField(GridBagConstraints gc, ActionListener actionListener) {
        textField = new MyTextField("1");
        textField.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 20, Toolkit.getDefaultToolkit().getScreenSize().height / 20));
        textField.useOnlyInts();
        textField.addActionListener(actionListener);
        textField.setActionCommand("number");
        this.add(textField, gc);
    }

    /**
     * sets up the action map for this panel
     *
     * @param programController reference to the controller to call the action methods
     */
    private void setupActionMap(QuizTimeProgramControlController programController) {
        this.getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                programController.rightButtonAction();
            }
        });

        this.getActionMap().put("wrong", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                programController.wrongButtonAction();
            }
        });

        this.getActionMap().put("next", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                programController.nextButtonAction();
            }
        });

        this.getActionMap().put("show", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                programController.show();
            }
        });

        this.getActionMap().put("hide", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                programController.hide();
            }
        });
    }

    /**
     * sets up the input map of this panel
     */
    private void setupInputMap() {
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("R"), "right");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F"), "wrong");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("N"), "next");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "show");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("V"), "hide");
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "hide");
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

    /**
     * @return returns the text field containing the number of the next question
     */
    public MyTextField getTextField() {
        return textField;
    }
}
