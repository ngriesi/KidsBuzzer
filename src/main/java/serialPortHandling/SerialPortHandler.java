package serialPortHandling;

import jssc.SerialPort;
import jssc.SerialPortException;

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
     * @param portName name of the port
     * @param serialPortReader reader this handler belongs to
     */
    SerialPortHandler(String portName, SerialPortReader serialPortReader) {
        this.portName = portName;
        this.serialPortReader = serialPortReader;

        new Thread(this::checkPort).start();
    }

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

        while (serialPortReader.getControlModel().isApplicationRunning() && !serialPortReader.isConnected()) {
            try {
                byte[] buffer = serialPort.readBytes();
                if (buffer != null) {
                    String temp = new String(buffer);
                    data.append(temp);
                    if (data.toString().trim().equals("buzzer:kids")) {
                        serialPortReader.receiverFound(this,data.toString().split(":")[1]);
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

    SerialPort getSerialPort() {
        return serialPort;
    }

    String getPortName() {
        return portName;
    }
}
