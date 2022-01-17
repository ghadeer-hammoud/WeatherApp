package com.example.weatherapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.weatherapp.R
import com.example.weatherapp.utils.AlarmUtils

class MySettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {

        if(p1.equals("isNotifications")){
            val isNotifications: SwitchPreference? = findPreference("isNotifications")
            if(isNotifications!!.isChecked){
                AlarmUtils.setDailyNotifier(requireContext())
            }
            else{
                AlarmUtils.stopDailyNotifier(requireContext())
            }
        }
    }
}
