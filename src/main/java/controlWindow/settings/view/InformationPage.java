package controlWindow.settings.view;

import assets.settings.general.SettingsPage;
import assets.settings.rows.MessageSettingsRow;
import savedataHandler.languages.Text;

public class InformationPage extends SettingsPage {


    private MessageSettingsRow midiDeviceStatus;

    /**
     * creates Panel with Layout
     */
    InformationPage() {
        super(Text.INFORMATION, "info");

        midiDeviceStatus = new MessageSettingsRow(Text.NO_MIDI_DEVICE_FOUND);

        super.addRow(midiDeviceStatus);
    }

    public void midiDeviceFound() {
        midiDeviceStatus.setText(Text.MIDI_DEVICE_FOUND);
    }

    public void midiDeviceLost() {
        midiDeviceStatus.setText(Text.NO_MIDI_DEVICE_FOUND);
    }
}
