package com.liamgooch.bafalconcustomclimatecontrol;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.USB_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class UsbSerial {
    //callback
    //on receive
    //broadcast receiver
    //write

    private static final String ACTION_USB_PERMISSION = "com.liamgooch.bafalconcustomclimatecontrol.permission";
    public static final int ARDUINO_VENDOR_ID = 0x2341;
    private static final String TAG = "BAFalcon-Test";

    private String serialIn = null;

    Context context = null;
    private USBSerialCallbacks _serialCalls = null;

    //usb serial
    UsbDevice device;
    UsbDeviceConnection usbConnection;
    UsbSerialDevice usbSerialDevice;

    UsbManager usbManager;

    public UsbSerial(Context context, USBSerialCallbacks _serialCalls){

        this.context = context;
        this._serialCalls = _serialCalls;

        usbManager = (UsbManager) context.getSystemService(USB_SERVICE);

        //usb serial
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        context.registerReceiver(broadcastReceiver, filter);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        //to start and stop connection
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_USB_PERMISSION)) {
                boolean granted = intent.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                if (granted) {
                    usbConnection = usbManager.openDevice(device);
                    usbSerialDevice = UsbSerialDevice.createUsbSerialDevice(device, usbConnection);
                    if (usbSerialDevice != null) {
                        if (usbSerialDevice.open()) {
//                            setUI(true);
                            Log.i(TAG, "onReceive: usb serial device created");
                            usbSerialDevice.setBaudRate(115200);
                            usbSerialDevice.setDataBits(UsbSerialInterface.DATA_BITS_8);
                            usbSerialDevice.setStopBits(UsbSerialInterface.STOP_BITS_1);
                            usbSerialDevice.setParity(UsbSerialInterface.PARITY_NONE);
                            usbSerialDevice.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                            usbSerialDevice.read(mCallback);
                            Log.i(TAG, "onReceive: connection opened");
//                            tvAppend(textView_output,"\n Connection established \n");
                        } else {
                            Log.i(TAG, "onReceive: port not open");

                        }
                    } else {
                        Log.i(TAG, "onReceive: port is null");
                    }
                } else {
                    Log.i(TAG, "onReceive: permission not granted");
                }
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
                //start
                startUSBConnection();
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
                //stop
                closeConnection();
            }
        }
    };

    UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() {
        @Override
        public void onReceivedData(byte[] arg0) {
            String recData = null;
            try {
                recData = new String(arg0, "UTF-8");
//                recData.concat("/n");
//                tvAppend(textView_output, recData);
                _serialCalls.serialInCallback(recData);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    };

    public boolean startUSBConnection() {
        //arduino
        HashMap<String, UsbDevice> usbDeviceHashMap = usbManager.getDeviceList();
        if (!usbDeviceHashMap.isEmpty()) {
            for (Map.Entry<String, UsbDevice> entry : usbDeviceHashMap.entrySet()) {
                int deviceID = entry.getValue().getDeviceId();
                String deviceSerial = entry.getValue().getSerialNumber();

                int deviceVID = entry.getValue().getVendorId();
                if (deviceVID == ARDUINO_VENDOR_ID) {
                    this.device = entry.getValue();
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, new Intent(ACTION_USB_PERMISSION), 0);
                    usbManager.requestPermission(device, pendingIntent);

//                    tvAppend("Connection established to: " + deviceID);

                    Log.i(TAG, "deviceid: " + deviceID + ", serial: " + deviceSerial);

//                    setUI(true);

                    return true;
                }else{
                    this.usbConnection = null;
                    this.device = null;
                }
            }
        }
        return false;
    }

    public void sendData(String d) {
        usbSerialDevice.write(d.getBytes());
//        tvAppend(textView_output,"\n data sent: " + d + "\n");
    }

    private void closeConnection() {
        usbSerialDevice.close();
//        setUI(false);
//        tvAppend(textView_output,"\n closed connection \n");
    }
}
