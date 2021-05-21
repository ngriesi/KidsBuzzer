package controlWindow;

import remoteHandler.RemoteHandler;
import savedataHandler.SaveDataHandler;
import savedataHandler.languages.Text;
import serialPortHandling.SerialPortReader;
import serialPortHandling.SerialPortReaderInterface;

import javax.swing.*;
import java.util.regex.Pattern;

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
     * String replacement pattern
     */
    private Pattern pattern = Pattern.compile("\\s+");

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
        SwingUtilities.invokeLater(() -> controlModel.getBuzzerControl().changeButtonText(Text.SEARCHING));
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
            String temp = pattern.matcher(data).replaceAll("");
            try {
                if (temp.length() == 1) {
                    handleBuzzerInput(Integer.parseInt(temp));
                } else {
                    Integer.parseInt(temp);
                    for (char c : data.toCharArray()) {
                        handleBuzzerInput(Integer.parseInt("" + c));
                    }
                }
            } catch (NumberFormatException ignored) {
                performMultipleActions(temp);
            }
        });
    }

    /**
     * called when the input from the buzzers is not just a number but also contains
     * chars
     *
     * @param data data coming from the serial port
     */
    private void performMultipleActions(String data) {
        for (char c : data.toCharArray()) {
            try {
                handleBuzzerInput(Integer.parseInt("" + c));
            } catch (NumberFormatException e) {
                handleRemotePress(c);
            }
        }
    }

    /**
     * Handles a char input coming from the serial buffer. This char corresponds to a
     * button on the remote that was pressed.
     *
     * @param c button that was pressed
     */
    private void handleRemotePress(char c) {
        controlModel.getCurrentProgram().remotePressedAction(getRemoteButton(c));
    }

    /**
     * returns the button of a remote from a char:
     * a = TOP_LEFT
     * b = TOP_RIGHT
     * c = BOTTOM_LEFT
     * d = BOTTOM_RIGHT
     *
     * @param c char that comes from the serial input
     * @return returns the button this char represents
     */
    private RemoteHandler.RemoteButton getRemoteButton(char c) {
        switch (c) {
            case 'a':
                return RemoteHandler.RemoteButton.TOP_LEFT;
            case 'b':
                return RemoteHandler.RemoteButton.TOP_RIGHT;
            case 'c':
                return RemoteHandler.RemoteButton.BOTTOM_LEFT;
            case 'd':
                return RemoteHandler.RemoteButton.BOTTOM_RIGHT;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Handles the Buzzer number input coming from the SerialBuffer (Part of the Serial Port Reader). The
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

            if(buzzerNum <= SaveDataHandler.BUZZER_COUNT) {
                boolean blockedSave = controlModel.getBuzzerControl().isBuzzerBlocked(buzzerNum);
                controlModel.getBuzzerControl().pressBuzzer(buzzerNum);
                controlModel.getCurrentProgram().handleBuzzerInput(buzzerNum, blockedSave);
            }
        }
    }
}
