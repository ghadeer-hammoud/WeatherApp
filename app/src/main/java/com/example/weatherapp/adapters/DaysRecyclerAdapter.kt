package com.example.weatherapp.adapters

import android.content.SharedPreferences
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.example.weatherapp.R
import com.example.weatherapp.models.WeatherReport
import com.example.weatherapp.utils.DateUtils


class DaysRecyclerAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var days: List<WeatherReport.Day> = ArrayList()
    private lateinit var glideInstance: RequestManager
    private lateinit var sharedPreference: SharedPreferences

    constructor(glideInstance: RequestManager, sharedPreference: SharedPreferences) : this() {
        this.glideInstance = glideInstance
        this.sharedPreference = sharedPreference
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_day_list_item, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DayViewHolder).bind(days[position])
    }

    override fun getItemCount(): Int {
        return days.size
    }

    fun setDays(days: List<WeatherReport.Day>) {
        this.days = days.subList(0, Integer.parseInt(sharedPreference.getString("days_to_show", "7")!!))
        notifyDataSetChanged()
    }

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var tvDate: TextView
        var tvDescription: TextView
        var ivIcon: ImageView
        var tvTemp: TextView
        var tvTempUnit: TextView
        var tvPressure: TextView
        var tvPressureUnit: TextView
        var tvHumidity: TextView
        var tvHumidityUnit: TextView
        var tvWind: TextView
        var tvWindUnit: TextView
        var tvClouds: TextView
        var tvCloudsUnit: TextView

        fun bind(day: WeatherReport.Day) {
            tvDate.text = DateUtils.timeStampToDate(day.dt)
            tvDescription.text = day.weather[0].main.toString()
            tvTemp.text = day.temp?.day.toString()
            var unit = sharedPreference.getString("temp_unit", "standard")
            when(unit){
                "standard" -> {tvTempUnit.text = "°K"}
                "metric" -> {tvTempUnit.text = "°C"}
                "imperial" -> {tvTempUnit.text = "°F"}
            }
            tvPressure.text = day.pressure.toString()
            tvPressureUnit.text = "hPa"
            tvHumidity.text = day.humidity.toString()
            tvHumidityUnit.text = "%"
            tvWind.text = day.wind_speed.toString()
            tvWindUnit.text = "M/H"
            tvClouds.text = day.clouds.toString()
            tvCloudsUnit.text = "%"

            glideInstance.load(
                "http://openweathermap.org/img/wn/${day.weather[0].icon}@2x.png"
            ).into(ivIcon)

        }

        init {
            tvDate = itemView.findViewById(R.id.tvDate)
            tvDescription = itemView.findViewById(R.id.tvDescription)
            ivIcon = itemView.findViewById(R.id.ivIcon)
            tvTemp = itemView.findViewById(R.id.tvTemp)
            tvTempUnit = itemView.findViewById(R.id.tvTempUnit)
            tvPressure = itemView.findViewById(R.id.tvPressure)
            tvPressureUnit = itemView.findViewById(R.id.tvPressureUnit)
            tvHumidity = itemView.findViewById(R.id.tvHumidity)
            tvHumidityUnit = itemView.findViewById(R.id.tvHumidityUnit)
            tvWind = itemView.findViewById(R.id.tvWind)
            tvWindUnit = itemView.findViewById(R.id.tvWindUnit)
            tvClouds = itemView.findViewById(R.id.tvClouds)
            tvCloudsUnit = itemView.findViewById(R.id.tvCloudsUnit)
        }

    }

}