package com.ando.bletester.bluetooth.ble.scanner

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanResult
import android.content.Context
import android.util.Log
import com.ando.bletester.App
import com.ando.bletester.ui.scanner.data.ScannerItem
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("MissingPermission")
@Singleton
class BleGattClient @Inject constructor(
    @ApplicationContext private val context : Context
) {
    companion object{
        private const val TAG = App.TAG+"BleGattClient"
    }
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _gattState = MutableStateFlow(GattConnectionState.DISCONNECTED)
    val gattState : StateFlow<GattConnectionState>
        get() = _gattState

    private val scanResults = mutableListOf<ScanResult>()
    private val characteristicList = mutableListOf<BluetoothGattCharacteristic>()

    init{
        Log.i(TAG, "$TAG is Start!")
    }

    fun testConnection(deviceName : String, success : Boolean = true){
        _gattState.update { if(success)GattConnectionState.CONNECTED else GattConnectionState.FAILED }
    }


    fun connectBleByIndex(scannerItem: ScannerItem) {
        val idx = scannerItem.idx
        if (scanResults.size <= idx) {
            Log.e(TAG, "not in scanResults")
            return
        }
        if(scanResults[idx].device.name != scannerItem.deviceName){
            return
        }

        val device = scanResults[idx].device
        device.connectGatt(context, false, gattCallback)
    }

    fun getScanResults(): List<ScanResult> = scanResults.toList()

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    _gattState.update { GattConnectionState.CONNECTING }
                    gatt?.discoverServices()
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    _gattState.update { GattConnectionState.DISCONNECTED }
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            if (status == BluetoothGatt.GATT_SUCCESS && gatt != null) {
                for (service in gatt.services) {
                    for (characteristic in service.characteristics) {
                        characteristicList.add(characteristic)
                    }
                }
                _gattState.update { GattConnectionState.CONNECTED }

            } else {
                _gattState.update { GattConnectionState.FAILED }
                Log.e(TAG, "서비스 검색 실패: $status")
            }
        }
    }
}