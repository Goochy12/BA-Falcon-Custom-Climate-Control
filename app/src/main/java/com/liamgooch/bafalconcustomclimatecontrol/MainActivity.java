package com.liamgooch.bafalconcustomclimatecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements USBSerialCallbacks {

    private static final String TAG = "BAFalcon-Test";
    private static final String ac_string = "ac";
    private static final String fan_up_string = "fan_up";
    private static final String fan_down_string = "fan_down";
    private static final String temp_up_string = "temp_up";
    private static final String temp_down_string = "temp_down";
    private static final String face_string = "face";
    private static final String feet_string = "feet";
    private static final String front_demist_string = "front_demist";
    private static final String rear_demist_string = "rear_demist";
    private static final String closed_cabin_string = "closed_cabin";
    private static final String open_cabin_string = "open_cabin";
    private static final String cabin_cycle_string = "cabin_cycle";
    private static final String dome_light_string = "dome_light";
    private static final String door_lock_string = "door_lock";

    private UsbSerial usbSerial;

    private ImageButton button_frontDemist, button_rearDemist, button_cabin_cycle, button_fanUp,
            button_fanDown, button_tempUp, button_tempDown, button_domeLight, button_doorLock;

    private Button button_ac, button_acMax;

    //boolean variables for button
    private Boolean button_frontDemist_isSelected, button_rearDemist_isSelected, button_cabin_cycle_isSelected, button_ac_isSelected, button_acMax_isSelected;

    private ProgressBar fanProgressBar, tempProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //declare buttons
        button_frontDemist = this.findViewById(R.id.button_frontDemist);
        button_rearDemist = findViewById(R.id.button_rearDemist);
        button_cabin_cycle = findViewById(R.id.button_cabin_cycle);
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
        button_frontDemist_isSelected = false;
        button_rearDemist_isSelected = false;
        button_cabin_cycle_isSelected = false;
        button_ac_isSelected = false;
        button_acMax_isSelected = false;

        //declare button listeners
        button_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAC_status();
                setAC(!getButton_ac_isSelected());
            }
        });

        button_acMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAcMax(!getButton_acMax_isSelected());
                if(getButton_acMax_isSelected()){
                    setTempProgressBar(0);
                }else {
                    setTempProgressBar(1);
                }
            }
        });

        button_frontDemist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendfrontDemist_status();
                setfrontDemist(!getButton_frontDemist_isSelected());
            }
        });

        button_rearDemist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRearDemist_status();
                setRearDemist(!getButton_rearDemist_isSelected());
            }
        });

        button_cabin_cycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCabinCycle_status();
                setCabinCycle(!getButton_cabin_cycle_isSelected());
            }
        });

        button_fanUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementFanProgressBar(1);
                sendFanUp_status();
            }
        });

        button_fanDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementFanProgressBar(-1);
                sendFanDown_status();
            }
        });

        button_tempUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementTempProgressBar(1);
                sendtempUp_status();
            }
        });

        button_tempDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementTempProgressBar(-1);
                sendtempDown_status();
            }
        });

        button_domeLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senddomeLight_status();
            }
        });

        button_doorLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDoorLock_status();
            }
        });

        setStartState();

        //usb serial

        this.usbSerial = new UsbSerial(this.getApplicationContext(), this);
        this.usbSerial.startUSBConnection();
        boolean startedSuccessfully = false;
        startedSuccessfully = this.usbSerial.startUSBConnection();
        if (!startedSuccessfully) {
            Toast.makeText(this, "USB SERIAL FAILED TO START", Toast.LENGTH_LONG).show();
            setDisableState();
        }
//
//        while (!startedSuccessfully){
//            startedSuccessfully = usbSerial.startUSBConnection();
//            if(!startedSuccessfully){
//                Toast toast = Toast.makeText(this, "USB SERIAL FAILED TO START", Toast.LENGTH_LONG).show();
//                toast.show()
//            }
//        }
    }

    private void setDisableState() {

    }

    private void setStartState() {
        //initialise variables
        setTempProgressBar(1);
        incrementFanProgressBar(0);
        setAC(true);
    }

    //function getters and setters
    //get from car
    //send to car
    private void sendAC_status() {
        sendData(ac_string);
    }

    private void setAC(boolean select) {
        if (select) {
            button_ac.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_ac_isSelected(true);
        } else {
            button_ac.setBackgroundColor(getResources().getColor(R.color.black));
            setAcMax(false);
            setButton_ac_isSelected(false);
        }
    }

    private void setAcMax(boolean select) {
        if (select) {
            button_acMax.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setAC(true);
            setButton_acMax_isSelected(true);
        } else {
            button_acMax.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_acMax_isSelected(false);
        }
    }

    private void sendfrontDemist_status() {
        sendData(front_demist_string);
    }

    private void setfrontDemist(boolean select) {
        if (select) {
            button_frontDemist.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_frontDemist_isSelected(true);
        } else {
            button_frontDemist.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_frontDemist_isSelected(false);
        }
    }

    private void sendRearDemist_status() {
        sendData(rear_demist_string);
    }

    private void setRearDemist(boolean select) {
        if (select) {
            button_rearDemist.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_rearDemist_isSelected(true);
        } else {
            button_rearDemist.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_rearDemist_isSelected(false);
        }
    }

    private void sendCabinCycle_status() {
        sendData(cabin_cycle_string);
    }

    private void setCabinCycle(boolean select) {
        if (select) {
            button_cabin_cycle.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_cabin_cycle_isSelected(true);
        } else {
            button_cabin_cycle.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_cabin_cycle_isSelected(false);
        }
    }

    private void senddomeLight_status() {
        sendData(dome_light_string);
    }

    private void sendDoorLock_status() {
        sendData(door_lock_string);
    }


    //progress bars

    private void sendFanUp_status(){
        sendData(fan_up_string);
    }
    private void sendFanDown_status(){
        sendData(fan_down_string);
    }

    public void incrementFanProgressBar(int incAmount) {
        this.fanProgressBar.incrementProgressBy(incAmount);
    }

    private void sendtempUp_status(){
        sendData(temp_up_string);
    }
    private void sendtempDown_status(){
        sendData(temp_down_string);
    }

    private void incrementTempProgressBar(int incAmount){
        this.tempProgressBar.incrementProgressBy(incAmount);
        setProgressColours();
        acMaxCheck();
    }

    private void setProgressColours() {
        if (this.tempProgressBar.getProgress() > (this.tempProgressBar.getMax() / 2)) {
            this.tempProgressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        } else {
            this.tempProgressBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        }
    }

    private void acMaxCheck() {
        if (this.tempProgressBar.getProgress() <= 0) {
            setAcMax(true);
        }else {
            setAcMax(false);
        }
    }

    public void setTempProgressBar(int amount) {
        this.tempProgressBar.setProgress(amount);
        setProgressColours();
        acMaxCheck();
    }


    //boolean getters and setters

    public Boolean getButton_frontDemist_isSelected() {
        return button_frontDemist_isSelected;
    }

    public void setButton_frontDemist_isSelected(Boolean button_frontDemist_isSelected) {
        this.button_frontDemist_isSelected = button_frontDemist_isSelected;
    }

    public Boolean getButton_rearDemist_isSelected() {
        return button_rearDemist_isSelected;
    }

    public void setButton_rearDemist_isSelected(Boolean button_rearDemist_isSelected) {
        this.button_rearDemist_isSelected = button_rearDemist_isSelected;
    }

    public Boolean getButton_cabin_cycle_isSelected() {
        return button_cabin_cycle_isSelected;
    }

    public void setButton_cabin_cycle_isSelected(Boolean button_cabin_cycle_isSelected) {
        this.button_cabin_cycle_isSelected = button_cabin_cycle_isSelected;
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

    private void process(String sIn) {
        setStartState();
        String[] arr = sIn.split(".");
        for (String x : arr) {
            decode(x);
            Log.i(TAG, "process: " + x);
        }
    }

    private void decode(String s) {
        switch (s) {
            case front_demist_string:
                setfrontDemist(true);
                return;
            case feet_string:
                return;
            case face_string:
                return;
            case open_cabin_string:
                setCabinCycle(true);
                return;
            case closed_cabin_string:
                setCabinCycle(true);
                return;
            case ac_string:
                setAC(true);
                return;
            default:
                break;
        }
    }

    @Override
    public void serialInCallback(String serial) {
        //decode

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                process(serial);
            }
        });
    }

    private void sendData(String d) {
//        this.usbSerial.sendData(d);
        Log.i(TAG, "sendData: " + d);
    }
}