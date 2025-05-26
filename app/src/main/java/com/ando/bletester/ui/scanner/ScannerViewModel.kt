package com.ando.bletester.ui.scanner

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.le.ScanResult
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ando.bletester.App
import com.ando.bletester.ble.scanner.BleScanState
import com.ando.bletester.data.repository.scan.BleScannerRepository
import com.ando.bletester.ui.scanner.data.ScannerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Scanner
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val scanRepo: BleScannerRepository
) : ViewModel(){
    companion object{
        private const val TAG = App.TAG+"ScannerViewModel"
    }
    private val scope = CoroutineScope(Dispatchers.IO)
    val scanState = scanRepo.scanState

    private val _scanResults = mutableStateOf<List<ScannerItem>>(emptyList())
    val scanResults: State<List<ScannerItem>> = _scanResults

    init{
        Log.i(TAG,"$TAG Start!")
        scope.launch {
            scanState.collect{
                if(it == BleScanState.Scanned) _scanResults.value = getFake()
            }
        }
    }


    fun startScan(){
        scanRepo.startScan()
    }

    fun stopScan(){
        scanRepo.stopScan()
    }

    private fun getFake() : List<ScannerItem>{
        return listOf(
            ScannerItem("Galaxy Buds+", "00:11:22:33:44:01", -45),
            ScannerItem("Mi Band 6", "00:11:22:33:44:02", -60),
            ScannerItem("Pixel Watch", "00:11:22:33:44:03", -70),
            ScannerItem("Unknown Device", "00:11:22:33:44:04", -90),
            ScannerItem("AirPods Pro", "00:11:22:33:44:05", -50),
            ScannerItem("Sony WH-1000XM5", "00:11:22:33:44:06", -40),
            ScannerItem("Fitbit Charge", "00:11:22:33:44:07", -65),
            ScannerItem(null, "00:11:22:33:44:08", -72),
            ScannerItem("JBL Speaker", "00:11:22:33:44:09", -58),
            ScannerItem("Oura Ring", "00:11:22:33:44:10", -81),
            ScannerItem("Samsung SmartTag", "00:11:22:33:44:11", -67),
            ScannerItem("Garmin Watch", "00:11:22:33:44:12", -55),
            ScannerItem("Huawei Band", "00:11:22:33:44:13", -73),
            ScannerItem(null, "00:11:22:33:44:14", -66),
            ScannerItem("Nest Thermostat", "00:11:22:33:44:15", -59),
            ScannerItem("Logitech Keyboard", "00:11:22:33:44:16", -42),
            ScannerItem("Tile Tracker", "00:11:22:33:44:17", -79),
            ScannerItem("OnePlus Buds", "00:11:22:33:44:18", -48),
            ScannerItem("Bose QC45", "00:11:22:33:44:19", -51),
            ScannerItem("Nothing Ear", "00:11:22:33:44:20", -63)
        )
    }

    @SuppressLint("MissingPermission")
    private fun getScanResult() : List<ScannerItem>{
        val list = scanRepo.getScanResult().map{ ScannerItem(deviceName = it.device.name?:"x", address = it.device.address, rssi = it.rssi) }
        return list
    }
}