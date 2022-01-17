package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName

class WeatherReport {

    @SerializedName("lat")
    var lat: Double = 0.0
    @SerializedName("lon")
    var lon: Double = 0.0
    @SerializedName("timezone")
    var timezone: String? = null
    @SerializedName("timezone_offset")
    var timezone_offset: Int = 0
    @SerializedName("current")
    var current: Current? = null
    @SerializedName("daily")
    var daily: ArrayList<Day> = ArrayList()

    class Current {
        @SerializedName("dt")
        var dt: Int = 0
        @SerializedName("sunrise")
        var sunrise: Int = 0
        @SerializedName("sunset")
        var sunset: Int = 0
        @SerializedName("temp")
        var temp: Double = 0.0
        @SerializedName("feels_like")
        var feels_like: Double = 0.0
        @SerializedName("pressure")
        var pressure: Int = 0
        @SerializedName("humidity")
        var humidity: Int = 0
        @SerializedName("dew_point")
        var dew_point: Double = 0.0
        @SerializedName("uvi")
        var uvi: Double = 0.0
        @SerializedName("clouds")
        var clouds: Int = 0
        @SerializedName("visibility")
        var visibility: Int = 0
        @SerializedName("wind_speed")
        var wind_speed: Double = 0.0
        @SerializedName("wind_deg")
        var wind_deg: Int = 0
        @SerializedName("weather")
        var weather: ArrayList<Weather> = ArrayList()
    }

    class Weather {
        @SerializedName("id")
        var id: Int = 0
        @SerializedName("main")
        var main: String? = null
        @SerializedName("description")
        var description: String? = null
        @SerializedName("icon")
        var icon: String? = null
    }

    class Day{
        @SerializedName("dt")
        var dt: Int = 0
        @SerializedName("sunrise")
        var sunrise: Int = 0
        @SerializedName("sunset")
        var sunset: Int = 0
        @SerializedName("moonrise")
        var moonrise: Int = 0
        @SerializedName("moonset")
        var moonset: Int = 0
        @SerializedName("moon_phase")
        var moon_phase: Double = 0.0
        @SerializedName("temp")
        var temp: Temp? = null
        @SerializedName("pressure")
        var pressure: Int = 0
        @SerializedName("humidity")
        var humidity: Int = 0
        @SerializedName("dew_point")
        var dew_point: Double = 0.0
        @SerializedName("wind_speed")
        var wind_speed: Double = 0.0
        @SerializedName("wind_deg")
        var wind_deg: Int = 0
        @SerializedName("wind_gust")
        var wind_gust: Double = 0.0
        @SerializedName("weather")
        var weather: ArrayList<Weather> = ArrayList()
        @SerializedName("clouds")
        var clouds: Int = 0
        @SerializedName("pop")
        var pop: Double = 0.0
        @SerializedName("uvi")
        var uvi: Double = 0.0
    }

    class Temp{
        @SerializedName("day")
        var day: Double = 0.0
        @SerializedName("min")
        var min: Double = 0.0
        @SerializedName("max")
        var max: Double = 0.0
        @SerializedName("night")
        var night: Double = 0.0
        @SerializedName("eve")
        var eve: Double = 0.0
        @SerializedName("morn")
        var morn: Double = 0.0
    }

}