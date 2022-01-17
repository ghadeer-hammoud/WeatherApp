package com.example.weatherapp.utils
import android.app.AlarmManager
import android.content.Context.ALARM_SERVICE
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.content.ComponentName
import android.content.Context
import com.example.weatherapp.receivers.AlarmReceiver
import java.util.*

class AlarmUtils {

    companion object{

        val DAILY_NOTIFIER_REQUEST_CODE: Int = 110

        fun setDailyNotifier(context: Context) {

            val currentTimeCalendar = Calendar.getInstance()
            val dailyTimeCalendar = Calendar.getInstance()
            dailyTimeCalendar.set(Calendar.HOUR_OF_DAY, 13)
            dailyTimeCalendar.set(Calendar.MINUTE, 21)
            dailyTimeCalendar.set(Calendar.SECOND, 0)

            if (dailyTimeCalendar.before(currentTimeCalendar)) dailyTimeCalendar.add(Calendar.DATE, 1)

            // Enable a receiver
            val receiver = ComponentName(context, AlarmReceiver::class.java)
            val pm: PackageManager = context.packageManager
            pm.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
            val intent1 = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                DAILY_NOTIFIER_REQUEST_CODE, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val am = context.getSystemService(ALARM_SERVICE) as AlarmManager
            am.setInexactRepeating(
                AlarmManager.RTC_WAKEUP, dailyTimeCalendar.timeInMillis,
                AlarmManager.INTERVAL_DAY, pendingIntent
            )
        }

        fun stopDailyNotifier(context: Context) {

            val receiver = ComponentName(context, AlarmReceiver::class.java)
            val pm = context.packageManager
            pm.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
            val intent1 = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                DAILY_NOTIFIER_REQUEST_CODE, intent1, PendingIntent.FLAG_UPDATE_CURRENT
            )
            val am = context.getSystemService(ALARM_SERVICE) as AlarmManager
            am.cancel(pendingIntent)
            pendingIntent.cancel()
        }
    }

}