package com.liamgooch.bafalconcustomclimatecontrol;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    Preference retrySerialPreference;
    SwitchPreferenceCompat headUnitControls;
    Preference getDataPreference;
    USBSerialCallbacks _serial;

    public SettingsFragment(USBSerialCallbacks _serial) {
        this._serial = _serial;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        retrySerialPreference = findPreference("retry_arduino");
        headUnitControls = findPreference("hu_buttons_switch");
        getDataPreference = findPreference("get_data");

        setPreferenceDefaults();

        retrySerialPreference.setOnPreferenceClickListener(preference -> {
            this._serial.startSerialConnection();
            return true;
        });

        getDataPreference.setOnPreferenceClickListener(preference -> {
            this._serial.getData();
            return true;
        });
    }

    private void setPreferenceDefaults() {
        headUnitControls.setChecked(true);
    }
}