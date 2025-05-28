package com.ando.bletester.ble.scanner

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.ando.bletester.App
import com.ando.bletester.ui.scanner.data.ScannerItem
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

@SuppressLint("MissingPermission")
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

    private val _gattState = MutableStateFlow(GattConnectionState.DISCONNECTED)
    val gattState : StateFlow<GattConnectionState>
        get() = _gattState

    private val scanResults = mutableListOf<ScanResult>()
    private val characteristicList = mutableListOf<BluetoothGattCharacteristic>()

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
            Log.i(TAG,"scanned success $scanResults")
        }
    }

    fun testStart(){
        _scanState.update { BleScanState.Scanned }
    }

    fun testConnection(idx : Int, success : Boolean = true){
        if (testGetList().size <= idx) {
            Log.e(TAG, "$idx not in scanResults")
            return
        }

        _gattState.update { if(success)GattConnectionState.CONNECTED else GattConnectionState.FAILED }

    }

    fun testGetList() : List<ScannerItem> = getFake()

    fun connectBleByIndex(scannerItem: ScannerItem) {
        val idx = scannerItem.idx
        if (scanResults.size <= idx) {
            Log.e(TAG, "not in scanResults")
            return
        }
        if(scanResults[idx].device.name != scannerItem.deviceName){
            return
        }

        val device = scanResults[idx].device
        device.connectGatt(context, false, gattCallback)
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

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    _gattState.update { GattConnectionState.CONNECTING }
                    gatt?.discoverServices()
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    _gattState.update { GattConnectionState.DISCONNECTED }
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            if (status == BluetoothGatt.GATT_SUCCESS && gatt != null) {
                for (service in gatt.services) {
                    for (characteristic in service.characteristics) {
                        characteristicList.add(characteristic)
                    }
                }
                _gattState.update { GattConnectionState.CONNECTED }

            } else {
                _gattState.update { GattConnectionState.FAILED }
                Log.e(TAG, "서비스 검색 실패: $status")
            }
        }
    }



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