package com.example.weatherapp

import com.example.weatherapp.di.DaggerAppComponent
import com.example.weatherapp.utils.AlarmUtils
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        AlarmUtils.setDailyNotifier(this)
    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}