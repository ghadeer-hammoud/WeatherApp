package com.example.weatherapp.room

import androidx.room.*

@Dao
interface CityDao {

    @Insert
    suspend fun insertCity(city: CityEntity)

    @Query("Select * from city")
    suspend fun gelAllCities(): List<CityEntity>

    @Update
    suspend fun updateCity(city: CityEntity)

    @Query("Delete from city where name=:name and country=:country")
    suspend fun deleteCity(name: String, country:String)

    @Query("Select exists(Select * from city where name=:name and country=:country)")
    suspend fun isCityExists(name: String, country:String): Boolean

}