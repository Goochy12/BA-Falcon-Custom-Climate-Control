package com.liamgooch.bafalconcustomclimatecontrol;

public interface USBSerialCallbacks {
    /**
     * Function for passing serial
     * @param serial - the serial input
     */
    void serialInCallback(String serial);

    /**
     * Function for starting the serial connection
     */
    void startSerialConnection();

    /**
     * Function for getting data from serial
     */
    void getData();

    /**
     * Function for sending a command via serial
     * @param command - the string command to send
     */
    void sendCommand(String command);
}
