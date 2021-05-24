package programs.quiztime.control.view;

import assets.standardAssets.MyButton;
import assets.standardAssets.MyLabel;
import assets.standardAssets.MyTextField;
import assets.standardAssets.StandardAssetFields;
import programs.quizPrograms.control.view.QuizControlView;
import programs.quizPrograms.control.view.SimpleViewPanel;
import programs.quiztime.control.control.QuizTimeProgramControlController;
import programs.quiztime.control.control.SimpleOutputView;
import savedataHandler.languages.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.GridBagConstraints.*;

/**
 * control view of the quiz time program
 */
public class QuizTimeProgramControlView extends QuizControlView<QuizTimeProgramControlController> {

    /**
     * text field containing the number of the next question
     */
    private MyTextField textField;

    /**
     * creates a new view
     *
     * @param programController sets the controller of the view
     */
    @SuppressWarnings("SpellCheckingInspection")
    public QuizTimeProgramControlView(QuizTimeProgramControlController programController) {
        super(programController);

        this.setBackground(StandardAssetFields.PANEL_BACKGROUND_COLOR);


        GridBagConstraints gc = new GridBagConstraints(0, 3, 1, 1, 0.5f, 0.5f, CENTER, NONE, new Insets(0, 0, 0, 0), 0, 0);

        MyLabel nextQuestion = new MyLabel(Text.NEXT_QUESTION);
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
     * @return returns the text field containing the number of the next question
     */
    public MyTextField getTextField() {
        return textField;
    }
}
