package com.uncannyvalley.cookflow.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CookFlowApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}