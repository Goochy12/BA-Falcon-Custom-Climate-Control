package com.liamgooch.bafalconcustomclimatecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static androidx.core.content.ContextCompat.getSystemService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BAFalcon-Test";
    private static final String ACTION_USB_PERMISSION = "com.liamgooch.bafalconcustomclimatecontrol.permission";
    public static final int ARDUINO_VENDOR_ID = 0x2341;

    private ImageButton button_frontDefrost, button_rearDefrost, button_recycle, button_fanUp,
            button_fanDown, button_tempUp, button_tempDown, button_domeLight, button_doorLock;

    private Button button_ac, button_acMax;

    //boolean variables for button
    private Boolean button_frontDefrost_isSelected, button_rearDefrost_isSelected, button_recycle_isSelected,
            button_domeLight_isSelected, button_doorLock_isSelected, button_ac_isSelected, button_acMax_isSelected;

    private ProgressBar fanProgressBar, tempProgressBar;

    //usb serial
    UsbDevice device;
    UsbDeviceConnection usbConnection;
    UsbSerialDevice usbSerialDevice;

    UsbManager usbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //usb serial
        usbManager = (UsbManager) getSystemService(USB_SERVICE);

        //declare buttons
        button_frontDefrost = this.findViewById(R.id.button_frontDefrost);
        button_rearDefrost = findViewById(R.id.button_rearDefrost);
        button_recycle = findViewById(R.id.button_recycle);
        button_fanUp = findViewById(R.id.button_fanUp);
        button_fanDown = findViewById(R.id.button_fanDown);
        button_tempUp = findViewById(R.id.button_tempUp);
        button_tempDown = findViewById(R.id.button_tempDown);
        button_domeLight = findViewById(R.id.button_domeLight);
        button_doorLock = findViewById(R.id.button_doorLock);

        button_ac = findViewById(R.id.button_ac);
        button_acMax = findViewById(R.id.button_acMax);

        //declare progress bars
        fanProgressBar = (ProgressBar) findViewById(R.id.fanProgressBar);
        tempProgressBar = (ProgressBar) findViewById(R.id.tempProgressBar);

        //set boolean variables
        button_frontDefrost_isSelected = false;
        button_rearDefrost_isSelected = false;
        button_recycle_isSelected = false;
        button_domeLight_isSelected = false;
        button_doorLock_isSelected = false;
        button_ac_isSelected = false;
        button_acMax_isSelected = false;

        //usb serial
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        this.registerReceiver(broadcastReceiver, filter);


        //declare button listeners
        button_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAC();
            }
        });

        button_acMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAcMax();
            }
        });

        button_frontDefrost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setfrontDefrost();
            }
        });

        button_rearDefrost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRearDefrost();
            }
        });

        button_recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecycle();
            }
        });

        button_fanUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFanProgressBar(10);
            }
        });

        button_fanDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFanProgressBar(-10);
            }
        });

        button_tempUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTempProgressBar(10);
            }
        });

        button_tempDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTempProgressBar(-10);
            }
        });

        button_domeLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdomeLight();
            }
        });

        button_doorLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDoorLock();
            }
        });


        //initialise variables
        setTempProgressBar(0);
        setFanProgressBar(0);

    }

    //function getters and setters
    //get from car
    //send to car
    private void sendAC_status() {

    }

    private void getAC_status() {

    }

    private void setAC() {
        if (!getButton_ac_isSelected()) {
            button_ac.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
        } else {
            button_ac.setBackgroundColor(getResources().getColor(R.color.black));
            button_acMax.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_acMax_isSelected(false);
        }
        setButton_ac_isSelected(!getButton_ac_isSelected());
    }

    private void sendAcMax_status() {

    }

    private void getAcMax_status() {

    }

    private void setAcMax() {
        if (!button_acMax_isSelected) {
            button_acMax.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            button_ac.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_ac_isSelected(true);
        } else {
            button_acMax.setBackgroundColor(getResources().getColor(R.color.black));
        }
        button_acMax_isSelected = !button_acMax_isSelected;
    }

    private void sendfrontDefrost_status() {

    }

    private void getfrontDefrost_status() {

    }

    private void setfrontDefrost() {
        if (!button_frontDefrost_isSelected) {
            button_frontDefrost.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
        } else {
            button_frontDefrost.setBackgroundColor(getResources().getColor(R.color.black));
        }
        button_frontDefrost_isSelected = !button_frontDefrost_isSelected;
    }

    private void sendRearDefrost_status() {

    }

    private void getRearDefrost_status() {

    }

    private void setRearDefrost() {
        if (!button_rearDefrost_isSelected) {
            button_rearDefrost.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
        } else {
            button_rearDefrost.setBackgroundColor(getResources().getColor(R.color.black));
        }
        button_rearDefrost_isSelected = !button_rearDefrost_isSelected;
    }

    private void sendRecycle_status() {

    }

    private void getRecycle_status() {

    }

    private void setRecycle() {
        if (!button_recycle_isSelected) {
            button_recycle.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
        } else {
            button_recycle.setBackgroundColor(getResources().getColor(R.color.black));
        }
        button_recycle_isSelected = !button_recycle_isSelected;
    }

    private void senddomeLight_status() {

    }

    private void getdomeLight_status() {

    }

    private void setdomeLight() {
        if (!button_domeLight_isSelected) {
            button_domeLight.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
        } else {
            button_domeLight.setBackgroundColor(getResources().getColor(R.color.black));
        }
        button_domeLight_isSelected = !button_domeLight_isSelected;
    }

    private void sendDoorLock_status() {

    }

    private void getDoorLock_status() {

    }

    private void setDoorLock() {
        if (!button_doorLock_isSelected) {
            button_doorLock.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
        } else {
            button_doorLock.setBackgroundColor(getResources().getColor(R.color.black));
        }
        button_doorLock_isSelected = !button_doorLock_isSelected;
    }

    //progress bars
    public void getFanProgressBar_status() {
    }

    public void setFanProgressBar_status() {
    }

    public void setFanProgressBar(int incAmmount) {
        this.fanProgressBar.incrementProgressBy(incAmmount);
    }

    public void getTempProgressBar_status() {
    }

    public void setTempProgressBar_status() {
    }

    public void setTempProgressBar(int incAmmount) {
        this.tempProgressBar.incrementProgressBy(incAmmount);
        if (this.tempProgressBar.getProgress() > 50) {
            this.tempProgressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        } else {
            this.tempProgressBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        }
    }


    //boolean getters and setters
    public Boolean getButton_frontDefrost_isSelected() {
        return button_frontDefrost_isSelected;
    }

    public void setButton_frontDefrost_isSelected(Boolean button_frontDefrost_isSelected) {
        this.button_frontDefrost_isSelected = button_frontDefrost_isSelected;
    }

    public Boolean getButton_rearDefrost_isSelected() {
        return button_rearDefrost_isSelected;
    }

    public void setButton_rearDefrost_isSelected(Boolean button_rearDefrost_isSelected) {
        this.button_rearDefrost_isSelected = button_rearDefrost_isSelected;
    }

    public Boolean getButton_recycle_isSelected() {
        return button_recycle_isSelected;
    }

    public void setButton_recycle_isSelected(Boolean button_recycle_isSelected) {
        this.button_recycle_isSelected = button_recycle_isSelected;
    }

    public Boolean getButton_domeLight_isSelected() {
        return button_domeLight_isSelected;
    }

    public void setButton_domeLight_isSelected(Boolean button_domeLight_isSelected) {
        this.button_domeLight_isSelected = button_domeLight_isSelected;
    }

    public Boolean getButton_doorLock_isSelected() {
        return button_doorLock_isSelected;
    }

    public void setButton_doorLock_isSelected(Boolean button_doorLock_isSelected) {
        this.button_doorLock_isSelected = button_doorLock_isSelected;
    }

    public Boolean getButton_ac_isSelected() {
        return button_ac_isSelected;
    }

    public void setButton_ac_isSelected(Boolean button_ac_isSelected) {
        this.button_ac_isSelected = button_ac_isSelected;
    }

    public Boolean getButton_acMax_isSelected() {
        return button_acMax_isSelected;
    }

    public void setButton_acMax_isSelected(Boolean button_acMax_isSelected) {
        this.button_acMax_isSelected = button_acMax_isSelected;
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
                            usbSerialDevice.setBaudRate(9600);
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
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    };

    private void startUSBConnection() {
        //arduino
        HashMap<String, UsbDevice> usbDeviceHashMap = usbManager.getDeviceList();
        if (!usbDeviceHashMap.isEmpty()) {
            for (Map.Entry<String, UsbDevice> entry : usbDeviceHashMap.entrySet()) {
                int deviceID = entry.getValue().getDeviceId();
                String deviceSerial = entry.getValue().getSerialNumber();

                int deviceVID = entry.getValue().getVendorId();
                if (deviceVID == ARDUINO_VENDOR_ID) {
                    this.device = entry.getValue();
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
                    usbManager.requestPermission(device, pendingIntent);

//                    tvAppend("Connection established to: " + deviceID);

                    Log.i(TAG, "deviceid: " + deviceID + ", serial: " + deviceSerial);

//                    setUI(true);

                    break;
                }else{
                    this.usbConnection = null;
                    this.device = null;
                }
            }
        }
    }

    private void sendData(String d) {
        usbSerialDevice.write(d.getBytes());
//        tvAppend(textView_output,"\n data sent: " + d + "\n");
    }

    private void closeConnection() {
        usbSerialDevice.close();
//        setUI(false);
//        tvAppend(textView_output,"\n closed connection \n");
    }
}