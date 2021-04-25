package serialPortHandling;

import controlWindow.ControlModel;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class reads the input form a serial USB port and uses it to update the buzzer states
 */
public class SerialPortReader {

    /**
     * reference to the main application logic
     */
    private ControlModel controlModel;

    /**
     * true if the receiver is connected and recognized
     */
    private boolean connected;

    /**
     * port the receiver is currently connected
     */
    private SerialPortHandler currentPort;

    /**
     * list of currently running serial port handler
     */
    private List<SerialPortHandler> serialPortHandlerList;


    /**
     * creates a new Thread to listen for inputs from the serial port
     *
     * @param controlModel reference to the main application logic
     */
    public SerialPortReader(ControlModel controlModel) {
        this.controlModel = controlModel;

        serialPortHandlerList = new ArrayList<>();

       new Thread(this::checkSerialPorts).start();
    }

    private void checkSerialPorts() {
        while (controlModel.isApplicationRunning()) {
            try {
                if (!connected) {
                    searchReceiver();
                    Thread.sleep(100);
                } else {
                    checkIfReceiverIsStillConnected();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * checks if the receiver is still connected
     *
     * @throws InterruptedException when the sleeping was interrupted
     */
    private void checkIfReceiverIsStillConnected() throws InterruptedException {


        try {
            if(!currentPort.getSerialPort().writeByte((byte) 1)) {
                receiverDisconnected();
            } else {

                Thread.sleep(1000);
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    /**
     * action performed when the receiver was disconnected
     */
    private void receiverDisconnected() {
        this.connected = false;
        serialPortHandlerList.clear();
        try {
            currentPort.getSerialPort().closePort();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
        currentPort = null;

    }

    /**
     * chooses the serial port by asking the available devices for a code
     */
    private void searchReceiver() {

        SwingUtilities.invokeLater(() -> controlModel.searchingForReceiver());


        String[] ports;
        try {

            ports = SerialPortList.getPortNames();
        } catch (NullPointerException n) {
            return;
        }

        for (String port : ports) {
            if(!searchingOnPortsStarted(port)) {
                serialPortHandlerList.add(new SerialPortHandler(port, this));
            }
        }
    }

    /**
     * checks if there is already a handler taking care of the port with this name
     *
     * @param portName name of the port that is checked
     * @return true if there is already a handler
     */
    private boolean searchingOnPortsStarted(String portName) {
        for (SerialPortHandler serialPortHandler : serialPortHandlerList) {
            if(serialPortHandler.getPortName().equals(portName)) return true;
        }
        return false;
    }


    /**
     * action when a receiver was found
     *
     * @param serialPortHandler handler of the port with the receiver
     * @param receiverName name of the receiver
     */
    void receiverFound(SerialPortHandler serialPortHandler, String receiverName) {
        connected = true;
        currentPort = serialPortHandler;
        new Thread(() -> listenForSerialInputs(serialPortHandler.getSerialPort())).start();
        SwingUtilities.invokeLater(() -> controlModel.receiverFound(receiverName));

    }

    /**
     * Method listens for serial inputs. It gets called by a separate Thread
     *
     * @param serialPort serial Port the method is listening on
     */
    private void listenForSerialInputs(SerialPort serialPort) {
        while (connected && controlModel.isApplicationRunning()) {
            try {
                byte[] buffer = serialPort.readBytes();
                if (buffer != null) {
                    String data = new String(buffer);
                    System.out.println(data);
                    updateBuzzerState(data);
                } else {
                    Thread.sleep(1);
                }
            } catch (SerialPortException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * calls the method to update the buzzer states in the ControlModel class within the main Swing thread
     *
     * @param data data received through the serial port
     */
    private void updateBuzzerState(final String data) {
        SwingUtilities.invokeLater(() -> {
            try {
                controlModel.handleBuzzerInput(Integer.parseInt(data.trim()));
            } catch (NumberFormatException ignored) {
            }
        });
    }

    ControlModel getControlModel() {
        return controlModel;
    }

    boolean isConnected() {
        return connected;
    }
}
