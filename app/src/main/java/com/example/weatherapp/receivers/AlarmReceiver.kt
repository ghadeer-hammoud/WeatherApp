package com.example.weatherapp.receivers

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import com.example.weatherapp.utils.AlarmUtils
import com.example.weatherapp.utils.NotificationUtils

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            AlarmUtils.setDailyNotifier(context!!)
            return
        }

        NotificationUtils.showNotification(context!!, "Current Temperature")
    }


    }
