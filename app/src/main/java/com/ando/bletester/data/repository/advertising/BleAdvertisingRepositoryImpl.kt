package com.ando.bletester.data.repository.advertising

import com.ando.bletester.App.Companion.prefs
import com.ando.bletester.bluetooth.ble.advertiser.AdvertiserManager
import com.ando.bletester.bluetooth.ble.advertiser.BleAdvertisingState
import com.ando.bletester.bluetooth.ble.advertiser.BleGattServer
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import javax.inject.Inject

class BleAdvertisingRepositoryImpl@Inject constructor(
    private val advertiserManager : AdvertiserManager,
    private val bleGattServer: BleGattServer
) : BleAdvertisingRepository {
    override var savedMode: Int
        get() = prefs.advertisingMode
        set(value) { prefs.advertisingMode = value }

    override var savedTxPower: Int
        get() = prefs.txPower
        set(value) { prefs.txPower = value}

    override var savedConnectable: Boolean
        get() = prefs.connectable
        set(value) { prefs.connectable = value}

    override var savedIncludedName: Boolean
        get() = prefs.includedName
        set(value) { prefs.includedName = value}

    override var savedIncludedTxPower: Boolean
        get() = prefs.includedTxPower
        set(value) { prefs.includedTxPower = value}

    override var savedIncludedManufacturerId: Int
        get() = prefs.includedManufacturerId
        set(value) { prefs.includedManufacturerId = value}

    override var savedIncludedManufacturerData: String
        get() = prefs.includedManufacturerData
        set(value) { prefs.includedManufacturerData = value }

    override val advertisingState: StateFlow<BleAdvertisingState>
        get() = advertiserManager.advertisingState

    override fun setAdvertisingParam(
        legacyMode: Boolean,
        connectable: Boolean,
        interval: Int,
        txPowerLevel: Int
    ) {
        advertiserManager.setAdvertisingParam(
            legacyMode,
            connectable,
            interval,
            txPowerLevel
        )
    }

    override fun setAdvertiseData(
        includeDeviceName: Boolean,
        serviceUuid: UUID?,
        manufacturerId: Int?,
        manufacturerData: ByteArray?
    ) {
        advertiserManager.setAdvertiseData(
            includeDeviceName,
            serviceUuid,
            manufacturerId,
            manufacturerData
        )
    }

    override fun setScanResponse(
        includeDeviceName: Boolean,
        serviceUuid: UUID?,
        manufacturerId: Int?,
        manufacturerData: ByteArray?
    ) {
        advertiserManager.setScanResponse(
            includeDeviceName,
            serviceUuid,
            manufacturerId,
            manufacturerData
        )
    }

    override fun initLegacyAdvertiser() {
        advertiserManager.initLegacyAdvertiser()
    }

    override fun startAdvertising() {
        advertiserManager.startAdvertising()
    }

    override fun stopAdvertising() {
        advertiserManager.stopAdvertising()
    }

    override fun startLegacyAdvertising() {
        advertiserManager.startLegacyAdvertising()
    }

    override fun stopLegacyAdvertising() {
        advertiserManager.stopLegacyAdvertising()
    }

    override fun getBtDeviceName(): String {
        return advertiserManager.getBtDeviceName()
    }

    override fun setBtDeviceName(name: String) {
        advertiserManager.setBtDeviceName(name)
    }

    override fun configureLegacyAdvertisingData(
        includeName: Boolean,
        includeTxPower: Boolean,
        manufacturerId: Int?,
        manufacturerData: ByteArray?,
        addServiceUuid: UUID?,
        addServiceData: ByteArray?
    ) {
        advertiserManager.configureLegacyAdvertisingData(
            includeName,
            includeTxPower,
            manufacturerId,
            manufacturerData,
            addServiceUuid,
            addServiceData
        )
    }

    override fun configureLegacyAdvertisingSetting(
        mode: Int,
        txPower: Int,
        connectable: Boolean
    ) {
        advertiserManager.configureLegacyAdvertisingSetting(
            mode,txPower,connectable
        )
    }
}