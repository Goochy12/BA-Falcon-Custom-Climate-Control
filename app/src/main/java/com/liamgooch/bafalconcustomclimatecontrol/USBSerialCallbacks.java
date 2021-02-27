package com.liamgooch.bafalconcustomclimatecontrol;

public interface USBSerialCallbacks {
    void serialInCallback(String serial);

    void startSerialConnection();
}
