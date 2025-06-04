package com.ando.bletester.bluetooth.ble.advertiser

import android.annotation.SuppressLint
import android.content.Context
import com.ando.bletester.App
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("MissingPermission")
@Singleton
class BleGattServer @Inject constructor(
    @ApplicationContext private val context : Context
)  {
    companion object{
        private const val TAG = App.TAG+"BleGattServer"
    }
    private val scope = CoroutineScope(Dispatchers.IO)

}