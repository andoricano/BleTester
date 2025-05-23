package com.ando.bletester.ble.scanner

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.ando.bletester.App
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleGattClient @Inject constructor(
    @ApplicationContext private val context : Context
) {
    companion object{
        private const val TAG = App.TAG+"BleGattClient"
    }
    private val scope = CoroutineScope(Dispatchers.IO)
    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private val _scanState = MutableStateFlow(BleScanState.Idle)
    val scanState : StateFlow<BleScanState>
        get() = _scanState

    private val scanResults = mutableListOf<ScanResult>()

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val indexQuery = scanResults.indexOfFirst { it.device.address == result.device.address }
            if (indexQuery != -1) {
                scanResults[indexQuery] = result
            } else {
                scanResults.add(result)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            _scanState.update { BleScanState.ScanFailed }
        }
    }

    private var scanJob : Job? = null

    init{
        Log.i(TAG, "$TAG is Start!")
    }

    @RequiresPermission(anyOf = [
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.BLUETOOTH_SCAN
    ])
    fun startScanWithTimeout(scanDurationMs: Long = 5000L) {
        if (scanState.value == BleScanState.Scanning) return

        scanResults.clear()
        _scanState.update { BleScanState.Scanning }

        startScan()

        // 5초 후에 자동 정지 & 상태 변경
        scanJob?.cancel()
        scanJob = scope.launch {
            delay(scanDurationMs)
            stopScan()
            _scanState.update {  BleScanState.Scanned }
            Log.i(TAG,"scanned success ${scanResults[0]}")
        }
    }


    private fun hasPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        }
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_SCAN])
    fun startScan() {
        if (!hasPermissions()) throw SecurityException("Missing required permissions")
        scanResults.clear()
        bluetoothAdapter?.bluetoothLeScanner?.startScan(scanCallback)
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun stopScan() {
        if (!hasPermissions()) throw SecurityException("…")
        bluetoothAdapter?.bluetoothLeScanner?.stopScan(scanCallback)
    }

    fun getScanResults(): List<ScanResult> = scanResults.toList()
}