package com.ando.bletester.data.repository.scan

import android.bluetooth.le.ScanResult
import com.ando.bletester.ble.scanner.BleScanState
import com.ando.bletester.ble.scanner.GattConnectionState
import kotlinx.coroutines.flow.StateFlow

interface BleScannerRepository {
    val scanState : StateFlow<BleScanState>
    val gattState : StateFlow<GattConnectionState>
    fun startScan()
    fun stopScan()
    fun getScanResult() : List<ScanResult>

    fun connectBleByIndex(idx : Int)
}