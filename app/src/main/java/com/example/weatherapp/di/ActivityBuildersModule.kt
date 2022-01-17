package com.example.weatherapp.di

import com.example.weatherapp.ui.FavoriteCitiesActivity
import com.example.weatherapp.ui.WeatherActivity
import com.example.weatherapp.ui.SearchCitiesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [MainModule::class, MainViewModelModule::class]
    )
    abstract fun contributeHomeActivity(): WeatherActivity

    @ContributesAndroidInjector(
        modules = [MainModule::class, MainViewModelModule::class]
    )
    abstract fun contributeSearchCitiesActivity(): SearchCitiesActivity

    @ContributesAndroidInjector(
        modules = [MainModule::class, MainViewModelModule::class]
    )
    abstract fun contributeFavoriteCitiesActivity(): FavoriteCitiesActivity

}