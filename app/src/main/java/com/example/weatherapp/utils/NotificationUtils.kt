package com.example.weatherapp.utils

import android.app.NotificationManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.weatherapp.R
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.core.app.NotificationCompat
import com.example.weatherapp.ui.WeatherActivity


class NotificationUtils {

    companion object{

        fun showNotification(context: Context, content: String?) {
            val channel_id = createNotificationChannel(context)
            val intent = Intent(context, WeatherActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notificationBuilder = NotificationCompat.Builder(context, channel_id!!)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(content)
                .setStyle(NotificationCompat.BigTextStyle().bigText(content))
                .setTicker(content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .setVibrate(longArrayOf(1000, 1000))
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1000, notificationBuilder.build())
        }

        fun createNotificationChannel(context: Context): String? {

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                // The id of the channel.
                val channelId = "Channel_id"

                val channelName: CharSequence = context.getString(R.string.app_name)
                val channelDescription: String =
                    context.getString(R.string.app_name).toString() + " Channel"
                val channelImportance = NotificationManager.IMPORTANCE_DEFAULT
                val channelEnableVibrate = true

                val notificationChannel =
                    NotificationChannel(channelId, channelName, channelImportance)
                notificationChannel.description = channelDescription
                notificationChannel.enableVibration(channelEnableVibrate)

                val notificationManager =
                    (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                notificationManager.createNotificationChannel(notificationChannel)
                channelId
            } else {
                // Returns null for pre-O (26) devices.
                ""
            }
        }
    }
}