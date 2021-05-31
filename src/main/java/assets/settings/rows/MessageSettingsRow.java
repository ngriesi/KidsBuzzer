package assets.settings.rows;

import assets.standardAssets.MyLabel;

/**
 * Message settings row used to display a Message in the settings
 */
public class MessageSettingsRow extends SettingsRow {

    /**
     * label used to display the message
     */
    private MyLabel label;

    /**
     * constructor creating a message row
     *
     * @param text text of the message
     */
    public MessageSettingsRow(String text) {
        super("");
        label = new MyLabel(text);
        this.add(label);
    }

    /**
     * method updates the content of the message
     *
     * @param text new content of the message
     */
    public void setText(String text) {
        label.setText(text);
    }

    /**
     * getter to access the label
     *
     * @return returns the label
     */
    public MyLabel getLabel() {
        return label;
    }
}
