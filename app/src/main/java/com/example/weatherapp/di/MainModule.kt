package com.example.weatherapp.di

import android.app.Application
import android.content.SharedPreferences
import com.example.weatherapp.network.CitiesApi
import com.example.weatherapp.utils.LocationFetcher
import com.example.weatherapp.network.WeatherApi
import com.example.weatherapp.repositories.CityRepository
import com.example.weatherapp.repositories.WeatherRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class MainModule {

    @Provides
    fun provideLocationFetcher(): LocationFetcher {
        return LocationFetcher()
    }

    @Provides
    fun provideWeatherApi( @Named("Weather") retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    fun provideCitiesApi( @Named("Cities") retrofit: Retrofit): CitiesApi {
        return retrofit.create(CitiesApi::class.java)
    }

    @Provides
    fun provideWeatherRepository(weatherApi: WeatherApi, sharedPreferences: SharedPreferences): WeatherRepository {
        return WeatherRepository(weatherApi, sharedPreferences)
    }

    @Provides
    fun provideCityRepository(citiesApi: CitiesApi, application: Application): CityRepository {
        return CityRepository(citiesApi, application)
    }

}