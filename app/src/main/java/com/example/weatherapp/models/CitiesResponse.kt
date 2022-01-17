package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName

class CitiesResponse {

    @SerializedName("data")
    var data: ArrayList<City> = ArrayList()
    @SerializedName("metadata")
    var metadata: MetaData? = null

    inner class MetaData {
        @SerializedName("currentOffset")
        var currentOffset: Int = 0
        @SerializedName("totalCount")
        var totalCount: Int = 0
    }
}