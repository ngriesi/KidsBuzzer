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
            e.printStackTrace();
        }
    }

    /**
     * listens for the initial input the receiver sends after it has finished its setup
     */
    private void listenForInitialSerialInput() throws SerialPortException {

        StringBuilder data = new StringBuilder();

        while (serialPortReader.getSerialPortReaderInterface().isRunning() && !serialPortReader.isConnected()) {
            try {
                byte[] buffer = serialPort.readBytes();
                if (buffer != null) {
                    String temp = new String(buffer);
                    data.append(temp);
                    if (data.toString().trim().equals("buzzer:kids")) {
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
