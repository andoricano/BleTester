package com.ando.bletester.bluetooth.ble.advertiser.legacy

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.os.ParcelUuid
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.UUID


@SuppressLint("MissingPermission")
class Advertiser(private val bluetoothAdapter : BluetoothAdapter) {
    companion object{
        private const val TAG = "Choi_Advertiser"
    }
    private var advertisingPacket = ""

    private val bluetoothLeAdvertiser = bluetoothAdapter.bluetoothLeAdvertiser

    private var advertiseSettings = AdvertiseSettings.Builder()
        .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
        .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
        .setConnectable(false)
        .setTimeout(300)
        .build()

    private var advertiseData = AdvertiseData.Builder()
        .setIncludeDeviceName(true)
        .setIncludeTxPowerLevel(true)
        .addServiceUuid(ParcelUuid(UUID.fromString("0000180D-0000-1000-8000-00805F9B34FB")))
        .addServiceData(
            ParcelUuid(UUID.fromString("0000180D-0000-1000-8000-00805F9B34FB")),
            byteArrayOf(0x01, 0x02, 0x03)
        )
        .addManufacturerData(
            0x004C,
            byteArrayOf(0x02, 0x15, 0x00, 0x01, 0x02, 0x03)
        )
        .build()

    fun getBtDeviceName() : String = bluetoothAdapter.name

    fun setBtDeviceName(s : String){
        Log.i(TAG, "setBtDeviceName : $s")
        bluetoothAdapter.setName(s)
    }

    fun configureSettings(
        mode: Int = AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY,
        txPower: Int = AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM,
        connectable: Boolean = false,
    ) {
        advertiseSettings = AdvertiseSettings.Builder()
            .setAdvertiseMode(mode)
            .setTxPowerLevel(txPower)
            .setConnectable(connectable)
            .build()
    }


    fun startAdvertising() {
        if (advertiseSettings == null || advertiseData == null) {
            Log.e(TAG, "Advertiser not configured")
            return
        }

        bluetoothLeAdvertiser.startAdvertising(advertiseSettings, advertiseData, advertiseCallback)
    }

    fun stopAdvertising() {
        advertiseCallback.let {
            bluetoothLeAdvertiser.stopAdvertising(it)
            Log.d(TAG, "Advertising stopped")
        }
    }


    private val advertiseCallback = object : AdvertiseCallback() {
        override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
            Log.d(TAG, "Advertising started successfully")
        }

        override fun onStartFailure(errorCode: Int) {
            Log.e(TAG, "Advertising failed: $errorCode")
        }
    }

    fun configureData(
        includeName: Boolean = true,
        includeTxPower: Boolean = false,
        manufacturerId: Int? = null,
        manufacturerData: ByteArray? = null,
        addServiceUuid : UUID? = null,
        addServiceData : ByteArray? = null
    ) {
        val builder = AdvertiseData.Builder()
            .setIncludeDeviceName(includeName)
            .setIncludeTxPowerLevel(includeTxPower)

        if (manufacturerId != null && manufacturerData != null) {
            builder.addManufacturerData(manufacturerId, manufacturerData)
        }

        if (addServiceUuid != null) {
            builder.addServiceUuid(ParcelUuid(addServiceUuid))
        }

        if (addServiceUuid != null && addServiceData != null) {
            builder.addServiceData(ParcelUuid(addServiceUuid), addServiceData)
        }

        Log.i(TAG, buildAdvertisingPacketHexString(
            includeName,
            includeTxPower,
            manufacturerId,
            manufacturerData
        ))

        advertiseData = builder.build()
    }
    fun buildAdvertisingPacketHexString(
        includeName: Boolean,
        includeTxPower: Boolean,
        manufacturerId: Int?,
        manufacturerData: ByteArray?,
        addServiceUuid: UUID? = null,
        addServiceData: ByteArray? = null
    ): String {
        val packet = mutableListOf<Byte>()

        if (includeName) {
            Log.i(TAG, "includeName")
            val name = bluetoothAdapter.name
            val nameBytes = name.toByteArray(Charsets.UTF_8)
            packet += (nameBytes.size + 1).toByte()
            packet += 0x09.toByte() // AD Type: Complete Local Name
            packet += nameBytes.toList()
        }

        if (includeTxPower) {
            Log.i(TAG, "includeTxPower")
            packet += 2.toByte()
            packet += 0x0A.toByte() // AD Type: Tx Power Level
            packet += 0x07.toByte() // 예: +7dBm (하드코딩)
        }

        if (manufacturerId != null && manufacturerData != null) {
            Log.i(TAG, "includeManufacturer")
            val length = 2 + manufacturerData.size // 2 bytes for manufacturer ID
            packet += (length + 1).toByte()
            packet += 0xFF.toByte() // Manufacturer specific data type
            packet += (manufacturerId and 0xFF).toByte()
            packet += ((manufacturerId shr 8) and 0xFF).toByte()
            packet += manufacturerData.toList()
        }

        if (addServiceUuid != null) {
            Log.i(TAG, "includeAddServiceUuid")
            // 128-bit Service UUID in little endian
            val uuidBytes = ByteBuffer.allocate(16)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putLong(addServiceUuid.leastSignificantBits)
                .putLong(addServiceUuid.mostSignificantBits)
                .array()

            packet += (uuidBytes.size + 1).toByte()
            packet += 0x06.toByte() // AD Type: Incomplete List of 128-bit Service UUIDs
            packet += uuidBytes.toList()
        }

        if (addServiceUuid != null && addServiceData != null) {
            Log.i(TAG, "includeAddServiceData")
            val uuidBytes = ByteBuffer.allocate(16)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putLong(addServiceUuid.leastSignificantBits)
                .putLong(addServiceUuid.mostSignificantBits)
                .array()

            val length = 1 + uuidBytes.size + addServiceData.size
            packet += length.toByte()
            packet += 0x21.toByte() // AD Type: Service Data - 128-bit UUID
            packet += uuidBytes.toList()
            packet += addServiceData.toList()
        }

        Log.i(TAG, "packet size : ${packet.size}")
        return packet.joinToString(" ") { "%02X".format(it) }
    }
}