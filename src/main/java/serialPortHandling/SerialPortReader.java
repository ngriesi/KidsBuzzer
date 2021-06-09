package serialPortHandling;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import java.util.ArrayList;
import java.util.List;

/**
 * This class reads the input form a serial USB port and uses it to update the buzzer states
 */
public class SerialPortReader {

    /**
     * reference to the main application logic
     */
    private SerialPortReaderInterface serialPortReaderInterface;

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
     * @param serialPortReaderInterface reference to the main application logic
     */
    public SerialPortReader(SerialPortReaderInterface serialPortReaderInterface) {
        this.serialPortReaderInterface = serialPortReaderInterface;

        serialPortHandlerList = new ArrayList<>();

        new Thread(this::checkSerialPorts).start();
    }

    /**
     * checks all available serial ports for the receiver
     */
    private void checkSerialPorts() {
        while (serialPortReaderInterface.isRunning()) {
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
            if (!currentPort.getSerialPort().writeByte((byte) 1)) {
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
    void searchReceiver() {

        serialPortReaderInterface.searchingForReceiver();


        String[] ports;
        try {

            ports = SerialPortList.getPortNames();
        } catch (NullPointerException n) {
            return;
        }

        for (String port : ports) {
            if (!searchingOnPortsStarted(port)) {
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
            if (serialPortHandler.getPortName().equals(portName)) return true;
        }
        return false;
    }


    /**
     * action when a receiver was found
     *
     * @param serialPortHandler handler of the port with the receiver
     * @param receiverName      name of the receiver
     */
    void receiverFound(SerialPortHandler serialPortHandler, String receiverName) {
        connected = true;
        currentPort = serialPortHandler;
        new Thread(() -> listenForSerialInputs(serialPortHandler.getSerialPort())).start();
        serialPortReaderInterface.receiverFound(receiverName);

    }

    /**
     * Method listens for serial inputs. It gets called by a separate Thread
     *
     * @param serialPort serial Port the method is listening on
     */
    private void listenForSerialInputs(SerialPort serialPort) {
        while (connected && serialPortReaderInterface.isRunning()) {
            try {
                byte[] buffer = serialPort.readBytes();
                if (buffer != null) {
                    String data = new String(buffer);
                    System.out.println(data);
                    serialPortReaderInterface.handleData(data);
                } else {
                    Thread.sleep(1);
                }
            } catch (SerialPortException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return returns true if the connection to the receiver is established
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isConnected() {
        return connected;
    }

    /**
     * @return returns the interface used to connect the serial port reader to the program
     */
    SerialPortReaderInterface getSerialPortReaderInterface() {
        return serialPortReaderInterface;
    }

    /**
     * removes a serial port handler form the list
     *
     * @param serialPortHandler serial port handler that gets removed
     */
    void removeSerialPortHandler(SerialPortHandler serialPortHandler) {
        serialPortHandlerList.remove(serialPortHandler);
    }
}
