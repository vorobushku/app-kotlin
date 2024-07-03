package com.example.astrohelper

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseInitializer.initialize(this)
    }
}