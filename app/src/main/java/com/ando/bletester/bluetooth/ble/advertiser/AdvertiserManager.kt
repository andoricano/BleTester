package com.ando.bletester.bluetooth.ble.advertiser

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.AdvertisingSet
import android.bluetooth.le.AdvertisingSetCallback
import android.bluetooth.le.AdvertisingSetParameters
import android.bluetooth.le.BluetoothLeAdvertiser
import android.os.ParcelUuid
import android.util.Log
import com.ando.bletester.App
import com.ando.bletester.bluetooth.ble.advertiser.legacy.Advertiser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

@SuppressLint("MissingPermission")
class AdvertiserManager (private val btManager : BluetoothManager){
    companion object{
        private const val TAG = App.TAG+"AdvertiserManager"
    }

    private val legacyAdvertiser = Advertiser(btManager.adapter)
    private val advertiser: BluetoothLeAdvertiser? = btManager.adapter.bluetoothLeAdvertiser
    private var advertisingSet: AdvertisingSet? = null

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _advertisingState = MutableStateFlow(BleAdvertisingState.None)
    val advertisingState : StateFlow<BleAdvertisingState>
        get() = _advertisingState


    private val _advertisingErrorState = MutableStateFlow(BleAdvertisingError.NONE)
    val advertisingErrorState : StateFlow<BleAdvertisingError>
        get() = _advertisingErrorState

    private var advertisingParam: AdvertisingSetParameters? = null
    private var advertiseData: AdvertiseData? = null
    private var scanResponse: AdvertiseData? = null

    fun setAdvertisingParam(
        legacyMode: Boolean,
        connectable: Boolean,
        interval: Int,
        txPowerLevel: Int
    ) {
        advertisingParam = AdvertisingSetParameters.Builder()
            .setLegacyMode(legacyMode)
            .setConnectable(connectable)
            .setInterval(interval)
            .setTxPowerLevel(txPowerLevel)
            .build()
    }

    fun setAdvertiseData(
        includeDeviceName: Boolean,
        serviceUuid: UUID?,
        manufacturerId: Int?,
        manufacturerData: ByteArray?
    ) {
        val builder = AdvertiseData.Builder()
        if (includeDeviceName) builder.setIncludeDeviceName(true)
        serviceUuid?.let { builder.addServiceUuid(ParcelUuid(it)) }
        if (manufacturerId != null && manufacturerData != null) {
            builder.addManufacturerData(manufacturerId, manufacturerData)
        }
        advertiseData = builder.build()
    }

    fun setScanResponse(
        includeDeviceName: Boolean,
        serviceUuid: UUID?,
        manufacturerId: Int?,
        manufacturerData: ByteArray?
    ) {
        val builder = AdvertiseData.Builder()
        if (includeDeviceName) builder.setIncludeDeviceName(true)
        serviceUuid?.let { builder.addServiceUuid(ParcelUuid(it)) }
        if (manufacturerId != null && manufacturerData != null) {
            builder.addManufacturerData(manufacturerId, manufacturerData)
        }
        scanResponse = builder.build()
    }

    fun startAdvertising() : Boolean {
        if (advertiser == null) {
            Log.e(TAG, "BluetoothLeAdvertiser is null. Cannot start advertising.")
            return false
        }

        if(advertisingParam == null){
            Log.e(TAG, "advertisingParam is null. Cannot start advertising.")
            return false
        }

        if(advertiseData == null){
            Log.e(TAG, "advertiseData is null. Cannot start advertising.")
            return false
        }

        if(scanResponse == null){
            Log.e(TAG, "scanResponse is null. Cannot start advertising.")
            return false
        }

        advertiser.startAdvertisingSet(
            advertisingParam,
            advertiseData,
            scanResponse,
            null,
            null,
            advertisingSetCallback
        )
        return true
    }

    fun stopAdvertising(){
        advertiser?.stopAdvertisingSet(advertisingSetCallback)
    }


    private val advertisingSetCallback = object : AdvertisingSetCallback(){
        //광고 성공
        override fun onAdvertisingSetStarted(
            advertisingSet: AdvertisingSet?,
            txPower: Int,
            status: Int
        ) {
            super.onAdvertisingSetStarted(advertisingSet, txPower, status)
            this@AdvertiserManager.advertisingSet = advertisingSet

            Log.i(TAG,"start advertisingSet : $advertisingSet, txPower : $txPower, status : $status")
            when(BleAdvertisingError.fromStatus(status)){
                BleAdvertisingError.SUCCESS -> {
                    _advertisingState.update{BleAdvertisingState.Advertising}
                }
                BleAdvertisingError.ALREADY_STARTED -> {}
                BleAdvertisingError.TOO_MANY_ADVERTISERS -> {}
                BleAdvertisingError.INTERNAL_ERROR -> {}
                BleAdvertisingError.DATA_TOO_LARGE -> {}
                BleAdvertisingError.FEATURE_UNSUPPORTED -> {}
                BleAdvertisingError.UNKNOWN, BleAdvertisingError.NONE -> { Log.e(TAG, "report to samsung and google")}
            }
        }

        //광고 중지
        override fun onAdvertisingSetStopped(advertisingSet: AdvertisingSet?) {
            super.onAdvertisingSetStopped(advertisingSet)
            Log.i(TAG,"stop advertisingSet : $advertisingSet")
        }

        //광고 파라미터 변경
        override fun onAdvertisingParametersUpdated(
            advertisingSet: AdvertisingSet?,
            txPower: Int,
            status: Int
        ) {
            super.onAdvertisingParametersUpdated(advertisingSet, txPower, status)
            Log.i(TAG, "onAdvertisingParametersUpdated - advertisingSet: $advertisingSet, txPower: $txPower, status: $status")

        }
        override fun onAdvertisingDataSet(advertisingSet: AdvertisingSet?, status: Int) {
            super.onAdvertisingDataSet(advertisingSet, status)
            Log.i(TAG, "onAdvertisingDataSet - advertisingSet: $advertisingSet, status: $status")

        }

        override fun onAdvertisingEnabled(
            advertisingSet: AdvertisingSet?,
            enable: Boolean,
            status: Int
        ) {
            super.onAdvertisingEnabled(advertisingSet, enable, status)
            Log.i(TAG, "onAdvertisingEnabled - advertisingSet: $advertisingSet, enable: $enable, status: $status")

        }


        override fun onPeriodicAdvertisingDataSet(advertisingSet: AdvertisingSet?, status: Int) {
            super.onPeriodicAdvertisingDataSet(advertisingSet, status)
            Log.i(TAG, "onPeriodicAdvertisingDataSet - advertisingSet: $advertisingSet, status: $status")

        }

        override fun onPeriodicAdvertisingEnabled(
            advertisingSet: AdvertisingSet?,
            enable: Boolean,
            status: Int
        ) {
            super.onPeriodicAdvertisingEnabled(advertisingSet, enable, status)
            Log.i(TAG, "onPeriodicAdvertisingEnabled - advertisingSet: $advertisingSet, enable: $enable, status: $status")

        }

        override fun onPeriodicAdvertisingParametersUpdated(
            advertisingSet: AdvertisingSet?,
            status: Int
        ) {
            super.onPeriodicAdvertisingParametersUpdated(advertisingSet, status)
            Log.i(TAG, "onPeriodicAdvertisingParametersUpdated - advertisingSet: $advertisingSet, status: $status")

        }

        override fun onScanResponseDataSet(advertisingSet: AdvertisingSet?, status: Int) {
            super.onScanResponseDataSet(advertisingSet, status)
            Log.i(TAG, "onScanResponseDataSet - advertisingSet: $advertisingSet, status: $status")

        }
    }

    fun startLegacyAdvertising(){
        legacyAdvertiser.startAdvertising()
    }

    fun stopLegacyAdvertising(){
        legacyAdvertiser.stopAdvertising()
    }

    fun setBtDeviceName(name : String){
        legacyAdvertiser.setBtDeviceName(name)
    }

    fun getBtDeviceName() : String{
        return legacyAdvertiser.getBtDeviceName()
    }

    fun configureLegacyAdvertisingData(
        includeName: Boolean = true,
        includeTxPower: Boolean = false,
        manufacturerId: Int? = null,
        manufacturerData: ByteArray? = null,
        addServiceUuid : UUID? = null,
        addServiceData : ByteArray? = null
    ){
        legacyAdvertiser.configureData(
            includeName,
            includeTxPower,
            manufacturerId,
            manufacturerData,
            addServiceUuid,
            addServiceData
        )
    }

    fun configureLegacyAdvertisingSetting(
        mode: Int = AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY,
        txPower: Int = AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM,
        connectable: Boolean = false,
    ){
        legacyAdvertiser.configureSettings(
            mode,txPower,connectable
        )
    }

}