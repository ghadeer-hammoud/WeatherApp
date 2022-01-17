package com.example.weatherapp.repositories

import android.app.Application
import com.example.weatherapp.models.City
import com.example.weatherapp.network.CitiesApi
import com.example.weatherapp.room.AppDatabase
import com.example.weatherapp.room.CityDao
import com.example.weatherapp.room.CityEntity
import com.example.weatherapp.utils.Constants
import javax.inject.Inject

class CityRepository @Inject constructor(
    private var citiesApi: CitiesApi,
    application: Application
) {

    private var localDB: CityDao

    init {
        this.localDB = AppDatabase.getInstance(application.applicationContext)?.cityDao()!!
    }


    suspend fun getCities(namePrefix: String) : ArrayList<City>{
        var cities = citiesApi.getCities(namePrefix, "name", 10, Constants.CITIES_API_KEY).data

        for(city: City in cities)
            if(localDB.isCityExists(city.name!!, city.country!!))
                city.isFavorite = 1
        return cities
    }


    suspend fun getAllFavoriteCities(): List<CityEntity>{
        return localDB.gelAllCities()
    }

    suspend fun addFavoriteCity(city: CityEntity){
        return localDB.insertCity(city)
    }

    suspend fun deleteFavoriteCity(city: CityEntity){
        return localDB.deleteCity(city.name, city.country)
    }

}