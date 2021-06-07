package com.covid.covidapps

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppCore : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}