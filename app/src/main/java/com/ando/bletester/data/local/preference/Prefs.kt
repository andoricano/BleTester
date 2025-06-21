package com.ando.bletester.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.ando.bletester.App
import androidx.core.content.edit

class Prefs {
    companion object{
        private const val TAG = App.TAG + "Prefs"

    }
    private var prefs : SharedPreferences? = null

    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////////BluetoothSetting///////////////////////////////
    var includedCheckBtDeviceName : String
        get() = getPrefString("includedCheckBtDeviceName")
        set(value) {
            setPrefString("includedCheckBtDeviceName",value)
        }
    /////////////////////////////BluetoothSetting///////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////////AdvertisingSetting/////////////////////////////
    var advertisingMode : Int
        get() = getPrefInt("advertisingMode",0)
        set(value) {
            setPrefInt("advertisingMode",value)
        }

    var txPower : Int
        get() = getPrefInt("advertising_txPower",0)
        set(value) {
            setPrefInt("advertising_txPower",value)
        }


    var connectable : Boolean
        get() = getPrefInt("advertising_connectable",1) == 1
        set(value) {
            setPrefInt("advertising_connectable",if(value) 1 else 0)
        }
    /////////////////////////////AdvertisingSetting/////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////////AdvertisingData////////////////////////////////
    var includedName : Boolean
        get() = getPrefInt("advertising_includedName",0) == 1
        set(value) {
            setPrefInt("advertising_includedName",if(value) 1 else 0)
        }

    var includedTxPower : Boolean
        get() = getPrefInt("advertising_includedTxPower",0) == 1
        set(value) {
            setPrefInt("advertising_includedTxPower",if(value) 1 else 0)
        }

    var includedManufacturerId : Int
        get() = getPrefInt("includedManufacturerId",0)
        set(value) {
            setPrefInt("includedManufacturerId",value)
        }

    var includedManufacturerData : String
        get() = getPrefString("advertisingManufacturerData")
        set(value) {
            setPrefString("advertisingManufacturerData",value)
        }
    /////////////////////////////AdvertisingData////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    fun init(context: Context) {
        prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }

    private fun setPrefInt(key: String, value: Int) {
        if (prefs == null) {
            Log.e(TAG, "setPrefString failed: prefs is null.")
            return
        }
        prefs!!.edit { putInt(key, value) }
    }

    private fun getPrefInt(key: String, defaultValue: Int = Int.MIN_VALUE): Int {
        if (prefs == null) {
            Log.e(TAG, "getPrefString failed: prefs is null.")
            return defaultValue
        }
        return prefs!!.getInt(key, defaultValue)
    }

    private fun setPrefString(key: String, value: String) {
        if (prefs == null) {
            Log.e(TAG, "setPrefString failed: prefs is null.")
            return
        }
        prefs!!.edit { putString(key, value) }
    }

    private fun getPrefString(key: String, defaultValue: String = ""): String {
        if (prefs == null) {
            Log.e(TAG, "getPrefString failed: prefs is null.")
            return defaultValue
        }
        return prefs!!.getString(key, defaultValue) ?: defaultValue
    }
}