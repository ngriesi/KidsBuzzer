package controlWindow;

import serialPortHandling.SerialPortReader;
import serialPortHandling.SerialPortReaderInterface;

import javax.swing.*;

/**
 * Class handles the serial input coming form the SerialPortReader by passing the information about the
 * receiver and buzzer presses on to the <code>BuzzerModel</code> and the current program
 */
public class BuzzerPressHandler implements SerialPortReaderInterface {

    /**
     * main model of the application
     */
    private ControlModel controlModel;

    /**
     * constructor sets the reference to the main model and creates the <code>SerialPortReader</code>
     *
     * @param controlModel reference to the main model of the application
     */
    BuzzerPressHandler(ControlModel controlModel) {
        this.controlModel = controlModel;
        new SerialPortReader(this);
    }

    /**
     * used by the <code>SerialPortReader</code> to check if the application is still running
     * so it knows when it should finish its work
     *
     * @return returns true if the application is running and not about to close
     */
    @Override
    public boolean isRunning() {
        return controlModel.isApplicationRunning();
    }

    /**
     * shows on the gui that the receiver was found
     *
     * @param receiverName name of the receiver
     */
    @Override
    public void receiverFound(String receiverName) {
        SwingUtilities.invokeLater(() -> controlModel.getBuzzerControl().changeButtonText(receiverName));
    }

    /**
     * shows on the gui that the <code>SerialPortReader</code> is searching for a receiver
     */
    @Override
    public void searchingForReceiver() {
        SwingUtilities.invokeLater(() -> controlModel.getBuzzerControl().changeButtonText("searching"));
    }

    /**
     * Handles the data coming from the <code>SerialPortReader</code>. Calls the handleBuzzerInput method
     * if the received data is an integer
     *
     * @param data data received from the serial port
     */
    @Override
    public void handleData(String data) {
        SwingUtilities.invokeLater(() -> {
            try {
                handleBuzzerInput(Integer.parseInt(data.trim()));
            } catch (NumberFormatException ignored) {
            }
        });
    }

    /**
     * Handles the Button number input coming from the SerialBuffer (Part of the Serial Port Reader). The
     * Method is called with SwingUtilities.invokeLater() for it to be able to do changes on the UI
     * <p>
     * The Method calls the methods to handle a buzzer press in the <code>BuzzerModel</code> and the
     * current program
     *
     * @param buzzerID ID of the pressed buzzer
     */
    public void handleBuzzerInput(int buzzerID) {
        if (!controlModel.getBuzzerControl().isBlockAllBuzzer()) {
            int buzzerNum = buzzerID / 2;

            boolean blockedSave = controlModel.getBuzzerControl().isBuzzerBlocked(buzzerNum);
            controlModel.getBuzzerControl().pressBuzzer(buzzerNum);
            controlModel.getCurrentProgram().handleBuzzerInput(buzzerNum, blockedSave);
        }
    }
}
