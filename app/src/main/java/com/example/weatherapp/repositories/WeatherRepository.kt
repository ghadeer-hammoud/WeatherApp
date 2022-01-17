package com.example.weatherapp.repositories

import android.content.SharedPreferences
import com.example.weatherapp.models.WeatherReport
import com.example.weatherapp.network.WeatherApi
import com.example.weatherapp.utils.Constants
import javax.inject.Inject

class WeatherRepository @Inject constructor(private var weatherApi: WeatherApi,
                                            private var sharedPreferences: SharedPreferences) {


    suspend fun getWeatherDataForLocation(lat: Double, lon: Double) : WeatherReport =
        weatherApi.getCurrentWeatherData(lat, lon, sharedPreferences.getString("temp_unit", "standard")!! ,Constants.WEATHER_API_KEY)

}