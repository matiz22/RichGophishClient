package com.matiz22.richgophishclient

import android.app.Application
import root.di.initKoin

class RichGophishClientApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}