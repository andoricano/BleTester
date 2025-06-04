package com.ando.bletester.data.repository.scan

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import android.util.Log
import com.ando.bletester.bluetooth.ble.advertiser.AdvertiserManager
import com.ando.bletester.bluetooth.ble.scanner.BleGattClient
import com.ando.bletester.bluetooth.ble.scanner.BleScanState
import com.ando.bletester.bluetooth.ble.scanner.GattConnectionState
import com.ando.bletester.bluetooth.ble.scanner.ScannerManager
import com.ando.bletester.ui.scanner.data.ScannerItem
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("MissingPermission")
@Singleton
class BleScannerRepositoryImpl @Inject constructor(
    private val scannerManager: ScannerManager,
    private val bleGattClient: BleGattClient
) : BleScannerRepository{
    init{
        Log.i("BleScannerRepositoryImpl", "BleScannerRepositoryImpl : start!!")
    }
    override val scanState: StateFlow<BleScanState>
        get() = scannerManager.scanState

    override val gattState: StateFlow<GattConnectionState>
        get() = bleGattClient.gattState

    override fun startScan() {
        scannerManager.startScan(5000L)
    }

    override fun stopScan() {
        scannerManager.stopScan()
    }

    override fun getScanResult(): List<ScanResult> = bleGattClient.getScanResults()
    override fun connectBleByIndex(scannerItem: ScannerItem) {
        bleGattClient.connectBleByIndex(scannerItem)
    }

    override fun getFindScannedDeviceInfo(scannerItem: ScannerItem): BluetoothDevice? {
        return scannerManager.getScannedDeviceInfo(scannerItem)
    }

    override fun testStart() {
        scannerManager.startTestScan()
    }

    override fun testGetList(): List<ScannerItem> {
        return scannerManager.testGetList()
    }

    override fun testConnection(deviceName: String, connection: Boolean) {
        bleGattClient.testConnection(deviceName)
    }
}