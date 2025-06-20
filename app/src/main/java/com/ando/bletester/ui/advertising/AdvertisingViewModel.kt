package com.ando.bletester.ui.advertising

import android.bluetooth.le.AdvertiseSettings
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

    var savedMode: Int
        get() = advertisingRepo.savedMode
        set(value) {advertisingRepo.savedMode = value}
    var savedTxPower: Int
        get() = advertisingRepo.savedTxPower
        set(value) {advertisingRepo.savedTxPower = value}

    var savedConnectable: Boolean
        get() = advertisingRepo.savedConnectable
        set(value) {advertisingRepo.savedConnectable = value}

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
        includeName: Boolean = true,
        includeTxPower: Boolean = false,
        manufacturerId: Int? = null,
        manufacturerData: ByteArray? = null,
        addServiceUuid : UUID? = null,
        addServiceData : ByteArray? = null){
        advertisingRepo.configureLegacyAdvertisingData(
            includeName,
            includeTxPower,
            manufacturerId,
            manufacturerData,
            addServiceUuid,
            addServiceData)
    }


}