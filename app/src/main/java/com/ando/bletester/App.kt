package com.ando.bletester

import android.app.Application
import android.util.Log
import com.ando.bletester.data.local.preference.Prefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object{
        const val TAG = "BleApp"
        lateinit var prefs: Prefs
    }
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs().apply {
            init(applicationContext)
        }
        Log.i(TAG, "BleApp Start!")
    }
}