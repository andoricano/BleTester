package com.ando.bletester.ui.advertising

import android.bluetooth.le.AdvertiseSettings
import android.util.Log
import androidx.lifecycle.ViewModel
import com.ando.bletester.App
import com.ando.bletester.App.Companion.prefs
import com.ando.bletester.bluetooth.ble.advertiser.BleAdvertisingState
import com.ando.bletester.data.repository.advertising.BleAdvertisingRepository
import com.ando.bletester.data.repository.scan.BleScannerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AdvertisingViewModel @Inject constructor(
    private val advertisingRepo: BleAdvertisingRepository
) : ViewModel() {
    companion object {
        private const val TAG = App.TAG + "AdvertisingViewModel"
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    val savedMode: Int
        get() = advertisingRepo.savedMode

    val savedTxPower: Int
        get() = advertisingRepo.savedTxPower

    val savedConnectable: Boolean
        get() = advertisingRepo.savedConnectable

    val includedName : Boolean
        get() = advertisingRepo.savedIncludedName

    val includedTxPower : Boolean
        get() = advertisingRepo.savedIncludedTxPower

    val includedManufacturerId : Int
        get() = advertisingRepo.savedIncludedManufacturerId

    val includedManufacturerData : String
        get() = advertisingRepo.savedIncludedManufacturerData

    fun setBtDeviceName(s : String){
        advertisingRepo.setBtDeviceName(s)
    }

    fun getBtDeviceName(): String{
        return advertisingRepo.getBtDeviceName()
    }

    fun startAdvertising(){
        advertisingRepo.startLegacyAdvertising()
    }

    fun stopAdvertising(){
        advertisingRepo.stopLegacyAdvertising()
    }

    fun configureLegacyAdvertisingSetting(
        mode: Int = AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY,
        txPower: Int = AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM,
        connectable: Boolean = false,

    ){
        advertisingRepo.savedMode = mode
        advertisingRepo.savedTxPower = txPower
        advertisingRepo.savedConnectable = connectable

        advertisingRepo.configureLegacyAdvertisingSetting(
            mode,txPower,connectable
        )
    }

    fun configureLegacyAdvertisingData(
        includedName: Boolean = true,
        includedTxPower: Boolean = false,
        manufacturerId: Int = 0,
        manufacturerData: String = "",
        addServiceUuid : UUID? = null,
        addServiceData : ByteArray? = null){

        advertisingRepo.savedIncludedName = includedName
        advertisingRepo.savedIncludedTxPower = includedTxPower
        advertisingRepo.savedIncludedManufacturerId = manufacturerId
        advertisingRepo.savedIncludedManufacturerData = manufacturerData

        advertisingRepo.configureLegacyAdvertisingData(
            includedName,
            includedTxPower,
            manufacturerId,
            manufacturerData.hexStringToByteArray(),
            addServiceUuid,
            addServiceData)
    }

    fun clearConfigData(){
        advertisingRepo.savedIncludedName = false
        advertisingRepo.savedIncludedTxPower = false
        advertisingRepo.savedIncludedManufacturerId = 0
        advertisingRepo.savedIncludedManufacturerData = ""

        advertisingRepo.configureLegacyAdvertisingData(
            includedName,
            includedTxPower,
            0,
            "".hexStringToByteArray(),
            null,
            null)
    }

    fun String.hexStringToByteArray(): ByteArray {
        val cleaned = this.uppercase().filter { it in '0'..'9' || it in 'A'..'F' }

        if (cleaned.length < 2) return byteArrayOf()

        val evenLengthStr = if (cleaned.length % 2 == 0) cleaned else cleaned.dropLast(1)

        return evenLengthStr.chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
    }
}