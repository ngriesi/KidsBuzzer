package assets.standardAssets;

import assets.standardAssets.documentFilter.MyIntegerFilter;
import assets.standardAssets.documentFilter.MySizeFilter;
import presentationWindow.engine.Action;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyTextField extends JTextField {

    private Action onChangedAction;

    private String actionCommand = "defaultActionCommand";

    private boolean useOnlyInts = false;

    public MyTextField(String text) throws HeadlessException {
        super(text);

        this.setForeground(StandardAssetFields.FOREGROUND_COLOR);
        this.setBackground(StandardAssetFields.NORMAL_COLOR);
        this.setCaretColor(StandardAssetFields.FOREGROUND_COLOR);
        this.setSelectionColor(StandardAssetFields.ROLLOVER_COLOR);
        this.setBorder(BorderFactory.createLineBorder(StandardAssetFields.NORMAL_COLOR,3));
        this.setFont(new Font("arial",Font.PLAIN,30));
        this.setPreferredSize(new Dimension(200,40));

        this.getDocument().addDocumentListener(new DocumentListener() {
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

            private void changedAction() {
                if (useOnlyInts) {
                    if (MyTextField.this.getText().equals("")) {
                        return;
                    }
                }
                for(ActionListener a: MyTextField.this.getActionListeners()) {
                    a.actionPerformed(new ActionEvent(MyTextField.this, ActionEvent.ACTION_PERFORMED, actionCommand) {
                        //Nothing need go here, the actionPerformed method (with the
                        //above arguments) will trigger the respective listener
                    });
                }
                if (onChangedAction != null) {
                    onChangedAction.execute();
                }
            }
        });
    }

    public void useOnlyInts() {
        ((PlainDocument)this.getDocument()).setDocumentFilter(new MyIntegerFilter());
        useOnlyInts = true;
    }

    public void setOnChangedAction(Action onChangedAction) {
        this.onChangedAction = onChangedAction;
    }

    @Override
    public void setActionCommand(String command) {
        this.actionCommand = command;
        super.setActionCommand(command);
    }

    public void setMaximumLength(int i) {
        ((PlainDocument) this.getDocument()).setDocumentFilter(new MySizeFilter(i));
    }
}
