package com.example.weatherapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityEntity(@PrimaryKey(autoGenerate = true)var cityId: Int? = null,
                val name: String,
                var country: String,
                val latitude: Double,
                val longitude: Double,
)