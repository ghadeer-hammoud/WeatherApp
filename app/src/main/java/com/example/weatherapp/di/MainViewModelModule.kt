package com.example.weatherapp.di

import androidx.lifecycle.ViewModel
import com.example.weatherapp.viewmodels.FavoriteCitiesViewModel
import com.example.weatherapp.viewmodels.WeatherViewModel
import com.example.weatherapp.viewmodels.SearchCitiesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindHomeViewModel(weatherViewModel: WeatherViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchCitiesViewModel::class)
    abstract fun bindSearchCitiesViewModel(searchCitiesViewModel: SearchCitiesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteCitiesViewModel::class)
    abstract fun bindFavoriteCitiesViewModel(favoriteCitiesViewModel: FavoriteCitiesViewModel): ViewModel
}