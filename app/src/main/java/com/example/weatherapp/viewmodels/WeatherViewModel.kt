package com.example.weatherapp.viewmodels

import android.app.Application
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Resource
import com.example.weatherapp.utils.LocationFetcher
import com.example.weatherapp.models.WeatherReport
import com.example.weatherapp.repositories.WeatherRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


class WeatherViewModel @Inject constructor(
    private var locationFetcher: LocationFetcher,
    private var weatherRepository: WeatherRepository,
    private var application: Application
) : ViewModel(), LocationFetcher.LocationCallback {

    private var resourceWeatherReportLiveData: MutableLiveData<Resource<WeatherReport>> = MutableLiveData<Resource<WeatherReport>>()
    private var messageLiveData: MutableLiveData<String> = MutableLiveData<String>()

    private var locationToView: Location? = null
    private var currentLocation: Location? = null

    init {
        if(currentLocation == null)
            locationFetcher.getCurrentLocation(application.applicationContext, this)
        else
            searchWeatherDataForLocation(currentLocation!!.latitude, currentLocation!!.longitude)
    }

    fun searchWeatherDataForLocation(lat: Double, lon: Double){
        locationToView!!.latitude = lat
        locationToView!!.longitude = lon

        viewModelScope.launch {
            try {
                val data = weatherRepository.getWeatherDataForLocation(lat, lon)
                resourceWeatherReportLiveData.value = Resource.success(data)
            } catch (exception: Exception) {
                resourceWeatherReportLiveData.value = Resource.error("Error ${exception.message}", null)
            }
        }
    }

    override fun onLocationAvailable(location: Location) {
        currentLocation = location
        locationToView = currentLocation
        searchWeatherDataForLocation(locationToView!!.latitude, locationToView!!.longitude)
    }

    fun refresh(){
        searchWeatherDataForLocation(locationToView!!.latitude, locationToView!!.longitude)
    }

    fun observeWeatherReport(): LiveData<Resource<WeatherReport>>{
        resourceWeatherReportLiveData.value = Resource.loading(null)
        return resourceWeatherReportLiveData
    }

    fun observeMessage(): LiveData<String>{
        return messageLiveData
    }
}