package controlWindow.settings.view;

import assets.settings.general.SettingsPage;
import assets.settings.rows.ButtonSettingsRow;
import assets.settings.rows.MessageSettingsRow;
import savedataHandler.languages.Text;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Information Page for the settings used to display information and the open button for the manual
 */
public class InformationPage extends SettingsPage {


    /**
     * Settings row that displays the midi devices status
     */
    private MessageSettingsRow midiDeviceStatus;

    /**
     * creates Panel with Layout
     */
    InformationPage() {
        super(Text.INFORMATION, "info");

        midiDeviceStatus = new MessageSettingsRow(Text.NO_MIDI_DEVICE_FOUND);

        ButtonSettingsRow buttonSettingsRow = new ButtonSettingsRow("manual", Text.TO_MANUAL, Text.MANUAL, InformationPage::openManual);

        super.addRow(buttonSettingsRow);

        super.addRow(midiDeviceStatus);
    }

    public void midiDeviceFound() {
        midiDeviceStatus.setText(Text.MIDI_DEVICE_FOUND);
    }

    public void midiDeviceLost() {
        midiDeviceStatus.setText(Text.NO_MIDI_DEVICE_FOUND);
    }

    /**
     * checks if the file that is about to be created exists and calls the creation
     * method if it does not find the file.
     */
    private static void openManual() {

        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("Anleitung.pdf");

            if (in == null) {
                return;
            }

            File tempFile = File.createTempFile("test", ".pdf");
            tempFile.deleteOnExit();

            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                //copy stream
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                Desktop.getDesktop().open(tempFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
