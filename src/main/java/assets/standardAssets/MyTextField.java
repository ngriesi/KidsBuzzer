package assets.standardAssets;

import assets.standardAssets.documentFilter.MyIntegerFilter;
import assets.standardAssets.documentFilter.MySizeFilter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * custom text field with a custom layout
 * it also posts an action event if its text changes
 */
public class MyTextField extends JTextField {

    /**
     * action command of this component
     */
    private String actionCommand = "defaultActionCommand";

    /**
     * flag that determines if the text filed only allows integers
     */
    private boolean useOnlyInts = false;

    /**
     * creates a new text field with a start text
     *
     * @param text start text of the text field
     */
    public MyTextField(String text) {
        super(text);

        setColors();
        setLayoutValues();

        this.getDocument().addDocumentListener(createDocumentListener());
    }

    /**
     * sets the layout values of this text field
     */
    private void setLayoutValues() {
        this.setBorder(BorderFactory.createLineBorder(StandardAssetFields.NORMAL_COLOR, 3));
        this.setFont(new Font("Yu Gothic Ui", Font.PLAIN, 30));
        this.setPreferredSize(new Dimension(200, 40));
    }

    /**
     * sets the colors of this text field
     */
    private void setColors() {
        this.setForeground(StandardAssetFields.FOREGROUND_COLOR);
        this.setBackground(StandardAssetFields.NORMAL_COLOR);
        this.setCaretColor(StandardAssetFields.FOREGROUND_COLOR);
        this.setSelectionColor(StandardAssetFields.ROLLOVER_COLOR);
    }

    /**
     * creates the document listener of the text field that creates the
     * action events
     *
     * @return returns the document listener used to create the action
     * events
     */
    private DocumentListener createDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedAction();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedAction();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changedAction();

            }
        };
    }

    /**
     * action performed when the document behind this text field got changed
     */
    private void changedAction() {
        if (useOnlyInts) {
            if (MyTextField.this.getText().equals("")) {
                return;
            }
        }
        for (ActionListener a : MyTextField.this.getActionListeners()) {
            a.actionPerformed(new ActionEvent(MyTextField.this, ActionEvent.ACTION_PERFORMED, actionCommand));
        }
    }

    /**
     * method tells the text field to only allow ints
     */
    public void useOnlyInts() {
        ((PlainDocument) this.getDocument()).setDocumentFilter(new MyIntegerFilter());
        useOnlyInts = true;
    }

    /**
     * sets the action command of this component
     *
     * @param command new action command of this component
     */
    @Override
    public void setActionCommand(String command) {
        this.actionCommand = command;
        super.setActionCommand(command);
    }

    /**
     * sets a maximum length for the text field
     *
     * @param maximumLength maximum length of the text field
     */
    public void setMaximumLength(int maximumLength) {
        ((PlainDocument) this.getDocument()).setDocumentFilter(new MySizeFilter(maximumLength));
    }
}
