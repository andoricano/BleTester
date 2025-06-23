package com.ando.bletester.bluetooth.ble.scanner

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.util.Log
import com.ando.bletester.App
import com.ando.bletester.ui.scanner.data.ScannerItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
class ScannerManager(private val bleScanner: BluetoothLeScanner?) {
    companion object{
        private const val TAG = App.TAG+"ScannerManager"
    }
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _scanState = MutableStateFlow(BleScanState.Idle)
    val scanState : StateFlow<BleScanState>
        get() = _scanState

    private val scanResults = mutableListOf<ScanResult>()
    private var scanJob : Job? = null

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            if(result.device.name == "chois") {
                Log.i(TAG,"====================================")
                Log.i(TAG,"============onScanResult============")
                val bytes = result.scanRecord?.bytes
                val hexString = bytes?.joinToString(" ") { String.format("%02X", it) }
                Log.i(TAG, "ScanRecord bytes (hex): $hexString")
                Log.i(TAG,"============onScanResult============")
                Log.i(TAG,"====================================")

            }
            val indexQuery =
                scanResults.indexOfFirst { it.device.address == result.device.address }
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

    private fun startScan() {
        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()
        bleScanner?.startScan(null, settings, scanCallback)
    }

    fun startScan(scanDurationMs: Long = 5000L){
        if (scanState.value == BleScanState.Scanning) return

        scanResults.clear()
        _scanState.update { BleScanState.Scanning }

        startScan()

        scanJob?.cancel()
        scanJob = scope.launch {
            delay(scanDurationMs)
            stopScan()
            _scanState.update {  BleScanState.Scanned }
            Log.i(TAG,"scanned success $scanResults")
        }
    }
    fun stopScan(){
        bleScanner?.stopScan(scanCallback)
    }

    fun startTestScan(){
        _scanState.update {  BleScanState.Scanned }
    }

    fun getScannedDeviceInfo(scannerItem: ScannerItem): BluetoothDevice? {
        return scanResults.firstOrNull { it.device.address == scannerItem.address }?.device
    }

    fun getScanList() :  MutableList<ScanResult> = scanResults

    fun testGetList() : List<ScannerItem> = getFake()

    private fun getFake() : List<ScannerItem>{
        return listOf(
            ScannerItem(0,"Galaxy Buds+", "00:11:22:33:44:01", -45),
            ScannerItem(1,"Mi Band 6", "00:11:22:33:44:02", -60),
            ScannerItem(2,"Pixel Watch", "00:11:22:33:44:03", -70),
            ScannerItem(3,"Unknown Device", "00:11:22:33:44:04", -90),
            ScannerItem(4,"AirPods Pro", "00:11:22:33:44:05", -50),
            ScannerItem(5,"Sony WH-1000XM5", "00:11:22:33:44:06", -40),
            ScannerItem(6,"Fitbit Charge", "00:11:22:33:44:07", -65),
            ScannerItem(7,"", "00:11:22:33:44:08", -72),
            ScannerItem(8,"JBL Speaker", "00:11:22:33:44:09", -58),
            ScannerItem(9,"Oura Ring", "00:11:22:33:44:10", -81),
            ScannerItem(10,"Samsung SmartTag", "00:11:22:33:44:11", -67),
            ScannerItem(11,"Garmin Watch", "00:11:22:33:44:12", -55),
            ScannerItem(12,"Huawei Band", "00:11:22:33:44:13", -73),
            ScannerItem(13,"", "00:11:22:33:44:14", -66),
            ScannerItem(14,"Nest Thermostat", "00:11:22:33:44:15", -59),
            ScannerItem(15,"Logitech Keyboard", "00:11:22:33:44:16", -42),
            ScannerItem(16,"Tile Tracker", "00:11:22:33:44:17", -79),
            ScannerItem(17,"OnePlus Buds", "00:11:22:33:44:18", -48),
            ScannerItem(18,"Bose QC45", "00:11:22:33:44:19", -51),
            ScannerItem(19,"Nothing Ear", "00:11:22:33:44:20", -63)
        )
    }
}