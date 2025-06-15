package com.ando.bletester.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.ando.bletester.App

class Prefs {
    companion object{
        private const val TAG = App.TAG + "Prefs"

    }
    private var prefs : SharedPreferences? = null

    var includedCheckBtDeviceName : String
        get() = getPrefString("includedCheckBtDeviceName")
        set(value) {
            setPrefString("includedCheckBtDeviceName",value)
        }

    fun init(context: Context) {
        prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }


    private fun setPrefString(key: String, value: String) {
        if (prefs == null) {
            Log.e(TAG, "setPrefString failed: prefs is null.")
            return
        }
        prefs!!.edit().putString(key, value).apply()
    }

    private fun getPrefString(key: String, defaultValue: String = ""): String {
        if (prefs == null) {
            Log.e(TAG, "getPrefString failed: prefs is null.")
            return defaultValue
        }
        return prefs!!.getString(key, defaultValue) ?: defaultValue
    }
}