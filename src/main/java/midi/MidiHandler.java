package midi;

import controlWindow.MainController;

import javax.sound.midi.*;

/**
 * handles the sending of midi signals to a connected midi device. Used to control
 * dmx light with the buzzers
 */
public class MidiHandler {

    /**
     * Midi device that receives the messages
     */
    private MidiDevice midiDevice;

    /**
     * Midi sequencer
     */
    private Sequencer sequencer;

    /**
     * Main controller of the program
     */
    private MainController mainController;

    /**
     * flag to tell if a midi device is connected
     */
    private boolean connected;


    public MidiHandler(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Sets up the midi system to send the messages to the light console
     */
    public void setupMidiHandler() {

        new Thread(() -> {
            try {
                midiDevice = searchMidiDevice();
                if (midiDevice != null) {
                    midiDevice.open();
                    mainController.midiDeviceFound();
                    connected = true;
                } else {
                    setupMidiHandler();
                    return;
                }

                Receiver receiver = midiDevice.getReceiver();
                sequencer = MidiSystem.getSequencer();
                sequencer.getTransmitter().setReceiver(receiver);
                sequencer.open();

                startMidiChecking();


            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Checks if the midi device is still connected
     */
    private void startMidiChecking() {

        while (mainController.getControlModel().isApplicationRunning()) {
            if (checkMidi()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                setupMidiHandler();
                mainController.midiDeviceLost();
                closeMidi();
                connected = false;
                return;
            }
        }
    }

    /**
     * checks for the midi device
     */
    private boolean checkMidi() {
        MidiDevice.Info[] devices = MidiSystem.getMidiDeviceInfo();

        for (MidiDevice.Info info : devices) {
            if (info.getName().contains("MIDIOUT2")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for the midi device
     */
    private MidiDevice searchMidiDevice() throws MidiUnavailableException {
        while (mainController.getControlModel().isApplicationRunning()) {
            MidiDevice.Info[] devices = MidiSystem.getMidiDeviceInfo();

            for (MidiDevice.Info info : devices) {
                if (info.getName().contains("MIDIOUT2")) {

                    return MidiSystem.getMidiDevice(info);

                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * sends a midi message to press the executor button of the light console
     * at the position x (starting at 1, up to 16) and y (starting at 1, up to 6)
     *
     * @param x x position of the executor button which action gets performed,
     *          value from 1 to 16
     * @param y y position of the executor button which action gets performed,
     *          value form 1 to 6
     */
    public void sendMessageToPressExecutor(int x, int y) {
        int note = (x - 1) * 6 + y;
        sendMidiMessage(note);
    }


    /**
     * creates a midi message and sends it to a connected midi device
     *
     * @param note note that is pressed
     */
    private void sendMidiMessage(int note) {

        if (connected) {

            try {

                Sequence sequence = new Sequence(Sequence.PPQ, 4);

                Track track = sequence.createTrack();

                sequencer.setSequence(sequence);

                ShortMessage shortMessage = new ShortMessage();
                shortMessage.setMessage(ShortMessage.NOTE_ON, 0, note, 1);

                MidiEvent event = new MidiEvent(shortMessage, -1);

                ShortMessage shortMessage1 = new ShortMessage();
                shortMessage1.setMessage(ShortMessage.NOTE_OFF, 0, note, 1);

                MidiEvent event1 = new MidiEvent(shortMessage1, -1);

                track.add(event);

                track.add(event1);


                sequencer.start();

            } catch (InvalidMidiDataException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * closes the midi system
     */
    public void closeMidi() {

        if (connected) {

            sequencer.close();
            midiDevice.close();

        }
    }
}
