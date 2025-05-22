package com.ando.bletester

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object{
        const val TAG = "BleApp"
    }
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "BleApp Start!")
    }
}