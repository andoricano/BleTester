package com.ando.bletester.data.repository.scan

import android.annotation.SuppressLint
import android.bluetooth.le.ScanResult
import android.util.Log
import com.ando.bletester.ble.scanner.BleGattClient
import com.ando.bletester.ble.scanner.BleScanState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("MissingPermission")
@Singleton
class BleScannerRepositoryImpl @Inject constructor(
    private val bleGattClient: BleGattClient
) : BleScannerRepository{
    init{
        Log.i("BleScannerRepositoryImpl", "BleScannerRepositoryImpl : start!!")
    }
    override val scanState: StateFlow<BleScanState>
        get() = bleGattClient.scanState

    override fun startScan() {
        bleGattClient.startScanWithTimeout()
    }

    override fun stopScan() {
        bleGattClient.stopScan()
    }

    override fun getScanResult(): List<ScanResult> = bleGattClient.getScanResults()

}