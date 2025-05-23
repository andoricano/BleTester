package com.ando.bletester.ui.scanner

import android.bluetooth.le.ScanResult
import android.util.Log
import androidx.lifecycle.ViewModel
import com.ando.bletester.App
import com.ando.bletester.data.repository.scan.BleScannerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val scanRepo: BleScannerRepository
) : ViewModel(){
    companion object{
        private const val TAG = App.TAG+"ScannerViewModel"
    }

    init{
        Log.i(TAG,"$TAG Start!")
    }

    val scanState = scanRepo.scanState

    fun startScan(){
        scanRepo.startScan()
    }

    fun stopScan(){
        scanRepo.stopScan()
    }

    fun getScanResult() : List<ScanResult>{
        return scanRepo.getScanResult()
    }
}