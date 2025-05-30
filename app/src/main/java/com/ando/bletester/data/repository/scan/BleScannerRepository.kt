package com.ando.bletester.data.repository.scan

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import com.ando.bletester.ble.scanner.BleScanState
import com.ando.bletester.ble.scanner.GattConnectionState
import com.ando.bletester.ui.scanner.data.ScannerItem
import kotlinx.coroutines.flow.StateFlow

interface BleScannerRepository {
    val scanState : StateFlow<BleScanState>
    val gattState : StateFlow<GattConnectionState>
    fun startScan()
    fun stopScan()
    fun getScanResult() : List<ScanResult>

    fun connectBleByIndex(scannerItem: ScannerItem)


    fun testStart()
    fun testGetList() : List<ScannerItem>

    fun testConnection(deviceName : String, connection : Boolean)
}