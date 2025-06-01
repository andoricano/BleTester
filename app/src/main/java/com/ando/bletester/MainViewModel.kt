package com.ando.bletester

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import com.ando.bletester.data.repository.scan.BleScannerRepository
import com.ando.bletester.ui.scanner.data.ScannerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val scanRepo: BleScannerRepository
) : ViewModel(){
    var selectedDevice : BluetoothDevice? = null

    fun updateSelectedDevice(scannerItem: ScannerItem){
        selectedDevice = scanRepo.getFindScannedDeviceInfo(scannerItem)
    }
}