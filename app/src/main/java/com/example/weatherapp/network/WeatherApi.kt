package com.example.weatherapp.network

import com.example.weatherapp.models.WeatherReport
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("onecall?")
    suspend fun getCurrentWeatherData(@Query("lat") lat: Double,
                                      @Query("lon") lon: Double,
                                      @Query("units") units: String,
                                      @Query("appid") app_id: String): WeatherReport
}