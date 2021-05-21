package midi;

import javax.sound.midi.*;

/**
 * handles the sending of midi signals to a connected midi device. Used to control
 * dmx light with the buzzers
 */
public class MidiHandler {


    /**
     * sends a midi message to press the executor button of the light console
     * at the position x (starting at 1, up to 16) and y (starting at 1, up to 6)
     *
     * @param x x position of the executor button which action gets performed,
     *          value from 1 to 16
     * @param y y position of the executor button which action gets performed,
     *          value form 1 to 6
     */
    public void sendMessageToPressExecuter(int x, int y) {
        int note = (x - 1) * 6 + y;
        sendMidiMessage(note);
    }


    /**
     * creates a midi message and sends it to a connected midi device
     *
     * @param note note that is pressed
     */
    private void sendMidiMessage(int note) {
        ShortMessage myMsg = createMidiMessage(note);
        sendMessage(myMsg);
    }

    /**
     * creates the midi message for the event that this note was pressed
     *
     * @param note note that was pressed
     * @return returns the build midi message
     */
    private ShortMessage createMidiMessage(int note) {
        ShortMessage myMsg = new ShortMessage();
        // Start playing the note note
        // moderately loud (velocity = 100).
        try {
            myMsg.setMessage(ShortMessage.NOTE_ON, 0, note, 100);
        } catch (InvalidMidiDataException e) {
            System.out.println("Invalid midi data");
            e.printStackTrace();
        }
        return myMsg;
    }

    /**
     * sends the midi message to a device if one is connected
     *
     * @param myMsg the midi message
     */
    private void sendMessage(ShortMessage myMsg) {
        long timeStamp = -1;
        Receiver rcvr;
        try {
            rcvr = MidiSystem.getReceiver();
            rcvr.send(myMsg, timeStamp);
        } catch (MidiUnavailableException e) {
            System.out.println("Cant find midi device");
            e.printStackTrace();
        }
    }
}
