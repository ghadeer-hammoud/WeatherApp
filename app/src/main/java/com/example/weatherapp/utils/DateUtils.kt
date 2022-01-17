package com.example.weatherapp.utils

import android.text.format.DateFormat
import java.util.*

class DateUtils {

    companion object{
        fun timeStampToDate(timestamp: Int): String {
            var cal = Calendar.getInstance(Locale.ENGLISH);
            cal.timeInMillis = (timestamp * 1000L)
            var dayInWeek = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            var dayInMonth = cal.get(Calendar.DAY_OF_MONTH)
            var month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            var year = cal.get(Calendar.YEAR)
            //return DateFormat.format("dd-MM-yyyy", cal).toString()
            return "$dayInWeek, $dayInMonth $month $year"
        }
    }
}