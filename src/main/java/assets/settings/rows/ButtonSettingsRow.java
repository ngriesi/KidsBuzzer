package assets.settings.rows;

import assets.standardAssets.MyButton;
import presentationWindow.engine.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Settings row with a single Button
 */
public class ButtonSettingsRow extends SettingsRow<String> implements ActionListener {

    /**
     * Action of the button
     */
    private Action action;

    /**
     * creates a new Button settings row
     *
     * @param name identification name of the settings row
     * @param description description of the settings row
     * @param buttonText text of the button
     * @param buttonAction action of the button
     */
    public ButtonSettingsRow(String name, String description, String buttonText, Action buttonAction) {
        super(name, description);

        this.action = buttonAction;

        MyButton button = new MyButton(buttonText);
        button.addActionListener(this);

        super.addInteractionElement(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        action.execute();
    }
}
