package com.liamgooch.bafalconcustomclimatecontrol;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import static com.liamgooch.bafalconcustomclimatecontrol.Strings.*;

public class SettingsFragment extends PreferenceFragmentCompat {

    Preference retrySerialPreference;   //preference for retrying the serial connection
    SwitchPreferenceCompat headUnitControls;    //switch preference for whether the head unit controls the climate or the android app
    Preference getDataPreference;   //preference to get data from serial
    Preference keyCodePreference;   //preference to send key code to serial
    USBSerialCallbacks _serial; //serial callback instance

    public SettingsFragment(USBSerialCallbacks _serial) {
        this._serial = _serial;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(R.color.black));
        return view;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        //find all preferences
        retrySerialPreference = findPreference("retry_arduino");
        headUnitControls = findPreference("hu_buttons_switch");
        getDataPreference = findPreference("get_data");
        keyCodePreference = findPreference("key_code");

        setPreferenceDefaults();    //set the default preferences

        //on retry serial connection press
        retrySerialPreference.setOnPreferenceClickListener(preference -> {
            this._serial.startSerialConnection();
            return true;
        });

        //in get preference from serial press
        getDataPreference.setOnPreferenceClickListener(preference -> {
            this._serial.getData();
            return true;
        });

        keyCodePreference.setOnPreferenceClickListener(preference -> {
            this._serial.sendCommand(keyCode_string);
            return true;
        });
    }

    /**
     * Method to set the default preferences
     */
    private void setPreferenceDefaults() {
        headUnitControls.setChecked(true);
    }
}