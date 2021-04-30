package serialPortHandling;

/**
 * interface used to connect the serial port reader to the program
 */
public interface SerialPortReaderInterface {

    /**
     * used by the serial port reader to check if the program it is connected to is still running
     *
     * @return should return true while the serial port reader should be alive
     */
    boolean isRunning();

    /**
     * method gets called when the receiver was found while checking the serial ports and a connection has been
     * established
     *
     * @param receiverName name of the receiver
     */
    void receiverFound(String receiverName);

    /**
     * method gets called every time the serial port reader tries to find the receiver (every 100 ms)
     *
     * only gets called if no receiver is connected
     */
    void searchingForReceiver();

    /**
     * method used to handle the data received over the serial port
     *
     * @param data data received from the serial port
     */
    void handleData(String data);
}
