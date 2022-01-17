package com.example.weatherapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Resource
import com.example.weatherapp.models.City
import com.example.weatherapp.repositories.CityRepository
import com.example.weatherapp.room.CityEntity
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchCitiesViewModel : ViewModel {

    private var cityRepository: CityRepository

    private var resourceCitiesListLiveData: MutableLiveData<Resource<ArrayList<City>>> = MutableLiveData<Resource<ArrayList<City>>>()
    private var messageLiveData: MutableLiveData<String> = MutableLiveData<String>()

    @Inject
    constructor(cityRepository: CityRepository) : super() {
        this.cityRepository = cityRepository

    }


    fun searchCities(namePrefix: String){
        viewModelScope.launch {
            try {
                val data = cityRepository.getCities(namePrefix)
                resourceCitiesListLiveData.value = Resource.success(data)
            } catch (exception: Exception) {
                resourceCitiesListLiveData.value = Resource.error("Error ${exception.message}", null)
            }
        }

    }

    fun addFavoriteCity(city: City){
        viewModelScope.launch {
            var cityEntity = CityEntity(null, city.name!!, city.country!!, city.latitude, city.longitude)
            cityRepository.addFavoriteCity(cityEntity)
            messageLiveData.value = "Added to Favorite List"
        }
    }

    fun removeFavoriteCity(city: City){
        viewModelScope.launch {
            var cityEntity = CityEntity(null, city.name!!, city.country!!, city.latitude, city.longitude)
            cityRepository.deleteFavoriteCity(cityEntity)
            messageLiveData.value = "Removed from Favorite List"
        }
    }

    fun observeCitiesList(): LiveData<Resource<ArrayList<City>>>{
        return resourceCitiesListLiveData
    }

    fun observeMessage(): LiveData<String>{
        return messageLiveData
    }

}