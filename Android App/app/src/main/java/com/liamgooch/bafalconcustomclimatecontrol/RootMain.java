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
     *
     * @param state - button enabled state
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
        setTemp(1);
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
    private void setBemState() {
        setRearDemistButton(false);
    }

    /**
     * Method to set the AC status
     *
     * @param select - AC status state
     */
    private void setAC(boolean select) {
        setACButton(select);    //set the button status
        if (!select) {
            if (getTempProgressBarProgress() <= 0) {
                //if status is false and the current progress is 0 (ac max)
                setAcMax(false);    //set AC Max to false
            }
        }
        sendAC_status();    //send the ac status to USB serial
    }

    /**
     * Method to send the AC status
     */
    private void sendAC_status() {
        sendData(ac_string);    //send data to serial
    }

    /**
     * Method to set the AC Button state
     *
     * @param select - state of button
     */
    private void setACButton(boolean select) {
        //set the buttons colours and boolean state
        if (select) {
            button_ac.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_ac_isSelected(true);
        } else {
            button_ac.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_ac_isSelected(false);
        }
    }

    /**
     * Method to set the AC MAX Status
     *
     * @param select - state of AC MAX
     */
    private void setAcMax(boolean select) {
        setAcMaxButton(select); //set the button state
        if (getButton_acMax_isSelected()) {
            setAC(true);    //set AC
            temp0();    //if AC MAX is selected set the temp to 0
        } else {
            //else - reset the progress to 1 and send a temp up status
            setTemp(1);
            //TODO: FIX THIS -> SEND UP STATUS
            sendTempUp_status();
        }
    }

    /**
     * Method to set the AC MAX button status
     *
     * @param select - state of the button
     */
    private void setAcMaxButton(boolean select) {
        //set button colours and state
        if (select) {
            button_acMax.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_acMax_isSelected(true);
        } else {
            button_acMax.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_acMax_isSelected(false);
        }
    }

    /**
     * Method to set front demist status
     *
     * @param select - state of front demist
     */
    private void setFrontDemist(boolean select) {
        setFrontDemistButton(select);   //set the button
        sendFrontDemist_status();   //send the status
    }

    /**
     * Method to send the front demist status to USB Serial
     */
    private void sendFrontDemist_status() {
        sendData(front_demist_string);  //send data to serial
    }

    /**
     * Method to set the front demist button status
     *
     * @param select - state of the button
     */
    private void setFrontDemistButton(boolean select) {
        //set button colours and state
        if (select) {
            button_frontDemist.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_frontDemist_isSelected(true);
        } else {
            button_frontDemist.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_frontDemist_isSelected(false);
        }
    }

    /**
     * Method to set the rear demist status
     *
     * @param select - state of the rear demist
     */
    private void setRearDemist(boolean select) {
        setRearDemistButton(select);    //set the button
        sendRearDemist_status();    //send the status
    }

    /**
     * Method to send rear demist status
     */
    private void sendRearDemist_status() {
        sendData(rear_demist_string);   //send data to USB Serial
    }

    /**
     * Method to set rear demist button status
     *
     * @param select - state of rear demist button
     */
    private void setRearDemistButton(boolean select) {
        //set button colours and state
        if (select) {
            button_rearDemist.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_rearDemist_isSelected(true);
        } else {
            button_rearDemist.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_rearDemist_isSelected(false);
        }
    }

    /**
     * Method to set cabin cycle status
     *
     * @param select - state of the cabin cycle
     */
    private void setCabinCycle(Decoder.Mappings select) {
        setCabinCycleButton(select);    //set button state
        sendCabinCycle_status();    //send status to USB Serial
    }

    /**
     * Method to send cabin cycle status
     */
    private void sendCabinCycle_status() {
        sendData(cabin_cycle_string);   //send string to USB Serial
    }

    /**
     * Method to set cabin cycle button state
     *
     * @param cycleString - state of the cabin cycle
     */
    private void setCabinCycleButton(Decoder.Mappings cycleString) {
        //set button colours and state
        if (cycleString == Decoder.Mappings.OPEN_CABIN) {
            button_cabin_cycle.setImageResource(R.drawable.open_cabin);
            setButton_cabin_cycle_isSelected(open_cabin_string);
        } else {
            button_cabin_cycle.setImageResource(R.drawable.closed_cabin);
            setButton_cabin_cycle_isSelected(closed_cabin_string);
        }
    }

    /**
     * Method to set face status
     *
     * @param select - status of face
     */
    private void setFace(boolean select) {
        setFaceButton(select);  //set button status
        sendFace_status();  //send status to USB Serial
    }

    /**
     * Method to send face status
     */
    private void sendFace_status() {
        sendData(face_string);  //send data to USB Serial
    }

    /**
     * Method to set face button status
     *
     * @param select - status of the button
     */
    private void setFaceButton(boolean select) {
        //set button colours and state
        if (select) {
            button_face.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_face_isSelected(true);
        } else {
            button_face.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_face_isSelected(false);
        }
    }

    /**
     * Method to set feet status
     *
     * @param select - status of feet
     */
    private void setFeet(boolean select) {
        setFeetButton(select);  //set button status
        sendFeet_status();  //send status to USB Serial
    }

    /**
     * Method to send feet status
     */
    private void sendFeet_status() {
        sendData(feet_string);  //send data to USB Serial
    }

    /**
     * Method to set button status
     *
     * @param select - status of the button
     */
    private void setFeetButton(boolean select) {
        //set button colours and state
        if (select) {
            button_feet.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_feet_isSelected(true);
        } else {
            button_feet.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_feet_isSelected(false);
        }
    }

    /**
     * Method to set face feet status
     *
     * @param select - status of face feet
     */
    private void setFaceFeet(boolean select) {
        setFaceFeetButton(select);  //set button status
        sendFaceFeet_status();  //send status to USB Serial
    }

    /**
     * Method to send face feet status
     */
    private void sendFaceFeet_status() {
        sendData(face_feet_string); //send data to USB Serial
    }

    /**
     * Method to set the face feet button status
     *
     * @param select - state of the button
     */
    private void setFaceFeetButton(boolean select) {
        //set button colours and state
        if (select) {
            button_face_feet.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_face_feet_isSelected(true);
        } else {
            button_face_feet.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_face_feet_isSelected(false);
        }
    }

    /**
     * Method to set feet front demist
     *
     * @param select - status of the feet front demist
     */
    private void setFeetFrontDemist(boolean select) {
        setFeetFrontDemistButton(select);   //set button status
        sendFeetFrontDemist_status();   //send status to USB Serial
    }

    /**
     * Method to send feet front demist status
     */
    private void sendFeetFrontDemist_status() {
        sendData(feet_front_demist_string); //send data to USB Serial
    }

    /**
     * Method to set feet front demist button status
     *
     * @param select - status of the button
     */
    private void setFeetFrontDemistButton(boolean select) {
        //set button colours and state
        if (select) {
            button_feet_front_demist.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
            setButton_feet_front_demist_isSelected(true);
        } else {
            button_feet_front_demist.setBackgroundColor(getResources().getColor(R.color.black));
            setButton_feet_front_demist_isSelected(false);
        }
    }

    /**
     * Method to send dome light status
     */
    private void sendDomeLight_status() {
        sendData(dome_light_string);    //send data to USB Serial
    }

    /**
     * Method to send door lock status
     */
    private void sendDoorLock_status() {
        sendData(door_lock_string); //send data to USB Serial
    }

    /**
     * Method to increase temperature
     */
    private void tempUp() {
        incrementTempProgressBar(1);    //increase temp by 1
        sendTempUp_status();    //send data to USB Serial
    }

    /**
     * Method to decrease temperature
     */
    private void tempDown() {
        incrementTempProgressBar(-1);   //decrease temp by 1
        sendTempDown_status();  //send data to USB Serial
    }

    /**
     * Method to set temperature to 0
     */
    private void temp0() {
        this.tempProgressBar.setProgress(0);    //set temp to 0
        sendTemp0_status(); //send data to USB Serial
    }

    /**
     * Method to increase fan
     */
    private void fanUp() {
        incrementFanProgressBar(1); //increase fan by 1
        sendFanUp_status(); //send data to USB Seria;
    }

    /**
     * Method to decrease fan
     */
    private void fanDown() {
        incrementFanProgressBar(-1);    //decrease fan by 1
        sendFanDown_status();   //send data to USB Serial
    }

    /**
     * Method to send temp status of 0
     */
    private void sendTemp0_status() {
        sendData(temp0_string); //send data to USB Serial
    }

    /**
     * Method to send temp status of x amount
     *
     * @param amount - the temp amount to send to USB Serial
     */
    private void sendTemp_status(int amount) {
        sendData(temp_set_string, amount);  //send data to USB Serial
    }

    /**
     * Method to send fan up status
     */
    private void sendFanUp_status() {
        sendData(fan_up_string);    //send data to USB Serial
    }

    /**
     * Method to send fan down status
     */
    private void sendFanDown_status() {
        sendData(fan_down_string);  //send data to USB Serial
    }

    /**
     * Method to increment fan progress bar by x amount
     */
    public void incrementFanProgressBar(int incAmount) {
        this.fanProgressBar.incrementProgressBy(incAmount); //increment progress
    }

    /**
     * Method to send temp up status
     */
    private void sendTempUp_status() {
        sendData(temp_up_string);   //send data to USB Serial
    }

    /**
     * Method to send temp down status
     */
    private void sendTempDown_status() {
        sendData(temp_down_string); //send data to USB Serial
    }

    /**
     * Method to increment temp progress bar by x amount
     */
    private void incrementTempProgressBar(int incAmount) {
        this.tempProgressBar.incrementProgressBy(incAmount);    //increment progress
        setTempProgressColours();   //set the colours of the progress bar
        acMaxCheck();   //check whether AC MAX should be enacted
    }

    /**
     * Method to set colours of the temp progress bar
     */
    private void setTempProgressColours() {
        //if the progress is greater than half way - set the colour red, else blue
        if (getTempProgressBarProgress() > (getTempProgressBarMaxProgress() / 2)) {
            this.tempProgressBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        } else {
            this.tempProgressBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        }
    }

    /**
     * Method to set ac Max if temp is 0
     */
    private void acMaxCheck() {
        setAcMaxButton(getTempProgressBarProgress() <= 0);  //set ac max if temp is 0
    }

    /**
     * Method to set temp by x amount
     *
     * @param amount - the amount to set the temprature to
     */
    public void setTemp(int amount) {
        this.tempProgressBar.setProgress(amount);   //set the temp by x amount
        sendTemp_status(amount);    //send the temp status to USB Serial
        setTempProgressColours();   //set temp progress bar colours
        acMaxCheck();   //check if AC MAX needs to be enabled
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

    /**
     * Method to process the received Serial string
     *
     * @param sIn - the serial received
     */
    private void process(String sIn) {
        //try to process and decode Serial messages
        try {
            HashMap<Integer, Integer> messages = new HashMap<>();   //create a new hash map for messages - ID as key
            String[] raw = sIn.split(endChar);  //split each string by the end character

            //for each split string
            for (String r : raw) {
                //TODO: try catch for each split string
                r = r.replace(startChar, "");   //replace the starting char with nothing
                String[] m = r.split(splitChar);    //split the string by the split character
                if (m[0].equals(canMsg_string)) {
                    //if the first part of the message is equal to the can message string - process the ID and value
                    messages.put(Integer.parseInt(m[1], 16), Integer.parseInt(m[2], 16));
                }
            }

            //for each Message in the hash map -> process
            for (Map.Entry<Integer, Integer> set : messages.entrySet()) {
                Integer codeHex = set.getKey(); //ID
                Integer msgHex = set.getValue();    //message

                if (codeHex == decoder.getHimID()) {
                    Log.i(TAG, "process: Decoding HIM - ID: " + codeHex + ", MSG: " + msgHex);    //log
                    setStartState();    //set the start state - before the buttons are updated
                    decode(decoder.getHimDecodedList(msgHex));  //decode and make changes
                } else if (codeHex == decoder.getBemID()) {
                    Log.i(TAG, "process: Decoding BEM - ID: " + codeHex + ", MSG: " + msgHex);  //log
                    setBemState();  //set the BEM state to default before buttons are changed
                    decode(decoder.getBemDecodedList(msgHex));  //decode and make changes
                } else {
                    Log.i(TAG, "process: NOT A VALID ID - " + sIn); //log
                }
            }
        } catch (Exception e) {
            //catch exceptions and show a toast
            Log.i(TAG, "process: " + e);
            Toast.makeText(thisContext, "ERROR PROCESSING SERIAL IN", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Method to decode the inputted mappings
     *
     * @param mappings - list of enum values to update the UI with appropriate values - [FACE, OPEN_CABIN, etc.]
     */
    private void decode(ArrayList<Decoder.Mappings> mappings) {
        for (Decoder.Mappings mapping : mappings) {
            //for each value in the list - make a change on the UI
            makeChanges(mapping);
        }
    }

    //TODO: serial sent - should buttons just be updated instead?

    /**
     * Method to make changes to the UI
     *
     * @param mappedValue - the enum value which will dictate the changes - (FACE, OPEN_CABIN etc.)
     */
    private void makeChanges(Decoder.Mappings mappedValue) {
        switch (mappedValue) {
            case AC:
                setACButton(true);
                return;
            case FACE:
                setFaceButton(true);
                return;
            case FEET:
                setFeetButton(true);
                return;
            case FACE_FEET:
                setFaceFeetButton(true);
                return;
            case FEET_FRONT_DEMIST:
                setFeetFrontDemistButton(true);
                return;
            case FRONT_DEMIST:
                setFrontDemistButton(true);
                return;
            case OPEN_CABIN:
                setCabinCycleButton(Decoder.Mappings.OPEN_CABIN);
                return;
            case CLOSED_CABIN:
                setCabinCycleButton(Decoder.Mappings.CLOSED_CABIN);
                return;
            case REAR_DEMIST:
                setRearDemistButton(true);
                return;
            default:
        }
    }

    /**
     * Callback method for when USB Serial is received
     *
     * @param serial - the serial string
     */
    @Override
    public void serialInCallback(String serial) {
        //decode and process the string - on the UI thread as changes to the UI are eventually made
        this.thisActivity.runOnUiThread(() -> process(serial));
    }

    /**
     * Method to construct a string to be sent over serial
     *
     * @param inputData - the input data
     * @return - properly formatted data to be sent
     */
    private String constructSerialData(String inputData) {
        return startChar + inputData + endChar; //append start and end chars for USB Serial
    }

    /**
     * Method to send data to USB serial
     *
     * @param d - the data string to send
     */
    private void sendData(String d) {
        if (startedSuccessfully) {
            //if USB Serial is connected
            this.usbSerial.sendData(constructSerialData(d));    //send the data
        }
        Log.i(TAG, "sendData: " + d);   //log the message
    }

    /**
     * Method to send data to USB Serial with an interger amount
     *
     * @param d      - the data string to send
     * @param amount - the amount to append to the string
     */
    private void sendData(String d, int amount) {
        String data = "";   //initialise the string
        if (startedSuccessfully) {
            //if USB Serial is connected
            data = d + ":" + amount;    //append the amount to the data
            this.usbSerial.sendData(constructSerialData(data)); //send the appended data
        }
        Log.i(TAG, "sendData: " + d);   //log the message sent
    }

    /**
     * Method to start the serial connection
     */
    @Override
    public void startSerialConnection() {
        this.startedSuccessfully = this.usbSerial.startUSBConnection(); //call the start method
        if (!startedSuccessfully) {
            //if unsuccessful create a toast
            Toast.makeText(this.thisActivity, "USB SERIAL FAILED TO START", Toast.LENGTH_LONG).show();
//            TODO: UNCOMMENT FOR TESTING
            setEnabledState(false); //set the button states to disabled
        } else {
            getData();  //if successful get HIM and BEM data from USB Serial
        }
    }

    /**
     * Method to get the current progress bar temperature
     *
     * @return - the current temperature in the progress bar
     */
    public int getTempProgressBarProgress() {
        return tempProgressBar.getProgress();
    }

    /**
     * Method to get the max temperature progress
     *
     * @return - the max temperature progress
     */
    public int getTempProgressBarMaxProgress() {
        return tempProgressBar.getMax();
    }
}