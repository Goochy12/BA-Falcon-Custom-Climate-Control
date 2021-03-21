package com.liamgooch.bafalconcustomclimatecontrol;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.liamgooch.bafalconcustomclimatecontrol.Strings.*;

public class RootMain extends Fragment implements USBSerialCallbacks {

//    TODO: Create a log to show in settings fragment

    private UsbSerial usbSerial;    //instance for USB serial manager class
    private Decoder decoder;    //instance to decoder class
    boolean startedSuccessfully = false;    //flag for successful USB Serial connection

    // image button declarations
    private ImageButton button_frontDemist, button_rearDemist, button_cabin_cycle, button_fanUp,
            button_fanDown, button_tempUp, button_tempDown, button_domeLight, button_doorLock, button_settings,
            button_face, button_feet, button_face_feet, button_feet_front_demist;

    //other button declarations
    private Button button_ac, button_acMax;

    //boolean variables for button
    private Boolean button_frontDemist_isSelected, button_rearDemist_isSelected, button_ac_isSelected, button_acMax_isSelected,
            button_face_isSelected, button_feet_isSelected, button_face_feet_isSelected, button_feet_front_demist_isSelected;
    private String button_cabin_cycle_isSelected;
    private ProgressBar fanProgressBar, tempProgressBar;

    //declaration for "this"
    private Activity thisActivity;
    private Context thisContext;

    public RootMain() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.thisActivity = getActivity();  //get this activity
        this.thisContext = getContext();    //get this contect
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_root_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //declare buttons
        button_settings = view.findViewById(R.id.button_settings);

        button_frontDemist = view.findViewById(R.id.button_frontDemist);
        button_rearDemist = view.findViewById(R.id.button_rearDemist);
        button_cabin_cycle = view.findViewById(R.id.button_cabin_cycle);
        button_fanUp = view.findViewById(R.id.button_fanUp);
        button_fanDown = view.findViewById(R.id.button_fanDown);
        button_tempUp = view.findViewById(R.id.button_tempUp);
        button_tempDown = view.findViewById(R.id.button_tempDown);
        button_domeLight = view.findViewById(R.id.button_domeLight);
        button_doorLock = view.findViewById(R.id.button_doorLock);
        button_face = view.findViewById(R.id.button_face);
        button_feet = view.findViewById(R.id.button_feet);
        button_face_feet = view.findViewById(R.id.button_face_feet);
        button_feet_front_demist = view.findViewById(R.id.button_feet_front_demist);

        button_ac = view.findViewById(R.id.button_ac);
        button_acMax = view.findViewById(R.id.button_acMax);

        button_settings = view.findViewById(R.id.button_settings);

        //declare progress bars
        fanProgressBar = view.findViewById(R.id.fanProgressBar);
        tempProgressBar = view.findViewById(R.id.tempProgressBar);

        //set boolean variables
//        setEnabledState(false);

        //declare button listeners
        button_settings.setOnClickListener(v -> getParentFragmentManager().beginTransaction().add(R.id.fragment_container, new SettingsFragment(RootMain.this)).addToBackStack("settings").commit());

        button_ac.setOnClickListener(v -> setAC(!getButton_ac_isSelected()));

        button_acMax.setOnClickListener(v -> setAcMax(!getButton_acMax_isSelected()));

        button_frontDemist.setOnClickListener(v -> setFrontDemist(!getButton_frontDemist_isSelected()));

        button_rearDemist.setOnClickListener(v -> setRearDemist(!getButton_rearDemist_isSelected()));

        button_cabin_cycle.setOnClickListener(v -> {
            if (getButton_cabin_cycle_isSelected().equals(open_cabin_string)) {
                setCabinCycle(Decoder.Mappings.CLOSED_CABIN);
            } else {
                setCabinCycle(Decoder.Mappings.OPEN_CABIN);
            }
        });

        button_fanUp.setOnClickListener(v -> fanUp());

        button_fanDown.setOnClickListener(v -> fanDown());

        button_tempUp.setOnClickListener(v -> tempUp());

        button_tempDown.setOnClickListener(v -> tempDown());

        button_domeLight.setOnClickListener(v -> sendDomeLight_status());

        button_doorLock.setOnClickListener(v -> sendDoorLock_status());

        button_face.setOnClickListener(v -> setFace(!getButton_face_isSelected()));

        button_feet.setOnClickListener(v -> setFeet(!getButton_feet_isSelected()));

        button_face_feet.setOnClickListener(v -> setFaceFeet(!getButton_face_feet_isSelected()));

        button_feet_front_demist.setOnClickListener(v -> setFeetFrontDemist(!getButton_feet_front_demist_isSelected()));

        setStartState();    //set the starting state for the interface
        this.decoder = new Decoder();   //create a new instance of the decoder

        //create a new USB Serial instance
        this.usbSerial = new UsbSerial(this.thisActivity.getApplicationContext(), this);
        startSerialConnection();    //attempt to start the serial connection
    }

    /**
     * Method to get a set of data from USB Serial
     */
    @Override
    public void getData() {
        sendData(getData_string);   //send the get_data string via USB Serial
    }

    /**
     * Method to set the enabled state of the buttons and progress bars
     * @param state - True or False
     */
    private void setEnabledState(boolean state) {
        //declare buttons
        button_frontDemist.setEnabled(state);
        button_rearDemist.setEnabled(state);
        button_cabin_cycle.setEnabled(state);
        button_fanUp.setEnabled(state);
        button_fanDown.setEnabled(state);
        button_tempUp.setEnabled(state);
        button_tempDown.setEnabled(state);
        button_domeLight.setEnabled(state);
        button_doorLock.setEnabled(state);
        button_face.setEnabled(state);
        button_feet.setEnabled(state);
        button_face_feet.setEnabled(state);
        button_feet_front_demist.setEnabled(state);

        button_ac.setEnabled(state);
        button_acMax.setEnabled(state);

        //declare progress bars
        fanProgressBar.setEnabled(state);
        tempProgressBar.setEnabled(state);

        //set boolean variables
//        button_frontDemist_isSelected = false;
//        button_rearDemist_isSelected = false;
//        button_cabin_cycle_isSelected = closed_cabin_string;
//        button_ac_isSelected = false;
//        button_acMax_isSelected = false;
//        button_face_isSelected = false;
//        button_feet_isSelected = false;
//        button_face_feet_isSelected = false;
//        button_feet_front_demist_isSelected = false;
    }

    /**
     * Method to set the starting state before USB serial connection
     */
    private void setStartState() {
//        TODO: Reset ALL
        //initialise variables
        setTempProgressBar(1);
        incrementFanProgressBar(0);
        setEnabledState(true);

        //set button states
        setFrontDemistButton(false);
        setRearDemistButton(false);
        setCabinCycleButton(Decoder.Mappings.CLOSED_CABIN);
        setACButton(false);
        setAcMaxButton(false);
        setFaceButton(false);
        setFeetButton(false);
        setFaceFeetButton(false);
        setFeetFrontDemistButton(false);

        getData();  //get the actual states of each button
    }

    /**
     * Method to set the state for the BEM variables (rear demist, door status)
     */
    private void setBemEnabledState() {
        setRearDemistButton(false);
    }

    /**
     * Method to set the AC status
     * @param select
     */
    private void setAC(boolean select) {
        setACButton(select);
        if (!select) {
            tempProgressBarAC();
        }
        sendAC_status();
    }

    private void sendAC_status() {
        sendData(ac_string);
    }

    private void setACButton(boolean select) {
        if (select) {
            button_ac.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_ac_isSelected(true);
        } else {
            button_ac.setBackgroundColor(getResources().getColor(R.color.black));
            setAcMax(false);
            setButton_ac_isSelected(false);
        }
    }

    private void tempProgressBarAC() {
        if (tempProgressBar.getProgress() <= 0) {
            setTempProgressBar(1);
        }
    }

    private void setAcMax(boolean select) {
        setAcMaxButton(select);
        if (getButton_acMax_isSelected()) {
            temp0();
        } else {
            setTempProgressBar(1);
            sendTempUp_status();
        }
    }

    private void setAcMaxButton(boolean select) {
        if (select) {
            button_acMax.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setAC(true);
            setButton_acMax_isSelected(true);
        } else {
            button_acMax.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_acMax_isSelected(false);
        }
    }

    private void setFrontDemist(boolean select) {
        setFrontDemistButton(select);
        sendFrontDemist_status();
    }

    private void sendFrontDemist_status() {
        sendData(front_demist_string);
    }

    private void setFrontDemistButton(boolean select) {
        if (select) {
            button_frontDemist.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_frontDemist_isSelected(true);
        } else {
            button_frontDemist.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_frontDemist_isSelected(false);
        }
    }

    private void setRearDemist(boolean select) {
        setRearDemistButton(select);
        sendRearDemist_status();
    }

    private void sendRearDemist_status() {
        sendData(rear_demist_string);
    }

    private void setRearDemistButton(boolean select) {
        if (select) {
            button_rearDemist.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_rearDemist_isSelected(true);
        } else {
            button_rearDemist.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_rearDemist_isSelected(false);
        }
    }

    private void setCabinCycle(Decoder.Mappings select) {
        setCabinCycleButton(select);
        sendCabinCycle_status();
    }

    private void sendCabinCycle_status() {
        sendData(cabin_cycle_string);
    }

    private void setCabinCycleButton(Decoder.Mappings cycleString) {
        if (cycleString == Decoder.Mappings.OPEN_CABIN) {
            button_cabin_cycle.setImageResource(R.drawable.open_cabin);
            setButton_cabin_cycle_isSelected(open_cabin_string);
        } else {
            button_cabin_cycle.setImageResource(R.drawable.closed_cabin);
            setButton_cabin_cycle_isSelected(closed_cabin_string);
        }
    }

    private void setFace(boolean select) {
        setFaceButton(select);
        sendFace_status();
    }

    private void sendFace_status() {
        sendData(face_string);
    }

    private void setFaceButton(boolean select) {
        if (select) {
            button_face.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_face_isSelected(true);
        } else {
            button_face.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_face_isSelected(false);
        }
    }

    private void setFeet(boolean select) {
        setFeetButton(select);
        sendFeet_status();
    }

    private void sendFeet_status() {
        sendData(feet_string);
    }

    private void setFeetButton(boolean select) {
        if (select) {
            button_feet.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_feet_isSelected(true);
        } else {
            button_feet.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_feet_isSelected(false);
        }
    }

    private void setFaceFeet(boolean select) {
        setFaceFeetButton(select);
        sendFaceFeet_status();
    }

    private void sendFaceFeet_status() {
        sendData(face_feet_string);
    }

    private void setFaceFeetButton(boolean select) {
        if (select) {
            button_face_feet.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_face_feet_isSelected(true);
        } else {
            button_face_feet.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_face_feet_isSelected(false);
        }
    }

    private void setFeetFrontDemist(boolean select) {
        setFeetFrontDemistButton(select);
        sendFeetFrontDemist_status();
    }

    private void sendFeetFrontDemist_status() {
        sendData(feet_front_demist_string);
    }

    private void setFeetFrontDemistButton(boolean select) {
        if (select) {
            button_feet_front_demist.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_feet_front_demist_isSelected(true);
        } else {
            button_feet_front_demist.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_feet_front_demist_isSelected(false);
        }
    }

    private void sendDomeLight_status() {
        sendData(dome_light_string);
    }

    private void sendDoorLock_status() {
        sendData(door_lock_string);
    }


    //progress bars

    private void tempUp() {
        incrementTempProgressBar(1);
        sendTempUp_status();
    }

    private void tempDown() {
        incrementTempProgressBar(-1);
        sendTempDown_status();
    }

    private void temp0() {
        this.tempProgressBar.setProgress(0);
        sendTemp0_status();
    }

    private void fanUp() {
        incrementFanProgressBar(1);
        sendFanUp_status();
    }

    private void fanDown() {
        incrementFanProgressBar(-1);
        sendFanDown_status();
    }

    private void sendTemp0_status() {
        sendData(temp0_string);
    }

    private void sendTemp_status(int amount) {
        sendData(temp_set_string, amount);
    }

    private void sendFanUp_status() {
        sendData(fan_up_string);
    }

    private void sendFanDown_status() {
        sendData(fan_down_string);
    }

    public void incrementFanProgressBar(int incAmount) {
        this.fanProgressBar.incrementProgressBy(incAmount);
    }

    private void sendTempUp_status() {
        sendData(temp_up_string);
    }

    private void sendTempDown_status() {
        sendData(temp_down_string);
    }

    private void incrementTempProgressBar(int incAmount) {
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
        setAcMaxButton(this.tempProgressBar.getProgress() <= 0);
    }

    public void setTempProgressBar(int amount) {
        this.tempProgressBar.setProgress(amount);
        sendTemp_status(amount);
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

    public String getButton_cabin_cycle_isSelected() {
        return button_cabin_cycle_isSelected;
    }

    public void setButton_cabin_cycle_isSelected(String button_cabin_cycle_isSelected) {
        this.button_cabin_cycle_isSelected = button_cabin_cycle_isSelected;
    }

    public Boolean getButton_ac_isSelected() {
        return button_ac_isSelected;
    }

    public void setButton_ac_isSelected(Boolean button_ac_isSelected) {
        this.button_ac_isSelected = button_ac_isSelected;
    }

    public Boolean getButton_face_isSelected() {
        return button_face_isSelected;
    }

    public void setButton_face_isSelected(Boolean button_face_isSelected) {
        this.button_face_isSelected = button_face_isSelected;
    }

    public Boolean getButton_feet_isSelected() {
        return button_feet_isSelected;
    }

    public void setButton_feet_isSelected(Boolean button_feet_isSelected) {
        this.button_feet_isSelected = button_feet_isSelected;
    }

    public Boolean getButton_face_feet_isSelected() {
        return button_face_feet_isSelected;
    }

    public void setButton_face_feet_isSelected(Boolean button_face_feet_isSelected) {
        this.button_face_feet_isSelected = button_face_feet_isSelected;
    }

    public Boolean getButton_feet_front_demist_isSelected() {
        return button_feet_front_demist_isSelected;
    }

    public void setButton_feet_front_demist_isSelected(Boolean button_feet_front_demist_isSelected) {
        this.button_feet_front_demist_isSelected = button_feet_front_demist_isSelected;
    }

    public Boolean getButton_acMax_isSelected() {
        return button_acMax_isSelected;
    }

    public void setButton_acMax_isSelected(Boolean button_acMax_isSelected) {
        this.button_acMax_isSelected = button_acMax_isSelected;
    }

    private void process(String sIn) {
        try {
            HashMap<Integer, Integer> messages = new HashMap<>();
            String[] raw = sIn.split(endChar);

            for (String r : raw) {
                r = r.replace(startChar, "");
                String[] m = r.split(splitChar);
                if (m[0].equals(canMsg_string)) {
                    messages.put(Integer.parseInt(m[1], 16), Integer.parseInt(m[2], 16));
                }
            }

            for (Map.Entry<Integer, Integer> set : messages.entrySet()) {
                Integer codeHex = set.getKey();
                Integer msgHex = set.getValue();

                if (codeHex == decoder.getHimID()) {
                    Log.i(TAG, "process: Decoding HIM");
                    setStartState();
                    decode(decoder.getHimDecodedList(msgHex));
                } else if (codeHex == decoder.getBemID()) {
                    Log.i(TAG, "process: Decoding BEM");
                    setBemEnabledState();
                    decode(decoder.getBemDecodedList(msgHex));
                } else {
                    Log.i(TAG, "process: NOT A VALID ID - " + sIn);
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "process: " + e);
            Toast.makeText(thisContext, "ERROR PROCESSING SERIAL IN", Toast.LENGTH_LONG).show();
        }

    }

    private void decode(ArrayList<Decoder.Mappings> mappings) {
        for (Decoder.Mappings mapping : mappings) {
            makeChanges(mapping);
        }
    }

    private void makeChanges(Decoder.Mappings mappedValue) {
        switch (mappedValue) {
            case AC:
                setAC(true);
                return;
            case FACE:
                setFace(true);
                return;
            case FEET:
                setFeet(true);
                return;
            case FACE_FEET:
                setFaceFeet(true);
                return;
            case FEET_FRONT_DEMIST:
                setFeetFrontDemist(true);
                return;
            case FRONT_DEMIST:
                setFrontDemist(true);
                return;
            case OPEN_CABIN:
                setCabinCycle(Decoder.Mappings.OPEN_CABIN);
                return;
            case CLOSED_CABIN:
                setCabinCycle(Decoder.Mappings.CLOSED_CABIN);
                return;
            case REAR_DEMIST:
                setRearDemist(true);
                return;
            default:
        }
    }

    @Override
    public void serialInCallback(String serial) {
        //decode
        this.thisActivity.runOnUiThread(() -> process(serial));
    }

    private String constructSerialData(String inputData) {
        return startChar + inputData + endChar;
    }

    private void sendData(String d) {
        if (startedSuccessfully) {
            this.usbSerial.sendData(constructSerialData(d));
        }
        Log.i(TAG, "sendData: " + d);
    }

    private void sendData(String d, int amount) {
        String data = "";
        if (startedSuccessfully) {
            data = d + ":" + amount;
            this.usbSerial.sendData(constructSerialData(data));
        }
        Log.i(TAG, "sendData: " + d);
    }

    @Override
    public void startSerialConnection() {
        this.usbSerial.startUSBConnection();
        this.startedSuccessfully = this.usbSerial.startUSBConnection();
        if (!startedSuccessfully) {
            Toast.makeText(this.thisActivity, "USB SERIAL FAILED TO START", Toast.LENGTH_LONG).show();
//            TODO: UNCOMMENT FOR TESTING
            setEnabledState(false);
        } else {
            getData();
        }
    }
}