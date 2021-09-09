package serialPortHandling;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * class used for handling the checking of one serial port. It checks if it receives the expected
 * initial message
 */
class SerialPortHandler {

    /**
     * name of this serial port
     */
    private String portName;

    /**
     * reader this handler belongs to
     */
    private SerialPortReader serialPortReader;

    /**
     * serial port object of this handler
     */
    private SerialPort serialPort;

    /**
     * creates a SerialPortHandler and starts checking if this port has the receiver connected
     *
     * @param portName         name of the port
     * @param serialPortReader reader this handler belongs to
     */
    SerialPortHandler(String portName, SerialPortReader serialPortReader) {
        this.portName = portName;
        this.serialPortReader = serialPortReader;

        new Thread(this::checkPort).start();
    }

    /**
     * checks if the receiver is connected to this port
     */
    private void checkPort() {
        try {
            createAndSetupPort();
            listenForInitialSerialInput();
        } catch (SerialPortException e) {
            System.out.println("Port opening failed");
            restartSearchingForThisPort();
        }
    }

    /**
     * restarts the searching for this port if the previous try failed
     */
    private void restartSearchingForThisPort() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!serialPortReader.isConnected()) {
            serialPortReader.removeSerialPortHandler(this);
            serialPortReader.searchReceiver();
        }
    }

    /**
     * listens for the initial input the receiver sends after it has finished its setup
     *
     * @throws SerialPortException if the serial port cant be red
     */
    private void listenForInitialSerialInput() throws SerialPortException {

        StringBuilder data = new StringBuilder();

        while (serialPortReader.getSerialPortReaderInterface().isRunning() && !serialPortReader.isConnected()) {
            try {
                byte[] buffer = serialPort.readBytes();
                if (buffer != null) {
                    String temp = new String(buffer);
                    data.append(temp);
                    System.out.println("Initial Value: " + data.toString());
                    if (data.toString().trim().contains("buzzer:kids")) {
                        serialPortReader.receiverFound(this, data.toString().split(":")[1]);
                        return;
                    }
                }
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }

        serialPort.closePort();
    }

    /**
     * creates the port
     *
     * @throws SerialPortException if the serial port cant be set up
     */
    private void createAndSetupPort() throws SerialPortException {
        serialPort = new SerialPort(portName);
        serialPort.openPort();
        serialPort.setParams(9600, 8, 1, 0);
        serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
    }

    /**
     * @return returns the serial port of this handler
     */
    SerialPort getSerialPort() {
        return serialPort;
    }

    /**
     * @return returns the serial port name of this handler
     */
    String getPortName() {
        return portName;
    }
}
