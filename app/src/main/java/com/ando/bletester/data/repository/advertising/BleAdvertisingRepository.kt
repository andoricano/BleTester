package com.ando.bletester.data.repository.advertising

import android.bluetooth.le.AdvertiseSettings
import com.ando.bletester.bluetooth.ble.advertiser.BleAdvertisingState
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

interface BleAdvertisingRepository {
    val advertisingState : StateFlow<BleAdvertisingState>
    fun setAdvertisingParam(legacyMode: Boolean, connectable: Boolean, interval: Int, txPowerLevel: Int)
    fun setAdvertiseData(includeDeviceName: Boolean, serviceUuid: UUID?, manufacturerId: Int?, manufacturerData: ByteArray?)
    fun setScanResponse(includeDeviceName: Boolean, serviceUuid: UUID?, manufacturerId: Int?, manufacturerData: ByteArray?)
    fun startAdvertising()
    fun stopAdvertising()

    fun startLegacyAdvertising()
    fun stopLegacyAdvertising()
    fun setBtDeviceName(name : String)
    fun getBtDeviceName(): String
    fun configureLegacyAdvertisingData(includeName: Boolean, includeTxPower: Boolean, manufacturerId: Int?, manufacturerData: ByteArray?, addServiceUuid : UUID?, addServiceData : ByteArray?)
    fun configureLegacyAdvertisingSetting(mode: Int, txPower: Int, connectable: Boolean, )
}