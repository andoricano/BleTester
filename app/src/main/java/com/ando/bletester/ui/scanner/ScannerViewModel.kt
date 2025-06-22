package com.ando.bletester.ui.scanner

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ando.bletester.App
import com.ando.bletester.bluetooth.ble.scanner.BleScanState
import com.ando.bletester.data.repository.scan.BleScannerRepository
import com.ando.bletester.ui.scanner.data.ScannerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
                if(it == BleScanState.Scanned) _scanResults.value = getScanResult()
            }
        }
    }


    fun startScan(){
        scanRepo.startScan()
    }

    fun stopScan(){
        scanRepo.stopScan()
    }

    fun connectBle(scannerItem: ScannerItem){
        scanRepo.connectBleByIndex(scannerItem)
    }

    @SuppressLint("MissingPermission")
    private fun getScanResult() : List<ScannerItem>{
        val list = scanRepo.getScanResult().mapIndexed{idx, device ->
            ScannerItem(
                idx = idx,
                deviceName = device.device.name?:"x",
                address = device.device.address,
                rssi = device.rssi)
        }
        return list
    }
}