package assets.settings.rows;

import assets.standardAssets.MyLabel;

public class MessageSettingsRow extends SettingsRow {

    private MyLabel label;

    public MessageSettingsRow(String text) {
        label = new MyLabel(text);
        this.add(label);
    }

    @Override
    void setSetting(Object value) {

    }

    public void setText(String text) {
        label.setText(text);
    }

    public MyLabel getLabel() {
        return label;
    }
}
