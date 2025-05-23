package com.ando.bletester.data.repository.scan

import android.bluetooth.le.ScanResult
import com.ando.bletester.ble.scanner.BleScanState
import kotlinx.coroutines.flow.StateFlow

interface BleScannerRepository {
    val scanState : StateFlow<BleScanState>
    fun startScan()
    fun stopScan()
    fun getScanResult() : List<ScanResult>
}