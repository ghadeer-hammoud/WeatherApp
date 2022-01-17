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


class FavoriteCitiesViewModel : ViewModel {

    private var cityRepository: CityRepository

    private var resourceCitiesListLiveData: MutableLiveData<Resource<ArrayList<City>>> = MutableLiveData<Resource<ArrayList<City>>>()
    private var messageLiveData: MutableLiveData<String> = MutableLiveData<String>()

    @Inject
    constructor(cityRepository: CityRepository) : super() {
        this.cityRepository = cityRepository

    }

    fun getFavoriteCities(){
        viewModelScope.launch {
            try {
                var entities = cityRepository.getAllFavoriteCities()
                var cities: ArrayList<City> = ArrayList()
                for(entity:CityEntity in entities){
                    var city = City()
                    city.name = entity.name
                    city.country = entity.country
                    city.latitude = entity.latitude
                    city.longitude = entity.longitude
                    city.isFavorite = 1
                    cities.add(city)
                }

                resourceCitiesListLiveData.value = Resource.success(cities)

            } catch (exception: Exception) {
                resourceCitiesListLiveData.value = Resource.error("Error ${exception.message}", null)
            }
        }

    }

    fun removeFavoriteCity(city: City){
        viewModelScope.launch {
            var cityEntity = CityEntity(null, city.name!!, city.country!!, city.latitude, city.longitude)
            cityRepository.deleteFavoriteCity(cityEntity)
            resourceCitiesListLiveData.value?.data?.remove(city)
            messageLiveData.value = "Removed from Favorite List"
        }
    }

    fun observeFavoriteCitiesList(): LiveData<Resource<ArrayList<City>>>{
        getFavoriteCities()
        return resourceCitiesListLiveData
    }

    fun observeMessage(): LiveData<String>{
        return messageLiveData
    }
}